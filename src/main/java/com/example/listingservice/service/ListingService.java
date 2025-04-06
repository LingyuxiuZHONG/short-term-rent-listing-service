package com.example.listingservice.service;

import com.example.feignapi.vo.*;
import com.example.listingservice.dto.ListingCreateDTO;
import com.example.listingservice.dto.ListingSearchDTO;
import com.example.listingservice.dto.ListingUpdateDTO;

import java.util.List;

public interface ListingService {
    ListingCard createListing(ListingCreateDTO listingCreateDTO);

    void deleteListing(Long listingId);

    ListingCard updateListing(Long listingId, ListingUpdateDTO listingUpdateDTO);

    ListingDetail getListingById(Long listingId);


    List<ListingCard> getAllListings();

    List<ListingCard> searchListings(ListingSearchDTO listingSearchDTO);

    void updateListingRating(Long id, Double rating);

    List<FavoriteListing> getFavoriteListings(List<Long> listingId);

    List<ListingManagementCard> getListingsByHostId(Long hostId);

    ListingSummary getListingSummary(Long id);
}
