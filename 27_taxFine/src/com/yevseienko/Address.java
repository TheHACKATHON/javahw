package com.yevseienko;

public class Address {
    private static int Id;
    private int _id;
    private City _city;
    private String _addressLine;
    private String _postalCode;

    static {
        Id = Settings.getMaxAddressId();
    }

    public Address(City city, String address, String postal) {
        _id = ++Id;
        Settings.newAddress();
        if (address != null) {
            address = address.trim();
        }

        if (postal != null) {
            postal = postal.trim();
        }

        if (city != null && address != null && !address.isEmpty() && postal != null && !postal.isEmpty()) {
            _city = city;
            _addressLine = address;
            _postalCode = postal;
        } else {
            throw new IllegalArgumentException("Все аргументы не должны быть пусты");
        }
    }

    public int getId() {
        return _id;
    }

    public City getCity() {
        return _city;
    }

    public String getAddressLine() {
        return _addressLine;
    }

    public String getPostalCode() {
        return _postalCode;
    }

    @Override
    public String toString() {
        return String.format("%s, %s", _city, _addressLine);
    }
}
