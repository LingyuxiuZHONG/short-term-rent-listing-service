package com.example.listingservice.service.impl;

import com.example.common.exception.BusinessException;
import com.example.feignapi.clients.BookingClient;
import com.example.feignapi.clients.ReviewClient;
import com.example.feignapi.clients.UserClient;
import com.example.feignapi.dto.CheckDateAvailabilityDTO;
import com.example.feignapi.vo.*;
import com.example.listingservice.dto.ListingCreateDTO;
import com.example.listingservice.dto.ListingSearchDTO;
import com.example.listingservice.dto.ListingUpdateDTO;
import com.example.listingservice.mapper.*;
import com.example.listingservice.model.Listing;
import com.example.listingservice.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class ListingServiceImpl implements ListingService {

    private final UserClient userClient;

    private final BookingClient bookingClient;

    private final ReviewClient reviewClient;

    private final ImageService imageService;

    private final RuleService ruleService;

    private final ListingAmenityService listingAmenityService;

    private final ListingMapper listingMapper;


    private final PriceHistoryService priceHistoryService;

    private final ListingTypeMapper listingTypeMapper;




    @Transactional
    @Override
    public ListingCard createListing(ListingCreateDTO listingCreateDTO) {
        log.info("创建房源开始，房东ID：{}", listingCreateDTO.getHostId());

        // 1. 检查房东是否存在
        UserVO user = userClient.getUserById(listingCreateDTO.getHostId()).getBody().getData();
        if (user == null) {
            log.warn("房东不存在，无法创建房源，房东ID：{}", listingCreateDTO.getHostId());
            throw new BusinessException("房东不存在，无法创建房源");
        }

        // 2. 检查房东角色权限
        if (user.getRoleType() == 1) {
            log.warn("角色无权限创建房源，ID：{}", listingCreateDTO.getHostId());
            throw new BusinessException("此角色无权限创建房源");
        }

        // 创建房源
        Listing listing = new Listing();
        BeanUtils.copyProperties(listingCreateDTO, listing);
        listing.setPrice(listingCreateDTO.getPrice());
        listingMapper.insert(listing);
        log.info("房源创建成功，房源ID：{}", listing.getId());

        // 创建价格历史
        priceHistoryService.insert(listing.getId(),listing.getPrice());
        log.info("房源价格历史记录已插入，房源ID：{}", listing.getId());

        // 设置设施
        if(listingCreateDTO.getAmenities() != null && !listingCreateDTO.getAmenities().isEmpty()){
            listingAmenityService.addAmenities(listing.getId(), listingCreateDTO.getAmenities());
            log.info("房源设施记录已插入，房源ID：{}", listing.getId());
        }

        // 设置入住规则
        if(listingCreateDTO.getRules() != null && !listingCreateDTO.getRules().isEmpty()){
            ruleService.addRules(listing.getId(), listingCreateDTO.getRules());
            log.info("房源入住规则记录已插入，房源ID：{}", listing.getId());
        }

        return convertToListingCard(listing);
    }

    @Transactional
    @Override
    public void deleteListing(Long listingId) {
        log.info("删除房源开始，房源ID：{}", listingId);

        // 1. 检查房源是否存在
        Listing listing = listingMapper.getListingById(listingId);
        if (listing == null) {
            log.warn("房源不存在，房源ID：{}", listingId);
            throw new BusinessException("房源不存在");
        }

        // 2. 删除房源
        int rows = listingMapper.deleteListing(listingId);
        if (rows == 0) {
            log.warn("删除房源失败，房源ID：{}", listingId);
            throw new BusinessException("删除房源失败");
        }

        log.info("房源删除成功，房源ID：{}", listingId);
    }

    @Transactional
    @Override
    public ListingCard updateListing(Long listingId, ListingUpdateDTO listingUpdateDTO) {
        log.info("更新房源开始，房源ID：{}", listingId);

        // 校验房源是否存在
        Listing listing = listingMapper.getListingById(listingId);
        if (listing == null) {
            log.warn("房源不存在，房源ID：{}", listingId);
            throw new BusinessException("房源不存在");
        }

        // 更新房源属性
        if (listingUpdateDTO.getTitle() != null) {
            listing.setTitle(listingUpdateDTO.getTitle());
        }
        if (listingUpdateDTO.getDescription() != null) {
            listing.setDescription(listingUpdateDTO.getDescription());
        }
        if (listingUpdateDTO.getAddress() != null) {
            listing.setAddress(listingUpdateDTO.getAddress());
        }
        if (listingUpdateDTO.getLatitude() != null) {
            listing.setLatitude(listingUpdateDTO.getLatitude());
        }
        if (listingUpdateDTO.getLongitude() != null) {
            listing.setLongitude(listingUpdateDTO.getLongitude());
        }
        if (listingUpdateDTO.getStatus() != null) {
            listing.setStatus(listingUpdateDTO.getStatus());
        }
        if (listingUpdateDTO.getListingTypeId() != null) {
            listing.setListingTypeId(listingUpdateDTO.getListingTypeId());
        }
        if (listingUpdateDTO.getPrice() != null && !listingUpdateDTO.getPrice().equals(listing.getPrice())) {
            listing.setPrice(listingUpdateDTO.getPrice());

            // 记录价格历史
            priceHistoryService.insert(listingId,listingUpdateDTO.getPrice());
            log.info("房源价格更新，房源ID：{}，新价格：{}", listingId, listingUpdateDTO.getPrice());
        }

        listingMapper.updateListing(listing);
        log.info("房源更新成功，房源ID：{}", listingId);

        return convertToListingCard(listing);
    }

    @Override
    public ListingDetail getListingById(Long listingId) {
        log.info("查询房源，房源ID：{}", listingId);

        // 查询房源
        Listing listing = listingMapper.getListingById(listingId);
        if (listing == null) {
            log.warn("房源不存在，房源ID：{}", listingId);
            throw new BusinessException("房源不存在");
        }

        log.info("房源查询成功，房源ID：{}", listingId);
        return convertToListingDetail(listing);
    }

    @Override
    public List<ListingCard> getAllListings() {
        log.info("查询所有房源");

        // 查询所有房源
        List<Listing> listings = listingMapper.getAllListings();

        // 转换成 VO
        List<ListingCard> listingCards = listings.stream()
                .map(this::convertToListingCard)
                .collect(Collectors.toList());

        log.info("查询到所有房源，总数：{}", listingCards.size());
        return listingCards;
    }

    @Override
    public List<ListingCard> searchListings(ListingSearchDTO listingSearchDTO) {
        // 1. 处理筛选条件
        if (listingSearchDTO.getFilters() != null && listingSearchDTO.getFilters().getAmenities() != null) {
            listingSearchDTO.getFilters().setAmenitiesCount(listingSearchDTO.getFilters().getAmenities().size());
        }
        List<ListingCard> filteredListings = listingMapper.searchListingsByFilters(listingSearchDTO);

        // 2. 查询用户收藏的房源ID
        Long userId = listingSearchDTO.getUserId();  // 确保 SearchDTO 里有 userId
        Set<Long> favoriteListingIds = new HashSet<>();
        if (userId != null) {
            favoriteListingIds = new HashSet<>(userClient.getFavoriteListingIds(userId).getBody().getData());
        }

        // 3. 处理图片和收藏状态
        for (ListingCard listingCard : filteredListings) {
            List<String> images = imageService.getImagesUrlsByListingId(listingCard.getId());
            listingCard.setImage(images.isEmpty() ? null : images.get(0));
            listingCard.setIsFavorite(favoriteListingIds.contains(listingCard.getId())); // 设置是否收藏
        }

        // 4. 如果没有日期范围，直接返回
        if (listingSearchDTO.getDateRange() == null ||
                listingSearchDTO.getDateRange().get("from") == null ||
                listingSearchDTO.getDateRange().get("to") == null) {
            return filteredListings;
        }

        // 5. 获取这些房源ID
        List<Long> listingIds = filteredListings.stream()
                .map(ListingCard::getId)
                .collect(Collectors.toList());

        // 6. 如果列表为空，直接返回空结果
        if (listingIds.isEmpty()) {
            return Collections.emptyList();
        }

        // 7. 调用 booking 微服务检查房源日期
        CheckDateAvailabilityDTO checkDateAvailabilityDTO = new CheckDateAvailabilityDTO(
                listingIds,
                listingSearchDTO.getDateRange().get("from"),
                listingSearchDTO.getDateRange().get("to")
        );
        List<Long> availableListingIds = bookingClient.checkDateAvailability(checkDateAvailabilityDTO).getBody().getData();

        // 8. 根据可用ID筛选最终列表
        List<ListingCard> availableListings = filteredListings.stream()
                .filter(listing -> availableListingIds.contains(listing.getId()))
                .collect(Collectors.toList());

        // 9. 查询评论数
        for (ListingCard listingCard : availableListings) {
            Integer reviewCount = reviewClient.getReviewCountByListingId(listingCard.getId()).getBody().getData();
            listingCard.setReviewCount(reviewCount);
        }

        return availableListings;
    }


    @Override
    public void updateListingRating(Long id, Double rating) {
        listingMapper.updateListingRating(id,rating);
    }

    @Override
    public List<FavoriteListing> getFavoriteListings(List<Long> listingIds) {
        List<FavoriteListing> favoriteListings = listingMapper.getFavoriteListings(listingIds);
        for(FavoriteListing listing : favoriteListings){
            List<String> imagesUrlsByListingId = imageService.getImagesUrlsByListingId(listing.getListingId());
            listing.setImages(imagesUrlsByListingId);
        }
        return favoriteListings;
    }

    @Override
    public List<ListingManagementCard> getListingsByHostId(Long hostId) {

        List<Listing> list = listingMapper.getListingsByHostId(hostId);
        List<ListingManagementCard> listingManagementCardList = new ArrayList<>();
        for(Listing listing : list){
            ListingManagementCard listingManagementCard = convertToListingManagementCard(listing);

            listingManagementCard.setImages(imageService.getImagesUrlsByListingId(listing.getId()));

            listingManagementCard.setAmenities(listingAmenityService.getAmenitiesByListingId(listing.getId()));

            listingManagementCard.setRules(ruleService.getRulesByListingId(listing.getId()));

            List<BookingVO> bookingList = bookingClient.getBookingsByListingId(listing.getId()).getBody().getData();
            listingManagementCard.setBookings(bookingList);

            listingManagementCardList.add(listingManagementCard);


        }
        return listingManagementCardList;

    }

    @Override
    public ListingSummary getListingSummary(Long id) {

        Listing listing = listingMapper.getListingById(id);

        return convertToListingSummary(listing);
    }

    private ListingSummary convertToListingSummary(Listing listing) {
        ListingSummary listingSummary = new ListingSummary();
        BeanUtils.copyProperties(listing,listingSummary);
        List<String> imagesUrlsByListingId = imageService.getImagesUrlsByListingId(listing.getId());
        listingSummary.setImage(imagesUrlsByListingId.get(0));
        return listingSummary;
    }


    private ListingCard convertToListingCard(Listing listing) {
        ListingCard listingCard = new ListingCard();
        BeanUtils.copyProperties(listing, listingCard);

        listingCard.setListingType(listingTypeMapper.getNameOfType(listing.getListingTypeId()));

        return listingCard;
    }

    private ListingManagementCard convertToListingManagementCard(Listing listing) {
        ListingManagementCard listingManagementCard = new ListingManagementCard();
        BeanUtils.copyProperties(listing, listingManagementCard);

        listingManagementCard.setListingType(listingTypeMapper.getNameOfType(listing.getListingTypeId()));

        return listingManagementCard;
    }

    private ListingDetail convertToListingDetail(Listing listing) {
        ListingDetail listingDetail = new ListingDetail();
        BeanUtils.copyProperties(listing, listingDetail);

        listingDetail.setListingType(listingTypeMapper.getNameOfType(listing.getListingTypeId()));

        listingDetail.setHost(userClient.getUserById(listing.getHostId()).getBody().getData());


        List<String> amenities = listingAmenityService.getAmenitiesByListingId(listing.getId());
        listingDetail.setAmenities(amenities);

        List<String> imageUrls = imageService.getImagesUrlsByListingId(listing.getId());
        listingDetail.setImages(imageUrls);

        List<String> rules = ruleService.getRulesByListingId(listing.getId());
        listingDetail.setRules(rules);

        return listingDetail;
    }


}
