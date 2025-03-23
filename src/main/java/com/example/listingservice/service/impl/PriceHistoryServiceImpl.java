package com.example.listingservice.service.impl;


import com.example.listingservice.mapper.PriceHistoryMapper;
import com.example.listingservice.model.PriceHistory;
import com.example.listingservice.service.PriceHistoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class PriceHistoryServiceImpl implements PriceHistoryService {

    private final PriceHistoryMapper priceHistoryMapper;


    @Override
    public void insert(Long listingId, BigDecimal price) {
        PriceHistory priceHistory = new PriceHistory();
        priceHistory.setListingId(listingId);
        priceHistory.setPrice(price);
        priceHistoryMapper.insert(priceHistory);
    }
}
