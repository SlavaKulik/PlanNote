<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <fieldset>
        <legend>
            Add subtask
        </legend>
        <div class="form-group">
            <form name="addSubtasks" action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>/add-subtask" method="post">
                <table class="table">
                    <tr>
                        <th scope="col">Name</th>
                        <th scope="col">Time from</th>
                        <th scope="col">Time till</th>
                    </tr>
                    <tr>
                        <td><input class="form-control" id="exampleFormControlInput1" type="text" name="subtaskName"/></td>
                        <td><input class="form-control" id="exampleFormControlInput1" type="datetime-local" name="startTime"></td>
                        <td><input class="form-control" id="exampleFormControlInput1" type="datetime-local" name="endTime"></td>
                        <td>
                            <button type="submit" class="btn btn-primary" value="Save">Save</button>
                            <button type="reset" class="btn btn-primary">Clear</button>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </fieldset>
    <a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>">Return back</a>
</@layout.page>