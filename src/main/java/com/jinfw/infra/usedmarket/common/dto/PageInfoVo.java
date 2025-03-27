package com.jinfw.infra.usedmarket.common.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PageInfoVo {
  private int page;
  private int size;
  private int totalElements;
  private int totalPages;
}
