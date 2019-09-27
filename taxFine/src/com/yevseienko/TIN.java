package com.yevseienko;

import java.util.UUID;

public class TIN {
    // Taxpayer Identification Number
    private static int Id;
    private int _id;
    private String _taxNumber;
    private User _user;

    static {
        Id = Settings.getMaxTINId();
    }

    public TIN(User user) {
        _id = ++Id;
        Settings.newTIN();

        if (user == null) {
            throw new IllegalArgumentException("Пользователь не может быть null");
        }

        _taxNumber = UUID.randomUUID().toString();
        _user = user;
    }

    public int getId() {
        return _id;
    }

    public String getTaxNumber() {
        return _taxNumber;
    }

    public User getUser() {
        return _user;
    }

    @Override
    public String toString() {
        return _taxNumber;
    }
}
