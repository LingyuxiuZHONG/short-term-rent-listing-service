package com.example.listingservice.service.impl;

import com.example.common.exception.BusinessException;
import com.example.listingservice.mapper.ImageMapper;
import com.example.listingservice.mapper.ListingMapper;
import com.example.listingservice.model.Image;
import com.example.listingservice.model.Listing;
import com.example.listingservice.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {

    private final ListingMapper listingMapper;

    private final ImageMapper imageMapper;

    @Value("${file.upload-dir}")
    private String uploadDir;

    @Override
    public void uploadImages(List<String> fileUrls, Long listingId) {
        // 校验房源是否存在
        Listing listing = listingMapper.getListingById(listingId);
        if (listing == null) {
            throw new BusinessException("房源不存在");
        }

        List<Image> list = new ArrayList<>();
        for(String url : fileUrls){
            list.add(new Image(listingId,url));
        }

        imageMapper.insertImages(list);

    }

    @Override
    public void deleteImage(Long listingId, Long imageId) {
        Image image = imageMapper.getImageById(imageId);
        if(image == null){
            throw new BusinessException("图片未找到");
        }

        String imageUrl = image.getImageUrl();
        File file = new File(imageUrl);

        if(file.exists()){
            if(!file.delete()){
                throw new BusinessException("图片删除失败");
            }
        }

        imageMapper.delete(imageId);

    }

    @Override
    public void deleteImagesByListingId(Long listingId) {
        imageMapper.deleteImageByListingId(listingId);
    }

    @Override
    public List<String> getImagesUrlsByListingId(Long listingId) {

        return imageMapper.getImagesByListingId(listingId);
    }
}
