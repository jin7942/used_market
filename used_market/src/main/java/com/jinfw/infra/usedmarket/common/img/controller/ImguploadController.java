package com.jinfw.infra.usedmarket.common.img.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.jinfw.infra.usedmarket.common.img.dto.ImguploadDto;
import com.jinfw.infra.usedmarket.common.img.service.ImguploadServiceImpl;
import lombok.RequiredArgsConstructor;

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
  public ResponseEntity<Boolean> instImgupload(@RequestBody ImguploadDto dto) throws Exception {

    boolean res = imguploadService.instImgupload(dto);

    return ResponseEntity.ok(res);
  }

}
