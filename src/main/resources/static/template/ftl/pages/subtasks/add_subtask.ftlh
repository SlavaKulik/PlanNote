<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <fieldset>
        <legend>
            Add project
        </legend>
        <form name="addSubtasks" action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>/add-subtask" method="post">
            Name : <input type="text" name="subtaskName"/> <br/>
            Time from: <input type="datetime-local" name="startTime"> <br/>
            Time till: <input type="datetime-local" name="endTime"> <br/>
            <input type="submit" value="  Save  " />
            <input type="reset" value="  Clear  " />
        </form>
    </fieldset>
    <a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>">Return back</a>
</@layout.page>