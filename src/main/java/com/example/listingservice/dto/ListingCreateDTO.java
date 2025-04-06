package com.example.listingservice.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class ListingCreateDTO {
    @NotNull(message = "标题不能为空")
    @Size(min = 3, max = 255, message = "标题长度必须在 3 到 255 字符之间")
    private String title;

    private String description;

    @NotNull(message = "地址不能为空")
    private String address;

    private Double latitude;

    private Double longitude;

    @NotNull(message = "房东ID不能为空")
    private Long hostId;


    @NotNull(message = "房源类型不能为空")
    private Long listingTypeId;

    @NotNull(message = "价格不能为空")
    @Positive(message = "价格必须为正数")
    private BigDecimal price;

    @NotNull(message = "最大人数不能为空")
    @Positive(message = "最大人数必须为正数")
    private Integer maxGuests;

    @NotNull(message = "房间数不能为空")
    @Positive(message = "房间数必须为正数")
    private Integer bedrooms;

    @NotNull(message = "浴室数不能为空")
    @Positive(message = "浴室数必须为正数")
    private Integer bathrooms;

    private String checkInInstructions;

    @NotNull(message = "取消政策不能为空")
    private Integer cancelPolicy;

    private List<Long> amenities;

    private List<String> rules;

}
