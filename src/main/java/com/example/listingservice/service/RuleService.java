package com.example.listingservice.service;

import java.util.List;

public interface RuleService {
    List<String> getRulesByListingId(Long id);

    void addRules(Long listingId, List<String> rules);
}
