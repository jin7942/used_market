package com.jinfw.infra.usedmarket.common.util;

import java.util.List;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class UtilDtoConverter {

  private final ModelMapper modelMapper;

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
}
