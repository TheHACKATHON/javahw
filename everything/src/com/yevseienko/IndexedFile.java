package com.yevseienko;

import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

public class IndexedFile {
    private boolean _file;
    private String _path;
    private String _fileName;
    private boolean _hidden;
    private long _size;
    private LocalDateTime _date;

    public boolean isFile() {
        return _file;
    }

    public String getPath() {
        return _path;
    }

    public String getFileName() {
        return _fileName;
    }

    public boolean isHidden() {
        return _hidden;
    }

    public long getSize() {
        return _size;
    }

    public LocalDateTime getDate() {
        return _date;
    }

    public String getDateString() { return _date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")); }

    private IndexedFile() { }

    public static IndexedFile of(String line) {
        var instance = new IndexedFile();
        instance._file = line.startsWith("1");

        var indexOfPath = line.indexOf("<") + 1;
        var indexOfHidden = line.indexOf("<", indexOfPath) + 1;
        int indexOfSize = 0;
        int indexOfDate = 0;

        if (instance._file) {
            indexOfSize = line.indexOf("<", indexOfHidden) + 1;
            indexOfDate = line.indexOf("<", indexOfSize) + 1;
        }

        instance._path = line.substring(indexOfPath, indexOfHidden - 1);
        instance._fileName = Paths.get(instance._path).getFileName().toString();

        String hidden = null;
        if (instance._file) {
            hidden = line.substring(indexOfHidden, indexOfSize - 1);
            var size = line.substring(indexOfSize, indexOfDate - 1);
            instance._size = Long.parseLong(size);
            var date = line.substring(indexOfDate);
            instance._date = LocalDateTime.ofEpochSecond(Long.parseLong(date),
                    0, ZoneOffset.systemDefault().getRules().getOffset(LocalDateTime.now()));
        } else {
            hidden = line.substring(indexOfHidden);
        }
        instance._hidden = hidden.equals("1");
        return instance;
    }
}
