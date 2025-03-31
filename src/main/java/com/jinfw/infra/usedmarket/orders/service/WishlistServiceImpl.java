package com.jinfw.infra.usedmarket.orders.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jinfw.infra.usedmarket.img.dto.ImguploadVo;
import com.jinfw.infra.usedmarket.img.repository.ImguploadRepository;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.item.service.ItemServiceImpl;
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
  private final UserServiceImpl userService;
  private final ItemServiceImpl itemService;
  private final ImguploadRepository imguploadRepository;

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
  @Transactional(readOnly = true)
  public List<ItemVo> getWishlist(HttpServletRequest req) throws Exception {
    User user = userService.getUserFromRequest(req);

    List<Wishlist> wishlist = wishlistRepository.findByUserSeq(user);

    // 찜한 상품 리스트 추출 → ItemVo로 변환
    return wishlist.stream().map(w -> {
      Item item = w.getItemSeq();
      // 썸네일 조회
      ImguploadVo img = imguploadRepository.findThumbnailByImgPseq(item.getSeq(), "ITEM");
      // 필요한 정보만 담은 Vo 생성
      return new ItemVo(item.getSeq(), // PK
          item.getUserSeq().getUserNickname(), // userNickname
          item.getItemTitle(), // itemTitle
          item.getItemDescription(), // itemDescription
          item.getItemPrice(), // itemPrice
          item.getUpdateDT(), // updateDT
          img.getImgUploadPath(), // 이미지 주소
          img.getImgUploadUuidName(), // 이미지 이름
          img.getImgUploadExt() // 이미지 확장자
      );
    }).toList();
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
