package com.jinfw.infra.usedmarket.item.service;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.jinfw.infra.usedmarket.common.dto.PageInfoVo;
import com.jinfw.infra.usedmarket.item.dto.ItemListVo;
import com.jinfw.infra.usedmarket.item.dto.ItemVo;
import com.jinfw.infra.usedmarket.item.repository.ItemRepositoryCustom;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

/**
 * 네이티브 쿼리 구현 클래스
 */
@Repository
public class ItemRepositoryImpl implements ItemRepositoryCustom {

	@PersistenceContext
	private EntityManager em;

	@Override
	@Transactional(readOnly = true)
	public ItemListVo getItemList(int page, int size, String search) {
		int offset = (page - 1) * size;
		boolean hasSearch = search != null && !search.isBlank();

		// 전체 게시글 수 조회 (totalElements)
		StringBuilder countSql = new StringBuilder("""
					SELECT COUNT(*) FROM item a
					JOIN imgupload b ON a.seq = b.imgPseq
					JOIN user c ON c.seq = a.user_seq
					WHERE b.imgUploadSort = 0
					  AND b.imgUploadStateCode = 'ENABLED'
					  AND b.imgUploadTypeCode = 'ITEM'
				""");

		if (hasSearch) {
			countSql.append(" AND a.itemTitle LIKE CONCAT('%', :search, '%')");
		}

		Query countQuery = em.createNativeQuery(countSql.toString());
		if (hasSearch) {
			countQuery.setParameter("search", search);
		}
		int totalElements = ((Number) countQuery.getSingleResult()).intValue();

		// 데이터 조회
		StringBuilder sql = new StringBuilder("""
					SELECT
						a.seq,
						c.userNickname,
						a.itemTitle,
						a.itemDescription,
						a.itemPrice,
						a.updateDT,
						b.imgUploadPath,
						b.imgUploadUuidName,
						b.imgUploadExt
					FROM item a
					JOIN imgupload b ON a.seq = b.imgPseq
					JOIN user c ON c.seq = a.user_seq
					WHERE b.imgUploadSort = 0
					  AND b.imgUploadStateCode = 'ENABLED'
					  AND b.imgUploadTypeCode = 'ITEM'
				""");

		if (hasSearch) {
			sql.append(" AND a.itemTitle LIKE CONCAT('%', :search, '%')");
		}
		sql.append(" ORDER BY a.createDT DESC LIMIT :limit OFFSET :offset");

		Query dataQuery = em.createNativeQuery(sql.toString());
		if (hasSearch) {
			dataQuery.setParameter("search", search);
		}
		dataQuery.setParameter("limit", size);
		dataQuery.setParameter("offset", offset);

		List<?> rows = dataQuery.getResultList();

		// List<ItemVo> 변환
		List<ItemVo> voList = rows.stream().map(row -> {
			Object[] obj = (Object[]) row;
			return new ItemVo(((Number) obj[0]).intValue(), // item Pk
					(String) obj[1], // userNickname
					(String) obj[2], // itemTitle
					(String) obj[3], // itemDescription
					((Number) obj[4]).intValue(), // itemPrice
					((Timestamp) obj[5]).toLocalDateTime(), // updateDT
					(String) obj[6], // imgUploadPath
					(String) obj[7], // imgUploadUuidName
					(String) obj[8] // imgUploadExt
			);
		}).toList();

		// 페이징 정보 구성
		int totalPages = (int) Math.ceil((double) totalElements / size);
		PageInfoVo pageInfoVo = new PageInfoVo(page, size, totalElements, totalPages);

		return new ItemListVo(voList, pageInfoVo);
	}
}
