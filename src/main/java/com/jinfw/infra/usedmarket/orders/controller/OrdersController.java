package com.jinfw.infra.usedmarket.orders.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jinfw.infra.usedmarket.common.dto.ResponseVo;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.orders.service.OrdersServiceImpl;
import com.jinfw.infra.usedmarket.orders.service.WishlistServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Order API", description = "결제 및 찜하기 관련 API")
@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrdersController {

  private final OrdersServiceImpl ordersService;
  private final WishlistServiceImpl wishlistServiceImpl;

  // 찜하기 토글 API
  @Operation(summary = "찜하기 토글", description = "찜하기 토글 API 입니다.")
  @PostMapping("/wishlist")
  public ResponseEntity<ResponseVo<Boolean>> toggleWishlist(@RequestParam int itemSeq,
      HttpServletRequest req) throws Exception {

    boolean isWishlisted = wishlistServiceImpl.toggleWishlist(itemSeq, req);
    String msg = isWishlisted ? "찜 등록 완료" : "찜 해제 완료";

    return ResponseEntity.ok(new ResponseVo<>(true, msg, isWishlisted));
  }

  // 찜한 상품 조회 API
  @Operation(summary = "찜한 목록", description = "찜한 목록 조회 API 입니다.")
  @GetMapping("/wishlist")
  public ResponseEntity<ResponseVo<List<ItemVo>>> getWishlist(HttpServletRequest req)
      throws Exception {
    List<ItemVo> result = wishlistServiceImpl.getWishlist(req);
    return ResponseEntity.ok(new ResponseVo<>(true, "찜 목록 조회 성공", result));
  }

  // 찜한 상품 카운트 API
  @Operation(summary = "찜한 목록 갯수", description = "찜한 목록 갯수 조회 API 입니다.")
  @GetMapping("/wishlist/count")
  public ResponseEntity<ResponseVo<Integer>> getWishlistCount(HttpServletRequest req)
      throws Exception {
    int count = wishlistServiceImpl.getWishlistCount(req);

    return ResponseEntity.ok(new ResponseVo<>(true, "찜 개수 조회 성공", count));
  }

  // 찜 여부 확인 API
  @Operation(summary = "찜 여부 확인", description = "상품에 대한 찜 여부 확인 API 입니다.")
  @GetMapping("/wishlist/checkIfWished")
  public ResponseEntity<ResponseVo<Boolean>> checkIfWished(@RequestParam int itemSeq,
      HttpServletRequest req) throws Exception {

    boolean exists = wishlistServiceImpl.checkIfWished(itemSeq, req);

    return ResponseEntity.ok(new ResponseVo<>(true, "찜 여부 확인 성공", exists));
  }

  // 결제 API
  @Operation(summary = "결제", description = "찜한 상품에 대한 결제 API 입니다.")
  @PostMapping("/pay")
  public ResponseEntity<ResponseVo<Boolean>> instOrders(@RequestBody List<Integer> itemSeqList,
      HttpServletRequest req) throws Exception {

    ordersService.instOrders(itemSeqList, req);

    return ResponseEntity.ok(new ResponseVo<>(true, "결제 성공", true));
  }

}
