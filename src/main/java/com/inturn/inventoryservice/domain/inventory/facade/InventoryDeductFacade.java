package com.inturn.inventoryservice.domain.inventory.facade;

import com.inturn.inventoryservice.domain.inventory.entity.InventoryEntity;
import com.inturn.inventoryservice.domain.inventory.service.InventoryCommandService;
import com.inturn.inventoryservice.domain.inventory.service.InventoryQueryService;
import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderItemRecord;
import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderRecord;
import com.inturn.inventoryservice.global.common.exception.NotFoundException;
import com.inturn.inventoryservice.global.common.exception.ValidateException;
import com.inturn.inventoryservice.global.utils.KeyUtils;
import com.inturn.inventoryservice.infra.redis.RedissonClientManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RLock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class InventoryDeductFacade {

    private final InventoryQueryService inventoryQueryService;

    private final InventoryCommandService inventoryCommandService;

    private final RedissonClientManager redissonClientManager;

    @Transactional
    public void deductInventory(CreateOrderRecord order) {
        processDeduct(order.itemList());
    }

    private void processDeduct(List<CreateOrderItemRecord> orderItemList) {

        for (CreateOrderItemRecord createOrderItemRecord : orderItemList) {

            final RLock lock = redissonClientManager.getLock(KeyUtils.generateRedisLockKey(createOrderItemRecord.itemId()));

            //waitTime - lock 요청을 기다리는 시간
            //leaseTime - lock이 풀리는 시간
            try {
                lock.tryLock(10, 5, TimeUnit.SECONDS);
                InventoryEntity inventory = inventoryQueryService.getInventoryByItemId(createOrderItemRecord.itemId());
                //재고가 존재하지 않을 경우는 throw
                if(ObjectUtils.isEmpty(inventory)) {
                    throw new NotFoundException();
                }

                if(createOrderItemRecord.orderQty() > inventory.getStockQty()) {
                    throw new ValidateException(String.format("해당 주문의 제품 중 제품명 - %s 의 재고가 부족합니다.", createOrderItemRecord.itemName()));
                }

                inventory.deduct(createOrderItemRecord.orderQty());
                inventoryCommandService.save(inventory);

            } catch (InterruptedException e) {
                log.error("processDeduct try lock {}", e.toString());
                throw new RuntimeException(e);
            } finally {
                lock.unlock();
            }
        }

    }
}
