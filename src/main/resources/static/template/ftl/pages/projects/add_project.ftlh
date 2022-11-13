<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <fieldset>
        <legend>
            Add project
        </legend>
        <form name="addProject" action="/my-projects/add-projects" method="post">
            <table class="table">
                <tr>
                    <th scope="col">Name</th>
                </tr>
                <tr>
                    <td><input class="form-control" id="exampleFormControlInput1" type="text" name="projectName"/></td>
                    <td><button type="submit" class="btn btn-primary" value="Save">Save</button>
                        <button type="reset" class="btn btn-primary">Clear</button></td>
                </tr>
            </table>
        </form>
    </fieldset>
    <a href="/my-projects" class="btn btn-primary" role="button">Go to my projects</a>
</@layout.page>