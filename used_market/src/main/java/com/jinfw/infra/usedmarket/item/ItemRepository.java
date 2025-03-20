package com.jinfw.infra.usedmarket.item;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemRepository extends JpaRepository<Item, String> {

  // 전체 상품 리스트 조회
  List<Item> findAll();

}
