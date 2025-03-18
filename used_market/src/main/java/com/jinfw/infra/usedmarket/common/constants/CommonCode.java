package com.jinfw.infra.usedmarket.common.constants;

// TODO: 리파지토리 계층 구현
/**
 * 공통 코드 부분 ENUM 정의
 */
public class CommonCode {

  //
  // USER_ROLE: 유저 역할 (ADMIN: 관리자, USER: 일반)
  // USER_STATUS: 유저 상태 (ACTIVE: 정상, DISABLED: 탈퇴)
  // ITEM_STATUS: 상품 상태 (SELLING: 판매중, SOLD: 판매완료)
  // ORDER_STATUS:주문 상태 (PENDING:결제 중, COMPLETED: 결제 완료)
  // USER_REVIEW_STATUS: 유저 리뷰 상태 (ENABLED: 정상, DELETED: 삭제)
  // IMAGE_UPLOAD_STATUS: 이미지 업로드 상태(ENABLED: 정상, FAILED, 비정상)
  // NOTIFICATION_TYPE: 알림 타입 (EMERGENCY: 긴급, NORMAL: 일반)
  // IMAGE_TYPE: 이미지 타입 (PROFILE: 프로필 이미지, ITEM: 상품 이미지)
  //

  /** 유저 역할 */
  public enum UserRoleCode {
    USER, ADMIN;
  }

  /** 유저 상태 */
  public enum UserStatusCode {
    ACTIVE, DISABLED;
  }

  /** 상품 상태 */
  public enum ItemStatusCode {
    SELLING, SOLD;
  }

  /** 주문 상태 */
  public enum OrderStatusCode {
    PENDING, COMPLETED;
  }

  /** 유저 리뷰 상태 */
  public enum UserReviewStatusCode {
    ENABLED, DELETED;
  }

  /** 이미지 업로드 상태 */
  public enum ImageUploadStatusCode {
    ENABLED, FAILED;
  }

  /** 이미지 타입 */
  public enum ImageUploadTypeCode {
    PROFILE, ITEM;
  }

  /** 알림 타입 */
  public enum NotificationTypeCode {
    EMERGENCY, NORMAL;
  }
}
