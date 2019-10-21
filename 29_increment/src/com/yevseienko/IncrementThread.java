package com.yevseienko;

public class IncrementThread implements Runnable {
    private int _counter;
    private String _name;

    public IncrementThread(String name) {
        this._name = name;
    }

    public String getName() {
        return _name;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            _counter++;
        }
        System.out.println(String.format("%s counter = %d", _name, _counter));
    }
}
