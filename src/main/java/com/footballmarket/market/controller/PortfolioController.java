package com.footballmarket.market.controller;


import com.footballmarket.market.model.PortfolioItem;
import com.footballmarket.market.repository.PortfolioItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/portfolio")
@RequiredArgsConstructor
public class PortfolioController {

    private final PortfolioItemRepository portfolioItemRepository;

    @GetMapping("/{userId}")
    public List<PortfolioItem> getAllPortfolioItems(@PathVariable Long userId) {
        return portfolioItemRepository.findByUserId(userId);
    }

}
