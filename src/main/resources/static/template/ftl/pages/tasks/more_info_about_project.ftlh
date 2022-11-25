<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <link rel="stylesheet" href="../../../../css/style.css">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link active" href="/">Home</a>
                <a class="nav-item nav-link active" href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>/add-transaction">Add transaction</a>
                <a class="nav-item nav-link" href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}/add-subtask</#if>">Create subtasks</a>
                <a class="nav-item nav-link" href="/my-projects/<#if projectId??>${projectId}</#if>/tasks">Return to tasks</a>
            </div>
        </div>
    </nav>
    <table class="table">
        <tr>
            <th scope="col">Task name</th>
            <th scope="col">Task status</th>
            <th scope="col">Deadline</th>
            <th scope="col">Task priority</th>
            <th scope="col">Sum</th>
        </tr>
        <#list model["taskList"] as task>
            <tr>
                <td>
                    ${task.taskName}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/edit-name" method="post">
                        <input class="form-control" id="exampleFormControlInput1" type="text" name="taskName">
                        <button type="submit" class="btn btn-primary" value="Save">Save</button>
                    </form>
                </td>
                <td>
                    ${task.statusTask}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/edit-status" method="post">
                        <select class="form-control" id="exampleFormControlSelect1" name="taskStatus">
                            <option value="To do">To do</option>
                            <option value="In-progress">In-progress</option>
                            <option value="Complete">Complete</option>
                        </select>
                        <button type="submit" class="btn btn-primary" value="Save">Save</button>
                    </form>
                </td>
                <td>
                    ${task.endTime}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/edit-end-time" method="post">
                        <input class="form-control" id="exampleFormControlInput1" type="datetime-local" name="taskTimeEnd"/>
                        <button type="submit" class="btn btn-primary" value="Save">Save</button>
                    </form>
                </td>
                <td>
                    ${task.priorityTask}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/edit-priority" method="post">
                        <select class="form-control" id="exampleFormControlSelect1" name="taskPriority">
                            <option value="High">High</option>
                            <option value="Mid">Mid</option>
                            <option value="Low">Low</option>
                        </select>
                        <button type="submit" class="btn btn-primary" value="Save">Save</button>
                    </form>
                </td>
                <td>${task.sum}</td>
            </tr>
        </#list>
    </table>
    <table class="table">
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Money flow</th>
        </tr>
        <#list model["transactionList"] as transactions>
            <tr>
                <td>${transactions.transactionName}</td>
                <td>${transactions.transactionMoneyFlow}</td>
            </tr>
        </#list>
    </table>
    <table class="table">
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Deadline</th>
        </tr>
        <#list model["subtaskList"] as subtasks>
            <tr>
                <td>
                    ${subtasks.subtaskName}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>/${subtasks.id}/edit-name" method="post">
                        <input class="form-control" id="exampleFormControlInput1" type="text" name="subtaskName">
                        <button type="submit" class="btn btn-primary" value="Save">Save</button>
                    </form>
                </td>
                <td>
                    ${subtasks.endTime}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>/${subtasks.id}/edit-end-time" method="post">
                        <input class="form-control" id="exampleFormControlInput1" type="datetime-local" name="endTime"/>
                        <button type="submit" class="btn btn-primary" value="Save">Save</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>
</@layout.page>