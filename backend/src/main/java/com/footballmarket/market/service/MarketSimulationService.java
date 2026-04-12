package com.footballmarket.market.service;


import com.footballmarket.market.model.Player;
import com.footballmarket.market.repository.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class MarketSimulationService {

    private final PlayerRepository playerRepository;
    private final Random random = new Random();

    @Scheduled(fixedRate = 60000)
    @Transactional
    public void simulateMarketFluctuations(){
        List<Player> players = playerRepository.findAll();

        System.out.println("Simulating market fluctuations for " + players.size() + " players...");

        for (Player player: players){

            double percentageChange = (random.nextDouble() * 10) - 5;

            BigDecimal multiplier = BigDecimal.valueOf(1 + (percentageChange / 100));

            BigDecimal newPrice = player.getPrice().multiply(multiplier).setScale(2, RoundingMode.HALF_UP);

            if (newPrice.compareTo(BigDecimal.valueOf(0.01)) < 0){
                newPrice = BigDecimal.valueOf(0.01);
            }

            player.setPrice(newPrice);

            System.out.println("Updated price for " + player.getFirstName() + " " + player.getLastName() + ": " + newPrice);
        }
    }
}
