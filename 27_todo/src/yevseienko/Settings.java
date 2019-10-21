package yevseienko;

import java.io.*;

public class Settings {
    private static int _maxCategoryId;
    private static int _maxUserId;
    private static int _maxTaskId;
    private static String _saveUsersPath;
    private static String _settingsPath;

    static {
        _settingsPath = "./settings.bin";

        if (new File(_settingsPath).isFile()) {
            reload();
        } else {
            _saveUsersPath = "./users.bin";
            // default path
            save();
        }
    }

    public static int getMaxCategoryId() {
        return _maxCategoryId;
    }

    public static int getMaxUserId() {
        return _maxUserId;
    }

    public static int getMaxTaskId() {
        return _maxTaskId;
    }

    public static String getSaveUsersPath() {
        return _saveUsersPath;
    }

    public static void newCategory() {
        _maxCategoryId++;
        save();
    }

    public static void newUser() {
        _maxUserId++;
        save();
    }

    public static void newTask() {
        _maxTaskId++;
        save();
    }

    public static void save() {
        try (DataOutputStream dos = new DataOutputStream(new FileOutputStream(_settingsPath))) {
            dos.writeInt(_maxCategoryId);
            dos.writeInt(_maxUserId);
            dos.writeInt(_maxTaskId);
            dos.writeUTF(_saveUsersPath);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void reload() {
        try (DataInputStream dis = new DataInputStream(new FileInputStream(_settingsPath))) {
            _maxCategoryId = dis.readInt();
            _maxUserId = dis.readInt();
            _maxTaskId = dis.readInt();
            _saveUsersPath = dis.readUTF();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
