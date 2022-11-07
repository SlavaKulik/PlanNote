<#import "../../layouts/main.ftl" as layout>
<@layout.page>

    <fieldset>
        <legend>
            Assign new member
        </legend>
            <form name="addUser" action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/" method="post">
                Name: <input type="text" name="userName">
                <input type="submit" value="  Save  " />
                <input type="reset" value="  Clear  " />
            </form>
    </fieldset>
    <a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks">Open project</a>
</@layout.page>