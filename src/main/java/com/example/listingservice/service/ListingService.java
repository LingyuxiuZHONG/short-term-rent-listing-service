package com.example.listingservice.service;

import com.example.feignapi.vo.ListingCard;
import com.example.listingservice.dto.ListingCreateDTO;
import com.example.listingservice.dto.ListingSearchDTO;
import com.example.listingservice.dto.ListingUpdateDTO;

import java.util.List;

public interface ListingService {
    ListingCard createListing(ListingCreateDTO listingCreateDTO);

    void deleteListing(Long listingId);

    ListingCard updateListing(Long listingId, ListingUpdateDTO listingUpdateDTO);

    ListingCard getListingById(Long listingId);

    List<ListingCard> getAllListings();

    List<ListingCard> searchListings(ListingSearchDTO listingSearchDTO);

    void updateListingRating(Long id, Double rating);
}
