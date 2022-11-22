package com.a104.freeproject.domain.amazonS3.service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsS3Service {
    String BASE_URL = "https://initpjtbucket.s3.ap-northeast-2.amazonaws.com";
    String IMAGE_URL = "/images";

    String uploadFileOnlyOne(MultipartFile multipartFile);

    void deleteFile(String url);
}
