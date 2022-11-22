<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <link rel="stylesheet" href="../../../../css/style.css">
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link" href="/my-projects/<#if projectId??>${projectId}</#if>/assign-members">Assign new member</a>
                <a class="nav-item nav-link" href="/my-projects">Go to another project</a>
            </div>
        </div>
    </nav>
    <fieldset>
        <legend>
            Add task
        </legend>
        <form name="addProject" action="/my-projects/<#if projectId??>${projectId}</#if>/tasks" method="post">
            <table class="table">
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Deadline</th>
                    <th scope="col">Priority</th>
                </tr>
                <tr>
                    <td><input style="min-width: 200px" class="form-control" id="exampleFormControlInput1" type="text" name="taskName"/></td>
                    <td><input class="form-control" id="exampleFormControlInput1" type="datetime-local" name="taskTimeEnd"/></td>
                    <td><select class="form-control" id="exampleFormControlSelect1" name="taskPriority" style="min-width: 75px">
                            <option value="High">High</option>
                            <option value="Mid">Mid</option>
                            <option value="Low">Low</option>
                        </select>
                    </td>
                    <td><button type="submit" class="btn btn-primary" value="Save">Save</button>
                        <button type="reset" class="btn btn-primary">Clear</button></td>
                </tr>
                <input class="form-control" id="exampleFormControlInput1" type="text" name="taskStatus" value="To do" hidden/> <br/>
            </table>
        </form>
    </fieldset>
    <h2>Project name: <#if projectName??>${projectName}</#if> </h2><h2>User: <#if userName??>${userName}</#if></h2>
    <#import "../../partial/task_list.ftl" as taskPartial>
    <@taskPartial.tasks />
</@layout.page>