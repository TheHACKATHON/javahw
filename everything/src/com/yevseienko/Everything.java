package com.yevseienko;

import javax.swing.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Everything {
    public static void main(String[] args){
        var sc = new Scanner(System.in);
        int input = 0;
        String message = null;

        do {
            if (input == 5) {
                break;
            }
            try {
                printMenu(message); // 1 - index, 2 - search, 3 - search file, 4 - search directory, 5 - exit
            } catch (IOException e) {
                e.printStackTrace();
            }
            input = stringToInt(sc.next(), 0);

            switch (input) {
                case 1: {
                    System.out.print("Вернуться назад: -1\nВведите кол-во потоков(default 2): ");
                    input = stringToInt(sc.next(), 2);
                    if (input == -1) {
                        break;
                    }
                    if(input <= 0){
                        input = 2;
                    }
                    try {
                        index(input);
                    } catch (IOException e) {
                        e.printStackTrace();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                break;
                case 2:
                case 3:
                case 4: {
                    System.out.print(
                            "1. Искать на всех дисках\n" +
                                    "2. Искать на одном диске\n" +
                                    "3. Искать в папке\n" +
                                    "4. Назад\n> ");
                    SearchOption searchOption = SearchOption.All;
                    if (input == 3) {
                        searchOption = SearchOption.Files;
                    } else if (input == 4) {
                        searchOption = SearchOption.Directories;
                    }
                    var input2 = stringToInt(sc.next(), 4);
                    SearchIn searchIn = null;
                    String searchInOption = null;
                    switch (input2) {
                        case 1: {
                            searchIn = SearchIn.Everywhere;
                        }
                        break;
                        case 2: {
                            searchIn = SearchIn.Disk;
                        }
                        break;
                        case 3: {
                            searchIn = SearchIn.Directory;
                        }
                        break;
                        case 4: {
                            searchIn = null;
                        }
                        break;
                    }
                    if (searchIn == null) {
                        break;
                    } else if (searchIn == SearchIn.Disk) {
                        System.out.println("Выберите букву диска:");
                        var disks = File.listRoots();
                        if (disks != null) {
                            for (int i = 0; i < disks.length; i++) {
                                System.out.println(String.format("%d. %s", i, disks[i]));
                            }
                            var diskIdx = stringToInt(sc.next(), -1);
                            if (diskIdx >= disks.length || diskIdx < 0) {
                                System.out.println("Вы выбрали неверную букву диска, буду искать везде");
                                searchIn = SearchIn.Everywhere;
                            } else {
                                searchInOption = disks[diskIdx].toString();
                            }
                        } else {
                            System.out.println("Мы не нашли ни одного диска на вашем компьютере");
                        }
                    } else if (searchIn == SearchIn.Directory) {
                        System.out.println("(должно открыться диалоговое окно)");
                        JFileChooser chooser = new JFileChooser();
                        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
                        int returnVal = chooser.showOpenDialog(null);
                        if (returnVal == JFileChooser.APPROVE_OPTION) {
                            searchInOption = chooser.getSelectedFile().getPath();
                        } else {
                            System.out.println("Вы не выбрали папку, буду искать везде");
                            searchIn = SearchIn.Everywhere;
                        }
                    }

                    System.out.print("Введите данные для поиска(поддерживается * и ?): ");
                    var sc2 = new Scanner(System.in);
                    var regex = sc2.nextLine();
                    searchAndPrintFiles(searchOption, regex, searchIn, searchInOption);
                    message = "результат выше /\\";
                }
                break;
                case 5: {
                    break;
                }
            }

        } while (true);
    }


    private static void searchAndPrintFiles(SearchOption options, String regex,
                                            SearchIn searchIn, String searchInOption) {
        try (var reader = new BufferedReader(new FileReader(JThread.IndexPath))) {
            /*
             * # формат записи: fd<path<hidden<size?<date?
             * # < - сепаратор
             * # fd - может быть 0 если это папка или 1 если файл
             * # path - путь к файлу или папке
             */
            System.out.println("Идёт поиск...");
            String line;
            int iterator = 0;
            while ((line = reader.readLine()) != null) {
                // todo: считывать один символ для проверки на папку/файл, а дальше если нужно то всю строку
                if (line.startsWith("#")) {
                    continue;
                }
                if (options == SearchOption.Directories) {
                    if (line.startsWith("1")) {
                        continue;
                    }
                } else if (options == SearchOption.Files) {
                    if (line.startsWith("0")) {
                        continue;
                    }
                }
                if (searchIn == SearchIn.Disk || searchIn == SearchIn.Directory) {
                    if (!line.contains("<" + searchInOption)) {
                        continue;
                    }
                }
                regex = regex.replace("*", ".*");
                regex = regex.replace("?", ".");

                var indexedFile = IndexedFile.of(line);
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(indexedFile.getFileName());

                if (matcher.find()) {
                    var type = indexedFile.isFile() ? "Файл" : "Папка";
                    System.out.print(String.format("%s[%s]%s", indexedFile.isHidden() ? "-" : "+", type, indexedFile.getPath()));
                    if (indexedFile.isFile()) {
                        System.out.print(String.format("; Размер: %s байт; Дата создания: %s", indexedFile.getSize(), indexedFile.getDateString()));
                    }
                    System.out.println();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int stringToInt(String text, int defaultReturn) {
        int result;
        try {
            result = Integer.parseInt(text);
        } catch (NumberFormatException ex) {
            result = defaultReturn;
        }
        return result;
    }

    private static void printMenu(String message) throws IOException {
        cls();
        if (message != null) {
            System.out.println(message + "\n\n");
        }
        message = null;
        var indexPath = Paths.get(JThread.IndexPath);
        boolean indexed = Files.exists(indexPath);
        LocalDateTime fileTime = null;
        if (indexed) {
            var attrs = Files.readAttributes(indexPath, BasicFileAttributes.class);
            fileTime = attrs.creationTime().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        }
        System.out.print(
                "Меню:\n" +
                        String.format("\t1. %s\n", indexed ? "Переиндексировать(последний индекс создан " + fileTime.format(DateTimeFormatter
                                .ofPattern("d MMMM yyyy HH:mm:ss")) + ")" : "Индексировать") +
                        "\t2. Искать\n" +
                        "\t3. Искать файл\n" +
                        "\t4. Искать папку\n" +
                        "\t5. Выход\n" +
                        "> "
        );
    }

    private static void cls() {
        System.out.print("\n".repeat(10));
    }

    private static void index(int threads) throws IOException, InterruptedException {
        System.out.println("Началась индексация...");

        var pool = new JThreadPool(threads);
        var disks = File.listRoots();
        if (disks != null) {
            for (var disk : disks) {
                pool.addDirectory(disk, false);
                var list = disk.listFiles();
                if (list != null) {
                    for (var dir : list) {
                        if (dir.isDirectory()) {
                            pool.addDirectory(dir, true);
                        }
                    }
                }
            }
        }
        pool.start();
        pool.join();
    }
}

