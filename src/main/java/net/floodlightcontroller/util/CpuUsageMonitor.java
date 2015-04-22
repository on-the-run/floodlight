package net.floodlightcontroller.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadMXBean;

public class CpuUsageMonitor extends Thread {
	private long toMeasure;
	private PrintWriter writerCPU;
	//private PrintWriter writerMem;
	
	public void run() {
		//System.out.format("Thread ID of CpuBenchmarker = %d%n", this.getId());
		ThreadMXBean thread = ManagementFactory.getThreadMXBean();
		if (!thread.isThreadCpuTimeSupported()) {
			System.err.println("JVM does not support thread CPU time!");
			return;
		}
		
		if (!thread.isThreadCpuTimeEnabled()) {
			System.err.println("The thread disable CPU monitoring by default, enabled it!");
			thread.setThreadCpuTimeEnabled(true);
		}
		
		//System.out.println("Thread ID of CpuBenchmarker = " + thread.getAllThreadIds()[1]);
		long startTime;
		long endTime;
		long startCPUTime;
		long endCPUTime;
		long timeToSleepMilli = 10;
		int timeToSleepNano = 0; 
		//long totalTime = 0;
		
		while(true) {
			startTime = System.nanoTime();
    		startCPUTime = thread.getThreadCpuTime(toMeasure);
			
			try {
				Thread.sleep(timeToSleepMilli, timeToSleepNano);
			} catch (InterruptedException e) {e.printStackTrace();}
			
			endCPUTime = thread.getThreadCpuTime(toMeasure);
			endTime = System.nanoTime();
			
			//totalTime += endTime - startTime;
			//writerCPU.println(ManagementFactory.getOperatingSystemMXBean().getAvailableProcessors() * 
			writerCPU.println(((float)(endCPUTime - startCPUTime) / (float)(endTime - startTime)) * 100.0);
			writerCPU.flush();
			//writerMem.println(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() + 
			//	ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed());
		}
	}
	
	public CpuUsageMonitor (long id) {
		toMeasure = id;
		try {
			writerCPU = new PrintWriter("./cpuForwarding.log."+toMeasure, "UTF-8");
			//writerMem = new PrintWriter("./memForwarding.log."+toMeasure, "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
