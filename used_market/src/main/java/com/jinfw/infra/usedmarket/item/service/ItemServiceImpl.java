package com.jinfw.infra.usedmarket.item.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.stereotype.Service;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.item.dto.ItemDto;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.item.repository.ItemRepository;
import jakarta.persistence.EntityNotFoundException;
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
   * 상품 리스트 조회 함수
   * 
   * @param page 현제 페이지
   * @param size 페이지당 항목 수
   * @return List<ItemVo> 조회된 상품 리스트
   */
  public List<ItemVo> getItemList(int page, int size) throws Exception {
    return itemRepository.getItemList(page, size);
  }

  /**
   * 상품 단일 조회 함수
   * 
   * @param dto 조회할 상품 seq
   * @return vo 조회된 상품 객체
   * @throws Exception
   */
  public ItemVo getItemOne(int seq) throws Exception {
    // 1. PK를 기준으로 검색
    Item item = itemRepository.findById(seq)
        .orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다."));
    // 2. Dto 로 변환 후 리턴
    return dtoConverter.toDto(item, ItemVo.class);
  }

}
