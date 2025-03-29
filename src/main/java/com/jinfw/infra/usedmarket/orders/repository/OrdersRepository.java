package com.jinfw.infra.usedmarket.orders.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.orders.entity.Orders;
import com.jinfw.infra.usedmarket.user.entity.User;

@Repository
public interface OrdersRepository extends JpaRepository<Orders, Integer> {

  boolean existsByUserSeqAndItemSeq(User user, Item item);

}
