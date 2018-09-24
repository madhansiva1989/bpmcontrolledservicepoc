package com.process.flow.task;

import java.util.Map;

import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.runtime.Execution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.process.flow.constant.FlowConstant;

@Service
public class SignalTask extends AbstractTask {

	@Autowired
	private RuntimeService runtimeService;

	@Override
	public void executeService(Map<String, Object> processVariable) throws Exception {
		String activityName = processVariable.get(FlowConstant.ACTIVITY_ID).toString();
		String id = processVariable.get(FlowConstant.PROCESS_ID).toString();
		Execution execution = runtimeService.createExecutionQuery().processInstanceId(id).activityId(activityName)
				.singleResult();
		if (execution != null) {
			runtimeService.signal(execution.getId(), processVariable);
		} else {
			throw new Exception("Execution not found for processId " + id + " and activity id ::" + activityName);
		}
	}

}
