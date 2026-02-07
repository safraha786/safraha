package com.example.safraha.membership.entity;

import com.example.safraha.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "night_wallets")
public class NightWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "membership_id", nullable = false, unique = true)
    private Membership membership;

    private Integer totalNights;

    private Integer usedNights;

    private Integer remainingNights;

    public NightWallet() {
    }
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getTotalNights() {
		return totalNights;
	}

	public void setTotalNights(Integer totalNights) {
		this.totalNights = totalNights;
	}

	public Integer getUsedNights() {
		return usedNights;
	}

	public void setUsedNights(Integer usedNights) {
		this.usedNights = usedNights;
	}

	public Integer getRemainingNights() {
		return remainingNights;
	}

	public void setRemainingNights(Integer remainingNights) {
		this.remainingNights = remainingNights;
	}


	public Membership getMembership() {
		return membership;
	}

	public void setMembership(Membership membership) {
		this.membership = membership;
	}

}
