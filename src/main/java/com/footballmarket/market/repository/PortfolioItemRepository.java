package com.footballmarket.market.repository;

import com.footballmarket.market.model.AppUser;
import com.footballmarket.market.model.Player;
import com.footballmarket.market.model.PortfolioItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PortfolioItemRepository extends JpaRepository<PortfolioItem, Long> {

    List<PortfolioItem> findByUserId(Long userId);

    Optional<PortfolioItem> findByUserAndPlayer(AppUser user, Player player);
}
