package com.yevseienko;

import com.github.amr.mimetypes.MimeType;
import com.github.amr.mimetypes.MimeTypes;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.regex.Pattern;

public class Downloader {
	private static final int THREAD_COUNT = 5;
	private static final ExecutorService EXECUTOR = Executors.newFixedThreadPool(THREAD_COUNT);
	// private static final ArrayList<String>
	static {
		MimeTypes.blank().load(Paths.get("src", "main", "resources", "mime.types"));
	}

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
					"\t3. Загрузить тестовые 10 файлов" +
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
		Download file = getFileType(path);

		HttpRequest request = HttpRequest.newBuilder()
				.uri(path).GET().build();

	/*	var fragment = path.getPath();
		var file = fragment.substring(fragment.lastIndexOf('/') + 1);
		HttpClient.newBuilder()
				.executor(EXECUTOR)
				.followRedirects(HttpClient.Redirect.ALWAYS)
				.build()
				.sendAsync(request, HttpResponse.BodyHandlers.ofFile(Path.of("./", saveDirectory, file)))
				.whenCompleteAsync((p, k) -> {
					System.out.println("[" + Thread.currentThread().getId() + "]" + file + " загружен");
				});*/
	}

	private static Download getFileType(URI path){
		Download download = new Download(path);
		String fragment = path.getPath();
		String filename = fragment.substring(fragment.lastIndexOf('/') + 1);
		if(filename.contains(".")){
			int idx = filename.lastIndexOf('.');
			download.setExtFileName(filename.substring(idx + 1));
			download.setFileName(filename.substring(0, idx));
		}

		HttpRequest headerRequest = HttpRequest.newBuilder()
				.uri(path).method("HEAD", HttpRequest.BodyPublishers.noBody()).build();
		HttpClient.newBuilder()
				.executor(EXECUTOR)
				.followRedirects(HttpClient.Redirect.ALWAYS)
				.build()
				.sendAsync(headerRequest, HttpResponse.BodyHandlers.ofString())
				.thenApply((response) -> {
					// TODO: достать хедеры content-type и content-length
					Map<String, List<String>> headers = response.headers().map();
					String headerContentLen = "Content-Length";
					String headerContentType = "Content-Type";
					String typeBinary = "application/octet-stream";

					if(headers.containsKey(headerContentLen)){
						download.setLen(Integer.parseInt(headers.get(headerContentLen).get(0)));
					}
					if(headers.containsKey(headerContentType)){
						String typeFromHeader = headers.get(headerContentType).get(0);
						MimeType mime = MimeTypes.getInstance().getByType(typeFromHeader);

						if(typeFromHeader.equals(typeBinary) || mime == null){
							// TODO: get type from file extension
							download.setExtSave(download.getExtFileName());
						}
						else{
							String extFromMIME = mime.getExtension();
							download.setExtSave(extFromMIME);
						}
					}
					return response;
				});

		return download;
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

