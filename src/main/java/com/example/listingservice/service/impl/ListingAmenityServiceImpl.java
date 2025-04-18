package com.example.listingservice.service.impl;

import com.example.listingservice.mapper.AmenityMapper;
import com.example.listingservice.mapper.ListingAmenityMapper;
import com.example.listingservice.service.ListingAmenityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ListingAmenityServiceImpl implements ListingAmenityService {

    private final ListingAmenityMapper listingAmenityMapper;

    private final AmenityMapper amenityMapper;

    @Override
    public List<String> getAmenitiesByListingId(Long listingId) {
        List<Long> amenityIds = listingAmenityMapper.getAmenityIdsByListingId(listingId);
        if(amenityIds.size() == 0){
            return new ArrayList<>();
        }
        List<String> amenities = amenityMapper.getAmenitiesByIds(amenityIds);
        return amenities;
    }

    @Override
    public void addAmenities(Long listingId, List<Long> amenities) {
        listingAmenityMapper.addAmenities(listingId, amenities);
    }
}
