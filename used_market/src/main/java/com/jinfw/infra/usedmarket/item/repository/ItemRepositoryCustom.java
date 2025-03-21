package com.jinfw.infra.usedmarket.item.repository;

import java.util.List;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;

public interface ItemRepositoryCustom {
  List<ItemVo> getItemList(int page, int size);
}
