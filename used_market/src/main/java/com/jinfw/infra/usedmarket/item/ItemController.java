package com.jinfw.infra.usedmarket.item;

import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

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

    Map<String, Object> res = itemService.instItem(dto);

    return ResponseEntity.ok(res);
  }

  /**
   * 상품 목록 조회 API
   * 
   * @param page 현재 페이지 번호 (1부터 시작)
   * @param size 페이지당 항목 수
   * @return 지정된 size만큼 상품리스트 리턴
   */
  @GetMapping("/list")
  public ResponseEntity<Page<ItemVo>> getItemList(@RequestParam(defaultValue = "1") int page,
      @RequestParam(defaultValue = "12") int size) {

    Page<ItemVo> items = itemService.getItemList(page, size);

    return ResponseEntity.ok(items);
  }

}
