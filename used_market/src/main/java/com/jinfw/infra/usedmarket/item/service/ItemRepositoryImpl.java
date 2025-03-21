package com.jinfw.infra.usedmarket.item.service;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.repository.ItemRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom {

  @PersistenceContext
  private EntityManager em;

  @Override
  public List<ItemVo> getItemList(int page, int size) {
    int offset = page * size;

    // TODO: 쿼리랑 dto 맞게 수정
    // TODO: uploadserver github 업데이트
    // TODO: 프론트 엔드 연동 시작
    String sql = """
            SELECT
                a.seq,
                a.user_seq
                a.itemTitle,
                a.itemDescription,
                a.itemPrice,
                b.imgUploadPath,
                b.imgUploadUuidname,
                b.imgUploadExt
            FROM item a
            JOIN imgupload b ON a.seq = b.imgPseq
            WHERE b.imgUploadSort = 1
              AND b.imgUploadStateCode = 'ENABLED'
              AND b.imgUploadTypeCode = 'ITEM'
            ORDER BY a.createDT DESC
            LIMIT %d OFFSET %d
        """.formatted(size, offset);

    List<?> rows = em.createNativeQuery(sql).getResultList();

    return rows.stream().map(row -> {
      Object[] obj = (Object[]) row;
      return new ItemVo(String.valueOf(obj[0]), // seq
          (String) obj[1], // itemTitle
          (BigDecimal) obj[2], // itemPrice
          (String) obj[3], // imgUploadPath
          (String) obj[4], // imgUploadUuidname
          (String) obj[5] // imgUploadExt
      );
    }).toList(); // List<ItemVo> 변환
  }
}
