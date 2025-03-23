package com.example.listingservice.controller;

import com.example.common.ApiResponse;
import com.example.listingservice.model.Image;
import com.example.listingservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ImageController {

    private final ImageService imageService;


    @PostMapping("/{listingId}/images")
    public ResponseEntity<ApiResponse<String>> uploadImages(
            @PathVariable Long listingId,
            @RequestParam("imageFiles") List<String> fileUrls){

        imageService.uploadImages(fileUrls, listingId);

        return ResponseEntity.ok(ApiResponse.success("图片上传成功"));
    }


    @DeleteMapping("/{listingId}/images/{imageId}")
    public ResponseEntity<ApiResponse<String>> deleteImage(
            @PathVariable Long listingId,
            @PathVariable Long imageId) {

        imageService.deleteImage(listingId, imageId);

        return ResponseEntity.ok(ApiResponse.success("图片删除成功"));
    }
}
