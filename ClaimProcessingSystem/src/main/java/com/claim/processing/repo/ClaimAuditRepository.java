package com.claim.processing.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.claim.processing.repo.model.ClaimAuditRegistry;

@Repository
public interface ClaimAuditRepository extends JpaRepository<ClaimAuditRegistry, Long> {

	public List<ClaimAuditRegistry> findByClaimId(String claimId);
}
