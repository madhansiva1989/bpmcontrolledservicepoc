package com.process.flow.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstance;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/process")
public class BPMEndPoint {

	@Autowired
	private RuntimeService runtimeService;

	@Autowired
	RabbitTemplate rabbitTemplate;

	@RequestMapping(value = "/start", method = RequestMethod.POST)
	public String startProcess(@RequestParam(name = "approvalStatus", required = true) boolean status) {
		Map<String, Object> variables = new HashMap<>();
		variables.put("value", 99);
		ProcessInstance instance = runtimeService.startProcessInstanceByKey("claimProcessing", variables);
		String instanceId = instance.getId();
		return "Started process with id::" + instanceId;
	}

	@RequestMapping(value = "/find/execution", method = RequestMethod.GET)
	public String findActivityById(@RequestParam(name = "activityId", required = true) String activityId,
			@RequestParam(name = "processId", required = true) String processId) {
		Execution execution = runtimeService.createExecutionQuery().processInstanceId(processId).activityId(activityId)
				.singleResult();
		return "process execution found with id::"+execution.getId();
	}

	@RequestMapping(value = "/proceed", method = RequestMethod.POST)
	public String startProcess(@RequestParam(name = "processId", required = true) String id,
			@RequestParam(name = "activityType", required = true) String activityType) {
		Execution execution = runtimeService.createExecutionQuery().processInstanceId(id).activityId(activityType)
				.singleResult();
		runtimeService.signal(execution.getId());
		return "Started process for id::" + id;
	}

}
