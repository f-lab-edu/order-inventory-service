package com.inturn.inventoryservice.domain.inventory.service;

import com.inturn.inventoryservice.domain.inventory.entity.InventoryEntity;
import com.inturn.inventoryservice.domain.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class InventoryCommandService {

    private final InventoryRepository inventoryRepository;

    @Transactional
    public InventoryEntity save(InventoryEntity inventoryEntity) {
        return inventoryRepository.save(inventoryEntity);
    }
}
