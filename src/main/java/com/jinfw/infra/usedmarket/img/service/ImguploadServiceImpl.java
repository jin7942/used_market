package com.jinfw.infra.usedmarket.img.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.jinfw.infra.usedmarket.common.constants.CommonCode.ImageUploadTypeCode;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.img.dto.ImguploadDto;
import com.jinfw.infra.usedmarket.img.dto.ImguploadVo;
import com.jinfw.infra.usedmarket.img.entity.Imgupload;
import com.jinfw.infra.usedmarket.img.repository.ImguploadRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImguploadServiceImpl {

  private final ImguploadRepository imguploadRepository;
  private final UtilDtoConverter dtoConverter; // DTO 변환 유틸

  // TODO: 공통필드 수정 프로필인지, 상품인지
  /**
   * 이미지 업로드
   * 
   * @param dto 이미지 등록 요청 리스트
   * @return
   * @throws Exception
   */
  public boolean instImgupload(ImguploadDto dto) throws Exception {

    List<Imgupload> uploads = dto.getImgList().stream().map(img -> {
      Imgupload imgupload = dtoConverter.toEntity(img, Imgupload.class);
      return imgupload;
    }).toList();
    imguploadRepository.saveAll(uploads);

    return true;
  }

  // TODO: JPQL 좆까 네이티브 쿼리로 간다
  /**
   * 이미지 썸네일 조회
   * 
   * @param itemSeq
   * @return ImguploadVo
   * @throws Exception
   */
  public ImguploadVo getImgOne(int seq) throws Exception {
    return imguploadRepository.findThumbnailByImgPseq(seq, ImageUploadTypeCode.ITEM.name());
  }

  /**
   * 이미지 리스트 조회
   * 
   * @param itemSeq
   * @return List<ImguploadVo>
   * @throws Exception
   */
  public List<ImguploadVo> getImgList(int seq) throws Exception {
    return imguploadRepository.findImgListByImgPseq(seq, ImageUploadTypeCode.ITEM.name());
  }

}
