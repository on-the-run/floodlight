package net.floodlightcontroller.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadCpuUsageMonitor extends Thread {
	private long toMeasure;
	private int numberOfOnlineThreads;
	private final int numberOfWorkers = 16;
	
	public ThreadCpuUsageMonitor() {
		toMeasure = 0;
		numberOfOnlineThreads = 0;
	}
	
	public void run () {
		String threadName = null;
		while (numberOfOnlineThreads < numberOfWorkers) {
			final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
			final ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 100);
			for (ThreadInfo threadInfo : threadInfos) {
				threadName = threadInfo.getThreadName();
				if (threadName.contains("New I/O server worker")) {
					numberOfOnlineThreads++;
					System.out.println("-----Start monitoring: " + threadInfo.getThreadName());
					toMeasure = threadInfo.getThreadId();
					(new CpuUsageMonitor(toMeasure)).start();
				}
			}
        }
	}
}
