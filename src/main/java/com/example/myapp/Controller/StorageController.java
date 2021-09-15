package com.example.myapp.Controller;

import com.example.myapp.service.StorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/file")
public class StorageController {

    @Autowired
    StorageService service;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam(value = "file")MultipartFile file,
                                             @RequestParam(value = "bucket")String bucket_name){
        return new ResponseEntity<>(service.uploadFile(file,bucket_name), HttpStatus.OK);
    }

    @PostMapping("/createWorkspace")
    public ResponseEntity<String> createBucket(@RequestParam(value = "bucketName")String bucketName){
        return new ResponseEntity<>(service.createBucket(bucketName), HttpStatus.OK);
    }

    @PostMapping("/deleteWorkspace")
    public ResponseEntity<String> deleteBucket(@RequestParam(value = "bucketName")String bucketName){
        return new ResponseEntity<>(service.deleteBucket(bucketName), HttpStatus.OK);
    }

}
