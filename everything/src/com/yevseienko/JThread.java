package com.yevseienko;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;

public class JThread extends Thread {
    public static final String IndexPath = "index.db";
    private File _disk;
    private ReentrantLock _locker;
    private boolean _includeDirectories;

    JThread(String name, File disk, ReentrantLock locker, boolean includeDirectories) {
        super(name);
        this._disk = disk;
        _locker = locker;
        _includeDirectories = includeDirectories;
    }

    public void run() {
        try {
            var tmpFile = File.createTempFile("everything", ".txt");
            tmpFile.deleteOnExit();
            try (var f = new FileWriter(tmpFile.getPath())) {
                if (_includeDirectories) {
                    Files.walkFileTree(this._disk.toPath(), new FileVisitor<Path>() {
                        @Override
                        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                            if (isInterrupted()) {
                                return FileVisitResult.TERMINATE;
                            }
                            if(!dir.toRealPath().toString().equals(dir.toString())){
                                return FileVisitResult.SKIP_SUBTREE;
                                // избегаю рекурсивных ссылок
                            }
                            try {
                                var isHidden = dir.toFile().isHidden() ? 1 : 0;
                                f.write(String.format("0<%s<%d\n", dir, isHidden));
                                return FileVisitResult.CONTINUE;
                            } catch (Exception ex) {
                                return FileVisitResult.SKIP_SUBTREE;
                            }
                        }

                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                            if (isInterrupted()) {
                                return FileVisitResult.TERMINATE;
                            }
                            var isHidden = file.toFile().isHidden() ? 1 : 0;
                            var size = attrs.size();
                            var unixTime = attrs.creationTime().to(TimeUnit.SECONDS);
                            f.write(String.format("1<%s<%d<%d<%d\n", file, isHidden, size, unixTime));
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }

                        @Override
                        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                            return FileVisitResult.CONTINUE;
                        }
                    });
                } else {
                    var list = _disk.listFiles();
                    if (list != null) {
                        for (var file : list) {
                            if (file.isFile()) {
                                var attr = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
                                var isHidden = file.isHidden() ? 1 : 0;
                                var size = attr.size();
                                var unixTime = attr.creationTime().to(TimeUnit.SECONDS);
                                f.write(String.format("1<%s<%d<%d<%d\n", file, isHidden, size, unixTime));
                            }
                        }
                    }

                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            _locker.lock();
            try (var reader = new BufferedReader(new FileReader(tmpFile))) {

                try (var writer = new FileWriter(IndexPath, true)) {
                    String text;
                    while((text=reader.readLine()) != null){
                        writer.write(text + "\n");
                        text=reader.readLine();
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            } finally {
                _locker.unlock();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
