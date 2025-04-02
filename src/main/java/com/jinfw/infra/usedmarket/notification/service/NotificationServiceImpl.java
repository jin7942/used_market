package com.jinfw.infra.usedmarket.notification.service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.stereotype.Service;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.NotificationTypeCode;
import com.jinfw.infra.usedmarket.common.dto.ResponseVo;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.item.service.ItemServiceImpl;
import com.jinfw.infra.usedmarket.notification.dto.NotificationVo;
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

  public void send(HttpServletRequest req, String message, NotificationTypeCode typeCode)
      throws Exception {
    User user = userService.getUserFromRequest(req);
    SseEmitter emitter = emitters.get(user.getUserEmail());

    if (emitter != null) {
      NotificationVo vo = new NotificationVo(message, typeCode);
      ResponseVo<NotificationVo> payload = new ResponseVo<>(true, "알림 도착", vo);

      try {
        emitter.send(SseEmitter.event().name("notify").data(payload));
      } catch (IOException e) {
        emitters.remove(user.getUserEmail());
      }
    }
  }
}
