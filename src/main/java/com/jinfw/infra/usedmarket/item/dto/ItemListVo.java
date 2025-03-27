package com.jinfw.infra.usedmarket.item.dto;

import java.util.List;
import com.jinfw.infra.usedmarket.common.dto.PageInfoVo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ItemListVo {
  private List<ItemVo> itemList;
  private PageInfoVo pageInfo;
}
