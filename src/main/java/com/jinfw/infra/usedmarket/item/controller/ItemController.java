package com.jinfw.infra.usedmarket.item.controller;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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
   * @param dto 상품 등록 요청 데이터
   * @return 등록된 상품 seq
   * @throws Exception
   */
  @PostMapping("/register")
  public ResponseEntity<Map<String, Object>> instItem(@RequestBody ItemDto dto) throws Exception {

    return ResponseEntity.ok(Map.of("seq", itemService.instItem(dto)));
  }

  /**
   * 상품 목록 조회 API
   * 
   * @param page 현재 페이지 번호 (1부터 시작)
   * @param int size 페이지당 항목 수
   * @return 지정된 size만큼 상품리스트 리턴
   * @throws Exception
   */
  @GetMapping("/list")
  public ResponseEntity<List<ItemVo>> getItemList(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "12") int size) throws Exception {
    return ResponseEntity.ok(itemService.getItemList(page, size));
  }

  /**
   * 상품 단일 조회 API
   * 
   * @param dto 조회할 상품 seq
   * @return vo 조회된 상품 객체
   * @throws Exception
   */
  @GetMapping("/detail")
  public ResponseEntity<ItemVo> getItemOne(@RequestParam int seq) throws Exception {
    return ResponseEntity.ok(itemService.getItemOne(seq));
  }

}
