package com.jinfw.infra.usedmarket.notification.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import com.jinfw.infra.usedmarket.common.dto.ResponseVo;
import com.jinfw.infra.usedmarket.notification.dto.NotificationVo;
import com.jinfw.infra.usedmarket.notification.service.NotificationServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Tag(name = "Notification API", description = "알림, SSE 관련 API")
@RestController
@RequestMapping("/api/notification")
@RequiredArgsConstructor
public class NotificationController {

  private final NotificationServiceImpl notificationService;

  // SSE 커넥트 API
  @Operation(summary = "sse 연결", description = "sse 커넥트 API 입니다.")
  @GetMapping("/sse/connect")
  public SseEmitter connect(@RequestParam String token) throws Exception {
    return notificationService.connect(token);
  }

  // 전체 알림 조회 API
  @Operation(summary = "전체 알림 조회", description = "전체 알림 조회 API 입니다.")
  @GetMapping("/getListAll")
  public ResponseEntity<ResponseVo<List<NotificationVo>>> getNotificationListAll(
      HttpServletRequest req) throws Exception {

    List<NotificationVo> voList = notificationService.getNotificationListAll(req);

    return ResponseEntity.ok(new ResponseVo<>(true, "조회 성공", voList));
  }

  // 안읽은 알림 조회 API
  @Operation(summary = "안읽은 알림 조회", description = "안읽은 알림 조회 API 입니다.")
  @GetMapping("/getListIsReadFalse")
  public ResponseEntity<ResponseVo<List<NotificationVo>>> getNotificationListIsReadFalse(
      HttpServletRequest req) throws Exception {

    List<NotificationVo> voList = notificationService.getNotificationListIsReadFalse(req);

    return ResponseEntity.ok(new ResponseVo<>(true, "조회 성공", voList));
  }

  // 읽음 처리 API
  @Operation(summary = "알림 읽음 처리", description = "알림 읽음을 처리하는 API 입니다.")
  @PostMapping("/markAsRead")
  public ResponseEntity<ResponseVo<Boolean>> markAsRead(
      @RequestBody List<Integer> notificationSeqList, HttpServletRequest req) throws Exception {

    markAsRead(notificationSeqList, req);

    return ResponseEntity.ok(new ResponseVo<>(true, "읽음 처리 성공", true));
  }

  // 알림 삭제 API
  @Operation(summary = "알림 삭제", description = "알림을 삭제하는 API 입니다.")
  @DeleteMapping("/remove")
  public ResponseEntity<ResponseVo<String>> deleteNotifications(
      @RequestBody List<Integer> notificationSeqList, HttpServletRequest req) throws Exception {

    notificationService.deleteNotification(notificationSeqList, req);

    return ResponseEntity.ok(new ResponseVo<>(true, "알림 삭제 완료", null));
  }

}
