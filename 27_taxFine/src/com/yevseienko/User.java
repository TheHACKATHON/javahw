package com.yevseienko;

import java.util.ArrayList;
import java.util.Set;

public class User {
    private static int Id;
    private int _id;
    private String _firstName;
    private String _lastName;
    private TIN _taxNumber;
    private Address _address;
    private ArrayList<Fine> _fines;

    static {
        Id = Settings.getMaxUserId();
    }

    public User(String firstName, String lastName, Address address) {
        this._id = ++Id;
        Settings.newUser();

        if (firstName != null) {
            firstName = firstName.trim();
        }
        if (lastName != null) {
            lastName = lastName.trim();
        }
        if (firstName != null && lastName != null && address != null && !firstName.isEmpty() && !lastName.isEmpty()) {
            _taxNumber = new TIN(this);
            this._firstName = firstName;
            this._lastName = lastName;
            this._address = address;
            _fines = new ArrayList<>();
        } else {
            throw new IllegalArgumentException("Все аргументы не должны быть пусты");
        }
    }

    public int getId() {
        return _id;
    }

    public String getFirstName() {
        return _firstName;
    }

    public String getLastName() {
        return _lastName;
    }

    public TIN getTaxNumber() {
        return _taxNumber;
    }

    public Address getAddress() {
        return _address;
    }

    @Override
    public String toString() {
        return String.format("%s %s", _firstName, _lastName);
    }
}
