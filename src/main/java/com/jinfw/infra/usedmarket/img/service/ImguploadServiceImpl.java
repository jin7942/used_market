package com.jinfw.infra.usedmarket.img.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

	/**
	 * 이미지 업로드
	 * 
	 * @param dto 이미지 등록 요청 리스트
	 * @return
	 * @throws Exception
	 */
	@Transactional
	public boolean instImgupload(ImguploadDto dto) throws Exception {

		List<Imgupload> uploads = dto.getImgList().stream().map(img -> {
			Imgupload imgupload = dtoConverter.toEntity(img, Imgupload.class);
			return imgupload;
		}).toList();
		imguploadRepository.saveAll(uploads);

		return true;
	}

	/**
	 * 썸네일 조회
	 * 
	 * @param itemSeq
	 * @return
	 */
	@Transactional(readOnly = true)
	public ImguploadVo getImgOne(int seq) {
		return imguploadRepository.findThumbnailByImgPseq(seq, ImageUploadTypeCode.ITEM.name());
	}

	/**
	 * 이미지 리스트 조회
	 * 
	 * @param itemSeq
	 * @return
	 */
	@Transactional(readOnly = true)
	public List<ImguploadVo> getImgList(int seq) {
		return imguploadRepository.findImgListByImgPseq(seq, ImageUploadTypeCode.ITEM.name());
	}

}
