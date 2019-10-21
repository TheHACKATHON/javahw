package com.yevseienko;

import java.io.*;

public class Settings {
    private static int _maxAddressId;
    private static int _maxCityId;
    private static int _maxFineId;
    private static int _maxFineTypeId;
    private static int _maxTINId;
    private static int _maxUserId;
    private static String _saveRepositoryPath;
    private static String _settingsPath;

    static {
        _settingsPath = "./.settings";

        if (new File(_settingsPath).isFile()) {
            reload();
        } else {
            _saveRepositoryPath = "./repository.db";
            // default path
            save();
        }
    }

    public static int getMaxAddressId() {
        return _maxAddressId;
    }

    public static int getMaxCityId() {
        return _maxCityId;
    }

    public static int getMaxFineId() {
        return _maxFineId;
    }

    public static int getMaxFineTypeId() {
        return _maxFineTypeId;
    }

    public static int getMaxTINId() {
        return _maxTINId;
    }

    public static int getMaxUserId() {
        return _maxUserId;
    }

    public static String getSaveRepositoryPath() {
        return _saveRepositoryPath;
    }

    public static void newAddress() {
        _maxUserId++;
        save();
    }

    public static void newCity() {
        _maxUserId++;
        save();
    }

    public static void newFine() {
        _maxUserId++;
        save();
    }

    public static void newFineType() {
        _maxUserId++;
        save();
    }

    public static void newTIN() {
        _maxUserId++;
        save();
    }

    public static void newUser() {
        _maxUserId++;
        save();
    }

    public static void setRepositoryPath(String path) {
        _saveRepositoryPath = path;
        save();
    }

    private static void save() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(_settingsPath))) {
            dos.writeInt(_maxAddressId);
            dos.writeInt(_maxCityId);
            dos.writeInt(_maxFineId);
            dos.writeInt(_maxFineTypeId);
            dos.writeInt(_maxTINId);
            dos.writeInt(_maxUserId);
            dos.writeUTF(_saveRepositoryPath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private static void reload() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(_settingsPath))) {
            _maxAddressId = dis.readInt();
            _maxCityId = dis.readInt();
            _maxFineId = dis.readInt();
            _maxFineTypeId = dis.readInt();
            _maxTINId = dis.readInt();
            _maxUserId = dis.readInt();
            _saveRepositoryPath = dis.readUTF();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
