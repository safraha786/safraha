package com.example.safraha.membership.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.safraha.membership.entity.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

	List<Membership> findByUser_IdAndActiveTrue(Long userId);
    List<Membership> findByUser_IdAndRegionIdAndActiveTrue(
            Long userId, Long regionId);
    
    
}
