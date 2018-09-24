package com.claim.processing.service;

import java.util.List;

import com.claim.processing.domain.model.ClaimInput;
import com.claim.processing.repo.model.ClaimAuditRegistry;
import com.claim.processing.repo.model.ClaimRegistry;

public interface ClaimService {

	public void processAndSaveClaim(String uniqueId, ClaimInput input);

	public ClaimRegistry findByClaimId(String claimId);

	public void updateClaim(ClaimRegistry claimReg);

	public List<ClaimAuditRegistry> findAuditByClaimId(String claimId);
}
