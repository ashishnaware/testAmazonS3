package com.example.myapp.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    @Autowired
    AmazonS3 s3Client;

    @Value("${cloud.aws.region.static}")
    String region;

    public String uploadFile(MultipartFile multipartFile){
        File fileObj = convertMultipartFileToFile(multipartFile);
        String filename = System.currentTimeMillis()+"_"+ multipartFile.getOriginalFilename();
        s3Client.putObject(new PutObjectRequest(bucketName,filename,fileObj));
        fileObj.delete();
        return "File uploaded:"+filename;
    }

    private File convertMultipartFileToFile(MultipartFile file){
        File convertedFile = new File(file.getOriginalFilename());
        try (FileOutputStream fos = new FileOutputStream(convertedFile)){
            fos.write(file.getBytes());
        }catch (IOException e){
            //log.error("Error converting file:",e);
            System.out.println(e);
        }
        return convertedFile;
    }
}
