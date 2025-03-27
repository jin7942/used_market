package com.jinfw.infra.usedmarket.item.service;

import org.springframework.stereotype.Service;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.item.dto.ItemDto;
import com.jinfw.infra.usedmarket.item.dto.ItemListVo;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.item.repository.ItemRepository;
import com.jinfw.infra.usedmarket.user.entity.User;
import com.jinfw.infra.usedmarket.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl {

  private final ItemRepository itemRepository;
  private final UserRepository userRepository;
  private final UtilDtoConverter dtoConverter; // DTO 변환 유틸

  /**
   * 상품 등록 함수
   * 
   * @param reqDto
   * @return 등록된 상품 seq 반환
   */
  public int instItem(ItemDto dto) throws Exception {

    User user =
        userRepository.findById(dto.getUserSeq()).orElseThrow(() -> new RuntimeException("사용자 없음"));
    Item item = dtoConverter.toEntity(dto, Item.class);
    item.setUserSeq(user);
    itemRepository.save(item);

    return item.getSeq();
  }

  /**
   * 상품 리스트 조회 함수
   * 
   * @param page 현제 페이지
   * @param size 페이지당 항목 수
   * @return List<ItemVo> 조회된 상품 리스트
   */
  public ItemListVo getItemList(int page, int size) throws Exception {
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
    Item item = itemRepository.findById(seq)
        .orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다."));

    ItemVo vo = dtoConverter.toDto(item, ItemVo.class);

    User user = item.getUserSeq();
    vo.setUserNickname(user.getUserNickname());

    return vo;
  }

}
