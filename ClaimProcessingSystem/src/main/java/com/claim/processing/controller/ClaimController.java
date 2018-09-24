package com.claim.processing.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.claim.processing.constant.FlowConstant;
import com.claim.processing.domain.model.ClaimInput;
import com.claim.processing.domain.model.ValueType;
import com.claim.processing.repo.model.ClaimAuditRegistry;
import com.claim.processing.service.ClaimService;

@RestController
@RequestMapping(value = "/claim")
public class ClaimController {

	@Autowired
	RestTemplate restTemplate;	
	
	@Autowired
	ClaimService claimService;

	@RequestMapping(value = "/create", method = RequestMethod.POST)
	public String initiateClaim(@RequestBody ClaimInput input) {
		Map<String, Object> variables = new HashMap<>();
		Map<String, Object> values = new HashMap<>();
		String uniqueId=UUID.randomUUID().toString();
		variables.put("variables", values);
		values.put(FlowConstant.SOURCE_SYSTEM, new ValueType<String>("claimSystemInbound", "String"));
		values.put("claimId", new ValueType<String>(uniqueId));
		values.put("value", new ValueType<Double>(input.getClaimAmount()));
		claimService.processAndSaveClaim(uniqueId, input);
		restTemplate.postForEntity("http://localhost:8080/rest/process-definition/key/claimProcessing/start", variables,
				String.class);
		return uniqueId;
	}
	@RequestMapping(value = "/audit/{claimId}", method = RequestMethod.GET)
	public List<ClaimAuditRegistry> getAudit(@PathVariable(name="claimId")String claimId) {
		return claimService.findAuditByClaimId(claimId);
	}
}
