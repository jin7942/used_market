package com.jinfw.infra.usedmarket.item.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jinfw.infra.usedmarket.common.constants.CommonCode.ItemStatusCode;
import com.jinfw.infra.usedmarket.common.util.UtilDtoConverter;
import com.jinfw.infra.usedmarket.item.dto.ItemDto;
import com.jinfw.infra.usedmarket.item.dto.ItemListVo;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.entity.Item;
import com.jinfw.infra.usedmarket.item.repository.ItemRepository;
import com.jinfw.infra.usedmarket.user.entity.User;
import com.jinfw.infra.usedmarket.user.service.UserServiceImpl;

import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl {

	private final ItemRepository itemRepository;
	private final UserServiceImpl userService;
	private final UtilDtoConverter dtoConverter; // DTO 변환 유틸

	/**
	 * itemSeq로 아이템 조회 함수
	 * 
	 * @param itemSeq
	 * @return Item 객체
	 */
	@Transactional(readOnly = true)
	public Item getItemById(int itemSeq) {
		return itemRepository.findById(itemSeq).orElseThrow(() -> new RuntimeException("상품 없음"));
	}

	/**
	 * 상품 등록 함수
	 * 
	 * @param reqDto
	 * @return 등록된 상품 seq 반환
	 */
	@Transactional
	public int instItem(ItemDto dto, HttpServletRequest req) throws Exception {
		// 토큰에서 이메일 추출
		User user = userService.getUserFromRequest(req);

		Item item = dtoConverter.toEntity(dto, Item.class);
		item.setUserSeq(user);
		itemRepository.save(item);

		return item.getSeq();
	}

	/**
	 * 상품 리스트 조회 및 검색 함수
	 * 
	 * @param page   현제 페이지
	 * @param size   페이지당 항목 수
	 * @param search 검색어
	 * @return List<ItemVo> 조회된 상품 리스트
	 */
	@Transactional(readOnly = true)
	public ItemListVo getItemList(int page, int size, String search) throws Exception {
		return itemRepository.getItemList(page, size, search);
	}

	/**
	 * 상품 단일 조회 함수
	 * 
	 * @param dto 조회할 상품 seq
	 * @return vo 조회된 상품 객체
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public ItemVo getItemOne(int seq) throws Exception {
		Item item = itemRepository.findById(seq).orElseThrow(() -> new EntityNotFoundException("상품이 존재하지 않습니다."));

		ItemVo vo = dtoConverter.toDto(item, ItemVo.class);

		User user = item.getUserSeq();
		vo.setUserNickname(user.getUserNickname());

		return vo;
	}

	/**
	 * 판매중인 상품 조회 함수
	 * 
	 * @param http 헤더
	 * @return List<ItemVo>
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<ItemVo> getItemSellingByUser(HttpServletRequest req) throws Exception {
		User user = userService.getUserFromRequest(req);
		List<Item> itemList = itemRepository.findByUserSeqAndItemStateCode(user, ItemStatusCode.SELLING);

		return dtoConverter.toItemVoList(itemList);
	}

	/**
	 * 판매한 상품 조회 함수
	 * 
	 * @param req http 헤더
	 * @return List<ItemVo>
	 * @throws Exception
	 */
	@Transactional(readOnly = true)
	public List<ItemVo> getItemSoldByUser(HttpServletRequest req) throws Exception {
		User user = userService.getUserFromRequest(req);
		List<Item> itemList = itemRepository.findByUserSeqAndItemStateCode(user, ItemStatusCode.SOLD);

		return dtoConverter.toItemVoList(itemList);
	}

}
