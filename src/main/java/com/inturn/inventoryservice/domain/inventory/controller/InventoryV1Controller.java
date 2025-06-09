package com.inturn.inventoryservice.domain.inventory.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/v1/inventory")
@RequiredArgsConstructor
public class InventoryV1Controller {

	//TODO - 해당 부분은 주문 요청으로 인한 재고 감소 인데 주문 요청으로 하는게 맞지 않을까 ?
//	@PostMapping("")
}
