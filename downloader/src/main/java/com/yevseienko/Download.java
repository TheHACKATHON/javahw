package com.yevseienko;

import java.net.URI;

public class Download{
	private static int _sId;
	private int _id;
	private URI _uri;
	private String _fileName;
	private String _extFileName;
	private String _extSave;
	private int _len;
	private float _percent;
	private boolean _done;

	public Download(URI uri) {
		_id = ++_sId;
		this._uri = uri;
	}

	public int getId() {
		return _id;
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

	public float getPercent() {
		return _percent;
	}

	public boolean isDone() {
		return _done;
	}

	public void setFileName(String fileName) {
		this._fileName = fileName;
	}

	public void setExtSave(String ext) {
		this._extSave = ext;
	}

	public void setLen(int len) {
		this._len = len;
	}

	public void setPercent(float percent) {
		this._percent = percent;
	}

	public void setDone(boolean done) {
		this._done = done;
	}

	public String getExtFileName() {
		return _extFileName;
	}

	public void setExtFileName(String _fileNameExt) {
		this._extFileName = _fileNameExt;
	}
}
