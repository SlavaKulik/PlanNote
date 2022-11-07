<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <fieldset>
        <legend>
            Add project
        </legend>
        <form name="addProject" action="/my-projects/add-projects" method="post">
            Name : <input type="text" name="projectName"/> <br/>
            <input type="submit" value="  Save  " />
            <input type="reset" value="  Clear  " />
        </form>
    </fieldset>
    <a href="/my-projects">Go to my projects</a>
</@layout.page>