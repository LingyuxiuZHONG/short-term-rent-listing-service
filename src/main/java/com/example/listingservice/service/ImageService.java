package com.example.listingservice.service;

import com.example.listingservice.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ImageService {
    List<Image> uploadImages(List<MultipartFile> images, Long listingId);

    void deleteImage(Long listingId, Long imageId);

    void deleteImagesByListingId(Long listingId);
}
