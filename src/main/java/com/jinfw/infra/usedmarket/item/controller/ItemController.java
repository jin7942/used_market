package com.jinfw.infra.usedmarket.item.controller;

import java.util.HashMap;
import java.util.List;
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
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.service.ItemServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Item API", description = "상품 관련 API")
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {
  // TODO : 응답 형식 마저 수정
  private final ItemServiceImpl itemService;

  /**
   * 상품 등록 API
   * 
   * @param dto 상품 등록 요청 데이터
   * @return 등록된 상품 seq
   * @throws Exception
   */
  @PostMapping("/register")
  public ResponseEntity<ResponseVo<Object>> instItem(@RequestBody ItemDto dto) throws Exception {

    Map<String, Object> result = new HashMap<>();
    result.put("seq", itemService.instItem(dto));
    ResponseVo<Object> res = new ResponseVo<>(true, "상품 등록 성공", result);

    return ResponseEntity.ok(res);
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
