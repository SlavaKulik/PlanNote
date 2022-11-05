<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <#import "../../partial/task_list.ftl" as taskPartial>
    <@taskPartial.tasks />
    <fieldset>
        <legend>
            Assign new member
        </legend>
            <form name="addUser" action="/projects/my-projects/<#if projectId??>${projectId}</#if>" method="post">
                Name: <input type="text" name="userName">
                <input type="submit" value="  Save  " />
                <input type="reset" value="  Clear  " />
            </form>
    </fieldset>
</@layout.page>