package com.example.listingservice.mapper;


import com.example.feignapi.vo.ListingCard;
import com.example.listingservice.dto.ListingSearchDTO;
import com.example.listingservice.model.Listing;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ListingMapper {
    // 获取所有房源
    List<Listing> getAllListings();

    // 根据ID获取房源
    Listing getListingById(Long id);

    // 根据房源类型ID获取房源
    List<Listing> getListingsByType(Long listingTypeId);

    // 创建房源
    void insert(Listing listing);

    // 更新房源
    void updateListing(Listing listing);

    // 删除房源
    int deleteListing(Long id);

    List<Listing> searchListings(ListingSearchDTO listingSearchDTO);

    List<ListingCard> searchListingsByFilters(ListingSearchDTO listingSearchDTO);

    void updateListingRating(Long id, Double rating);
}
