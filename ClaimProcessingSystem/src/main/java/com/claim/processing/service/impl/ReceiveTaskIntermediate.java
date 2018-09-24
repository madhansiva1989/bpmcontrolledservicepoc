package com.claim.processing.service.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.claim.processing.messaging.MessageProcessor;
import com.claim.processing.repo.model.ClaimRegistry;
import com.claim.processing.service.ClaimService;

@Service
public class ReceiveTaskIntermediate extends AbstractService {
	
	@Autowired
	MessageProcessor processor;

	@Autowired
	ClaimService claimService;
	
	@Override
	public void executeService(Map<String, Object> processVariable) {
		String claimId=processVariable.get("claimId").toString();
		ClaimRegistry reg=claimService.findByClaimId(claimId);
		reg.setState("ReceiveTaskIntermediate");
		claimService.updateClaim(reg);
		processor.sendMessage("flowInbound", processVariable);
		System.out.println("ReceiveTaskIntermediate executed in claim service");
	}

}
