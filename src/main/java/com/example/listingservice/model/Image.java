package com.example.listingservice.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Image {
    private Long id;
    private Long listingId;
    private String imageURL;
    private LocalDateTime createAt;
}
