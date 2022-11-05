<#macro tasks>
    <table class="datatable">
        <tr>
            <th>Task name</th> <th>Project name</th> <th>User name</th> <th>Task status</th> <th>Time from</th>
            <th>Time to</th> <th>Task label</th> <th>Task priority</th> <th>Task transactions</th> <th>Subtasks</th>
        </tr>
        <#list model["taskList"] as task>
            <tr>
                <td>${task.taskName}</td> <td>${task.projectTask}</td> <td>${task.userTask}</td>
                <td>${task.statusTask}</td> <td>${task.startTime}</td> <td>${task.endTime}</td>
                <td>${task.labelTask}</td> <td>${task.priorityTask}</td> <td>${task.transactions}</td>
                <td>${task.subtasks}</td>
            </tr>
        </#list>
    </table>
</#macro>