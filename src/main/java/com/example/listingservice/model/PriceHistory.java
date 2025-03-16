package com.example.listingservice.model;


import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class PriceHistory {

    private Long id;                    // 价格历史记录ID
    private Long listingId;             // 房源ID
    private BigDecimal price;               // 价格
    private LocalDateTime createAt;
}
