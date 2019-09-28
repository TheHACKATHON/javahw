package com.yevseienko;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class Downloader {
	private static final int THREAD_COUNT = 5;
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(THREAD_COUNT);
	// private static final ArrayList<String>

	public static void main(String[] args) {
		Scanner in = new Scanner(System.in);
		String menuString = null;
		String message = null;
		Menu menu;

		mainWhile:
		do {
			cls();
			if (message != null) {
				System.out.println("[INFO]:" + message);
			}
			System.out.print("Меню:\n" +
					"\t1. Загрузить файл\n" +
					"\t2. Посмотреть прогресс загрузки" +
					"\n\t0. Выход\n>");
			menuString = in.next().trim();
			if (Pattern.matches("^\\d$", menuString)) {
				int menuInt = Integer.parseInt(menuString);
				menu = Menu.getByNumber(menuInt);
			} else {
				menu = Menu.TRY_AGAIN;
			}

			switch (menu) {
				case EXIT:
					break mainWhile;
				case DOWNLOAD:
					System.out.print("Вставьте ссылку на файл: ");
					String url = new Scanner(System.in).nextLine();
					download(url, "downloads");
					// TODO: запросить у пользователя куда сохранять
					break;
				case SHOW_PROGRESS:
					message = "Show progress";
					break;
				case TRY_AGAIN:
					message = "Try again";
					break;
			}
		} while (true);
		EXECUTOR.shutdown();
	}

	private static void download(String webPath, String saveDirectory) {
		URI path = URI.create(webPath);
		HttpRequest request = HttpRequest.newBuilder()
				.uri(path).GET().build();

		var fragment = path.getPath();
		var file = fragment.substring(fragment.lastIndexOf('/') + 1);
		HttpClient.newBuilder()
				.executor(EXECUTOR)
				.followRedirects(HttpClient.Redirect.ALWAYS)
				.build()
				.sendAsync(request, HttpResponse.BodyHandlers.ofFile(Path.of("./", saveDirectory, file)))
				.whenCompleteAsync((p, k) -> {
					System.out.println("[" + Thread.currentThread().getId() + "]" + file + " загружен");
				});
	}

	private static void cls() {
		System.out.println("\n".repeat(10)) ;
	}

	private enum Menu {
		EXIT(0), DOWNLOAD(1), SHOW_PROGRESS(2), TRY_AGAIN(-1);

		private int _number;
		private static Map<Integer, Menu> _menu = null;

		public int getNumber() {
			return _number;
		}

		private Menu(int num) {
			_number = num;
		}

		public static Menu getByNumber(int num) {
			if (_menu == null) {
				ArrayList<Menu> menu = new ArrayList<>(Arrays.asList(Menu.values()));
				_menu = new HashMap<>();
				for (Menu m : menu) {
					_menu.put(m._number, m);
				}

			}

			if (_menu.containsKey(num)) {
				return _menu.get(num);
			}
			return TRY_AGAIN;
		}
	}
}
