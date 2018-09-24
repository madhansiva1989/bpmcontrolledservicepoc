package com.process.flow.task;

import java.util.Map;

public abstract class AbstractTask {

	public abstract void executeService(Map<String, Object> processVariable) throws Exception;

}
