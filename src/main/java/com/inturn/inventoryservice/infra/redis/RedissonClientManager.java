package com.inturn.inventoryservice.infra.redis;

import lombok.RequiredArgsConstructor;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RedissonClientManager {

    private final RedissonClient redissonClient;

    public RLock getLock(String itemKey) {
        return redissonClient.getLock(itemKey);
    }
}
