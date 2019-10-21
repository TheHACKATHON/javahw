package com.yevseienko;

import java.util.Random;
import java.util.concurrent.*;

public class Task02 {
	/*
	 * Есть задача: поднять тяжелый камень, но для этого необходимо усилие 10 человек.
	 * Человек - это класс, который выполняет определенную работу в отдельном потоке.
	 * Написать программу, в которой создать 10 потоков (людей), которые совместными
	 * услиями одновременно (только когда соберется 10 человек) смогут сдвинуть камень.
	 *
	 * Какой способ синхронизации лучше всего для этого использовать: Semaphore,
	 * CountDownLatch или CyclicBarrier.
	 *  - CyclicBarrier
	 *
	 * Для запуска потоков использовать ThreadPool
	 */
	private static final CyclicBarrier BARRIER = new CyclicBarrier(10, new Stone());
	private static final Random RND = new Random();

	public static void main(String[] args) throws InterruptedException {
		ExecutorService pool = Executors.newCachedThreadPool();
		pool.execute(new Human("Ваня"));
		pool.execute(new Human("Саня"));
		pool.execute(new Human("Паня"));
		pool.execute(new Human("Женя"));
		pool.execute(new Human("Таня"));
		pool.execute(new Human("Маня"));
		pool.execute(new Human("Соня"));
		pool.execute(new Human("Даня"));
		pool.execute(new Human("Толя"));
		pool.execute(new Human("Илья"));

		Thread.sleep(20 * 1000);
		pool.shutdownNow();
	}

	public static class Stone implements Runnable {
		@Override
		public void run() {
			System.out.println("=======Подняли камень=======");
		}
	}

	public static class Human implements Runnable {
		private String _name;

		public Human(String _name) {
			this._name = _name;
		}

		@Override
		public void run() {
			while (!Thread.currentThread().isInterrupted()) {
				try {
					System.out.printf("%s ждёт у камня\n", _name);
					BARRIER.await();
					Thread.sleep(RND.nextInt(5000));
				} catch (InterruptedException | BrokenBarrierException ignored) {
					break;
				}
			}
		}
	}
}