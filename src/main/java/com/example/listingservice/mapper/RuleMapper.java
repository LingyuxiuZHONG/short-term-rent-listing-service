package com.example.listingservice.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RuleMapper {

    List<String> getRulesByListingId(Long listingId);

    void addRules(Long listingId, List<String> rules);
}
