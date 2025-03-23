package com.jinfw.infra.usedmarket.item.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.item.entity.Item;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>, ItemRepositoryCustom {

}
