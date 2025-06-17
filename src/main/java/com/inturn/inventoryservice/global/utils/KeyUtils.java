package com.inturn.inventoryservice.global.utils;

import lombok.experimental.UtilityClass;

import java.util.List;

@UtilityClass
public class KeyUtils {

    private final String INVENTORY_KEY = "inventory:";

    public String generateRedisInventoryKey(String itemId) {
        return String.format("%s%s", INVENTORY_KEY, itemId);
    }

    public String getItemIdByRedisInventoryKey(String redisInventoryKey) {
        return redisInventoryKey.replace(INVENTORY_KEY, "");
    }
}
