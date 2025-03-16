package com.example.listingservice.dto.search;


import lombok.Data;

import java.util.List;

@Data
public class Filters {
    private List<Integer> priceRange;
    private List<String> propertyType;
    private String bedrooms;
    private String bathrooms;
    private List<String> amenities;
    private Integer amenitiesCount;

}