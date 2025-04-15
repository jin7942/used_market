package com.jinfw.infra.usedmarket.orders.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jinfw.infra.usedmarket.common.constants.CommonCode.ItemStatusCode;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.NotificationTypeCode;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.OrderStatusCode;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.item.service.ItemServiceImpl;
import com.jinfw.infra.usedmarket.notification.service.NotificationServiceImpl;
import com.jinfw.infra.usedmarket.orders.entity.Orders;
import com.jinfw.infra.usedmarket.orders.entity.Wishlist;
import com.jinfw.infra.usedmarket.orders.repository.OrdersRepository;
import com.jinfw.infra.usedmarket.orders.repository.WishlistRepository;
import com.jinfw.infra.usedmarket.user.entity.User;
import com.jinfw.infra.usedmarket.user.service.UserServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrdersServiceImpl {

	private final OrdersRepository ordersRepository;
	private final NotificationServiceImpl notificationService;
	private final WishlistRepository wishlistRepository;
	private final UserServiceImpl userService;
	private final ItemServiceImpl itemServiceImpl;
	private final UtilDtoConverter dtoConverter;

	/**
	 * 상품 결제 함수
	 * 
	 * @param itemSeqList
	 * @param req         http 헤더
	 * @throws Exception
	 */
	@Transactional
	public void instOrders(List<Integer> itemSeqList, HttpServletRequest req) throws Exception {
		User user = userService.getUserFromRequest(req);

		for (int itemSeq : itemSeqList) {
			Item item = itemServiceImpl.getItemById(itemSeq);

			// 찜 처리 (이미 찜했으면 넘어감)
			wishlistRepository.findByUserSeqAndItemSeq(user, item).orElseGet(() -> {
				Wishlist wish = new Wishlist();
				wish.setUserSeq(user);
				wish.setItemSeq(item);
				return wishlistRepository.save(wish);
			});

			// 결제 처리 (중복 결제 방지)
			if (ordersRepository.existsByUserSeqAndItemSeq(user, item)) {
				continue; // 또는 로그만 남기고 넘어감
			}
			item.setItemStateCode(ItemStatusCode.SOLD);

			Orders order = new Orders();
			order.setUserSeq(user);
			order.setItemSeq(item);
			order.setOrderStateCode(OrderStatusCode.COMPLETED);
			ordersRepository.save(order);

			wishlistRepository.findByUserSeqAndItemSeq(user, item).ifPresent(wishlistRepository::delete);

			// 알림 전송
			User seller = item.getUserSeq();
			notificationService.generateNotification(item, user, seller, NotificationTypeCode.NORMAL,
					user.getUserNickname() + " 님이 " + item.getItemTitle() + " 을 구매하였습니다.");
		}
	}

	/**
	 * 구매한 상품 조회 함수
	 * 
	 * @param req http 헤더
	 * @return List<ItemVo>
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<ItemVo> getItemBought(HttpServletRequest req) throws Exception {
		User user = userService.getUserFromRequest(req);
		List<Orders> orders = ordersRepository.findByUserSeq(user);
		List<Item> itemList = orders.stream().map(order -> order.getItemSeq()).toList();

		return dtoConverter.toItemVoList(itemList);
	}

}
