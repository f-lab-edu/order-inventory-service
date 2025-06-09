package com.inturn.inventoryservice.domain.inventory.entity;

import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "inventory")
@Getter
@SuperBuilder
@NoArgsConstructor
public class InventoryEntity extends Inventory {


}
