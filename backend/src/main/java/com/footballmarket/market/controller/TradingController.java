package com.footballmarket.market.controller;


import com.footballmarket.market.repository.AppUserRepository;
import com.footballmarket.market.repository.PlayerRepository;
import com.footballmarket.market.repository.PortfolioItemRepository;
import com.footballmarket.market.service.TradingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/trade")

public class TradingController {

    private final TradingService tradingService;

    @PostMapping("/buy")
    public ResponseEntity<String> buyPlayer(@RequestParam Long userId, @RequestParam Long playerId, @RequestParam int quantity) {
        try {
            tradingService.buyPlayer(userId, playerId, quantity);
            return ResponseEntity.ok("Player bought successfully " + quantity + " times");

        }catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body("Transaction failure: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error " + e.getMessage());
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<String> sellPlayer(@RequestParam Long userId, @RequestParam Long playerId, @RequestParam int quantity) {
        try {
            tradingService.sellPlayer(userId, playerId, quantity);
            return ResponseEntity.ok("Player sold successfully " + quantity + " times");
            
        } catch (IllegalArgumentException | IllegalStateException e) {
            return ResponseEntity.badRequest().body("Transaction failure: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Server error " + e.getMessage());
        }
    }
}
