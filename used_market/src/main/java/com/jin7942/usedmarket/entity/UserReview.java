package com.jin7942.usedmarket.entity;

import java.time.LocalDateTime;
import com.jin7942.usedmarket.common.enums.CommonCode.UserReviewStatusCode;
import com.jin7942.usedmarket.common.utill.Utill;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class UserReview {

  /**
   * userReview 엔티티 정의
   */
  @Entity
  @Getter
  @Setter
  @NoArgsConstructor
  @Table(name = "userReview")
  public class Review {

    // TODO: 리뷰 대상 상품 컬럼 추가
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seq")
    private int seq; // PK

    @ManyToOne
    @JoinColumn(name = "user_seq", nullable = false) // FK
    private User userSeq; // 리뷰 작성자

    @ManyToOne
    @JoinColumn(name = "item_seq", nullable = false) // FK
    private Item itemSeq; // 리뷰 대상 상품

    // 리뷰 내용
    @Column(name = "userReviewComment", columnDefinition = "TEXT")
    private String userReviewComment;

    // 리뷰 상태 (공통 코드 ENUM)
    @Enumerated(EnumType.STRING)
    @Column(name = "userReviewStateCode", nullable = false)
    private UserReviewStatusCode userReviewStateCode = UserReviewStatusCode.ENABLED;

    // TODO: 베이스 엔티티 생성, 컬럼명 createDT 통일
    // TODO: erd 수정 및 스키마 재생성
    // 리뷰 생성 및 수정 날짜
    @Column(name = "userReviewUpdateDT", nullable = false, updatable = false)
    private LocalDateTime userReviewUpdateDT;

    @Column(name = "userUpdateDT", nullable = false)
    private LocalDateTime userUpdateDT;

    @PrePersist
    protected void onCreate() {
      this.userReviewUpdateDT = Utill.getCurrentTime();
      this.userUpdateDT = Utill.getCurrentTime();
    }

    @PreUpdate
    protected void onUpdate() {
      this.userUpdateDT = Utill.getCurrentTime();
    }
  }

}
