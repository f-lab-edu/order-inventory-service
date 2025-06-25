package com.inturn.inventoryservice.domain.inventory.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity(name = "inventory")
@Getter
@SuperBuilder
@NoArgsConstructor
public class InventoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long inventoryId;

    @Column(nullable = false)
    private String warehouseId;

    @Column(nullable = false)
    private String itemId;

    @Column(nullable = false)
    private Integer stockQty;

    public void deduct(Integer deductQty) {
        this.stockQty -= deductQty;
    }
}
