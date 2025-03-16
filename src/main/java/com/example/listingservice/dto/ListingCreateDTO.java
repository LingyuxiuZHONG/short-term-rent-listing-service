package com.example.listingservice.dto;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;

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

    private String status;

    @NotNull(message = "房源类型不能为空")
    private Long listingTypeId;

    @NotNull(message = "价格不能为空")
    @Positive(message = "价格必须为正数")
    private BigDecimal price;

}
