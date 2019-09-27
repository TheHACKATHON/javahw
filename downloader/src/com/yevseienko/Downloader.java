package com.yevseienko;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class Downloader {
	private static final int THREAD_COUNT = 5;
	public static void main(String[] args) throws InterruptedException {
		ExecutorService service = Executors.newFixedThreadPool(THREAD_COUNT);
		ArrayList<URI> filesToDownload = new ArrayList<>();
		AtomicInteger ready = new AtomicInteger();
		for (String path : args) {
			filesToDownload.add(URI.create(path));
		}
		int filesCount = filesToDownload.size();

		for (URI path : filesToDownload) {
			HttpRequest request = HttpRequest.newBuilder()
					.uri(path).GET().build();

			var fragment = path.getPath();
			var file = fragment.substring(fragment.lastIndexOf('/') + 1);
			HttpClient.newBuilder()
					.executor(service)
					.build()
					.sendAsync(request, HttpResponse.BodyHandlers.ofFile(Path.of("./", "downloads", file)))
					.whenComplete((p, k) -> {
						System.out.println("[" + Thread.currentThread().getId() + "]" + file + " загружен");
						ready.getAndIncrement();
					});
		}

		while (ready.get() != filesCount) {
			Thread.sleep(100);
		}

		service.shutdown();
	}
}
