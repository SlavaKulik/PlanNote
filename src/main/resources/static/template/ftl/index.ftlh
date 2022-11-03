<#import "layouts/main.ftl" as layout>
<@layout.page>
    <h1>
        Hy, it's my PlanNote!
    </h1>
    <div class="todo-container">
        <div class="status" id="no_status">
            <h1>No status</h1>
            <div id="add_btn">
                <label for="todo_input" id="add_label">Add Todo</label>
                <input type="text" id="todo_input">
                <input type="submit" value="Add todo" id="todo_submit">
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