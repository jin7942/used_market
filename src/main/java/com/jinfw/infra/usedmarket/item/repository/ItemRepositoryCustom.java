package com.jinfw.infra.usedmarket.item.repository;

import com.jinfw.infra.usedmarket.item.dto.ItemListVo;

public interface ItemRepositoryCustom {
  ItemListVo getItemList(int page, int size);
}
