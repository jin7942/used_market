package com.jinfw.infra.usedmarket.item;

import java.util.Map;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/item")
@RequiredArgsConstructor
public class ItemController {

  private final ItemServiceImpl itemService;

  @PostMapping("/register")
  public ResponseEntity<Map<String, Object>> registerItem(@RequestBody ItemDto reqDto)
      throws Exception {

    Map<String, Object> res = itemService.registerItem(reqDto);

    return ResponseEntity.ok(res);
  }

}
