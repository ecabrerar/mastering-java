package org.latamjugs.mastering.async;

import java.util.Iterator;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.logging.Logger;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

class OperacionesMatematicasTest {
	private Logger logger = Logger.getLogger(this.getClass().getName());

	@Test
	void testFactorial() {
		long timeBeforeStart = System.currentTimeMillis();

		logger.info("testFactorial 1");
		System.out.println(OperacionesMatematicas.factorial(5));

		logger.info("testFactorial 2");
		System.out.println(OperacionesMatematicas.factorial(5));

		long timeNow = System.currentTimeMillis();

		long totalExecutionTime = timeNow - timeBeforeStart;
		logger.info(String.format("Tiempo ejecución testFactorial : %d segundos ", (totalExecutionTime / 1000)));
	}

	@Test
	void testFactorialWithMultiplesThreads() throws InterruptedException {
		Thread thread1 = new Thread(() -> System.out.println(OperacionesMatematicas.factorial(5)));
		Thread thread2 = new Thread(() -> System.out.println(OperacionesMatematicas.factorial(5)));

		logger.info(getPoolInfo());

		long timeBeforeStart = System.currentTimeMillis();

		logger.info("testFactorialWithMultiplesThreads thread1");
		thread1.start();

		logger.info("testFactorialWithMultiplesThreads thread2");
		thread2.start();

		thread1.join();
		thread2.join();

		long timeNow = System.currentTimeMillis();

		long totalExecutionTime = timeNow - timeBeforeStart;
		logger.info(String.format("Tiempo ejecución testFactorial : %d segundos ", (totalExecutionTime / 1000)));

	}

	@Test
	void testFactorialWithMultiplesAsync() {
		logger.info(getPoolInfo());

		long timeBeforeStart = System.currentTimeMillis();

		logger.info("testFactorialWithMultiplesAsync 1");
		Long cf1 = CompletableFuture.supplyAsync(() -> OperacionesMatematicas.factorial(5)).join();
		System.out.println(cf1);

		long timeNow = System.currentTimeMillis();

		long totalExecutionTime = timeNow - timeBeforeStart;
		logger.info(String.format("Tiempo ejecución testFactorialWithMultiplesAsync 1: %d segundos ",
				(totalExecutionTime / 1000)));

		long timeBeforeStart1 = System.currentTimeMillis();
		logger.info("testFactorialWithMultiplesAsync 2");
		Long cf2 = CompletableFuture.supplyAsync(() -> OperacionesMatematicas.factorial(5)).join();
		System.out.println(cf2);

		long timeNow1 = System.currentTimeMillis();

		long totalExecutionTime1 = timeNow1 - timeBeforeStart1;
		logger.info(String.format("Tiempo ejecución testFactorialWithMultiplesAsync 2: %d segundos ",
				(totalExecutionTime1 / 1000)));

	}

	@Test
	void testFactorial2() throws InterruptedException {

		ExecutorService executor = Executors.newFixedThreadPool(5);
		ExecutorService executor2 = ForkJoinPool.commonPool();
		logger.info(getPoolInfo());
		
		for (int i = 0; i < 10; i++) {
			
			 CompletableFuture
					.runAsync(() -> OperacionesMatematicas.factorial2(5), executor2)
					.thenAccept( System.out::println);
		}
		
		Thread.sleep(10000);

	}

	private String getPoolInfo() {
		return ("Pool size: " + ForkJoinPool.commonPool().getPoolSize() + ". Thread:  "
				+ Thread.currentThread().getName());
	}

}
