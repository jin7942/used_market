package com.jinfw.infra.usedmarket.notification.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.notification.entity.Notification;
import com.jinfw.infra.usedmarket.user.entity.User;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {
  // 전체 조회
  List<Notification> findByUserSeqOrderByCreateDTDesc(User user);

  // 안 읽은 알림만
  List<Notification> findByUserSeqAndNotificationReadNY(User user, boolean readNY);

}
