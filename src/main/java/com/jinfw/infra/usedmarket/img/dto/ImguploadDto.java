package com.jinfw.infra.usedmarket.img.dto;

import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Imgupload request Dto
 */
@Getter
@Setter
public class ImguploadDto {

  List<ImguploadInfoDto> imgList;

}
