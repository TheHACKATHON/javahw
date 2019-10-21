package com.yevseienko;

import java.util.ArrayList;

public class Increment {
    public static void main(String[] args) {
        var threadList = new ArrayList<Thread>();
        for (byte i = 0; i < 5; i++) {
            var it = new IncrementThread("IncrementThread " + i);
            var thread = new Thread(it, it.getName());
            thread.setPriority((i + 1) * 2);
            threadList.add(thread);
        }
        threadList.forEach(Thread::start);
        try {
            Thread.sleep(300);
            threadList.forEach(Thread::interrupt);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

