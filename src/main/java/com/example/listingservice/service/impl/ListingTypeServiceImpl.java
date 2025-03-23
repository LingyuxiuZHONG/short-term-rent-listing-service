package com.example.listingservice.service.impl;

import com.example.feignapi.vo.ListingTypeVO;
import com.example.listingservice.mapper.ListingTypeMapper;
import com.example.listingservice.service.ListingTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class ListingTypeServiceImpl implements ListingTypeService {

    private final ListingTypeMapper listingTypeMapper;


    @Override
    public List<ListingTypeVO> getListingTypes() {
        return listingTypeMapper.getListingTypes();
    }
}
