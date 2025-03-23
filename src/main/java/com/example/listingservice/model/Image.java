package com.example.listingservice.model;


import lombok.Data;

import java.time.LocalDateTime;

@Data
public class Image {
    private Long id;
    private Long listingId;
    private String imageUrl;
    private LocalDateTime createAt;

    public Image(Long listingId, String imageUrl){
        this.listingId = listingId;
        this.imageUrl = imageUrl;
    }
}
