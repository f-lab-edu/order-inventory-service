package com.inturn.inventoryservice.domain.order.controller;

import com.inturn.inventoryservice.domain.order.dto.request.CreateOrderRecord;
import com.inturn.inventoryservice.global.common.dto.response.CommonResponseDTO;
import com.inturn.inventoryservice.infra.redis.RedisInventoryManager;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/order")
@RequiredArgsConstructor
public class OrderV1Controller {

	private final RedisInventoryManager redisInventoryManager;

	@PostMapping
	public ResponseEntity<CommonResponseDTO> createOrder(@RequestBody CreateOrderRecord req) {
		return ResponseEntity.ok(redisInventoryManager.checkInventoryWithDeduct(req));
	}
}
