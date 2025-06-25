package com.inturn.inventoryservice.domain.inventory.repository;

import com.inturn.inventoryservice.domain.inventory.entity.InventoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface InventoryRepository extends JpaRepository<InventoryEntity, String> {

	List<InventoryEntity> findAllByItemIdIn(Collection<String> itemIdList);

	InventoryEntity findFirstByItemId(String itemId);
}