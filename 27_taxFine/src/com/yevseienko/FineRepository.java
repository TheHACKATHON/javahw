package com.yevseienko;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

public class FineRepository {
    private static FineRepository _instance;
    private static String _dataPath;

    private ArrayList<Fine> _fines;
    private ArrayList<User> _users;
    private ArrayList<City> _cities;
    private ArrayList<Address> _addresses;

    static {
        _dataPath = "repository.db";
    }

    private FineRepository() {
        load();
    }

    private void init() {
        _fines = new ArrayList<>();
        _users = new ArrayList<>();
        _cities = new ArrayList<>();
        _addresses = new ArrayList<>();
    }

    public static FineRepository get() {
        return _instance != null ? _instance : (_instance = new FineRepository());
    }

    private void save() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(_dataPath))) {
            oos.writeObject(_fines);
            oos.writeObject(_users);
            oos.writeObject(_cities);
            oos.writeObject(_addresses);
        } catch (Exception ex) {
            System.out.println("Не удалось сохранить данные!");
        }
    }

    private void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(_dataPath))) {
            _fines = (ArrayList<Fine>) ois.readObject();
            _users = (ArrayList<User>) ois.readObject();
            _cities = (ArrayList<City>) ois.readObject();
            _addresses = (ArrayList<Address>) ois.readObject();

            if (_fines == null) {
                _fines = new ArrayList<>();
            }
            if (_users == null) {
                _users = new ArrayList<>();
            }
            if (_cities == null) {
                _cities = new ArrayList<>();
            }
            if (_addresses == null) {
                _addresses = new ArrayList<>();
            }
        } catch (Exception ex) {
            System.out.println("Не удалось загрузить данные!");
            init();
        }
    }

    public void print() {
        int i = 0;
        for (var fine : _fines) {
            System.out.println(String.format("%d. %s", i + 1, fine.toString()));
        }
    }

    public int addUser(String name, String lastName, Address address) {
        _addresses.add(address);
        var newUser = new User(name, lastName, address);
        _users.add(newUser);
        save();
        return newUser.getId();
    }

    public User getUser(int id) {
        var user = _users.stream().filter(u -> u.getId() == id).findAny();
        return user.orElse(null);
    }

    public User[] getUsers() {
        return _users.toArray(new User[0]);
    }

    public int addCity(String name) {
        var city = new City(name);
        _cities.add(city);
        save();
        return city.getId();
    }

    public City getCity(int id) {
        var city = _cities.stream().filter(c -> c.getId() == id).findAny();
        return city.orElse(null);
    }

    public City[] getCities() {
        return _cities.toArray(new City[0]);
    }

    public int addFine(User user, FineType type, double income) {
        var fine = new Fine(user, type, income);
        _fines.add(fine);
        save();
        return fine.getId();
    }

    public Fine getFine(int id) {
        var fine = _fines.stream().filter(f -> f.getId() == id).findAny();
        return fine.orElse(null);
    }

    public Fine[] getFines() {
        return _fines.toArray(new Fine[0]);
    }
}
