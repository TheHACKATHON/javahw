package com.yevseienko;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public final class Settings implements Serializable {
	private static Path _sSettingsPath = Paths.get(".", ".settings");
	private static Settings _sSettings;
	private Path _sDownloadPath;
	private Path _sMimeTypesPath;

	private Settings() {
		// эти значения заполнятся при первом запуске, дальше будут тянуться сериализированные
		this._sDownloadPath = Paths.get("downloads");
		this._sMimeTypesPath = Paths.get("src", "main", "resources", "mime.types");
	}

	public static Settings get() {
		if (_sSettings == null) {
			reload();
		}
		return _sSettings;
	}

	public Path getDownloadPath() {
		return _sDownloadPath;
	}

	public Path getMimeTypesPath() {
		return _sMimeTypesPath;
	}

	public void setDownloadPath(Path downloadPath) {
		this._sDownloadPath = downloadPath;
		save();
	}

	public void setMimeTypesPath(Path mimeTypesPath) {
		this._sMimeTypesPath = mimeTypesPath;
		save();
	}

	public static void save() {
		try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(_sSettingsPath.toFile()))) {
			outputStream.writeObject(_sSettings);
		} catch (Exception ex) {
			System.out.println("Serialize error");
		}
	}

	public static void reload() {
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