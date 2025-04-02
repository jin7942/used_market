package com.jinfw.infra.usedmarket.notification.service;

import java.nio.file.AccessDeniedException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.NotificationTypeCode;
import com.jinfw.infra.usedmarket.common.dto.ResponseVo;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.item.service.ItemServiceImpl;
import com.jinfw.infra.usedmarket.notification.dto.NotificationVo;
import com.jinfw.infra.usedmarket.notification.entity.Notification;
import com.jinfw.infra.usedmarket.notification.repository.NotificationRepository;
import com.jinfw.infra.usedmarket.user.entity.User;
import com.jinfw.infra.usedmarket.user.service.UserServiceImpl;
import io.jsonwebtoken.io.IOException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl {

  private final NotificationRepository notificationRepository;
  private final UserServiceImpl userService;
  private final ItemServiceImpl itemServiceImpl;
  private final UtilDtoConverter dtoConverter;
  private final Map<String, SseEmitter> emitters = new ConcurrentHashMap<>();

  /**
   * SSE 커넥트 함수
   * 
   * @param req http 헤더
   * @return
   * @throws Exception
   */
  public SseEmitter connect(HttpServletRequest req) throws Exception {
    User user = userService.getUserFromRequest(req);

    SseEmitter emitter = new SseEmitter(60 * 60 * 1000L); // 1시간
    emitters.put(user.getUserEmail(), emitter);

    emitter.onCompletion(() -> emitters.remove(user.getUserEmail()));
    emitter.onTimeout(() -> emitters.remove(user.getUserEmail()));

    try {
      emitter.send(SseEmitter.event().name("connect").data("SSE 연결됨"));
    } catch (IOException e) {
      emitter.completeWithError(e);
    }

    return emitter;
  }

  /**
   * 알림 전송 함수
   * 
   * @param user
   * @param vo
   * @throws Exception
   */
  private void send(User user, NotificationVo vo) throws Exception {
    SseEmitter emitter = emitters.get(user.getUserEmail());

    if (emitter != null) {
      try {
        ResponseVo<NotificationVo> payload = new ResponseVo<>(true, "알림 도착", vo);
        emitter.send(SseEmitter.event().name("notify").data(payload));
      } catch (IOException e) {
        emitters.remove(user.getUserEmail());
      }
    }
  }

  /**
   * 알림 저장 함수 저장 후 send()호출로 알림 전송
   * 
   * @param user
   * @param typeCode
   * @param message
   * @throws Exception
   */
  @Transactional
  public void instNotification(User user, NotificationTypeCode typeCode, String message)
      throws Exception {
    Notification notification = new Notification();
    notification.setUserSeq(user);
    notification.setNotificationTypeCode(typeCode);
    notification.setNotificationMessage(message);

    notificationRepository.save(notification);

    NotificationVo vo = new NotificationVo(notification.getNotificationMessage(),
        notification.getNotificationTypeCode(), false, notification.getUpdateDT());
    send(user, vo);
  }

  /**
   * 전체 알림 조회 함수
   * 
   * @param req http 헤더
   * @return List<NotificationVo>
   * @throws Exception
   */
  public List<NotificationVo> getNotificationListAll(HttpServletRequest req) throws Exception {
    User user = userService.getUserFromRequest(req);
    List<Notification> notificationList =
        notificationRepository.findByUserSeqOrderByCreateDTDesc(user);

    return dtoConverter.toDtoList(notificationList, NotificationVo.class);
  }

  /**
   * 안읽은 알림 조회 함수
   * 
   * @param req http 헤더
   * @return List<NotificationVo>
   * @throws Exception
   */
  public List<NotificationVo> getNotificationListIsReadFalse(HttpServletRequest req)
      throws Exception {
    User user = userService.getUserFromRequest(req);
    List<Notification> notificationList =
        notificationRepository.findByUserSeqAndNotificationReadNY(user, false);

    return dtoConverter.toDtoList(notificationList, NotificationVo.class);
  }

  /**
   * 읽음 처리 함수
   * 
   * @param notificationSeqList
   * @param req
   * @throws Exception
   */
  @Transactional
  public void markAsRead(List<Integer> notificationSeqList, HttpServletRequest req)
      throws Exception {
    User user = userService.getUserFromRequest(req);

    for (int notificationSeq : notificationSeqList) {

      Notification notification = notificationRepository.findById(notificationSeq)
          .orElseThrow(() -> new RuntimeException("알림 없음"));

      // 소유자 검증
      if (notification.getUserSeq().getSeq() != user.getSeq()) {
        throw new AccessDeniedException("권한 없음");
      }

      notification.setNotificationReadNY(true); // 읽음 처리
    }
  }

  /**
   * 알림 삭제 함수
   * 
   * @param notificationSeqList
   * @param req
   * @throws Exception
   */
  @Transactional
  public void deleteNotification(List<Integer> notificationSeqList, HttpServletRequest req)
      throws Exception {
    User user = userService.getUserFromRequest(req);

    for (int notificationSeq : notificationSeqList) {

      Notification notification = notificationRepository.findById(notificationSeq)
          .orElseThrow(() -> new RuntimeException("알림 없음"));

      // 소유자 검증
      if (notification.getUserSeq().getSeq() != user.getSeq()) {
        throw new AccessDeniedException("권한 없음");
      }

      notificationRepository.delete(notification);
    }
  }

}
