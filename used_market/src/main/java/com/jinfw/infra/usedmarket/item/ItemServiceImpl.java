package com.jinfw.infra.usedmarket.item;

import java.util.HashMap;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl {

  private final ItemRepository itemRepository;
  private final UtilDtoConverter dtoConverter; // DTO 변환 유틸

  /**
   * 상품 등록 함수
   * 
   * @param reqDto
   * @return 등록된 상품 seq 반환
   */
  public Map<String, Object> registerItem(ItemDto reqDto) throws Exception {

    // 1. Dto to Entity
    Item item = dtoConverter.toEntity(reqDto, Item.class);
    item.setItemTitle(reqDto.getItemTitle());
    item.setItemDescription(reqDto.getItemDescription());
    item.setItemPrice(reqDto.getItemPrice());

    // 2. DB에 저장
    itemRepository.save(item);

    // 3. 등록된 상품 seq 리턴
    Map<String, Object> res = new HashMap<>();
    res.put("seq", item.getSeq());

    return res;
  }

}
