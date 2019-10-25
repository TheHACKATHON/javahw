/* jshint esversion:6 */
const todoList = document.querySelectorAll("ul.todo>li");
const newTaskInput = document.querySelector("input#taskName");
const newTaskSubmit = document.querySelector("button#taskSubmit");
const ulTodo = document.querySelector("ul.todo");

// клик по li переключает класс checked
// li: HTMLElement
const addLiEventClick = function(li) {
    li.addEventListener("click", function () {
        this.classList.toggle("checked");
    });
};

// добавление к li кнопки удаления
// li: HTMLElement
const addLiRemoveBtn = function(li) {
    const btn = document.createElement("a");
    btn.classList.add("remove-btn");
    addRemoveBtnEventClick(btn);
    li.appendChild(btn);
};

// добавления нового задания в лист
// task : string - название
const addTask = function(task){
    const li = document.createElement("li");
    li.textContent = task;
    addLiEventClick(li);
    addLiRemoveBtn(li);
    if(ulTodo.firstChild != null){
        ulTodo.insertBefore(li, ulTodo.firstChild);
    } else{
        ulTodo.appendChild(li);
    }
};

// добавление события клик на кнопку удаления
// btn: HTMLElement
const addRemoveBtnEventClick = function(btn){
    btn.addEventListener("click", function(){
        this.parentNode.remove();        
    });
};

// добавление событий на существующие todo>li при загрузке страницы
todoList.forEach(li => {
    addLiEventClick(li);
    addLiRemoveBtn(li);
});

newTaskSubmit.addEventListener("click", () => {
    const task = newTaskInput.value.trim();
    newTaskInput.value = "";
    if(task.length == 0){
        alert("enter a task name");
        return;
    }
    addTask(task);
});