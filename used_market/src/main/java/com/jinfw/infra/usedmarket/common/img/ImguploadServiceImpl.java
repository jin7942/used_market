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

  /**
   * 이지 업로드
   * 
   * @param dto 이미지 등록 요청 리스트
   * @return
   * @throws Exception
   */
  public boolean instImgupload(ImguploadDto dto) throws Exception {

    // 1. 이미지 엔티티 리스트 정의
    List<Imgupload> uploads = dto.getImgList().stream().map(img -> {
      // 2. Dto to Entity
      Imgupload imgupload = dtoConverter.toEntity(img, Imgupload.class);
      // 3. 변환된 엔티티 리턴
      return imgupload;
      // 4. 리스트로 변환
    }).toList();
    // 5. 리스트 저장
    imguploadRepository.saveAll(uploads);

    return true;
  }

}
