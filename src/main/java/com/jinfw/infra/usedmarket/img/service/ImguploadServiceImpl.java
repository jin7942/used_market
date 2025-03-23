package com.jinfw.infra.usedmarket.img.service;

import java.util.List;
import org.springframework.stereotype.Service;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.img.dto.ImguploadDto;
import com.jinfw.infra.usedmarket.img.entity.Imgupload;
import com.jinfw.infra.usedmarket.img.repository.ImguploadRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImguploadServiceImpl {

  private final ImguploadRepository imguploadRepository;
  private final UtilDtoConverter dtoConverter; // DTO 변환 유틸

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

}
