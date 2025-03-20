package com.jinfw.infra.usedmarket.item;

import java.util.List;
import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
   * @param reqDto 상품 등록 요청 데이터
   * @return 등록된 상품 seq
   * @throws Exception
   */
  @PostMapping("/register")
  public ResponseEntity<Map<String, Object>> registerItem(@RequestBody ItemDto reqDto)
      throws Exception {

    Map<String, Object> res = itemService.registerItem(reqDto);

    return ResponseEntity.ok(res);
  }

  @GetMapping("/list")
  public ResponseEntity<List<ItemVo>> getItemList(@RequestBody ItemVo vo) throws Exception {

    // TODO: 페이징 기능 구현
    List<ItemVo> res = itemService.getItemList(vo);

    return ResponseEntity.ok(res);
  }

}
