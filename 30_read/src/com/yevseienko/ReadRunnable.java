package com.yevseienko;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.concurrent.locks.ReentrantLock;

public class ReadRunnable implements Runnable {
    private static ReentrantLock _locker;
    private BufferedReader _in;


    static {
        _locker = new ReentrantLock();
    }

    public ReadRunnable(BufferedReader in) {
        _in = in;
    }

    public static ReentrantLock getLocker() {
        return _locker;
    }

    @Override
    public void run() {
        String tmp = null;
        do {
            try {
                _locker.lock();
                tmp = _in.readLine();
                if (tmp != null) {
                    System.out.println("[" + Thread.currentThread().getId() + "]" + tmp);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                _locker.unlock();

            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while (tmp != null);
    }
}
