<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <table class="datatable">
        <tr>
            <th><a href="">Project name</a></th>
        </tr>
        <#list model["userList"] as user>
            <tr>
                <td><#list user.projectId as ids><a href="/my-projects/${ids}/tasks"><button>Go!</button></a><br/></#list></td>
                <td><#list user.projectName as names>${names}<br/></#list></a></td>
            </tr>
        </#list>
    </table>
    <a href="/my-projects/add-projects">Add new project</a>
</@layout.page>