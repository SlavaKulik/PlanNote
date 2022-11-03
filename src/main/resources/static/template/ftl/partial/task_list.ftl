<#macro tasks>
    <table class="datatable">
        <tr>
            <th>task_name</th> <th>project_name</th> <th>user_name</th> <th>task_status</th> <th>time_from</th>
            <th>time_till</th> <th>task_label</th> <th>task_priority</th>
        </tr>
        <#list model["taskList"] as task>
            <tr>
                <td>${task.taskName}</td> <td>${task.projectTask}</td> <td>${task.userTask}</td>
                <td>${task.taskStatus}</td> <td>${task.taskTimeStart}</td> <td>${task.taskTimeEnd}</td>
                <td>${task.taskLabel}</td> <td>${task.taskPriority}</td>
            </tr>
        </#list>
    </table>
</#macro>