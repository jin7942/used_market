package com.jinfw.infra.usedmarket.img.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.jinfw.infra.usedmarket.img.entity.Imgupload;

@Repository
public interface ImguploadRepository extends JpaRepository<Imgupload, Integer> {

}
