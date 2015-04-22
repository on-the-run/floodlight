package net.floodlightcontroller.util;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class ThreadDump {
	 public static String crunchifyGenerateThreadDump() {
	        final StringBuilder dump = new StringBuilder();
	        final ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
	        final ThreadInfo[] threadInfos = threadMXBean.getThreadInfo(threadMXBean.getAllThreadIds(), 100);
	        for (ThreadInfo threadInfo : threadInfos) {
	            dump.append('"');
	            dump.append(threadInfo.getThreadId());
	            dump.append(": ");
	            dump.append(threadInfo.getThreadName());
	            dump.append("\" ");
	            final Thread.State state = threadInfo.getThreadState();
	            dump.append("\n   java.lang.Thread.State: ");
	            dump.append(state);
	            final StackTraceElement[] stackTraceElements = threadInfo.getStackTrace();
	            for (final StackTraceElement stackTraceElement : stackTraceElements) {
	                dump.append("\n        at ");
	                dump.append(stackTraceElement);
	            }
	            dump.append("\n");
	        }
	        dump.append("\n========================\n");
	        return dump.toString();
	    }
	 
	 public static void main(String[] args) {
		 System.out.println(ThreadDump.crunchifyGenerateThreadDump());
	 }
}


