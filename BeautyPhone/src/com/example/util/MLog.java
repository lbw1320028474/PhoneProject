package com.example.util;

public class MLog {
	public static int line(Exception e) {

		StackTraceElement[] trace = e.getStackTrace();

		if (trace == null || trace.length == 0)

			return -1; //

			return trace[0].getLineNumber();

	}

	public static String fun(Exception e) {

		StackTraceElement[] trace = e.getStackTrace();

		if (trace == null)

			return ""; //

		return trace[0].getMethodName();

	}
}
