package com.yevseienko;

import javax.swing.*;
import java.io.*;
import java.nio.file.Paths;

public class Settings implements Serializable {
    private Settings() {
    }

    static {
        load();
    }

    private static Settings instance;

    public static Settings get() {
        return instance != null ? instance : (instance = new Settings());
    }

    private String _source;
    private String _destination;
    private static final String Path = ".settings";

    public String getSource() {
        return _source;
    }

    public String getDestination() {
        return _destination;
    }

    public String selectSource() {
        var newPath = selectDirectory();
        if (newPath != null) {
            var dir = new File(newPath);
            if (dir.exists() && dir.isDirectory()) {
                _source = newPath;
                save();
            }
        }
        return _source != null &&_source.equals(newPath) ? _source : null;
    }

    public String selectDestination() {
        var newPath = selectDirectory();
        if (newPath != null) {
            _destination = newPath;
            save();
        }

        return _destination != null && _destination.equals(newPath) ? _destination : null;
    }

    public void nullSource(){
        _source = null;
    }

    public void nullDestination(){
        _destination = null;
    }

    private static void save() {
        if (instance != null) {
            try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(Path))) {
                oos.writeObject(instance);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }

    private static void load() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(Path))) {
            instance = (Settings) ois.readObject();
        } catch (Exception ex) {
            // файл настроек ещё не создан
            instance = new Settings();
        }
    }

    private String selectDirectory() {
        JFileChooser chooser = new JFileChooser();
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
            return chooser.getSelectedFile().getPath();
        }
        return null;
    }
}
