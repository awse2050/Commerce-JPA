package com.awse.commerce.service;

import com.awse.commerce.domains.util.s3.S3UploadService;
import lombok.extern.log4j.Log4j2;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@Log4j2
public class S3UploadServiceTests {

    @Autowired
    private S3UploadService s3UploadService;

    @DisplayName("저장된 이미지(볼펜) 찾기")
    @Test
    public void findImg() {
        String imgPath = s3UploadService.getThumbnailPath("bolpen.jpg");
        log.info(imgPath);
        Assertions.assertThat(imgPath).isNotNull();
    }

}
