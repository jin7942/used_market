package com.jinfw.infra.usedmarket.img.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jinfw.infra.usedmarket.common.dto.ResponseVo;
import com.jinfw.infra.usedmarket.img.dto.ImguploadDto;
import com.jinfw.infra.usedmarket.img.service.ImguploadServiceImpl;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Imgupload API", description = "이미지 처리 관련 API")
@RestController
@RequestMapping("/api/imges")
@RequiredArgsConstructor
public class ImguploadController {

  private final ImguploadServiceImpl imguploadService;

  /**
   * 이미지 업로드 API
   * 
   * @param dto 이미지 등록 요청 리스트
   * @return true / false
   * @throws Exception
   */
  @PostMapping("/upload")
  public ResponseEntity<ResponseVo<Boolean>> instImgupload(@RequestBody ImguploadDto dto)
      throws Exception {

    ResponseVo<Boolean> res = new ResponseVo<>(true, "업로드 성공", imguploadService.instImgupload(dto));

    return ResponseEntity.ok(res);
  }

}
