package com.jsharper.basics.webflux.utils;

public class MathUtils {

	public static void sleepCurr(int seconds) {
		
		try {
			Thread.sleep(seconds*1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
