package com.process.flow.task;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.process.flow.constant.FlowConstant;
import com.process.flow.messaging.MessageProcessor;

@Service
public class DelegateTask extends AbstractTask {
	@Autowired
	MessageProcessor messageProcessor;

	@Override
	public void executeService(Map<String, Object> processVariables) {
		messageProcessor.sendMessage(processVariables.get(FlowConstant.SOURCE_SYSTEM).toString(), processVariables);
	}

}
