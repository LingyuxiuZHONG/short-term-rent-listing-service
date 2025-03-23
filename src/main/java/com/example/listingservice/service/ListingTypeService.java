package com.example.listingservice.service;

import com.example.feignapi.vo.ListingTypeVO;

import java.util.List;

public interface ListingTypeService {
    List<ListingTypeVO> getListingTypes();
}
