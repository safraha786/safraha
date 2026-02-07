package com.example.safraha.membership.dto;

import java.time.LocalDate;

public class MembershipResponse {

	private String planName;
    private String regionName;
    private LocalDate startDate;
    private LocalDate endDate;

    private Integer totalNights;
    private Integer usedNights;
    private Integer remainingNights;

    public MembershipResponse() {
    }

	public String getPlanName() {
		return planName;
	}

	public void setPlanName(String planName) {
		this.planName = planName;
	}

	public String getRegionName() {
		return regionName;
	}

	public void setRegionName(String regionName) {
		this.regionName = regionName;
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
