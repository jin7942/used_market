package com.jinfw.infra.usedmarket.item;

import java.util.HashMap;
import java.util.Map;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
  public Map<String, Object> instItem(ItemDto dto) throws Exception {

    // 1. Dto to Entity
    Item item = dtoConverter.toEntity(dto, Item.class);
    item.setItemTitle(dto.getItemTitle());
    item.setItemDescription(dto.getItemDescription());
    item.setItemPrice(dto.getItemPrice());

    // 2. DB에 저장
    itemRepository.save(item);

    // 3. 등록된 상품 seq 리턴
    Map<String, Object> res = new HashMap<>();
    res.put("seq", item.getSeq());

    return res;
  }

  /**
   * 상품 목록 리스트 조회 함수
   * 
   * @param page 현재 페이지 번호 (1부터 시작)
   * @param size 페이지당 항목 수
   * @return 지정된 size만큼 상품리스트 리턴
   */
  public Page<ItemVo> getItemList(int page, int size) {
    Pageable pageable = PageRequest.of(page, size, Sort.by("createDT").descending());

    return itemRepository.findAll(pageable).map(item -> new ItemVo(item));
  }

}
