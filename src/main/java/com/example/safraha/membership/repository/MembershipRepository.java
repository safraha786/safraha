package com.example.safraha.membership.repository;


import com.example.safraha.membership.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MembershipRepository extends JpaRepository<Membership, Long> {

    Optional<Membership> findByUserIdAndActiveTrue(Long userId);
}
