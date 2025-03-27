package com.jinfw.infra.usedmarket.img.controller;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.jinfw.infra.usedmarket.common.dto.ResponseVo;
import com.jinfw.infra.usedmarket.img.dto.ImguploadDto;
import com.jinfw.infra.usedmarket.img.dto.ImguploadVo;
import com.jinfw.infra.usedmarket.img.service.ImguploadServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Imgupload API", description = "이미지 처리 관련 API")
@RestController
@RequestMapping("/api/imges")
@RequiredArgsConstructor
public class ImguploadController {

  private final ImguploadServiceImpl imguploadService;

  // 이미지 등록 API
  @Operation(summary = "이미지 업로드", description = "이미지 서버에 저장된 정보를 업로드 합니다.")
  @PostMapping("/upload")
  public ResponseEntity<ResponseVo<Boolean>> instImgupload(@RequestBody ImguploadDto dto)
      throws Exception {

    ResponseVo<Boolean> res = new ResponseVo<>(true, "업로드 성공", imguploadService.instImgupload(dto));

    return ResponseEntity.ok(res);
  }

  // 썸네일 조회 API
  @Operation(summary = "썸네일 조회", description = "상품 썸네일을 조회 합니다.")
  @GetMapping("/getImgThum")
  public ResponseEntity<ResponseVo<ImguploadVo>> getImgOne(@RequestParam int seq) throws Exception {

    ImguploadVo vo = imguploadService.getImgOne(seq);
    ResponseVo<ImguploadVo> res = new ResponseVo<>(true, "썸네일 조회 성공", vo);

    return ResponseEntity.ok(res);
  }

  // 이미지 리스트 조회
  @GetMapping("getImgList")
  public ResponseEntity<ResponseVo<List<ImguploadVo>>> getImgList(@RequestParam int seq)
      throws Exception {

    List<ImguploadVo> vo = imguploadService.getImgList(seq);
    ResponseVo<List<ImguploadVo>> res = new ResponseVo<>(true, "이미지 리스트 조회 성공", vo);

    return ResponseEntity.ok(res);
  }

}
