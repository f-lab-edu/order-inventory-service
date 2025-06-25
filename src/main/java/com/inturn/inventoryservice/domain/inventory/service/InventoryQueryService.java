package com.inturn.inventoryservice.domain.inventory.service;

import com.inturn.inventoryservice.domain.inventory.entity.InventoryEntity;
import com.inturn.inventoryservice.domain.inventory.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class InventoryQueryService {

	private final InventoryRepository inventoryRepository;

	public List<InventoryEntity> getInventoryByItemIdList(Collection<String> itemIdList) {
		return inventoryRepository.findAllByItemIdIn(itemIdList);
	}

	public InventoryEntity getInventoryByItemId(String itemId) {
		return inventoryRepository.findFirstByItemId(itemId);
	}
}
