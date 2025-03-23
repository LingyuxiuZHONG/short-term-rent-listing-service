package com.example.listingservice.service;

import java.util.List;

public interface ImageService {
    void uploadImages(List<String> fileUrls, Long listingId);

    void deleteImage(Long listingId, Long imageId);

    void deleteImagesByListingId(Long listingId);

    List<String> getImagesUrlsByListingId(Long listingId);
}
