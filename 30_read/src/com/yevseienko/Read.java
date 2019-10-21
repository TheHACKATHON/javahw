package com.yevseienko;

import java.io.*;
import java.util.ArrayList;

public class Read {
    public static void main(String[] args) {
        var file = "exm.txt";
        var out = "out.txt";
        var threadCount = 10;
        var threads = new ArrayList<Thread>();

        try (var fileReader = new FileReader(file);
             var reader = new BufferedReader(fileReader);
             var fileOut = new FileOutputStream(out);
             var printStream = new PrintStream(fileOut)) {
            System.setOut(printStream);
            for (byte i = 0; i < threadCount; i++) {
                threads.add(new Thread(new ReadRunnable(reader)));
            }
            threads.forEach(Thread::start);
            for (Thread thread : threads) {
                thread.join();
            }

        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}