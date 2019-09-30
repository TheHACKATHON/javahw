package com.yevseienko;

import java.net.URI;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class Downloader {
	private static final int THREAD_COUNT = 5;
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(THREAD_COUNT);
	private static final ArrayList<Download> _downloads = new ArrayList<>();

	public static void main(String[] args) throws InterruptedException {
		Scanner in = new Scanner(System.in);
		String menuString = null;
		String message = null;
		Menu menu;

		mainWhile:
		do {
			cls();
			if (message != null) {
				System.out.println("[INFO]:" + message);
				message = null;
			}
			System.out.printf("Меню:\n" +
					"\t1. Загрузить файл\n" +
					"\t2. Посмотреть прогресс загрузки\n" +
					"\t3. Загрузить тестовые 10 файлов\n" +
					"\t4. Изменить папку загруки(%s)\n" +
					"\n\t0. Выход\n>", Settings.get().getDownloadPath().toString());
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
					String path = new Scanner(System.in).nextLine().trim();
					download(path);
					break;
				case SHOW_PROGRESS:
					message = "Show progress";
					break;
				case TEST:
					message = "начал загрузку десяти файлов";
					download("https://github.com/maxchv/javalesson2019/raw/master/books/OCA%20Oracle%20Certified%20Associate%20Java%20SE%208%20Programmer%20I%20Study%20Guide%20Exam%201Z0-808.pdf");
					download("https://github.com/maxchv/javalesson2019/raw/master/books/Oracle%20Certified%20Professional%20Java%20SE%208%20Programmer%20Exam%201Z0-809.pdf");
					download("https://github.com/maxchv/javalesson2019/raw/master/books/java%20для%20профессионалов.pdf");
					download("https://github.com/maxchv/javalesson2019/raw/master/01.java/module11/hw01.rar");
					download("https://github.com/maxchv/javalesson2019/raw/master/01.java/module11/hw03.rar");
					download("https://github.com/maxchv/javalesson2019/raw/master/01.java/module11/hw02.txt");
					download("https://github.com/maxchv/javalesson2019/raw/master/01.java/module13/Modul%2011%20Vychislitelnye%20potoki.pptx");
					download("https://github.com/maxchv/javalesson2019/raw/master/01.java/module13/README.md");
					download("https://github.com/maxchv/javalesson2019/raw/master/01.java/module13/hw04/pom.xml");
					download("http://130.61.122.39/index.php/s/Sd3CLXZQtMfp56o/download");

					break;
				case CHANGE_DOWNLOAD_DIR:
					break;
				case TRY_AGAIN:
					message = "Try again";
					break;
			}
		} while (true);
		EXECUTOR.shutdown();
	}

	private static void download(String webPath) {
		URI path = URI.create(webPath);
		Download file = new Download(path);
		_downloads.add(file);
		EXECUTOR.execute(file);
	}

	private static void cls() {
		System.out.println("\n".repeat(10));
	}
}

