package com.example.listingservice.mapper;

import com.example.listingservice.model.Listing;
import com.example.listingservice.model.PriceHistory;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface PriceHistoryMapper {
    void insert(PriceHistory priceHistory);

    void deleteByListingId(Long listingId);

    PriceHistory getPriceHistoryByListingId(Long id);

}
