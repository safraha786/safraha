package com.example.safraha.membership.entity;

import java.time.LocalDate;

import com.example.safraha.user.entity.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "memberships")
public class Membership {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    private String planName;     // MONTHLY_10, MONTHLY_15, QUARTERLY

    private Integer totalNights;

    private LocalDate startDate;

    private LocalDate endDate;

    private Boolean active;
    
    private Long regionId;
    
    @OneToOne(mappedBy = "membership", cascade = CascadeType.ALL)
    private NightWallet wallet;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public Integer getTotalNights() {
		return totalNights;
	}

	public void setTotalNights(Integer totalNights) {
		this.totalNights = totalNights;
	}

	public LocalDate getStartDate() {
		return startDate;
	}

	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	public LocalDate getEndDate() {
		return endDate;
	}

	public void setEndDate(LocalDate endDate) {
		this.endDate = endDate;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	public Long getRegionId() {
		return regionId;
	}

	public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public NightWallet getWallet() {
		return wallet;
	}

	public void setWallet(NightWallet wallet) {
		this.wallet = wallet;
	}

}