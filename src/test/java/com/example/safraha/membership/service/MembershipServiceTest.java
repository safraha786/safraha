package com.example.safraha.membership.service;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import com.example.safraha.membership.PlanCatalog;
import com.example.safraha.membership.entity.Membership;
import com.example.safraha.membership.entity.NightWallet;
import com.example.safraha.membership.repository.MembershipRepository;
import com.example.safraha.membership.repository.NightWalletRepository;

@SpringBootTest
@Transactional
class MembershipServiceTest {

    @Autowired
    private MembershipService membershipService;

    @Autowired
    private MembershipRepository membershipRepository;

    @Autowired
    private NightWalletRepository nightWalletRepository;

    @Test
    void shouldCreateMembershipAndWallet() {

        Long userId = 1L;
        String plan = PlanCatalog.MONTHLY_10;

        membershipService.activateMembership(userId, plan);

        Membership membership = membershipRepository
                .findByUserIdAndActiveTrue(userId)
                .orElseThrow();

        NightWallet wallet = nightWalletRepository
                .findByUserId(userId)
                .orElseThrow();

        assertEquals(plan, membership.getPlanName());
        assertEquals(10, wallet.getTotalNights());
        assertEquals(0, wallet.getUsedNights());
        assertEquals(10, wallet.getRemainingNights());
    }

    @Test
    void shouldNotAllowMultipleActiveMemberships() {

        Long userId = 2L;

        membershipService.activateMembership(userId, PlanCatalog.MONTHLY_10);

        RuntimeException ex = assertThrows(
                RuntimeException.class,
                () -> membershipService.activateMembership(userId, PlanCatalog.MONTHLY_15)
        );

        assertEquals("Active membership already exists", ex.getMessage());
    }
}
