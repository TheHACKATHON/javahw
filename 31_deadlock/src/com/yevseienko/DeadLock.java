package com.yevseienko;

import java.util.concurrent.locks.ReentrantLock;

public class DeadLock {
    private static final ReentrantLock Lock1 = new ReentrantLock();
    private static final ReentrantLock Lock2 = new ReentrantLock();

    private static class Thread1 extends Thread {
        public void run() {
            if(Lock1.tryLock()){
                System.out.println("Thread 1: Has Lock1");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
                System.out.println("Thread 1: Waiting for Lock 2");
                if(Lock2.tryLock()){
                    System.out.println("Thread 1: No DeadLock");
                    Lock2.unlock();
                }
                Lock1.unlock();
            }
        }
    }

    private static class Thread2 extends Thread {
        public void run() {
            if(Lock2.tryLock()) {
                System.out.println("Thread 2: Has Lock2");
                try {
                    Thread.sleep(100);
                } catch (InterruptedException ignored) {}
                System.out.println("Thread 2: Waiting for Lock 1");
                if(Lock1.tryLock()) {
                    System.out.println("Thread 2: No DeadLock");
                    Lock1.unlock();
                }
                Lock2.unlock();
            }
        }
    }

    public static void main(String[] args) {
        Thread1 thread1 = new Thread1();
        Thread2 thread2 = new Thread2();
        thread1.start();
        thread2.start();
    }
}
