package com.jinfw.infra.usedmarket.orders.repository;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.orders.entity.Wishlist;
import com.jinfw.infra.usedmarket.user.entity.User;

@Repository
public interface WishlistRepository extends JpaRepository<Wishlist, Integer> {
  Optional<Wishlist> findByUserSeqAndItemSeq(User user, Item item);

  List<Wishlist> findByUserSeq(User user);

  int countByUserSeq(User user);

  boolean existsByUserSeqAndItemSeq(User user, Item item);

  void deleteByUserSeqAndItemSeq(User user, Item item);

}
