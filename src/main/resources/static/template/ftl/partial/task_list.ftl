<#macro tasks>
        <div class="todo-container">
            <div class="status" id="to_do">
                <h1>To do</h1>
                <#list model["todoTasks"] as todos>
                    <div class="todo" draggable="true">
                        <a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${todos.id}" class="btn btn-primary" role="button">${todos.taskName}</a>
                    </div>
                </#list>
            </div>
            <div class="status">
                <h1>In-progress</h1>
                <#list model["progressTasks"] as progress>
                    <div class="todo" draggable="true">
                        <a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${progress.id}" class="btn btn-primary" role="button">${progress.taskName}</a>
                    </div>
                </#list>
            </div>
            <div class="status">
                <h1>Completed</h1>
                <#list model["completedTasks"] as completed>
                    <div class="todo" draggable="true">
                        <a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${completed.id}" class="btn btn-primary" role="button">${completed.taskName}</a>
                    </div>
                </#list>
            </div>
        </div>
</#macro>