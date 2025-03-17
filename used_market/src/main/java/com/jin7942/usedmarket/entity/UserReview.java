package com.jin7942.usedmarket.entity;

import com.jin7942.usedmarket.common.enums.CommonCode.UserReviewStatusCode;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


/**
 * userReview 엔티티 정의
 */
@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "userReview")
public class UserReview extends BaseEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "seq")
  private int seq; // PK

  @ManyToOne
  @JoinColumn(name = "user_seq", nullable = false) // FK
  private User userSeq; // 리뷰 작성자

  // 리뷰 내용
  @Column(name = "userReviewComment", columnDefinition = "TEXT")
  private String userReviewComment;

  // 리뷰 상태 (공통 코드 ENUM)
  @Enumerated(EnumType.STRING)
  @Column(name = "userReviewStateCode", nullable = false)
  private UserReviewStatusCode userReviewStateCode = UserReviewStatusCode.ENABLED;

}

