package com.yevseienko;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Settings implements Serializable {
	private static final Path _sSettingsPath = Paths.get(".", ".settings");
	private static final String _sDefaultDownloadPath = "downloads";
	private static Settings _sSettings;
	private String _sDownloadPath;
	private String _sMimeTypesPath;

	private Settings() {
		// эти значения заполнятся при первом запуске, дальше будут тянуться сериализированные
		this._sDownloadPath = _sDefaultDownloadPath;
		this._sMimeTypesPath = Paths.get("src", "main", "resources", "mime.types").toString();
	}

	public static Settings get() {
		if (_sSettings == null) {
			reload();
		}
		return _sSettings;
	}

	public Path getDownloadPath() {
		return Path.of(_sDownloadPath);
	}

	public Path getMimeTypesPath() {
		return Path.of(_sMimeTypesPath);
	}

	public void setDownloadPath(Path downloadPath) {
		this._sDownloadPath = downloadPath.toString();
		save();
	}

	public void setMimeTypesPath(Path mimeTypesPath) {
		this._sMimeTypesPath = mimeTypesPath.toString();
		save();
	}

	private static void save() {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(_sSettingsPath.toFile()))) {
			outputStream.writeObject(_sSettings);
		} catch (Exception ex) {
			System.out.println("Serialize error");
		}
	}

	private static void reload() {
		try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(_sSettingsPath.toFile()))) {
			Settings tmp = (Settings) inputStream.readObject();
			if (tmp == null) {
				_sSettings = new Settings();
			} else {
				_sSettings = tmp;
			}
		} catch (Exception ex) {
			_sSettings = new Settings();
		}
	}
}