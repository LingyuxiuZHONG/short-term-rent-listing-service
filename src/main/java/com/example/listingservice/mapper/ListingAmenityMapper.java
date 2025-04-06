package com.example.listingservice.mapper;


import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ListingAmenityMapper {
    List<Long> getAmenityIdsByListingId(Long listingId);

    void addAmenities(Long listingId, List<Long> amenities);
}
