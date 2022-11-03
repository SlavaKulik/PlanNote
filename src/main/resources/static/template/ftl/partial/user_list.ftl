<#macro user>
    <table class="datatable">
        <tr>
            <th>user_id</th> <th>user_name</th> <th>user_position</th> <th>user_account_score</th>
        </tr>
        <#list model["userList"] as user>
            <tr>
                <td>${user.id}</td> <td>${user.userName}</td> <td>${user.userPosition}</td> <td>${user.userScore}</td>
            </tr>
        </#list>
    </table>
</#macro>