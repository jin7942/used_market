package com.jinfw.infra.usedmarket.item.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.ItemStatusCode;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.user.entity.User;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>, ItemRepositoryCustom {
  List<Item> findByUserSeqAndItemStateCode(User user, ItemStatusCode itemStatusCode);
}
