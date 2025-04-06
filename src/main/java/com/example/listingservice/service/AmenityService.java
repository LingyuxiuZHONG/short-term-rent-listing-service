package com.example.listingservice.service;

import com.example.feignapi.vo.AmenityVO;

import java.util.List;

public interface AmenityService {
    List<AmenityVO> getAmenities();
}
