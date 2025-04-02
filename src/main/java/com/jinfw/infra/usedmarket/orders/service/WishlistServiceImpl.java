package com.jinfw.infra.usedmarket.orders.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.NotificationTypeCode;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.item.service.ItemServiceImpl;
import com.jinfw.infra.usedmarket.notification.service.NotificationServiceImpl;
import com.jinfw.infra.usedmarket.orders.entity.Wishlist;
import com.jinfw.infra.usedmarket.orders.repository.WishlistRepository;
import com.jinfw.infra.usedmarket.user.entity.User;
import com.jinfw.infra.usedmarket.user.service.UserServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl {

  private final WishlistRepository wishlistRepository;
  private final NotificationServiceImpl notificationService;
  private final UserServiceImpl userService;
  private final ItemServiceImpl itemService;
  private final UtilDtoConverter dtoConverter;

  /**
   * 찜하기 토글 함수
   * 
   * @param itemSeq
   * @param req http 헤더
   * @return true / false
   * @throws Exception
   */
  @Transactional
  public boolean toggleWishlist(int itemSeq, HttpServletRequest req) throws Exception {
    User user = userService.getUserFromRequest(req);
    Item item = itemService.getItemById(itemSeq);

    Optional<Wishlist> existing = wishlistRepository.findByUserSeqAndItemSeq(user, item);

    if (existing.isPresent()) {
      // 이미 찜한 경우 삭제
      wishlistRepository.delete(existing.get());
      return false; // 찜 해제
    } else {
      // 찜 등록
      Wishlist wishlist = new Wishlist();
      wishlist.setUserSeq(user);
      wishlist.setItemSeq(item);
      wishlistRepository.save(wishlist);

      // 판매자에게 알림 전송
      User seller = item.getUserSeq();
      notificationService.generateNotification(item, user, seller, NotificationTypeCode.NORMAL,
          user.getUserNickname() + " 님이 " + item.getItemTitle() + " 에 관심을 가졌습니다.");

      return true; // 찜 등록
    }

  }

  /**
   * 찜하기 목록 조회 함수
   * 
   * @param req http헤더
   * @return List<ItemVo>
   * @throws Exception
   */
  public List<ItemVo> getWishlist(HttpServletRequest req) throws Exception {
    User user = userService.getUserFromRequest(req);

    List<Wishlist> wishlist = wishlistRepository.findByUserSeq(user);
    List<Item> itemList = wishlist.stream().map(wish -> wish.getItemSeq()).toList();

    return dtoConverter.toItemVoList(itemList);
  }

  /**
   * 찜한 상품 카운트 함수
   * 
   * @param req http 헤더
   * @return 찜목록 갯수
   * @throws Exception
   */
  public int getWishlistCount(HttpServletRequest req) throws Exception {
    User user = userService.getUserFromRequest(req);

    return wishlistRepository.countByUserSeq(user);
  }

  /**
   * 특정 상품에 대해 찜 여부 확인
   * 
   * @param itemSeq
   * @param req http 헤더
   * @return true / false
   * @throws Exception
   */
  public boolean checkIfWished(int itemSeq, HttpServletRequest req) throws Exception {
    User user = userService.getUserFromRequest(req);
    Item item = itemService.getItemById(itemSeq);

    return wishlistRepository.existsByUserSeqAndItemSeq(user, item);
  }

}
