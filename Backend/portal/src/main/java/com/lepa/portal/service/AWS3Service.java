package com.lepa.portal.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;


@Slf4j
@Service
@Data
public class AWS3Service {

    @Value("${aws.accesskey: default Value}")
    private String ACCESS_KEY;

    @Value("${aws.secretkey: default Value}")
    private String SECRET_KEY;
    private static final String APPIMAGEBUCKET = "appimagebucket";

    public AmazonS3 amazonClient() {
        return AmazonS3ClientBuilder
                .standard()
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY,SECRET_KEY)))
                .withRegion(Regions.US_EAST_2)
                .build();
    }

    public void bucketCheck(String bucketName) {
        log.info("check bucket is exist");
        if (amazonClient().doesBucketExistV2(bucketName)) {
            log.info("bucket exist");
        } else {
            amazonClient().createBucket(bucketName);
        }
    }

    public void saveImage(MultipartFile file, String pathToObject, String newFileName) {
        ObjectMetadata metadata = new ObjectMetadata();

        metadata.setContentType(file.getContentType());
        metadata.setContentLength(file.getSize());
        metadata.setHeader("filename", newFileName);

        ByteArrayInputStream bas = null;
        try {
            bas = new ByteArrayInputStream(file.getBytes());
        } catch (IOException e) {
            log.info("io error");
            e.printStackTrace();
        }
        bucketCheck(APPIMAGEBUCKET);
        amazonClient().putObject(APPIMAGEBUCKET, pathToObject + newFileName, bas, metadata);
    }

    public S3ObjectInputStream loadImage(String pathToObject, String fileName) {
        S3Object s3Object = amazonClient().getObject(APPIMAGEBUCKET, pathToObject + fileName);
        return s3Object.getObjectContent();
    }


}



