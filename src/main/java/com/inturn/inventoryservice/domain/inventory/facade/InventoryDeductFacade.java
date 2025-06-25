package com.inturn.inventoryservice.domain.inventory.facade;

import com.inturn.inventoryservice.domain.inventory.define.InventoryErrorCode;
import com.inturn.inventoryservice.domain.inventory.entity.InventoryEntity;
import com.inturn.inventoryservice.domain.inventory.service.InventoryCommandService;
import com.inturn.inventoryservice.domain.inventory.service.InventoryQueryService;
import com.inturn.inventoryservice.domain.order.define.OrderStatus;
import com.inturn.inventoryservice.domain.order.dto.event.CompleteOrderEvent;
import com.inturn.inventoryservice.domain.order.dto.event.CreateOrderEvent;
import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderItemRecord;
import com.inturn.inventoryservice.domain.inventory.exception.InventoryException;
import com.inturn.inventoryservice.domain.order.service.OrderCommandService;
import com.inturn.inventoryservice.global.common.exception.BaseException;
import com.inturn.inventoryservice.global.utils.KeyUtils;
import com.inturn.inventoryservice.infra.redis.RedissonClientManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.redisson.api.RLock;
import org.springframework.context.ApplicationEventPublisher;
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

    private final ApplicationEventPublisher publisher;

    @Transactional
    public void deductInventoryWithCompleteOrder(CreateOrderEvent evt) {
        publisher.publishEvent(new CompleteOrderEvent(evt.orderId()));
        processDeduct(evt.itemList());
    }

    private void processDeduct(List<CreateOrderItemRecord> orderItemList) {

        for (CreateOrderItemRecord createOrderItemRecord : orderItemList) {

            final RLock lock = redissonClientManager.getLock(KeyUtils.generateRedisLockKey(createOrderItemRecord.itemId()));

            //waitTime - lock 요청을 기다리는 시간
            //leaseTime - lock이 풀리는 시간
            try {
                lock.tryLock(10, 5, TimeUnit.SECONDS);
                InventoryEntity inventory = inventoryQueryService.getInventoryByItemId(createOrderItemRecord.itemId());

                validateDeductInventory(createOrderItemRecord, inventory);

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

    private void validateDeductInventory(CreateOrderItemRecord createOrderItemRecord, InventoryEntity inventory) {
        //재고가 존재하지 않을 경우는 throw
        if(ObjectUtils.isEmpty(inventory)) {
            throw new InventoryException(InventoryErrorCode.ITEM_NOT_FOUND_EXCEPTION);
        }

        if(createOrderItemRecord.orderQty() > inventory.getStockQty()) {
            throw new InventoryException(InventoryErrorCode.ITEM_NOT_FOUND_EXCEPTION,
                    String.format(InventoryErrorCode.ITEM_NOT_FOUND_EXCEPTION.getErrorMessage(), createOrderItemRecord.itemName()));
        }
    }
}
