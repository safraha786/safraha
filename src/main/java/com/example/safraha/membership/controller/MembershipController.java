package com.example.safraha.membership.controller;

import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.safraha.membership.dto.MembershipResponse;
import com.example.safraha.membership.service.MembershipService;

@RestController
@RequestMapping("/membership")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/activate")
    public String activate(@RequestParam String plan,
                           @RequestParam String region,
                           Authentication authentication) {
    	
    	System.out.println(">>> HIT activate endpoint");


        Long userId = Long.parseLong(authentication.getName());
        membershipService.activateMembership(userId, plan, region);

        return "Membership activated";
    }
    
    @GetMapping("/me")
    public List<MembershipResponse> getMyMembership(Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName());
        return membershipService.getActiveMembership(userId);
    }
}

