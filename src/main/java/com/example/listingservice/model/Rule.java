package com.example.listingservice.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Rule {
    private Long id;
    private Long listingId;
    private String description;
    private LocalDateTime createdAt;
}
