package com.example.listingservice.controller;


import com.example.common.ApiResponse;
import com.example.feignapi.vo.*;
import com.example.listingservice.dto.ListingCreateDTO;
import com.example.listingservice.dto.ListingSearchDTO;
import com.example.listingservice.dto.ListingUpdateDTO;
import com.example.listingservice.service.AmenityService;
import com.example.listingservice.service.ListingService;
import com.example.listingservice.service.ListingTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    private final ListingTypeService listingTypeService;

    private final AmenityService amenityService;


    @PostMapping("")
    public ResponseEntity<ApiResponse<ListingCard>> createListing(@Valid @RequestBody ListingCreateDTO listingCreateDTO) {
        // 调用服务层进行房源创建
        ListingCard listingCard = listingService.createListing(listingCreateDTO);
        return ResponseEntity.ok(ApiResponse.success("房源创建成功", listingCard));
    }

    @PutMapping("/{listingId}")
    public ResponseEntity<ApiResponse<ListingCard>> updateListing(@PathVariable Long listingId, @Valid @RequestBody ListingUpdateDTO listingUpdateDTO){
        ListingCard listingCard = listingService.updateListing(listingId,listingUpdateDTO);
        return ResponseEntity.ok(ApiResponse.success("房源更新成功", listingCard));
    }

    @PutMapping("/{id}/rating")
    public ResponseEntity<ApiResponse<String>> updateListingRating(@PathVariable Long id, @RequestBody Double rating){
        listingService.updateListingRating(id,rating);
        return ResponseEntity.ok(ApiResponse.success("房源评分更新成功"));
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<ApiResponse<ListingDetail>> getListingById(@PathVariable Long listingId) {
        // 调用服务层查询房源
        ListingDetail listingDetail = listingService.getListingById(listingId);
        return ResponseEntity.ok(ApiResponse.success("查询成功", listingDetail));
    }


    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ListingCard>>> getAllListings() {
        // 调用服务层查询所有房源
        List<ListingCard> listingCardList = listingService.getAllListings();
        return ResponseEntity.ok(ApiResponse.success("查询成功", listingCardList));
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteListing(@PathVariable Long id){
        listingService.deleteListing(id);
        return ResponseEntity.ok(ApiResponse.success("房源删除成功"));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<ListingCard>>> searchListings(@RequestBody ListingSearchDTO listingSearchDTO) {
        List<ListingCard> listingCardList = listingService.searchListings(listingSearchDTO);
        return ResponseEntity.ok(ApiResponse.success("查询成功", listingCardList));
    }

    @PostMapping("/favoriteListings")
    ResponseEntity<ApiResponse<List<FavoriteListing>>> getFavoriteListings(@RequestBody List<Long> listingIds){
        List<FavoriteListing> list = listingService.getFavoriteListings(listingIds);
        return ResponseEntity.ok(ApiResponse.success("查询成功",list));
    }



    @GetMapping("/listingTypes")
    ResponseEntity<ApiResponse<List<ListingTypeVO>>> getListingTypes(){
        List<ListingTypeVO> list = listingTypeService.getListingTypes();
        return ResponseEntity.ok(ApiResponse.success("查询成功", list));
    }

    @GetMapping("/amenities")
    ResponseEntity<ApiResponse<List<AmenityVO>>> getAmenities(){
        List<AmenityVO> list = amenityService.getAmenities();
        return ResponseEntity.ok(ApiResponse.success("查询成功", list));
    }


    @GetMapping("/{id}/summary")
    ResponseEntity<ApiResponse<ListingSummary>> getListingSummary(@PathVariable Long id){
        ListingSummary list = listingService.getListingSummary(id);
        return ResponseEntity.ok(ApiResponse.success("查询成功", list));
    }



    @GetMapping("/landlord/{hostId}")
    public ResponseEntity<ApiResponse<List<ListingManagementCard>>> getListingsByLandlordId(@PathVariable Long hostId) {
        List<ListingManagementCard> list = listingService.getListingsByHostId(hostId);
        return ResponseEntity.ok(ApiResponse.success("查询成功", list));
    }


}
