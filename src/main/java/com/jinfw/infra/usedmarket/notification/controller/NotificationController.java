package com.jinfw.infra.usedmarket.notification.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.jinfw.infra.usedmarket.notification.service.NotificationServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/sse")
public class NotificationController {

  private final NotificationServiceImpl notificationService;

  @GetMapping("/connect")
  public SseEmitter connect(HttpServletRequest req) throws Exception {
    return notificationService.connect(req);
  }

}
