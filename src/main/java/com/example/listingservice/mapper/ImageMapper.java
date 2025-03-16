package com.example.listingservice.mapper;

import com.example.listingservice.model.Image;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ImageMapper {

    void insert(Image image);

    Image getImageById(Long imageId);

    void delete(Long imageId);

    void deleteImageByListingId(Long listingId);
}
