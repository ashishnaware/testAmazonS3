package com.example.myapp.service;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.auth.credentials.AwsCredentials;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Service
public class StorageService {

    @Value("${application.bucket.name}")
    private String bucketName;

    /*@Autowired
    @Qualifier("s3Client")
    private AmazonS3 s3Client;*/
    @Value("${cloud.aws.credentials.access-key}")
    String accesskey;

    @Value("${cloud.aws.credentials.secret-key}")
    String accesssecret;

    @Value("${cloud.aws.region.static}")
    String region;

    public String uploadFile(MultipartFile multipartFile){
        File fileObj = convertMultipartFileToFile(multipartFile);
        BasicAWSCredentials credentials = new BasicAWSCredentials(accesskey,accesssecret);
        AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider((AWSCredentials) credentials)).withRegion(region).build();
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
