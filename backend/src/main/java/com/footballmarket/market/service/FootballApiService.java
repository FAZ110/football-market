package com.footballmarket.market.service;


import com.footballmarket.market.dto.ApiFootballResponse;
import com.footballmarket.market.model.Player;
import com.footballmarket.market.model.Position;
import com.footballmarket.market.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;


@Service
public class FootballApiService {

    private final PlayerRepository playerRepository;
    private final RestClient restClient;

    public FootballApiService(
            PlayerRepository playerRepository,
            RestClient.Builder restClientBuilder,
            @Value("${api.football.url}") String apiUrl,
            @Value("${api.football.key}") String apiKey){
        this.playerRepository = playerRepository;

        this.restClient = restClientBuilder
                .baseUrl(apiUrl)
                .defaultHeader("x-apisports-key", apiKey)
                .build();
    }

    // USUNIĘTO @Transactional !!!
    public void fetchAndSavePlayers(int leagueId, int season, int startPage, int endPage){

        System.out.println("Fetching players for league " + leagueId + " and season " + season + " from Football API...");

        for (int page = startPage; page <= endPage; page++) {
            System.out.println("Fetching page " + page + " of players...");
            int finalPage = page;
            ApiFootballResponse apiResponse = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/players")
                            .queryParam("league", leagueId)
                            .queryParam("season", season)
                            .queryParam("page", finalPage)
                            .build())
                    .retrieve()
                    .body(ApiFootballResponse.class);

            if (apiResponse != null && apiResponse.response() != null) {
                int addedCount = 0;
                for (ApiFootballResponse.PlayerWrapper wrapper : apiResponse.response()) {

                    if (wrapper.statistics() == null || wrapper.statistics().isEmpty()) {
                        continue;
                    }

                    ApiFootballResponse.PlayerDto playerDto = wrapper.player();
                    ApiFootballResponse.StatisticDto stats = wrapper.statistics().getFirst();

                    if (!playerRepository.existsByFirstNameAndLastName(playerDto.firstname(), playerDto.lastname())) {
                        Player player = new Player();
                        player.setFirstName(playerDto.firstname());
                        player.setLastName(playerDto.lastname());
                        player.setPosition(mapPosition(stats.games().position()));

                        player.setAge(playerDto.age());
                        player.setPhotoUrl(playerDto.photo());

                        if (stats.team() != null) {
                            player.setTeamName(stats.team().name());
                            player.setTeamLogo(stats.team().logo());
                        }

                        BigDecimal initialPrice = calculateInitialPrice(playerDto.age(), stats.games().rating());
                        player.setPrice(initialPrice);

                        // Metoda .save() ma wbudowany własny, bezpieczny @Transactional
                        playerRepository.save(player);
                        addedCount++;
                    }
                }
                System.out.println("Finished fetching and saving players. Total players in database: " + playerRepository.count() + ". Players added in this run: " + addedCount);
            }

            // SLEEP PRZENIESIONY TUTAJ! (Czekamy 2 sekundy po każdej STRONIE, a nie po graczu)
            try {
                Thread.sleep(2000);
            } catch(InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private BigDecimal calculateInitialPrice(int age, String ratingString){
        double rating = 6.0;

        if (ratingString != null && !ratingString.isEmpty()){
            try{
                rating = Double.parseDouble(ratingString);
            }catch (NumberFormatException e){
                System.err.println("Invalid rating format: " + ratingString + ". Defaulting to 6.0");
            }
        }
        double ratingBase = Math.max(0.1, rating - 5.0);

        double rawPrice = Math.pow(ratingBase, 3) * 1000;

        if (age <= 21) {
            rawPrice *= 1.8;
        } else if (age >= 30 && age < 34) {
            rawPrice *= 0.7;
        } else if (age >= 34) {
            rawPrice *= 0.3;
        }

        if(rawPrice < 50.0){
            rawPrice = 50.0;
        }
        return BigDecimal.valueOf(rawPrice).setScale(2, RoundingMode.HALF_UP);
    }

    private Position mapPosition(String apiPosition){
        return switch (apiPosition.toLowerCase()){
            case "goalkeeper" -> Position.GOALKEEPER;
            case "defender" -> Position.DEFENDER;
            case "midfielder" -> Position.MIDFIELDER;
            case "attacker" -> Position.ATTACKER;
            default -> Position.MIDFIELDER; // default to midfielder if unknown
        };
    }
}
