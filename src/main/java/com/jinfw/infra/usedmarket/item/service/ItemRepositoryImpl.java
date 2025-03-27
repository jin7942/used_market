package com.jinfw.infra.usedmarket.item.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.common.dto.PageInfoVo;
import com.jinfw.infra.usedmarket.item.dto.ItemListVo;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.repository.ItemRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

/**
 * 네이티브 쿼리 구현 클래스
 */
@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom {

  @PersistenceContext
  private EntityManager em;

  @Override
  public ItemListVo getItemList(int page, int size) {
    int offset = (page - 1) * size;

    // 전체 게시글 수 조회 (totalElements)
    String countSql = """
            SELECT COUNT(*) FROM item a
            JOIN imgupload b ON a.seq = b.imgPseq
            JOIN user c ON c.seq = a.user_seq
            WHERE b.imgUploadSort = 1
              AND b.imgUploadStateCode = 'ENABLED'
              AND b.imgUploadTypeCode = 'ITEM'
        """;

    int totalElements = ((Number) em.createNativeQuery(countSql).getSingleResult()).intValue();

    String sql = """
            SELECT
                a.seq,
                c.userNickname,
                a.itemTitle,
                a.itemDescription,
                a.itemPrice,
                a.updateDT,
                b.imgUploadPath,
                b.imgUploadUuidName,
                b.imgUploadExt
            FROM item a
            JOIN imgupload b ON a.seq = b.imgPseq
            JOIN user c ON c.seq = a.user_seq
            WHERE b.imgUploadSort = 1
              AND b.imgUploadStateCode = 'ENABLED'
              AND b.imgUploadTypeCode = 'ITEM'
            ORDER BY a.createDT DESC
            LIMIT %d OFFSET %d
        """.formatted(size, offset);

    List<?> rows = em.createNativeQuery(sql).getResultList();

    List<ItemVo> voList = rows.stream().map(row -> {
      Object[] obj = (Object[]) row;
      return new ItemVo(Integer.parseInt(String.valueOf(obj[0])), // item Pk
          (String) obj[1], // userNickname
          (String) obj[2], // itemTitle
          (String) obj[3], // itemDescirption
          (BigDecimal) obj[4], // itemPrice
          (LocalDateTime) obj[5], // updateDT
          (String) obj[6], // imgUploadPath
          (String) obj[7], // imgUploadUuidName
          (String) obj[8] // imgUploadExt
      );
    }).toList(); // List<ItemVo> 변환

    int totalPages = (int) Math.ceil((double) totalElements / size);
    PageInfoVo pageInfoVo = new PageInfoVo(page, size, totalElements, totalPages);

    return new ItemListVo(voList, pageInfoVo);
  }
}
