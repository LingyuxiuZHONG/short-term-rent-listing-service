package com.example.listingservice.service;

import java.util.List;

public interface ListingAmenityService {
    List<String> getAmenitiesByListingId(Long listingId);
}
