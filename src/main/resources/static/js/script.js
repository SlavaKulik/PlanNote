const todos = document.querySelectorAll(".todo");
const all_status = document.querySelectorAll(".status");
let draggableTodo = null;

todos.forEach((todo) => {
    todo.addEventListener('dragstart', dragStart);
    todo.addEventListener('dragend', dragEnd);
});
function dragStart() {
    draggableTodo = this;
    setTimeout(() => {
        this.style.display = "none";
    }, 0);
    console.log("dragStart");
}
function dragEnd() {
    draggableTodo = null;
    setTimeout(() => {
        this.style.display = "block";
    }, 0);
    console.log("dragEnd");
}

all_status.forEach((status) => {
    status.addEventListener('dragover', dragOver);
    status.addEventListener('dragenter', dragEnter);
    status.addEventListener('dragleave', dragLeave);
    status.addEventListener('drop', dragDrop);
})

function dragOver(e) {
    e.preventDefault();
}

function dragEnter() {
    this.style.border = "2px dashed #ccc";
    console.log("dragEnter");
}

function dragLeave() {
    this.style.border = "none";
    console.log("dragLeave");
}

function dragDrop() {
    this.style.border = "none";
    this.appendChild(draggableTodo);
    console.log("dropped");
}

const todo_submit = document.getElementById("todo_submit");

if(todo_submit)
{
    todo_submit.addEventListener('click', createTodo);
}

function createTodo() {

    const todo_div = document.createElement("div");
    const input_val = document.getElementById("todo_input").value;
    const txt = document.createTextNode(input_val);
    const no_status = document.getElementById("to_do");

    todo_div.appendChild(txt);
    todo_div.classList.add("todo");
    todo_div.setAttribute("draggable", "true");

    const span = document.createElement("span");
    const span_txt = document.createTextNode("\u00D7");
    span.classList.add("close");
    span.appendChild(span_txt);

    todo_div.appendChild(span);

    no_status.appendChild(todo_div);

    span.addEventListener('click', ()=> {
        span.parentElement.style.display = "none";
    })

    todo_div.addEventListener("dragstart", dragStart);

    todo_div.addEventListener("dragend", dragEnd);

    document.getElementById("todo_input").value = "";
}

const close_btns = document.querySelectorAll(".close");

close_btns.forEach(btn => {
    btn.addEventListener('click', ()=> {
        btn.parentElement.style.display = "none";
    })
});