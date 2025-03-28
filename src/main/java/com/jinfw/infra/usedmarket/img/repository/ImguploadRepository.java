package com.jinfw.infra.usedmarket.img.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.img.dto.ImguploadVo;
import com.jinfw.infra.usedmarket.img.entity.Imgupload;

@Repository
public interface ImguploadRepository extends JpaRepository<Imgupload, Integer> {

  @Query(value = """
      SELECT
        imgPseq,
        imgUploadSort,
        imgUploadName,
        imgUploadUuidName,
        imgUploadPath,
        imgUploadExt,
        imgUploadTypeCode
      FROM imgupload
      WHERE imgPseq = :seq
        AND imgSort = 1
        AND imgUploadTypeCode = :type
      LIMIT 1
      """, nativeQuery = true)
  ImguploadVo findThumbnailByImgPseq(@Param("seq") int seq, @Param("type") String type);

  @Query(value = """
      SELECT
        imgPseq,
        imgUploadSort,
        imgUploadName,
        imgUploadUuidName,
        imgUploadPath,
        imgUploadExt,
        imgUploadTypeCode
      FROM imgupload
      WHERE imgPseq = :seq
        AND imgUploadTypeCode = :type
      ORDER BY imgUploadSort ASC
      """, nativeQuery = true)
  List<ImguploadVo> findImgListByImgPseq(@Param("seq") int seq, @Param("type") String type);
}
