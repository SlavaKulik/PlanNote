<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <table class="table">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Project name</th>
        </tr>
        <#list model["projectList"] as projects>
            <tr>
                <td><#list projects.projectId as ids><a href="/my-projects/${ids}/tasks" class="btn btn-primary" role="button">Go!</a><br/></#list></td>
                <td><#list projects.projectName as names>${names}<br/></#list></td>
            </tr>
        </#list>
    </table>
    <a href="/my-projects/add-projects" class="btn btn-primary" role="button">Add new project</a>
</@layout.page>