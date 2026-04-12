package com.footballmarket.market.controller;


import com.footballmarket.market.service.FootballApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class adminController {
    private final FootballApiService footballApiService;

    @PostMapping("/import-players")
    public ResponseEntity<String> importPlayers() {
        try {
            footballApiService.fetchAndSavePlayers(39, 2024, 4, 20);
            return ResponseEntity.ok("Players imported successfully");
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("Error importing players: " + e.getMessage());
        }
    }
}
