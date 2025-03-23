package com.jinfw.infra.usedmarket.item.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jinfw.infra.usedmarket.item.dto.ItemDto;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.service.ItemServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Item API", description = "상품 관련 API")
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

  private final ItemServiceImpl itemService;

  /**
   * 상품 등록 API
   *
   * @param dto 등록할 상품 정보
   * @return 등록된 상품의 seq 반환
   */
  @PostMapping
  public ResponseEntity<Map<String, Object>> registerItem(@RequestBody ItemDto dto)
      throws Exception {
    Map<String, Object> res = itemService.instItem(dto);
    return ResponseEntity.ok(res);
  }

  /**
   * 상품 목록 조회 API
   *
   * @param page 현재 페이지 번호 (1부터 시작)
   * @param size 페이지당 항목 수
   * @return 상품 목록
   */
  @GetMapping
  public ResponseEntity<List<ItemVo>> getItemList(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "12") int size) throws Exception {
    return ResponseEntity.ok(itemService.getItemList(page, size));
  }

  /**
   * 상품 단건 조회 API
   *
   * @param seq 조회할 상품 번호
   * @return 해당 상품 정보
   */
  @GetMapping("/{seq}")
  public ResponseEntity<ItemVo> getItemOne(@PathVariable int seq) throws Exception {
    return ResponseEntity.ok(itemService.getItemOne(seq));
  }
}
