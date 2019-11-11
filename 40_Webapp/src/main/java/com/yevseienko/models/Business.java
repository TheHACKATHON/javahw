package com.yevseienko.models;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class Business {
  public static ArrayList<Homework> getHomeworkList(String startPath, String configPath, String findFile) {
    ArrayList<Homework> result = new ArrayList<>();
    Path root = Paths.get(startPath);
    File[] files = root.toFile().listFiles();
    if (files != null) {
      for (File file : files) {
        if (file.isDirectory()) {
          File[] homeworkDirectoryFiles = file.listFiles();
          if (homeworkDirectoryFiles != null) {
            Optional<File> indexOpt = Arrays.stream(homeworkDirectoryFiles)
                    .filter(f -> f.getName().equals(findFile))
                    .findFirst();
            if (indexOpt.isPresent()) {
              Optional<File> configOpt = Arrays.stream(homeworkDirectoryFiles)
                      .filter(f -> f.getName().equals(configPath))
                      .findFirst();

              if (configOpt.isPresent()) {
                File configFile = configOpt.get();
                try (FileReader reader = new FileReader(configFile)) {
                  JSONArray arr = (JSONArray) new JSONParser().parse(reader);
                  JSONObject jObj = (JSONObject) arr.get(0);
                  JSONObject root1 = (JSONObject) jObj.get("root");
                  String homeworkName = (String) root1.get("homework");
                  String taskName = (String) root1.get("task");
                  String idStr = (String) root1.get("id");
                  int id = Integer.parseInt(idStr);
                  Optional<Homework> hwOpt = result.stream().filter(h -> h.getId() == (id)).findFirst();
                  Homework hw;
                  if(hwOpt.isPresent()){
                    hw = hwOpt.get();
                  } else {
                    hw = new Homework(id, homeworkName);
                    result.add(hw);
                  }
                  hw.addTask(taskName, "/" + configFile.getParentFile().getName());
                } catch (Exception e) {
                  e.printStackTrace();
                }
              }
            }
          }
        }
      }
    }
    return result;
  }
}
