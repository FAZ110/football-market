package com.footballmarket.market.config;

import com.footballmarket.market.model.AppUser;
import com.footballmarket.market.model.Player;
import com.footballmarket.market.repository.AppUserRepository;
import com.footballmarket.market.repository.PlayerRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.util.List;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner commandLineRunner(PlayerRepository playerRepository, AppUserRepository appUserRepository){
        return args -> {
//            if (playerRepository.count() == 0){
//                Player p1 = new Player();
//                p1.setFirstName("Robert");
//                p1.setLastName("Lewandowski");
//                p1.setPrice(new BigDecimal("1000.00"));
//                p1.setPosition("ST");
//
//                Player p2 = new Player();
//                p2.setFirstName("Lamine");
//                p2.setLastName("Yamal");
//                p2.setPrice(new BigDecimal("2500.00"));
//                p2.setPosition("RW");
//
//                Player p3 = new Player();
//                p3.setFirstName("Wojciech");
//                p3.setLastName("Szczęsny");
//                p3.setPrice(new BigDecimal("500.00"));
//                p3.setPosition("GK");
//
//                playerRepository.saveAll(List.of(p1, p2, p3));
//
//                System.out.println(">>>>>Initialized database with sample players.");
//
//            }else{
//                System.out.println("Database already contains players. Skipping initialization.");
//            }

            if(appUserRepository.count() == 0){
                AppUser user1 = new AppUser();
                user1.setUsername("john_doe");
                user1.setPassword("password123");
                user1.setEmail("johndoe@gmail.com");
                user1.setBalance(new BigDecimal("5000.00"));
                appUserRepository.save(user1);
                System.out.println(">>>>>Initialized database with sample user.");

            }else{
                System.out.println("Database already contains users. Skipping initialization.");
            }
        };
    }
}
