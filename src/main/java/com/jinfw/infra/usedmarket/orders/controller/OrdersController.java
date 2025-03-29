package com.jinfw.infra.usedmarket.orders.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jinfw.infra.usedmarket.orders.service.OrdersServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order API", description = "결제 및 찜하기 관련 API")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

  private final OrdersServiceImpl ordersService;



}
