package com.process.flow.delegate;

import java.util.Map;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.process.flow.constant.FlowConstant;
import com.process.flow.task.AbstractTask;
import com.process.flow.task.DelegateTask;

@Service
public class BaseDelegate implements JavaDelegate, BeanFactoryAware {

	@Autowired
	BeanFactory beanFactory;

	@Override
	public void execute(DelegateExecution execution) {
		try {
			System.out.println("executing::" + execution.getProcessInstanceId());
			Map<String, Object> processVariables = execution.getVariables();
			processVariables.put(FlowConstant.PROCESS_ID, execution.getProcessInstanceId());
			processVariables.put(FlowConstant.ACTIVITY_NAME, execution.getCurrentActivityName());
			processVariables.put(FlowConstant.ACTIVITY_ID, execution.getCurrentActivityId());
			AbstractTask task = beanFactory.getBean(DelegateTask.class, AbstractTask.class);
			task.executeService(processVariables);
		} catch (Exception e) {
			
		}
	}

	@Override
	public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
		this.beanFactory = beanFactory;
	}

}
