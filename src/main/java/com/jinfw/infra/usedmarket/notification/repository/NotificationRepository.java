package com.jinfw.infra.usedmarket.notification.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.notification.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

}
