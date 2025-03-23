package com.example.listingservice.mapper;

import com.example.listingservice.model.Image;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ImageMapper {

    void insert(Image image);

    Image getImageById(Long imageId);

    void delete(Long imageId);

    void deleteImageByListingId(Long listingId);

    List<String> getImagesByListingId(Long listingId);

    void insertImages(List<Image> images);
}
