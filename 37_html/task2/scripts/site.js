/* jshint esversion:6 */
const todoList = document.querySelectorAll("ul.todo>li");
const newTaskInput = document.querySelector("input#taskName");
const newTaskSubmit = document.querySelector("button#taskSubmit");
const ulTodo = document.querySelector("ul.todo");

const addLiEventClick = function(li) {
    li.addEventListener("click", function () {
        this.classList.toggle("checked");
    });
};

const addTask = function(task){
    const li = document.createElement("li");
    li.textContent = task;
    addLiEventClick(li);
    if(ulTodo.firstChild != null){
        ulTodo.insertBefore(li, ulTodo.firstChild);
    } else{
        ulTodo.appendChild(li);
    }
};

todoList.forEach(li => {
    addLiEventClick(li);
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