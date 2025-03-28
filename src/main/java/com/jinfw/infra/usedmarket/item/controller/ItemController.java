package com.jinfw.infra.usedmarket.item.controller;

import java.util.HashMap;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jinfw.infra.usedmarket.common.dto.ResponseVo;
import com.jinfw.infra.usedmarket.item.dto.ItemDto;
import com.jinfw.infra.usedmarket.item.dto.ItemListVo;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.service.ItemServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Item API", description = "상품 관련 API")
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

  private final ItemServiceImpl itemService;

  // 상품 등록 API
  @Operation(summary = "상품 등록", description = "상품을 등록 합니다.")
  @PostMapping("/register")
  public ResponseEntity<ResponseVo<Map<String, Object>>> instItem(@RequestBody ItemDto dto,
      HttpServletRequest req) throws Exception {

    Map<String, Object> result = new HashMap<>();
    result.put("seq", itemService.instItem(dto, req));
    ResponseVo<Map<String, Object>> res = new ResponseVo<>(true, "상품 등록 성공", result);

    return ResponseEntity.ok(res);
  }

  // 상품 목록 조회 API
  @Operation(summary = "상품 리스트 조회", description = "상품 리스트를 조회 합니다.")
  @GetMapping("/list")
  public ResponseEntity<ResponseVo<ItemListVo>> getItemList(
      @RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "12") int size)
      throws Exception {

    ItemListVo vo = itemService.getItemList(page, size);
    ResponseVo<ItemListVo> res = new ResponseVo<>(true, "상품 목록 조회 성공", vo);

    return ResponseEntity.ok(res);
  }

  // 상품 조회 API
  @Operation(summary = "상품 조회", description = "단일 상품을 조회 합니다.")
  @GetMapping("/detail")
  public ResponseEntity<ResponseVo<ItemVo>> getItemOne(@RequestParam int seq) throws Exception {

    ItemVo vo = itemService.getItemOne(seq);
    ResponseVo<ItemVo> res = new ResponseVo<>(true, "상품 조회 성공", vo);

    return ResponseEntity.ok(res);
  }

}
