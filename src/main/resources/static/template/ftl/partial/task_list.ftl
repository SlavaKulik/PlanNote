<#macro tasks>
    <table class="table">
        <tr>
            <th scope="col">Task name</th>
            <th scope="col">Task status</th>
            <th scope="col">Deadline</th>
            <th scope="col">Task priority</th>
            <th scope="col">Sum</th>
            <th scope="col">More options</th>
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
                <td>
                    <p><a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/add-transaction" class="btn btn-primary" role="button">Add transaction</a></p>
                    <p><a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}" class="btn btn-primary" role="button">More info about task</a></p>
                </td>
            </tr>
        </#list>
    </table>
</#macro>