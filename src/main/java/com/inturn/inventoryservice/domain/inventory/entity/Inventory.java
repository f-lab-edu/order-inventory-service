package com.inturn.inventoryservice.domain.inventory.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@MappedSuperclass
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Inventory {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "order_seq_gen", sequenceName = "order_seq", allocationSize = 1)
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
