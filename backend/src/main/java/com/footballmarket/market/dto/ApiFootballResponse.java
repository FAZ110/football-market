package com.footballmarket.market.dto;

import java.util.List;

public record ApiFootballResponse(List<PlayerWrapper> response) {

    public record PlayerWrapper(PlayerDto player, List<StatisticDto> statistics) {}

    public record PlayerDto(Long id, String firstname, String lastname, Integer age, String photo) {}

    public record StatisticDto(TeamDto team,GamesDto games) {}

    public record GamesDto(String position, String rating) {}

    public record TeamDto(String name, String logo){}
}
