package yevseienko;

import com.jakewharton.fliptables.FlipTableConverters;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Main {
    private static ArrayList<User> _users;
    private static Serializer<User> userSerializer;
    private static User _loginedUser;

    static {
        userSerializer = new Serializer<>();
        _users = userSerializer.deserialize(Settings.getSaveUsersPath());
        if (_users == null) {
            _users = new ArrayList<User>();
            saveUsers();
        }
    }

    public static void main(String[] args) {
        start();
    }

    private static void saveUsers() {
        userSerializer.serialize(Settings.getSaveUsersPath(), _users);
    }

    private static void start() {
        String input = null;
        Scanner in = new Scanner(System.in);
        while (true) {
            cls();
            do {
                System.out.print(String.format("Уже есть аккаунт? (y/n)\n%s>%s", ConsoleColors.PURPLE, ConsoleColors.RESET));
                input = in.next();
            } while (!Pattern.matches("^[ny]|quit$", input));
            if (input.equals("y")) {
                login();
            } else if (input.equals("n")) {
                register();
            } else if (input.equals("quit")) {
                return;
            }
        }
    }

    private static void login(String ...message) {
        if(message.length == 0){
            cls();
            System.out.println("Вход в аккаунт");
        }
        else{
            cls();
            System.out.println(message[0]);
        }

        var login = input(null,
                "Введите логин: ",
                "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d]{5,24}$",
                text -> {
                    System.out.println(String.format("%sАккаунт с таким логином не найден, проверьте правильность введенных данных%s", ConsoleColors.YELLOW, ConsoleColors.RESET));
                });

        if(login == null){
            return;
        }

        cls();

        var password = input(() -> {
                    System.out.println(String.format("Логин: %s%s%s", ConsoleColors.BLUE, login, ConsoleColors.RESET));
                },
                "Введите пароль: ",
                "^(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{8,32}$",
                text -> {
                    System.out.println(String.format("%sАккаунт с таким логином и паролем не найден, проверьте правильность введенных данных%s", ConsoleColors.YELLOW, ConsoleColors.RESET));
                });

        if (password == null) {
            return;
        }

        var user = _users.stream().filter(u -> u.getLogin().equalsIgnoreCase(login)).findFirst();
        if(user.isPresent()){
            if(user.get().verifyMD5(password)){
                _loginedUser = user.get();
                var cat = _loginedUser.addUserCategory("home");
                _loginedUser.newTask("test task 1", (byte)5, LocalDateTime.of(2019, 9, 01, 0, 0), cat);
                _loginedUser.newTask("test task 2", (byte)5, null, cat);
                _loginedUser.newTask("test task 3", (byte)1, LocalDateTime.of(2019, 9, 01, 0, 0), null);
                _loginedUser.newTask("test task 4", (byte)10, null, null);
                mainMenu();
            }
        }
        else{
            login("Аккаунт с таким логином и паролем не найден, проверьте правильность введенных данных");
        }

    }

    private static void mainMenu() {
        String input = null;
        Scanner in = new Scanner(System.in);

        cls();
        System.out.println( String.format("Аккаунт: %s%s%s Список задач", ConsoleColors.BLUE, _loginedUser.getLogin(), ConsoleColors.RESET));
        var page = 0;
        printTasks(page);
        System.out.println("1. Добавить задачу\n" +
                String.format("2. Фильтр(%s)\n", _loginedUser.getSortType()) +
                "3. Архив");
        System.out.print( String.format("%s>%s", ConsoleColors.PURPLE, ConsoleColors.RESET));
        input = in.next();

        switch (input){
            case "1":{
                newTask();
            }
        }
    }

    private static void newTask() {
        cls();
        System.out.println( String.format("Аккаунт: %s%s%s Создание новой задачи:", ConsoleColors.BLUE, _loginedUser.getLogin(), ConsoleColors.RESET));
        var taskName = input(null,"Введите описание задачи: " , ".|", null);
        var taskPrioriry = input(null,"Введите описание задачи: " , ".|", null);

    }


    private static void printTasks(int page){
        String[] headers = {"#", "Описание", "Приоритет", "Срок выполнения", "Категория"};

        var tasks = _loginedUser.getTasks(page);
        Object[][] data = new Object[tasks.length][5];

        for(int i = 0; i < tasks.length; i++){
            data[i][0] = String.format("%d%d", page, i);
            data[i][1] = tasks[i].getName();
            data[i][2] = tasks[i].getPriority();
            data[i][3] = tasks[i].getDeadline();
            data[i][4] = tasks[i].getCategory();
        }
        System.out.println(FlipTableConverters.fromObjects(headers, data));
    }

    private static void register(boolean... tmp) {
        if (tmp.length == 0) {
            cls();
            System.out.println("Регистрация нового аккаунта");
        }

        var login = input(() -> {
                    System.out.println(String.format("Требования для логина:\n" +
                            "\t%s-только латинские символы и цифры\n" +
                            "\t-минимум 6 символов\n" +
                            "\t-максимум 24 символа%s", ConsoleColors.YELLOW, ConsoleColors.RESET));
                },
                "Введите логин: ",
                "^(?=.*[A-Za-z0-9]$)[A-Za-z][A-Za-z\\d]{5,24}$",
                text -> {
                    System.out.println(String.format("\n%sОшибка, логин %s%s%s не подходит, попробуйте снова%s",
                            ConsoleColors.RED, ConsoleColors.BLUE, text, ConsoleColors.RED, ConsoleColors.RESET));
                });

        if (login == null) {
            return;
        }

        if (_users.stream().anyMatch(u -> u.getLogin().equalsIgnoreCase(login))) {
            cls();
            System.out.println(String.format("%sОшибка, такой логин уже зарегистрирован!%s", ConsoleColors.RED, ConsoleColors.RESET));
            register(true);
            return;
        }

        cls();
        var password = input(() -> {
                    System.out.println(String.format("Логин: %s%s%s\n" +
                            "Требования для пароля:\n" +
                            "\t%s-минимум 8 символов\n" +
                            "\t-максимум 32 символа\n" +
                            "\t-буква в нижнем регистре\n" +
                            "\t-буква в верхнем регистре%s", ConsoleColors.BLUE, login, ConsoleColors.RESET, ConsoleColors.YELLOW, ConsoleColors.RESET));
                },
                "Введите пароль: ",
                "^(?=.*[a-zа-я])(?=.*[A-ZА-Я]).{8,32}$",
                text -> {
                    System.out.println(String.format("\n%sОшибка, пароль не подходит, попробуйте снова%s", ConsoleColors.RED, ConsoleColors.RESET));
                });

        if (password == null) {
            return;
        }

        var user = new User(login, password);
        _users.add(user);
        saveUsers();
    }

    private static String input(VoidAction required, String message, String pattern, Action error) {
        Scanner in = new Scanner(System.in);
        String input = null;
        String backMessage = "quit";

        do {
            if (input != null) {
                if (input.equals(backMessage)) {
                    return null;
                }
                cls();
                if(error != null) {
                    error.execute(input);
                }
            }
            System.out.println(String.format("Вернуться назад: %s%s%s", ConsoleColors.BLUE, backMessage, ConsoleColors.RESET));
            if (required != null) {
                required.voidExecute();
            }
            System.out.print(message);
            input = in.next();
        } while (!Pattern.matches(pattern, input));
        return input;
    }

    private static void cls() {
        System.out.print("\n".repeat(20));
    }

}

/*      String[] headers = {"First Name", "Last Name", "Age", "Type"};
        Object[][] data = {
                {"Big", "Bird", 16, "COSTUME"},
                {"Joe", "Smith", 42, "HUMAN"},
                {"Oscar", "Grouchajsdfgjksdjkfgdfgnt", 8, "PUPPET"}
        };
        System.out.println(FlipTableConverters.fromObjects(headers, data));
*/