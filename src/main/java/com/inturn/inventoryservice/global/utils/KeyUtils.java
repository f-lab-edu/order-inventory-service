package com.inturn.inventoryservice.global.utils;

import lombok.experimental.UtilityClass;

import java.util.Arrays;
import java.util.List;

@UtilityClass
public class KeyUtils {

    private final String INVENTORY_KEY = "inventory:";
    private final String INVENTORY_LOCK_KEY = "inventory-lock:";

    public String generateRedisInventoryKey(String itemId) {
        return String.format("%s%s", INVENTORY_KEY, itemId);
    }

    public String generateRedisLockKey(String itemId) {
        return String.format("%s%s", INVENTORY_LOCK_KEY, itemId);
    }

    public String getItemIdByRedisInventoryKey(String redisInventoryKey) {
        String[] keyArr = redisInventoryKey.split(":");
        return keyArr[keyArr.length - 1];
    }
}
