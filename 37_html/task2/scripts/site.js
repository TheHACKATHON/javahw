/* jshint esversion:6 */
const todoList = document.querySelectorAll("ul.todo>li");
const newTaskInput = document.querySelector("input#taskName");
const newTaskSubmit = document.querySelector("button#taskSubmit");
const ulTodo = document.querySelector("ul.todo");

const liFunctions = {
    // клик по li переключает класс checked
    // li: HTMLElement
    addEventClick: function (li) {
        li.addEventListener("click", function () {
            this.classList.toggle("checked");
        });
    },
    // добавление к li кнопки удаления
    // li: HTMLElement
    appendRemoveBtn: function (li) {
        const btn = document.createElement("a");
        btn.classList.add("remove-btn");
        btn.addEventListener("click", function () {
            this.parentNode.remove();
        });
        li.appendChild(btn);
    },
    // добавления нового задания в лист
    // task: string - название
    createAndAppendTask: function (task) {
        const li = document.createElement("li");
        li.textContent = task;
        liFunctions.addEventClick(li);
        liFunctions.appendRemoveBtn(li);
        if (ulTodo.firstChild != null) {
            ulTodo.insertBefore(li, ulTodo.firstChild);
        } else {
            ulTodo.appendChild(li);
        }
    },
};

// добавление событий на существующие todo>li при загрузке страницы
todoList.forEach(li => {
    liFunctions.addEventClick(li);
    liFunctions.appendRemoveBtn(li);
});

newTaskSubmit.addEventListener("click", () => {
    const task = newTaskInput.value.trim();
    newTaskInput.value = "";
    if (task.length == 0) {
        alert("enter a task name");
        return;
    }
    liFunctions.createAndAppendTask(task);
});