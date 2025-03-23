package com.example.listingservice.mapper;

import com.example.listingservice.model.Amenity;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AmenityMapper {

    List<String> getAmenities(List<Long> amenityIds);
}
