package com.jinfw.infra.usedmarket.orders.service;

import org.springframework.stereotype.Service;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.common.util.UtilJwt;
import com.jinfw.infra.usedmarket.item.repository.ItemRepository;
import com.jinfw.infra.usedmarket.orders.repository.OrdersRepository;
import com.jinfw.infra.usedmarket.orders.repository.WishlistRepository;
import com.jinfw.infra.usedmarket.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl {

  private final OrdersRepository ordersRepository;
  private final WishlistRepository wishlistRepository;
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final UtilDtoConverter dtoConverter; // DTO 변환 유틸
  private final UtilJwt utilJwt;



}
