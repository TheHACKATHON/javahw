package com.yevseienko;

import com.github.amr.mimetypes.MimeType;
import com.github.amr.mimetypes.MimeTypes;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class Download implements Runnable {
	private static final Object _sLOCK = new Object();
	private static int _sId;
	private int _id;
	private URI _uri;
	private String _fileName;
	private String _extFileName;
	private String _extSave;
	private int _len;
	private float _percent;
	private boolean _done;
	private boolean _failed;

	static {
		MimeTypes.blank().load(Paths.get("src", "main", "resources", "mime.types"));
	}

	public Download(URI uri) {
		_id = ++_sId;
		this._uri = uri;
		//_percent = new AtomicInteger();
	}

	public URI getUri() {
		return _uri;
	}

	public String getFileName() {
		return _fileName;
	}

	public String getExtSave() {
		return _extSave;
	}

	public int getLen() {
		return _len;
	}

	public float getDownloadPercent() {
		return _percent;
	}

	public boolean isDone() {
		return _done;
	}

	public boolean isFailed() {
		return _failed;
	}

	private void download() {
		String saveDirectory = Settings.get().getDownloadPath().toString();
		try {
			Files.createDirectories(Paths.get(saveDirectory));
		} catch (IOException e) {
			System.out.println("[Error] Нет доступа к папке загрузки, выберите другую папку");
			return;
		}

		HttpRequest request = HttpRequest.newBuilder()
				.uri(_uri).GET().build();

		Path file = null;
		synchronized (_sLOCK) {
			int iterator = 0;
			do {
				String fileName = _fileName + (iterator == 0 ? "" : ("(" + iterator + ")"));
				file = Path.of(saveDirectory, fileName + "." + _extSave);
				if (file.toFile().exists()) {
					iterator++;
				} else {
					break;
				}
			} while (true);
		}

		try (InputStream stream = HttpClient.newBuilder()
				.followRedirects(HttpClient.Redirect.ALWAYS)
				.build()
				.send(request, HttpResponse.BodyHandlers.ofInputStream()).body();
		     FileOutputStream fileOutputStream = new FileOutputStream(file.toFile())) {
			int bufferLength = 512;
			int ready = 0;
			byte[] buffer = new byte[bufferLength];
			int read;
			while ((read = stream.read(buffer, 0, bufferLength)) != -1) {
				fileOutputStream.write(buffer, 0, read);
				ready += read;
				_percent = 100f * ((float) ready / _len);
			}

			_done = true;
			//System.out.printf("\n[%s]%s загружен по пути %s\n", Thread.currentThread().getId(), _fileName, saveDirectory);
			// вместо этих уведомлений о загрузке сделал прогресс для каждого файла, второй пункт меню
		} catch (Exception ex) {
			_failed = true;
			System.out.println("[Error] Нет доступа к папке загрузки, выберите другую папку");
		}
	}

	private boolean getFileInfo(URI path){
		String fragment = path.getPath();
		_fileName = fragment.substring(fragment.lastIndexOf('/') + 1);
		if (_fileName.contains(".")) {
			int idx = _fileName.lastIndexOf('.');
			this._extFileName = _fileName.substring(idx + 1);
			this._fileName = _fileName.substring(0, idx);
		}
		HttpRequest headerRequest = null;
		try {
			headerRequest = HttpRequest.newBuilder()
					.uri(path).method("HEAD", HttpRequest.BodyPublishers.noBody()).build();
		} catch (Exception ignored) {
			System.out.println("Ссылка имеет неверный формат или файл недоступен");
			return false;
		}
		HttpResponse response = null;
		try {
			response = HttpClient.newBuilder()
					//.executor(EXECUTOR)
					.followRedirects(HttpClient.Redirect.ALWAYS)
					.build()
					.send(headerRequest, HttpResponse.BodyHandlers.ofString());
		} catch (Exception ex) {
			System.out.println("Не удалось загрузить " + path.toString());
		}

		Map<String, List<String>> headers = response.headers().map();
		String headerContentLen = "Content-Length";
		String headerContentType = "Content-Type";
		String typeBinary = "application/octet-stream";

		if (headers.containsKey(headerContentLen)) {
			this._len = Integer.parseInt(headers.get(headerContentLen).get(0));
		}
		MimeType mime = null;
		if (headers.containsKey(headerContentType)) {
			String typeFromHeader = headers.get(headerContentType).get(0);
			mime = MimeTypes.getInstance().getByType(typeFromHeader);
			if (typeFromHeader.equals(typeBinary)) {
				mime = null;
			}
		}

		if (mime == null) {
			this._extSave = this._extFileName;
		} else {
			this._extSave = mime.getExtension();
		}
		return true;
	}

	@Override
	public void run() {
		if (getFileInfo(_uri)) {
			download();
		}
	}
}
