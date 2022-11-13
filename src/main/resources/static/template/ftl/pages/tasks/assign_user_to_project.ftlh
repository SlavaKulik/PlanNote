<#import "../../layouts/main.ftl" as layout>
<@layout.page>

    <fieldset>
        <legend>
            Assign new member
        </legend>
        <table class="table">
            <tr>
                <th scope="col">Name</th>
            </tr>
            <tr>
                <form name="addUser" action="/my-projects/<#if projectId??>${projectId}</#if>/assign-members" method="post">
                    <td><input type="text" name="userName" class="form-control" id="exampleFormControlInput1"></td>
                    <td><button type="submit" class="btn btn-primary" value="Save">Save</button>
                        <button type="reset" class="btn btn-primary">Clear</button></td>
                </form>
            </tr>
        </table>
    </fieldset>
    <a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks" class="btn btn-primary" role="button">Open project</a>
</@layout.page>