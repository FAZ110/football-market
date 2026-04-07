package com.footballmarket.market.service;


import com.footballmarket.market.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestClient;
import org.springframework.beans.factory.annotation.Value;


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

    @Transactional
    public void fetchAndSavePlayers(int leagueId, int season){

        System.out.println("Fetching players for league " + leagueId + " and season " + season + " from Football API...");


    }
}
