package com.inturn.inventoryservice.infra.redis;

import com.inturn.inventoryservice.domain.inventory.define.InventoryErrorCode;
import com.inturn.inventoryservice.domain.inventory.entity.InventoryEntity;
import com.inturn.inventoryservice.domain.inventory.service.InventoryQueryService;
import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderRecord;
import com.inturn.inventoryservice.global.common.dto.response.CommonResponseDTO;
import com.inturn.inventoryservice.global.utils.KeyUtils;
import com.inturn.inventoryservice.infra.kafka.producer.OrderProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisInventoryManager {

	private final RedisTemplate<String, Object> redisTemplate;

	private final InventoryQueryService inventoryQueryService;

	private final RedissonClientManager redissonClientManager;

	private final OrderProducer orderProducer;

	/**
	 * 재고가 redis에 존재하는지 확인. tt
	 * @param itemKeyList
	 * @return
	 */
	public List<String> missingRedisInventory(List<String> itemKeyList) {

		String script = """
				local missing = {}
				    
				for i, key in ipairs(KEYS) do
				    if redis.call("EXISTS", key) == 0 then
				        table.insert(missing, key)
				    end
				end
				    
				return missing
				""";

		RedisScript<List> redisScript = RedisScript.of(script, List.class);

		List<Object> rawResult = redisTemplate.execute(redisScript, itemKeyList);

		List<String> result = rawResult.stream()
				.map(Object::toString)
				.collect(Collectors.toList());

		return result;
	}

	public InventoryErrorCode setRedisMissingInventory(List<String> missingItemList) {

		List<InventoryEntity> inventoryEntityList = new ArrayList<>();

		for(String missingItem : missingItemList) {
			final RLock lock = redissonClientManager.getLock(missingItem);

			//waitTime - lock 요청을 기다리는 시간
			//leaseTime - lock이 풀리는 시간
			try {
				lock.tryLock(3, 2, TimeUnit.SECONDS);
				InventoryEntity inventory = inventoryQueryService.getInventoryByItemId(KeyUtils.getItemIdByRedisInventoryKey(missingItem));
				//재고가 존재하지 않을 경우는 바로 return
				if(ObjectUtils.isEmpty(inventory)) {
					return InventoryErrorCode.ITEM_NOT_FOUND_EXCEPTION;
				}

				inventoryEntityList.add(inventory);
			} catch (InterruptedException e) {
				log.error("setRedisMissingInventory try lock {}", e.toString());
				throw new RuntimeException(e);
			} finally {
				lock.unlock();
			}
		}

		String script = """
				for i, key in ipairs(KEYS) do
				    redis.call("SET", key, ARGV[i])
				end
				    
				return "OK"
				""";

		List<String> keys = inventoryEntityList.stream().map(o -> KeyUtils.generateRedisInventoryKey(o.getItemId())).toList();
		List<String> vals = inventoryEntityList.stream().map(o -> String.valueOf(o.getStockQty())).toList();

		RedisScript<List> redisScript = RedisScript.of(script, List.class);
		redisTemplate.execute(redisScript, keys, vals.toArray());

		return null;
	}

	public CommonResponseDTO checkInventoryWithDeduct(CreateOrderRecord req) {

		List<String> keys = req.itemList().stream().map(o -> KeyUtils.generateRedisInventoryKey(o.itemId())).toList();
		List<String> missingItemList = missingRedisInventory(keys);

		//redis에 재고가 존재하지 않는 제품이 있으면 set 처리
		if(!missingItemList.isEmpty()) {
			 InventoryErrorCode errorCode = setRedisMissingInventory(missingItemList);

			 if(ObjectUtils.isNotEmpty(errorCode)) {
				 return CommonResponseDTO.fail(errorCode.getErrorMessage());
			 }
		}

		List<String> vals = req.itemList().stream().map(o -> String.valueOf(o.orderQty())).toList();

		String script = """
                local result = {}
				for i, key in ipairs(KEYS) do
				    local stock = redis.call("GET", key)
				    local required = tonumber(ARGV[i])
				    
				    if not stock or tonumber(stock) < required then
				        table.insert(result, key)
				    end
				end
				
				-- result가 있다는 것은 재고가 없거나 불충분한 재고가 존재하기 때문.
				if #result > 0 then
					return result;
				end
				
				-- 모든 검증 통과 후 → 재고 차감 수행
				for i, key in ipairs(KEYS) do
				    local required = tonumber(ARGV[i])
				    redis.call("DECRBY", key, required)
				end
				
				return result
				""";

		RedisScript<List> redisScript = RedisScript.of(script, List.class);
		List<Object> rawResult = redisTemplate.execute(redisScript, keys, vals.toArray());

		//TODO - key값을 변경하여 재고 아이디로 error 표시.
		List<String> result = rawResult.stream()
				.map(Object::toString)
				.collect(Collectors.toList());

		if(CollectionUtils.isEmpty(result)) {
			orderProducer.sendOrder(req);
			return CommonResponseDTO.ok();
		}
		else {
			return CommonResponseDTO.fail(InventoryErrorCode.INVENTORY_OUT_OF_STOCK.getErrorMessage());
		}
	}


}
