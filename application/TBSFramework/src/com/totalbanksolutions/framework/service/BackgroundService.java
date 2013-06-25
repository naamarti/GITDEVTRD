package com.totalbanksolutions.framework.service;

/**
 * =================================================================================================
 * Modified:  15 Sep 2012 VC #1859: Automate Late Processing : Service to Run Queued Jobs
 * =================================================================================================
 */
public interface BackgroundService extends Runnable {

	public void doShutDown();
}
