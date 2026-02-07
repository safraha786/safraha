package com.example.safraha.membership.repository;


import com.example.safraha.membership.entity.NightWallet;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NightWalletRepository extends JpaRepository<NightWallet, Long> {

	Optional<NightWallet> findByMembership_Id(Long membershipId);
}
