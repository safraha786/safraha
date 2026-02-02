package com.example.safraha.membership.controller;

import com.example.safraha.membership.service.MembershipService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/membership")
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping("/activate")
    public String activate(@RequestParam String plan,
                           Authentication authentication) {

        Long userId = Long.parseLong(authentication.getName()); // we’ll fix this later
        membershipService.activateMembership(userId, plan);

        return "Membership activated";
    }
}

