package com.example.listingservice.dto;


import com.example.listingservice.dto.search.Filters;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class ListingSearchDTO {
    private String address;
    private Map<String, LocalDateTime> dateRange;
    private Map<String,Integer> guests;
    private Filters filters;

}
