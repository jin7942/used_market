package com.jinfw.infra.usedmarket.orders.service;

import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.jinfw.infra.usedmarket.common.exception.InvalidLoginException;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.common.util.UtilJwt;
import com.jinfw.infra.usedmarket.img.dto.ImguploadVo;
import com.jinfw.infra.usedmarket.img.repository.ImguploadRepository;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.item.repository.ItemRepository;
import com.jinfw.infra.usedmarket.orders.entity.Wishlist;
import com.jinfw.infra.usedmarket.orders.repository.OrdersRepository;
import com.jinfw.infra.usedmarket.orders.repository.WishlistRepository;
import com.jinfw.infra.usedmarket.user.entity.User;
import com.jinfw.infra.usedmarket.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WishlistServiceImpl {

  private final OrdersRepository ordersRepository;
  private final WishlistRepository wishlistRepository;
  private final UserRepository userRepository;
  private final ImguploadRepository imguploadRepository;
  private final ItemRepository itemRepository;
  private final UtilDtoConverter dtoConverter; // DTO 변환 유틸
  private final UtilJwt utilJwt;

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
    String email = utilJwt.extractUserEmailFromRequest(req);

    User user = userRepository.findByUserEmail(email)
        .orElseThrow(() -> new InvalidLoginException("해당 이메일에 대한 사용자를 찾을 수 없습니다."));

    Item item = itemRepository.findById(itemSeq)
        .orElseThrow(() -> new RuntimeException("해당 상품이 존재하지 않습니다."));

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
   * 찜하기 목록 조회
   * 
   * @param req http헤더
   * @return List<ItemVo>
   * @throws Exception
   */
  @Transactional(readOnly = true)
  public List<ItemVo> getWishlist(HttpServletRequest req) throws Exception {
    String email = utilJwt.extractUserEmailFromRequest(req);

    User user = userRepository.findByUserEmail(email)
        .orElseThrow(() -> new InvalidLoginException("해당 이메일에 대한 사용자를 찾을 수 없습니다."));

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


}
