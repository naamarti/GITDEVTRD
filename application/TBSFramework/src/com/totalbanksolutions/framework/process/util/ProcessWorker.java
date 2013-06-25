package com.totalbanksolutions.framework.process.util;

public interface ProcessWorker 
{
	public abstract ProcessStatus run(ProcessConfig config);
}