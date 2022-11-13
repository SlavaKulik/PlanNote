<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <table class="table">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Project name</th>
        </tr>
        <#list model["userList"] as user>
            <tr>
                <td><#list user.projectId as ids><a href="/my-projects/${ids}/tasks" class="btn btn-primary" role="button">Go!</a><br/></#list></td>
                <td><#list user.projectName as names>${names}<br/></#list></a></td>
            </tr>
        </#list>
    </table>
    <a href="/my-projects/add-projects" class="btn btn-primary" role="button">Add new project</a>
</@layout.page>