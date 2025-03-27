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

  @Query("SELECT new com.jinfw.infra.usedmarket.img.dto.ImguploadVo("
      + "i.imgPseq, i.imgSort, i.imgUploadName, "
      + "i.imgUploadUuidName, i.imgUploadPath, i.imgUploadExt, i.imgUploadTypeCode) "
      + "FROM Imgupload i "
      + "WHERE i.imgPseq = :seq AND i.imgSort = 1 AND i.imgUploadTypeCode = :type")
  ImguploadVo findThumbnailByImgPseq(@Param("seq") int seq, @Param("type") String type);

  @Query("SELECT new com.jinfw.infra.usedmarket.img.dto.ImguploadVo("
      + "i.imgPseq, i.imgSort, i.imgUploadName, "
      + "i.imgUploadUuidName, i.imgUploadPath, i.imgUploadExt, i.imgUploadTypeCode) "
      + "FROM Imgupload i " + "WHERE i.imgPseq = :seq AND i.imgUploadTypeCode = :type "
      + "ORDER BY i.imgSort ASC")
  List<ImguploadVo> findImgListByImgPseq(@Param("seq") int seq, @Param("type") String type);
}
