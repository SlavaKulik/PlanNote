<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <table class="datatable">
        <tr>
            <th><a href="">Project name</a></th>
        </tr>
        <#list model["userList"] as user>
            <tr>
                <td><a href=""><#list user.projectId as ids>${ids}<br/></#list></a></td>
                <td><a href=""><#list user.projectName as names>${names}<br/></#list></a></td>
            </tr>
        </#list>
    </table>
</@layout.page>