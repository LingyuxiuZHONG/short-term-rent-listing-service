package com.example.listingservice.utils.parser;

import com.example.listingservice.dto.ListingSearchDTO;
import com.example.listingservice.dto.search.Filters;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ListingSearchRequestParser {

    public static ListingSearchDTO parseRequest(Map<String, Object> requestBody) {
        ListingSearchDTO dto = new ListingSearchDTO();

        // 解析 location
        dto.setAddress((String) requestBody.get("location"));

        // 解析 dateRange


        // 解析 guests
        Map<String, Integer> guests = (Map<String, Integer>) requestBody.get("guests");
        dto.setGuests(guests);

        // 解析 filters
        Filters filters = parseFilters((Map<String, Object>) requestBody.get("filters"));
        dto.setFilters(filters);

        return dto;
    }

    private static Filters parseFilters(Map<String, Object> filterMap) {
        Filters filters = new Filters();

        // 解析 filters 的各个字段
        filters.setPriceRange((List<Integer>) filterMap.get("priceRange"));
        filters.setPropertyType((List<String>) filterMap.get("propertyType"));
        filters.setBedrooms((String) filterMap.get("bedrooms"));
        filters.setBathrooms((String) filterMap.get("bathrooms"));
        filters.setAmenities((List<String>) filterMap.get("amenities"));

        return filters;
    }


    private static Map<String, LocalDateTime> parseDateRange(Map<String, String> dateRange){
        try {
            // 解析日期范围
            String fromStr = dateRange.get("from");
            String toStr = dateRange.get("to");

            if (fromStr != null && toStr != null) {
                // 使用 DateTimeFormatter 解析日期，确保格式正确
                DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
                LocalDateTime fromDate = LocalDateTime.parse(fromStr, formatter);
                LocalDateTime toDate = LocalDateTime.parse(toStr, formatter);

                // 创建一个新的 Map 来存放 LocalDateTime 对象
                Map<String, LocalDateTime> dateRangeMap = new HashMap<>();
                dateRangeMap.put("from", fromDate);
                dateRangeMap.put("to", toDate);

                return dateRangeMap;
            } else {
                // 如果 from 或 to 为 null，处理相应的错误
                throw new IllegalArgumentException("Date range 'from' or 'to' is missing.");
            }
        } catch (DateTimeParseException e) {
            // 如果日期格式不对，捕获异常并处理
            throw new IllegalArgumentException("Invalid date format for 'from' or 'to'.", e);
        }

    }
}
