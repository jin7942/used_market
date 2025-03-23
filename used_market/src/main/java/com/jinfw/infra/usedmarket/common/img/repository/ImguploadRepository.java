package com.jinfw.infra.usedmarket.common.img.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.common.img.entity.Imgupload;

@Repository
public interface ImguploadRepository extends JpaRepository<Imgupload, Integer> {

}
