package com.claim.processing.domain.model;

public class ClaimInput {
	private String claimRequestor;
	private double claimAmount;

	public String getClaimRequestor() {
		return claimRequestor;
	}

	public void setClaimRequestor(String claimRequestor) {
		this.claimRequestor = claimRequestor;
	}

	public double getClaimAmount() {
		return claimAmount;
	}

	public void setClaimAmount(double claimAmount) {
		this.claimAmount = claimAmount;
	}

}
