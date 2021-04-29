package com.lepa.portal.service;


import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.ecr.model.EmptyUploadException;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.google.common.io.Files;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.UUID;

@Slf4j
@Service
@Data
public class UploadService {

    private AWS3Service aws3Service;
    String pathToObject = "images/";

    @Autowired
    public UploadService(AWS3Service aws3Service) {
        this.aws3Service = aws3Service;
    }

    public String uploadToAWS(MultipartFile file) {
        if (file.isEmpty()) throw new EmptyUploadException("file is empty");
        String filename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();


        assert filename != null : "filename is null";

        String newFileName =
                Files.getNameWithoutExtension(filename)
                        + uuid.toString()
                        + "."
                        + Files.getFileExtension(filename);

        try {
            aws3Service.saveImage(file,pathToObject,newFileName);

        } catch (AmazonServiceException e) {
            log.info("Amazon problem");
            e.printStackTrace();
        } catch (SdkClientException e) {
            log.info("sdk error");
            e.printStackTrace();
        }
        return newFileName;

    }

    public S3ObjectInputStream loadImageFromAWS(String fileName){
     return aws3Service.loadImage(pathToObject,fileName);
    }


    public String uploadToFolder(MultipartFile file) {
        File uploadDirection = new File("src/main/resources/image");
        if (!uploadDirection.exists()) {
            uploadDirection.mkdirs();
        }
        String filename = file.getOriginalFilename();
        UUID uuid = UUID.randomUUID();

        assert filename != null;
        File image = new File("src/main/resources/image/"
                + Files.getNameWithoutExtension(filename)
                + uuid.toString() + "."
                + Files.getFileExtension(filename));

        try (OutputStream outputStream = new FileOutputStream(image); InputStream inputStream = file.getInputStream()) {
            IOUtils.copy(inputStream, outputStream);

        } catch (IOException e) {
            e.printStackTrace();
        }
        return image.getPath();
    }


}
