package com.jinfw.infra.usedmarket.common.util;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import com.jinfw.infra.usedmarket.img.dto.ImguploadVo;
import com.jinfw.infra.usedmarket.img.repository.ImguploadRepository;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.entity.Item;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UtilDtoConverter {

  private final ModelMapper modelMapper;
  private final ImguploadRepository imguploadRepository;

  /**
   * Entity to Dto 컨버터 함수
   * 
   * @param entity
   * @param dtoClass
   * @return Dto
   */
  public <D, E> D toDto(E entity, Class<D> dtoClass) {
    return modelMapper.map(entity, dtoClass);
  }

  /**
   * Dto to Entity 컨버터 함수
   * 
   * @param dto
   * @param entityClass
   * @return Entity
   */
  public <E, D> E toEntity(D dto, Class<E> entityClass) {
    return modelMapper.map(dto, entityClass);
  }

  /**
   * Entity List to Dto List 컨버터 함수
   * 
   * @param <D>
   * @param <E>
   * @param entityList
   * @param dtoClass
   * @return
   */
  public <D, E> List<D> toDtoList(List<E> entityList, Class<D> dtoClass) {
    return entityList.stream().map(entity -> modelMapper.map(entity, dtoClass))
        .collect(Collectors.toList());
  }

  /**
   * List<Item> to List<ItemVo> 컨버터 함수
   * 
   * @param itemList
   * @return List<ItemVo>
   */
  public List<ItemVo> toItemVoList(List<Item> itemList) {
    return itemList.stream().map(item -> {
      ImguploadVo img = imguploadRepository.findThumbnailByImgPseq(item.getSeq(), "ITEM");

      return new ItemVo(item.getSeq(), // PK
          item.getUserSeq().getUserNickname(), // userNickname
          item.getItemTitle(), // itemTitle
          item.getItemDescription(), // itemDescription
          item.getItemPrice(), // itemPrice
          item.getUpdateDT(), // updateDT
          img != null ? img.getImgUploadPath() : "/upload/", // 기본 이미지
          img != null ? img.getImgUploadUuidName() : "baseImg.png",
          img != null ? img.getImgUploadExt() : ".png");
    }).toList();
  }

}
