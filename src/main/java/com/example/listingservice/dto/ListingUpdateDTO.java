package com.example.listingservice.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ListingUpdateDTO {
    @Size(min = 3, max = 255, message = "标题长度必须在 3 到 255 字符之间")
    private String title;

    private String description;

    private String address;

    private Double latitude;

    private Double longitude;

    private String status;

    private Long listingTypeId;

    private BigDecimal price;
}
