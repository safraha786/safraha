package com.example.safraha.membership.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.safraha.membership.PlanCatalog;
import com.example.safraha.membership.dto.MembershipResponse;
import com.example.safraha.membership.entity.Membership;
import com.example.safraha.membership.entity.NightWallet;
import com.example.safraha.membership.repository.MembershipRepository;
import com.example.safraha.membership.repository.NightWalletRepository;
import com.example.safraha.region.entity.Region;
import com.example.safraha.region.repository.RegionRepository;
import com.example.safraha.user.entity.User;
import com.example.safraha.user.repository.UserRepository;

@Service
public class MembershipService {

    private final MembershipRepository membershipRepository;
    private final NightWalletRepository walletRepository;
    private final RegionRepository regionRepository;
    private final UserRepository userRepository;
    
    public MembershipService(MembershipRepository membershipRepository,
                             NightWalletRepository walletRepository, 
                             RegionRepository regionRepository,
                             UserRepository userRepository) {
        this.membershipRepository = membershipRepository;
        this.walletRepository = walletRepository;
        this.regionRepository = regionRepository;
        this.userRepository = userRepository;
    }

    public void activateMembership(Long userId, String plan, String regionName) {
    	
    	User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    	
    	Region region = regionRepository.findByName(regionName)
    	        .orElseThrow(() -> new RuntimeException("Invalid region"));

    	List<Membership> mship = membershipRepository.findByUser_IdAndRegionIdAndActiveTrue(userId, region.getId());
        if(!mship.isEmpty()) {
        	throw new RuntimeException("Active membership already exists");
        }
    	

        int nights = PlanCatalog.nightsFor(plan);
        int duration = PlanCatalog.durationDays(plan);

        Membership membership = new Membership();
        membership.setUser(user);
        membership.setPlanName(plan);
        membership.setTotalNights(nights);
        membership.setStartDate(LocalDate.now());
        membership.setEndDate(LocalDate.now().plusDays(duration));
        membership.setActive(true);
        membership.setRegionId(region.getId());
        
        
        
        NightWallet wallet = new NightWallet();
        wallet.setMembership(membership);
        wallet.setTotalNights(nights);
        wallet.setUsedNights(0);
        wallet.setRemainingNights(nights);
        
        membership.setWallet(wallet);
        membership = membershipRepository.save(membership);

        walletRepository.save(wallet);
        
    }
    
    public List<MembershipResponse> getActiveMembership(Long userId) {

        List<Membership> membership = membershipRepository
                .findByUser_IdAndActiveTrue(userId);
        if(membership.isEmpty()) {
        	throw new RuntimeException("No active membership");
        }
              
        List<MembershipResponse> membershipResponse = new ArrayList<>();
        
        for(Membership mbship : membership) {
        	
        	 NightWallet wallet = walletRepository
                     .findByMembership_Id(mbship.getId())
                     .orElseThrow(() -> new RuntimeException("Wallet not found"));

             Region region = regionRepository.findById(mbship.getRegionId())
                     .orElseThrow(() -> new RuntimeException("Region not found"));
             
             MembershipResponse response = new MembershipResponse();
             response.setPlanName(mbship.getPlanName());
             response.setRegionName(region.getName());
             response.setStartDate(mbship.getStartDate());
             response.setEndDate(mbship.getEndDate());

             response.setTotalNights(wallet.getTotalNights());
             response.setUsedNights(wallet.getUsedNights());
             response.setRemainingNights(wallet.getRemainingNights());
             
             membershipResponse.add(response);
             
        }

        return membershipResponse;
    }

}
