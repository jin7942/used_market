package com.jinfw.infra.usedmarket.orders.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.OrderStatusCode;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.common.util.UtilJwt;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.item.repository.ItemRepository;
import com.jinfw.infra.usedmarket.orders.entity.Orders;
import com.jinfw.infra.usedmarket.orders.entity.Wishlist;
import com.jinfw.infra.usedmarket.orders.repository.OrdersRepository;
import com.jinfw.infra.usedmarket.orders.repository.WishlistRepository;
import com.jinfw.infra.usedmarket.user.entity.User;
import com.jinfw.infra.usedmarket.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl {

  private final OrdersRepository ordersRepository;
  private final WishlistRepository wishlistRepository;
  private final WishlistServiceImpl wishlistServiceImpl;
  private final UserRepository userRepository;
  private final ItemRepository itemRepository;
  private final UtilDtoConverter dtoConverter; // DTO 변환 유틸
  private final UtilJwt utilJwt;

  /**
   * 상품 결제 함수
   * 
   * @param itemSeqList
   * @param req http 헤더
   * @throws Exception
   */
  @Transactional
  public void instOrders(List<Integer> itemSeqList, HttpServletRequest req) throws Exception {
    User user = wishlistServiceImpl.getUserFromRequest(req);

    for (int itemSeq : itemSeqList) {
      Item item = wishlistServiceImpl.getItemById(itemSeq);

      // 1. 찜 처리 (이미 찜했으면 넘어감)
      wishlistRepository.findByUserSeqAndItemSeq(user, item).orElseGet(() -> {
        Wishlist wish = new Wishlist();
        wish.setUserSeq(user);
        wish.setItemSeq(item);
        return wishlistRepository.save(wish);
      });

      // 2. 결제 처리 (중복 결제 방지)
      if (ordersRepository.existsByUserSeqAndItemSeq(user, item)) {
        continue; // 또는 로그만 남기고 넘어감
      }

      Orders order = new Orders();
      order.setUserSeq(user);
      order.setItemSeq(item);
      order.setOrderStateCode(OrderStatusCode.COMPLETED);
      ordersRepository.save(order);
    }
  }

}
