package net.floodlightcontroller.util;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.management.ManagementFactory;

public class MemUsageMonitor extends Thread {
	private PrintWriter writerMem;
	
	public void run() {
		long timeToSleepMilli = 10;
		int timeToSleepNano = 0; 
		
		while(true) {
			writerMem.println(ManagementFactory.getMemoryMXBean().getHeapMemoryUsage().getUsed() + ManagementFactory.getMemoryMXBean().getNonHeapMemoryUsage().getUsed());
			writerMem.flush();
			try {
				Thread.sleep(timeToSleepMilli, timeToSleepNano);
			} catch (InterruptedException e) {e.printStackTrace();}
		}
	}
	
	public MemUsageMonitor () {
		try {
			writerMem = new PrintWriter("./memForwarding.log", "UTF-8");
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
}
