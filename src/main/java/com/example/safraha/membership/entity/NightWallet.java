package com.example.safraha.membership.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "night_wallets")
public class NightWallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long userId;

    private Integer totalNights;

    private Integer usedNights;

    private Integer remainingNights;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
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

}
