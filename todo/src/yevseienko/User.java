package yevseienko;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class User implements Serializable {
    private static int Id;

    private int _id;
    private String _login;
    private String _passwordMD5;
    private String _salt;
    private int _saltPosition;
    private SortType _sortType;
    private SortType _prevType;
    private ArrayList<Category> _userCategories;
    private ArrayList<Task> _tasks;
    private ArrayList<Task> _archive;

    static {
        Id = Settings.getMaxUserId();
    }

    public User(String login, String password) {
        this._id = ++Id;
        Settings.newUser();
        // todo: regex login, password

        this._userCategories = new ArrayList<>();
        this._tasks = new ArrayList<>();
        this._archive = new ArrayList<>();
        this._sortType = SortType.All;
        this._login = login;
        calculateMD5(password);
    }

    public SortType getSortType() {
        return _sortType;
    }

    public SortType changeSortType(boolean isArchive) {
        if (isArchive) {
            _prevType = _sortType;
            _sortType = SortType.Archive;
        } else {
            if (_sortType.equals(SortType.All)) {
                _sortType = SortType.Outstanding;
            } else if (_sortType.equals(SortType.Outstanding)) {
                _sortType = SortType.Done;
            } else if (_sortType.equals(SortType.Done)) {
                _sortType = SortType.All;
            }
            else if(_sortType.equals(SortType.Archive)){
                _sortType = _prevType;
                _prevType = null;
            }
        }
        return _sortType;
    }

    public String getLogin() {
        return _login;
    }

    public String getPasswordMD5() {
        return _passwordMD5;
    }

    private void calculateMD5(String password) {
        this._salt = Generator.randomHexValues(16);
        this._saltPosition = Generator.randomInt(password.length());
        password = TextTransform.insertString(password, this._salt, this._saltPosition);
        this._passwordMD5 = Generator.md5(password);
    }

    public boolean verifyMD5(String password) {
        if (password.length() < this._saltPosition) {
            return false;
        }
        password = TextTransform.insertString(password, this._salt, this._saltPosition);
        return Generator.md5(password).equals(this._passwordMD5);
    }

    public Category addUserCategory(String name) {
        var category = new Category(name);
        if (_userCategories.stream().anyMatch(c -> c.getName().equals(category.getName()))) {
            throw new IllegalArgumentException("Такая категория уже существует");
        }
        _userCategories.add(category);
        return category;
    }

    public boolean removeUserCategory(int categoryId) {
        var tmp = _userCategories.stream().filter(u -> u.getId() == categoryId).findFirst();
        if (tmp.isPresent()) {
            _userCategories.remove(tmp.get());
            return true;
        }
        return false;
    }

    public Category[] getUserCategories() {
        return _userCategories.toArray(new Category[0]);
    }

    public Task newTask(String name, byte priority, LocalDateTime deadline, Category category) {
        Task task = null;
        if (deadline == null && category == null) {
            task = new Task(name, priority);
        } else if (deadline == null) {
            task = new Task(name, priority, category);
        } else if (category == null) {
            task = new Task(name, priority, deadline);
        } else {
            task = new Task(name, priority, deadline, category);
        }
        _tasks.add(task);
        return task;
    }

    public boolean removeTask(int taskId) {
        var tmp = _tasks.stream().filter(u -> u.getId() == taskId).findFirst();
        if (tmp.isPresent()) {
            _tasks.remove(tmp.get());
            return true;
        }
        return false;
    }

    public boolean archiveTask(int taskId) {
        var tmp = _tasks.stream().filter(u -> u.getId() == taskId).findFirst();
        if (tmp.isPresent()) {
            _tasks.remove(tmp.get());
            _archive.add(tmp.get());
            return true;
        }
        return false;
    }

    public boolean returnFromArchiveTask(int taskId) {
        var tmp = _archive.stream().filter(u -> u.getId() == taskId).findFirst();
        if (tmp.isPresent()) {
            _archive.remove(tmp.get());
            _tasks.add(tmp.get());
            return true;
        }
        return false;
    }

    public Task[] getTasks() {
        // todo: сортировка
        switch (_sortType) {
            case All: {
                return _tasks.toArray(new Task[0]);
            }
            case Done:
            case Outstanding: {
                var tmp = _tasks.stream().filter(t -> (_sortType == SortType.Done) == t.isDone());
                return tmp.toArray(t -> new Task[0]);
            }
            case Archive: {
                return _archive.toArray(new Task[0]);
            }
        }
        return null;
    }

    public Task[] getTasks(int page) {
        // todo: сортировка

        var take = 10;
        var skip = page * take;
        if(_tasks.size() < skip + take)
        {
            take = _tasks.size() - skip;
        }
        switch (_sortType) {
            case All: {
                return _tasks.subList(skip, take).toArray(new Task[0]);
            }
            case Done:
            case Outstanding: {
                var tmp = _tasks.stream().filter(t -> (_sortType == SortType.Done) == t.isDone()).limit(take);
                return tmp.toArray(t -> new Task[0]);
            }
            case Archive: {
                return _archive.subList(skip, take).toArray(new Task[0]);
            }
        }
        return null;
    }
}

