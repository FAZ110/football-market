package com.footballmarket.market.dto;

import java.util.List;

public record ApiFootballResponse(List<PlayerWrapper> response) {

    public record PlayerWrapper(PlayerDto player, List<StatisticDto> statistics) {}

    public record PlayerDto(Long id, String firstname, String lastname, int age) {}

    public record StatisticDto(GamesDto games) {}

    public record GamesDto(String position, String rating) {}
}
