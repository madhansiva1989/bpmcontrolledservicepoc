package com.claim.processing.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claim.processing.repo.model.ClaimRegistry;

@Repository
public interface ClaimRepository extends JpaRepository<ClaimRegistry, Long> {

	public ClaimRegistry findByClaimId(String claimId);
}
