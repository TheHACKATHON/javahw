package yevseienko;

import java.io.Serializable;
import java.time.LocalDateTime;

public class Task implements Serializable {
    private static int Id;
    private int _id;
    private String _name;
    private byte _priority;
    private LocalDateTime _deadline;
    private LocalDateTime _creationDate;
    private Category _category;
    private boolean _isDone;

    static {
        Id = Settings.getMaxTaskId();
    }

    public Task(String name, byte priority) {
        _id = ++Id;
        Settings.newTask();

        name = name.trim();
        if (name.isEmpty()) {
            throw new IllegalArgumentException("Недопустимое название задачи");
        }
        if (priority < 0) {
            priority = 0;
        } else if (priority > 5) {
            priority = 5;
        }

        this._name = name;
        this._priority = priority;
        this._creationDate = LocalDateTime.now();
        this._deadline = null;
        this._category = null;
    }

    public Task(String name, byte priority, LocalDateTime deadline) {
        this(name, priority);
        if (deadline == null || deadline.isBefore(LocalDateTime.now())) {
            throw new IllegalArgumentException("Указан неверный срок выполенения");
        }
        this._deadline = deadline;
    }

    public Task(String name, byte priority, Category category) {
        this(name, priority);
        if (category == null) {
            throw new IllegalArgumentException("Указана неверная категория");
        }
        this._category = category;
    }

    public Task(String name, byte priority, LocalDateTime deadline, Category category) {
        this(name, priority, deadline);
        if (category == null) {
            throw new IllegalArgumentException("Указана неверная категория");
        }
        this._category = category;
    }

    public int getId() {
        return _id;
    }

    public String getName() {
        return _name;
    }

    public byte getPriority() {
        return _priority;
    }

    public LocalDateTime getDeadline() {
        return _deadline;
    }

    public LocalDateTime getCreationDate() {
        return _creationDate;
    }

    public Category getCategory() {
        return _category;
    }

    public boolean isDone() {
        return _isDone;
    }

    public boolean ToggleDone() {
        _isDone = !_isDone;
        return isDone();
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", getName(), getPriority());
    }


}

