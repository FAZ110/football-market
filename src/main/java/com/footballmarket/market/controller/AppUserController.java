package com.footballmarket.market.controller;


import com.footballmarket.market.model.AppUser;
import com.footballmarket.market.repository.AppUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appusers")
@RequiredArgsConstructor
public class AppUserController {
    private final AppUserRepository appUserRepository;

    @GetMapping
    public List<AppUser> getAllUsers(){
        return appUserRepository.findAll();
    }

    @GetMapping("/{id}")
    public AppUser getUserById(@PathVariable Long id){
        return appUserRepository.findById(id).orElse(null);
    }
}
