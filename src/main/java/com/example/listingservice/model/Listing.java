package com.example.listingservice.model;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class Listing {
    private Long id;
    private String title;
    private String description;
    private String address;
    private Double latitude;
    private Double longitude;
    private Long hostId;  // 房东ID
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String status;
    private Long listingTypeId;  // 房源类型ID
    private BigDecimal price;
    private Integer bedrooms;
    private Integer bathrooms;
    private Double rating;
    private Boolean isFavorite;
}
