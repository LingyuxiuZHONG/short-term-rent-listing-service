package com.example.listingservice.controller;


import com.example.common.ApiResponse;
import com.example.feignapi.vo.ListingCard;
import com.example.listingservice.dto.ListingCreateDTO;
import com.example.listingservice.dto.ListingSearchDTO;
import com.example.listingservice.dto.ListingUpdateDTO;
import com.example.listingservice.service.ListingService;
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

    @PutMapping("/api/listings/{id}/rating")
    public ResponseEntity<ApiResponse<String>> updateListingRating(@PathVariable Long id, @RequestBody Double rating){
        listingService.updateListingRating(id,rating);
        return ResponseEntity.ok(ApiResponse.success("房源评分更新成功"));
    }

    @GetMapping("/{listingId}")
    public ResponseEntity<ApiResponse<ListingCard>> getListingById(@PathVariable Long listingId) {
        // 调用服务层查询房源
        ListingCard listingCard = listingService.getListingById(listingId);
        return ResponseEntity.ok(ApiResponse.success("查询成功", listingCard));
    }

    @GetMapping("/test")
    public ResponseEntity<ApiResponse<String>> getListingById() {
        return ResponseEntity.ok(ApiResponse.success("成功"));
    }

    @GetMapping("")
    public ResponseEntity<ApiResponse<List<ListingCard>>> getAllListings() {
        // 调用服务层查询所有房源
        List<ListingCard> listingCardList = listingService.getAllListings();
        return ResponseEntity.ok(ApiResponse.success("查询成功", listingCardList));
    }


    @DeleteMapping("/{listingId}")
    public ResponseEntity<ApiResponse<String>> deleteListing(@PathVariable Long listingId){
        listingService.deleteListing(listingId);
        return ResponseEntity.ok(ApiResponse.success("房源删除成功"));
    }

    @PostMapping("/search")
    public ResponseEntity<ApiResponse<List<ListingCard>>> searchListings(@RequestBody ListingSearchDTO listingSearchDTO) {
        List<ListingCard> listingCardList = listingService.searchListings(listingSearchDTO);
        return ResponseEntity.ok(ApiResponse.success("查询成功", listingCardList));
    }



}
