<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <table class="datatable">
        <tr>
            <th><a href="">Project name</a></th>
        </tr>
        <#list model["userList"] as user>
            <tr>
                <td><#list user.projectId as ids><a href="/projects/my-projects/${ids}"><button>Go!</button></a><br/></#list></td>
                <td><#list user.projectName as names><a href="">${names}</a><br/></#list></a></td>
            </tr>
        </#list>
    </table>
</@layout.page>