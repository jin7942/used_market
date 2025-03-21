package com.jinfw.infra.usedmarket.common.img;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ImguploadRepository extends JpaRepository<Imgupload, Integer> {

}
