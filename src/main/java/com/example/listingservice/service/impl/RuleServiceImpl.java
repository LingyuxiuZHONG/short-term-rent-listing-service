package com.example.listingservice.service.impl;

import com.example.listingservice.mapper.RuleMapper;
import com.example.listingservice.service.RuleService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@AllArgsConstructor
public class RuleServiceImpl implements RuleService {
    private final RuleMapper ruleMapper;

    @Override
    public List<String> getRulesByListingId(Long id) {
        return ruleMapper.getRulesByListingId(id);
    }

    @Override
    public void addRules(Long listingId, List<String> rules) {
        ruleMapper.addRules(listingId, rules);
    }
}
