package com.yevseienko;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Stream;

public class Task01 {
	/*
	 * Класс QuickSort предназначен для быстрой сортировки массивов
	 * Необходимо применить ForkJoinPool для ускорения сортировки массивов.
	 * Оценить эффективность распараллеливания данной задачи.
	 * Сравнить сортировку без распараллеливания с параллелной реализвацией
	 * через ForkJoinPool и с параллельной сортировкой через стримы.
	 */
	public static void main(String[] args) throws ExecutionException, InterruptedException {
		ForkJoinPool pool = new ForkJoinPool();
		Random rnd = new Random(2520);
		Integer[] data = rnd.ints().limit(10_000_000).boxed().toArray(Integer[]::new);
		Integer[] data2 = rnd.ints().limit(10_000_000).boxed().toArray(Integer[]::new);

		QuickSort<Integer> quickSort = new QuickSort<>(data);
		Instant start = Instant.now();
		pool.execute(quickSort);
		quickSort.get();
		Duration duration = Duration.between(start, Instant.now());
		System.out.println("ForkJoinPool duration = " + duration.toMillis());

		Stream k = Arrays.stream(data2).parallel();
		start = Instant.now();
		k.sorted().count();
		duration = Duration.between(start, Instant.now());
		System.out.println("Stream duration = " + duration.toMillis());

		// результаты:
		// без распаралеливания 9 секунд
		// ForkJoinPool 3.8 секунд
		// ParallelStream 2.9 секунд
	}
}

class QuickSort<T extends Comparable> extends RecursiveTask {

	private final T[] data;
	private int left;
	private int right;

	public QuickSort(T[] data) {
		this.data = data;
		left = 0;
		right = data.length - 1;
	}

	public QuickSort(T[] data, int left, int right) {
		this.data = data;
		this.left = left;
		this.right = right;
	}

	public T[] getData() {
		return data;
	}

	private int partition(T data[], int left, int right) {
		T pivot = data[right];
		int i = (left - 1);

		for (int j = left; j < right; j++) {
			if (data[j].compareTo(pivot) <= 0) {
				i++;

				T swapTemp = data[i];
				data[i] = data[j];
				data[j] = swapTemp;
			}
		}

		T swapTemp = data[i + 1];
		data[i + 1] = data[right];
		data[right] = swapTemp;

		return i + 1;
	}

	@Override
	protected Object compute() {
		if (left < right) {
			int partitionIndex = partition(data, left, right);

			QuickSort<T> leftSort = new QuickSort<>(data, left, partitionIndex - 1);
            leftSort.fork();
			new QuickSort<>(data, partitionIndex + 1, right).compute();
            leftSort.join();
		}
		return null;
	}
}
