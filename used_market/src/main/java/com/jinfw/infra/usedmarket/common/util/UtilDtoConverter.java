package com.jinfw.infra.usedmarket.common.util;

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
}
