package com.example.listingservice.service;

import java.math.BigDecimal;

public interface PriceHistoryService {
    void insert(Long id, BigDecimal price);
}
