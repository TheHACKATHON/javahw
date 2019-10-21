package com.yevseienko;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class JThreadPool extends Thread{
    private ArrayList<JThread> _threads;
    private ReentrantLock _locker;
    private static int _maxActiveThreadsCount;
    private int activeThreads;

    public JThreadPool(int threads) throws IOException {
        _locker = new ReentrantLock();
        _threads = new ArrayList<>();
        activeThreads = 0;
        _maxActiveThreadsCount = threads;

        try(var stream = new FileWriter(JThread.IndexPath)){
            stream.write(
                    "# формат записи: fd<path<hidden<size?<date?\n" +
                    "# < - сепаратор\n" +
                    "# fd - может быть 0 если это папка или 1 если файл\n" +
                    "# path - путь к файлу или папке\n" +
                    "# hidden - 1 если файл или папка скрыты, иначе 0\n" +
                    "# size - размер файла в байтах(для папок не указывается)\n" +
                    "# date - дата и время создания файла в unix-формате(для папок не указывается)\n");
        }
    }

    public void run(){
        int iterator = 0;
        while (!isInterrupted()){
            iterator++;
            for (var i = _threads.size() - 1; i >= 0; i--){
                var current = _threads.get(i);
                if(current.getState() == State.TERMINATED){
                    System.out.print(String.format("\n[%s]Закончил индекс %s", current.getId(), current.getName()));
                    _threads.remove(current);
                    activeThreads--;
                    continue;
                }
                if(activeThreads < _maxActiveThreadsCount && current.getState() == State.NEW){
                    activeThreads++;
                    current.start();
                    System.out.print(String.format("\n[%s]Начал индекс %s", current.getId(), current.getName()));
                }
            }
            if(_threads.size() == 0){
                System.out.print("\nИндексация закончена");
                break;
            }
            try {
                Thread.sleep(10);
                if(iterator % 60 == 0){
                    System.out.print(".");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    public void addDirectory(File dir, boolean includeDirectories){
        _threads.add(new JThread("JThread " + dir, dir, _locker, includeDirectories));
    }
}
