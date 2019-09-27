package com.yevseienko;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class Fine {
    private static int Id;
    private int _id;
    private User _user;
    private FineType _type;
    private double _income;
    // доход
    private boolean _paid;
    private LocalDate _date;
    private LocalDate _payDay;
    private boolean _wrong;

    static {
        Id = Settings.getMaxFineId();
    }

    public Fine(User user, FineType type, double income) {
        _id = ++Id;
        Settings.newFine();

        if (user == null || type == null) {
            throw new IllegalArgumentException("Задайте все параметры");
        }

        _date = LocalDate.now();
        _user = user;
        _type = type;
        _income = income;
    }

    public void markAsWrong(){
        _wrong = true;
    }

    public boolean isWrong(){
        return _wrong;
    }

    public double getAmountOfFine() {
        return _type.calcFine(_income);
    }

    public void pay() {
        _payDay = LocalDate.now();
        _paid = true;
    }

    public int getId() {
        return _id;
    }

    public User getUser() {
        return _user;
    }

    public FineType getType() {
        return _type;
    }

    public double getIncome() {
        return _income;
    }

    public boolean isPaid() {
        return _paid;
    }

    public LocalDate getDate() {
        return _date;
    }

    public LocalDate getPayDay() {
        return _payDay;
    }

    @Override
    public String toString() {
        return String.format("%s %s %s %s", _user, _type, isPaid() ? "Оплачен" : "Не оплачен", getDate().format(DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss")));
    }
}
