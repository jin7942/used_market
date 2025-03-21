package com.jinfw.infra.usedmarket.common.img;

import java.util.List;
import org.springframework.stereotype.Service;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImguploadServiceImpl {

  private final ImguploadRepository imguploadRepository;
  private final UtilDtoConverter dtoConverter; // DTO 변환 유틸

  public boolean instImgupload(ImguploadDto dto) throws Exception {

    List<Imgupload> uploads = dto.getImgList().stream().map(img -> {
      Imgupload imgupload = dtoConverter.toEntity(img, Imgupload.class);

      // 공통 필드 지정
      imgupload.setImgUploadTypeCode(img.getImgUploadTypeCode());

      return imgupload;
    }).toList();

    imguploadRepository.saveAll(uploads);

    return true;
  }

}
