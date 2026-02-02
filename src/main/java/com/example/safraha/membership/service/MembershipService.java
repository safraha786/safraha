package com.example.safraha.membership.service;

import com.example.safraha.membership.PlanCatalog;
import com.example.safraha.membership.entity.Membership;
import com.example.safraha.membership.entity.NightWallet;
import com.example.safraha.membership.repository.MembershipRepository;
import com.example.safraha.membership.repository.NightWalletRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final NightWalletRepository walletRepository;

    public MembershipService(MembershipRepository membershipRepository,
                             NightWalletRepository walletRepository) {
        this.membershipRepository = membershipRepository;
        this.walletRepository = walletRepository;
    }

    public void activateMembership(Long userId, String plan) {

        membershipRepository.findByUserIdAndActiveTrue(userId)
                .ifPresent(m -> {
                    throw new RuntimeException("Active membership already exists");
                });

        int nights = PlanCatalog.nightsFor(plan);
        int duration = PlanCatalog.durationDays(plan);

        Membership membership = new Membership();
        membership.setUserId(userId);
        membership.setPlanName(plan);
        membership.setTotalNights(nights);
        membership.setStartDate(LocalDate.now());
        membership.setEndDate(LocalDate.now().plusDays(duration));
        membership.setActive(true);

        membershipRepository.save(membership);

        NightWallet wallet = new NightWallet();
        wallet.setUserId(userId);
        wallet.setTotalNights(nights);
        wallet.setUsedNights(0);
        wallet.setRemainingNights(nights);

        walletRepository.save(wallet);
    }
}
