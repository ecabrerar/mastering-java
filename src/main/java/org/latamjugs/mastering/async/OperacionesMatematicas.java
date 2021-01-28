package org.latamjugs.mastering.async;

import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;

/**
 * @author ecabrerar
 * @since Jan 27, 2021
 */
public final class OperacionesMatematicas {
	private static Logger logger = Logger.getLogger(OperacionesMatematicas.class.getName());

	private OperacionesMatematicas() {
	}

	public static Long factorial(int n) {

		sleep(1000);
		logger.info(getPoolInfo());

		if (n == 1) {
			return 1L;
		} else {
			return n * factorial(n - 1);
		}
	}

	public static Long factorial2(int n) {

		sleep(1000);
		logger.info(getPoolInfo());

		return 1L;

	}

	private static void sleep(long timeToSleep) {

		try {

			Thread.sleep(timeToSleep);

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private static String getPoolInfo() {
		return ("Pool size: " + ForkJoinPool.commonPool().getPoolSize() + ". Thread:  "
				+ Thread.currentThread().getName());
	}
}
