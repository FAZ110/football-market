package com.footballmarket.market.service;


import com.footballmarket.market.model.AppUser;
import com.footballmarket.market.model.Player;
import com.footballmarket.market.model.PortfolioItem;
import com.footballmarket.market.repository.AppUserRepository;
import com.footballmarket.market.repository.PlayerRepository;
import com.footballmarket.market.repository.PortfolioItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TradingService {

    private final AppUserRepository appUserRepository;
    private final PlayerRepository playerRepository;
    private final PortfolioItemRepository portfolioItemRepository;

    @Transactional
    public void buyPlayer(Long userId, Long playerId, int quantity){

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        BigDecimal totalCost = player.getPrice().multiply(BigDecimal.valueOf(quantity));

        if (user.getBalance().compareTo(totalCost) < 0){
            throw new IllegalStateException("Insufficient balance to buy " + player.getFirstName() + " " + player.getLastName() + " " + quantity + " times.");
        }

        user.setBalance(user.getBalance().subtract(totalCost));

        Optional<PortfolioItem> existingItem = portfolioItemRepository.findByUserAndPlayer(user, player);

        if (existingItem.isPresent()){
            PortfolioItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + quantity);
        }else{
            PortfolioItem newItem = new PortfolioItem();
            newItem.setUser(user);
            newItem.setPlayer(player);
            newItem.setQuantity(quantity);
            portfolioItemRepository.save(newItem);
        }
    }

    @Transactional
    public void sellPlayer(Long userId, Long playerId, int quantity){

        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new IllegalArgumentException("Player not found"));

        PortfolioItem item = portfolioItemRepository.findByUserAndPlayer(user, player)
                .orElseThrow(() -> new IllegalArgumentException("You do not own any shares of " + player.getFirstName() + " " + player.getLastName()));

        if (item.getQuantity() < quantity) {
            throw new IllegalArgumentException("Not enough quantity to sell. You have " + item.getQuantity() + " but tried to sell " + quantity);
        }

        BigDecimal totalRevenue = player.getPrice().multiply(BigDecimal.valueOf(quantity));
        user.setBalance(user.getBalance().add(totalRevenue));

        int newQuantity = item.getQuantity() - quantity;
        if (newQuantity == 0) {
            portfolioItemRepository.delete(item);
        } else {
            item.setQuantity(newQuantity);
        }

    }
}
