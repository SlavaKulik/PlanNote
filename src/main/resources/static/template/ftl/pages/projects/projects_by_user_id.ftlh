<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link" href="/logout">Change account</a>
            </div>
        </div>
    </nav>
    <table class="table">
        <tr>
            <th scope="col">#</th>
            <th scope="col">Project name</th>
        </tr>
        <#list model["projectList"] as projects>
            <tr>
                <td><#list projects.projectId as ids>
                        <a href="/my-projects/${ids}/tasks" class="btn btn-primary" role="button">Go!</a><br/>
                    </#list>
                </td>
                <td>
                    <#list projects.projectName as names><p></names>${names}</p></#list>
                </td>
                <td><#list projects.projectId as ids>
                        <form action="/my-projects/${ids}/delete" method="post">
                            <button type="submit" class="btn btn-primary" value="delete">Delete</button>
                        </form>
                    </#list>
                </td>
            </tr>
        </#list>
    </table>
    <a href="/my-projects/add-projects" class="btn btn-primary" role="button">Add new project</a>
</@layout.page>