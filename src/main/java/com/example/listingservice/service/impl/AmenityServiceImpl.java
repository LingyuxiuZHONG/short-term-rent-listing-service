package com.example.listingservice.service.impl;

import com.example.feignapi.vo.AmenityVO;
import com.example.listingservice.mapper.AmenityMapper;
import com.example.listingservice.model.Amenity;
import com.example.listingservice.service.AmenityService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@AllArgsConstructor
public class AmenityServiceImpl implements AmenityService {

    private final AmenityMapper amenityMapper;


    @Override
    public List<AmenityVO> getAmenities() {
        List<Amenity> amenityList = amenityMapper.getAmenities();
        return amenityList.stream()
                .map(this::convertToVO)
                .collect(Collectors.toList());
    }

    private AmenityVO convertToVO(Amenity amenity){
        AmenityVO amenityVO = new AmenityVO();

        BeanUtils.copyProperties(amenity,amenityVO);
        return amenityVO;
    }
}
