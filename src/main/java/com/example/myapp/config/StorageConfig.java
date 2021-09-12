package com.example.myapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class StorageConfig {
    @Value("${cloud.aws.credentials.access-key}")
    String accesskey;

    @Value("${cloud.aws.credentials.secret-key}")
    String accesssecret;

    @Value("${cloud.aws.region.static}")
    String region;

    /*@Bean("s3Client")
    public AmazonS3 s3Client(){
        AwsCredentials credentials = (AwsCredentials) new BasicAWSCredentials(accesskey,accesssecret);
        return AmazonS3ClientBuilder.standard().withCredentials(new AWSStaticCredentialsProvider((AWSCredentials) credentials)).withRegion(region).build();

    }*/
}
