package com.example.listingservice.mapper;


import com.example.feignapi.vo.ListingTypeVO;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ListingTypeMapper {

    String getNameOfType(Long id);

    List<ListingTypeVO> getListingTypes();
}
