package com.yevseienko;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.Comparator;
import java.util.Scanner;

public class Copy {
    public static void main(String[] args) throws IOException, InterruptedException {
        var sc = new Scanner(System.in);
        String error = null;
        String message = null;
        var settings = Settings.get();
        int input;
        do {
            cls();
            if (error != null) {
                System.out.println(String.format("%sError: %s%s", ConsoleColors.RED, error, ConsoleColors.RESET));
            }
            error = null;
            if (message != null) {
                System.out.println(String.format("%s%s%s", ConsoleColors.BLUE, message, ConsoleColors.RESET));
            }
            message = null;
            System.out.print(
                    "Меню:\n" +
                            String.format("\t1. Указать папку источник(%s)\n", settings.getSource()) +
                            String.format("\t2. Указать папку назначения(%s)\n", settings.getDestination()) +
                            "\t3. Копировать\n" +
                            "\t4. Копировать v2\n" +
                            "\t5. Выход\n" +
                            String.format("%s>%s ", ConsoleColors.PURPLE, ConsoleColors.RESET)
            );
            var switchErrorMessage = "Неверный пункт меню, попробуйте снова";
            var tmp = sc.next();
            // если считывать сразу инт, то сканер может сломаться
            try {
                input = Integer.parseInt(tmp);
            } catch (NumberFormatException ex) {
                input = 0;
            }
            if (input != 0) {
                switch (input) {
                    case 1: {
                        if (settings.selectSource() == null) {
                            error = "Не удалось выбрать папку источника!";
                        } else {
                            message = "Выбрана папка источника";
                        }
                    }
                    break;
                    case 2: {
                        if (settings.selectDestination() == null) {
                            error = "Не удалось выбрать папку назначения!";
                        } else {
                            message = "Выбрана папка назначения";
                        }
                    }
                    break;
                    case 3:
                    case 4: {
                        var srcString = settings.getSource();
                        var dstString = settings.getDestination();

                        if (srcString == null || srcString.isBlank() || dstString == null || dstString.isBlank()) {
                            error = "Выберите источник и место назначения";
                        } else {
                            var srcPath = Paths.get(srcString);
                            var dstPath = Paths.get(dstString);

                            var dstFile = dstPath.toFile();
                            var srcFile = srcPath.toFile();

                            if (!srcFile.exists()) {
                                error = "Папки источника не существует, укажите её ещё раз";
                                settings.nullSource();
                            } else {
                                System.out.print("Вы уверены что хотите скопировать файлы?(y/n)\n" +
                                        ConsoleColors.RED + "Всё содержимое папки " + ConsoleColors.BLUE + dstString + ConsoleColors.RED + " будет очищено перед копированием\n" +
                                        ConsoleColors.PURPLE + "> " + ConsoleColors.RESET);

                                tmp = sc.next();
                                if(tmp.equalsIgnoreCase("y")){
                                    if (dstFile.exists()) {
                                        Files.walk(dstPath)
                                                .sorted(Comparator.reverseOrder())
                                                .map(Path::toFile)
                                                .forEach(File::delete);
                                        Thread.sleep(10);
                                    } else {
                                        if (!dstFile.mkdirs()) {
                                            error = "Не удалось создать папку";
                                        }
                                    }
                                    if (error == null) {
                                        System.out.println("Выполняется копирование данных...");
                                        if (input == 3) {
                                            copyTree(srcFile, dstFile, true);
                                        }
                                        // v1
                                        else {
                                            Files.walk(srcPath).forEach(source -> copy(source, dstPath.resolve(srcPath.relativize(source))));
                                        }
                                        // v2
                                        message = "Данные успешно скопированы";
                                    }
                                }
                            }
                        }
                    }
                    break;
                    case 5: {
                        System.out.println(String.format("%sПриходите ещё%s", ConsoleColors.BLUE, ConsoleColors.RESET));
                        return;
                    }
                    default: {
                        error = switchErrorMessage;
                    }
                }
            } else {
                error = switchErrorMessage;
            }
        } while (true);
    }

    private static void cls() {
        System.out.print("\n".repeat(20));
    }

    private static void copy(Path source, Path dest) {
        try {
            Files.copy(source, dest, StandardCopyOption.REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    private static void copyTree(File source, File destination, boolean... firstIteration) throws IOException {
        var isFirst = firstIteration.length != 0;
        if (isFirst) {
            destination.mkdirs();
        }
        if (source.exists()) {
            var dest = destination.getPath() + File.separator + source.getName();
            if (!isFirst) {
                Files.copy(source.toPath(), Paths.get(dest), StandardCopyOption.REPLACE_EXISTING);
            }
            if (source.isDirectory() && source.canRead()) {
                var files = source.listFiles();
                if (files != null) {
                    for (var newFile : files) {
                        copyTree(newFile, Paths.get(destination.getPath(), !isFirst ? source.getName() : "").toFile());
                    }
                }
            }
        }
    }
}


