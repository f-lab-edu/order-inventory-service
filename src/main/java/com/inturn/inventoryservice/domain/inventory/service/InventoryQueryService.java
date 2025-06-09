package com.inturn.inventoryservice.domain.inventory.service;

import com.inturn.inventoryservice.domain.inventory.entity.InventoryEntity;
import com.inturn.inventoryservice.domain.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InventoryQueryService {

	private final InventoryRepository inventoryRepository;

	public List<InventoryEntity> getInventoryByItemIdList(List<String> itemIdList) {
		return inventoryRepository.findAllByItemIdIn(itemIdList);
	}

	public InventoryEntity getInventoryByItemId(String itemId) {
		return inventoryRepository.findFirstByItemId(itemId);
	}
}
