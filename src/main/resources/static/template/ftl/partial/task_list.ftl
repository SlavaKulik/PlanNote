<#macro tasks>
    <table class="datatable">
        <tr>
            <th>Task name</th> <th>Task status</th> <th>Time from</th>
            <th>Time to</th> <th>Task priority</th> <th>Sum</th> <th>More options</th>
        </tr>
        <#list model["taskList"] as task>
            <tr>
                <td>
                    ${task.taskName}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/edit-name" method="post">
                        <input type="text" name="taskName">
                        <input type="submit" value="Submit">
                    </form>
                </td>
                <td>
                    ${task.statusTask}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/edit-status" method="post">
                        <select name="taskStatus">
                            <option value="To do">To do</option>
                            <option value="In-progress">In-progress</option>
                            <option value="Complete">Complete</option>
                        </select>
                        <input type="submit" value="Submit">
                    </form>
                </td>
                <td>
                    ${task.startTime}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/edit-start-time" method="post">
                        <input type="datetime-local" name="taskTimeStart"/>
                        <input type="submit">
                    </form>
                </td>
                <td>
                    ${task.endTime}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/edit-end-time" method="post">
                        <input type="datetime-local" name="taskTimeEnd"/>
                        <input type="submit">
                    </form>
                </td>
                <td>
                    ${task.priorityTask}
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/edit-priority" method="post">
                        <select name="taskPriority">
                            <option value="High">High</option>
                            <option value="Mid">Mid</option>
                            <option value="Low">Low</option>
                            <input type="submit">
                        </select>
                    </form>
                </td>
                <td>${task.sum}</td>
                <td>
                    <p><a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}/add-transaction" class="button">Add transaction</a></p>
                    <p><a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/${task.id}" class="button">More info about task</a></p>
                </td>
            </tr>
        </#list>
    </table>
</#macro>