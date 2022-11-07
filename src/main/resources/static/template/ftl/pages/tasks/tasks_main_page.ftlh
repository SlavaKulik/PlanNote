<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <link rel="stylesheet" href="../../../../css/style.css">
    <a href="/my-projects/<#if projectId??>${projectId}</#if>/assign-members">Assign new member</a>
    <a href="/my-projects">Go to another project</a>
    <fieldset>
        <legend>
            Add task
        </legend>
        <form name="addProject" action="/my-projects/<#if projectId??>${projectId}</#if>/tasks" method="post">
            Name : <input type="text" name="taskName"/> <br/>
            <input type="text" name="taskStatus" value="To do" hidden/> <br/>
            Start time : <input type="datetime-local" name="taskTimeStart"/> <br/>
            End time : <input type="datetime-local" name="taskTimeEnd"/> <br/>
            Priority : <input type="text" name="taskPriority"/> <br/>
            <input type="submit" value="  Save  " />
            <input type="reset" value="  Clear  " />
        </form>
    </fieldset>
    <h2>Project name: <#if projectName??>${projectName}</#if> </h2> of <h2>User: <#if userName??>${userName}</#if></h2>
    <#import "../../partial/task_list.ftl" as taskPartial>
    <@taskPartial.tasks />
</@layout.page>