package com.yevseienko;

public class City {
    private static int Id;
    private int _id;
    private String _name;

    static {
        Id = Settings.getMaxCityId();
    }

    public City(String name) {
        _id = ++Id;
        Settings.newCity();

        if (name != null) {
            name = name.trim();
        }
        if (name != null && !name.isEmpty()) {
            _name = name;
        } else {
            throw new IllegalArgumentException("Введите название города");
        }
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    @Override
    public String toString() {
        return _name;
    }
}
