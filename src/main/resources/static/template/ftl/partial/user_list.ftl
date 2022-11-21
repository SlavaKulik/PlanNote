<#macro user>
    <table class="table">
        <tr>
            <th scope="col">Name</th>
            <th scope="col">Position</th>
            <th scope="col">Account score</th>
        </tr>
        <#list model["userList"] as user>
            <tr>
                <td>${user.userName}</td>
                <td>${user.userPosition}</td>
                <td>${user.userScore}</td>
                <td>
                    <form action="/my-projects/<#if projectId??>${projectId}</#if>/delete/${user.id}" method="post">
                        <button type="submit" class="btn btn-primary" value="delete">Delete</button>
                    </form>
                </td>
            </tr>
        </#list>
    </table>
</#macro>