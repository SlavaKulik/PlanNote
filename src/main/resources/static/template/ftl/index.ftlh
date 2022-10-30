<#import "layouts/main.ftl" as layout>
<@layout.page>
    <h1>
        Hy, it's my PlanNote!
    </h1>
    <script>
        const todos = document.querySelectorAll(".todo");
        const all_status = document.querySelectorAll(".status");
        let draggableTodo = null;

        todos.forEach((todo) => {
            todo.addEventListener("dragstart", dragStart);
            todo.addEventListener("dragend", dragEnd);
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
            status.addEventListener("dragover", dragOver);
            status.addEventListener("dragenter", dragEnter);
            status.addEventListener("dragleave", dragLeave);
            status.addEventListener("drop", dragDrop);
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

        // todo_submit.addEventListener("click", createTodo);

        function createTodo() {
            const todo_div = document.createElement("div");
            const input_val = document.getElementById("todo_input").value;
            const txt = document.createTextNode(input_val);
            const no_status = document.getElementById("no_status");

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
    </script>
    <div class="todo-container">
        <div class="status" id="no_status">
            <h1>No status</h1>
            <div id="add_btn">
                <label for="todoname" id="add_label">Add Todo</label>
                <input type="text" name="todoname" id="todo_input">
                <input type="submit" value="Add todo" id="todo_submit" onclick="createTodo()">
            </div>
            <div class="todo" draggable="true">
                Buy a pizza
                <span class="close">&times;</span>
            </div>
        </div>
        <div class="status">
            <h1>Not started</h1>
        </div>
        <div class="status">
            <h1>In-progress</h1>
        </div>
        <div class="status">
            <h1>Completed</h1>
        </div>
    </div>
</@layout.page>