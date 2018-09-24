package com.claim.processing.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claim.processing.domain.model.ClaimInput;
import com.claim.processing.repo.ClaimAuditRepository;
import com.claim.processing.repo.ClaimRepository;
import com.claim.processing.repo.model.ClaimAuditRegistry;
import com.claim.processing.repo.model.ClaimRegistry;
import com.claim.processing.service.ClaimService;

@Service
public class ClaimServiceImpl implements ClaimService {

	@Autowired
	ClaimRepository claimRepo;
	
	@Autowired
	ClaimAuditRepository claimAuditRepo;

	@Override
	public void processAndSaveClaim(String uniqueId, ClaimInput input) {
		// TODO Auto-generated method stub
		ClaimRegistry registry = new ClaimRegistry();
		registry.setClaimAmount(input.getClaimAmount());
		registry.setClaimId(uniqueId);
		registry.setState("INITIATED");
		claimRepo.save(registry);
		createClaimAudit(registry);
	}

	@Override
	public ClaimRegistry findByClaimId(String claimId) {
		System.out.println(claimRepo.count());
		return claimRepo.findByClaimId(claimId);
	}
	
	@Override
	public void updateClaim(ClaimRegistry claimReg) {
		 claimRepo.save(claimReg);
		 createClaimAudit(claimReg);
	}

	@Override
	public List<ClaimAuditRegistry> findAuditByClaimId(String claimId) {
		System.out.println(claimRepo.count());
		return claimAuditRepo.findByClaimId(claimId);
	}
	
	private void createClaimAudit(ClaimRegistry registry) {
		ClaimAuditRegistry reg=new ClaimAuditRegistry();
		reg.setClaimId(registry.getClaimId());
		reg.setState(registry.getState());
		reg.setCreatedDate(new Date());
		claimAuditRepo.save(reg);
	}
}
