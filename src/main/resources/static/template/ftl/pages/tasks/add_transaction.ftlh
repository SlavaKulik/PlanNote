<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <fieldset>
        <legend>
            Add transaction
        </legend>
        <div class="form-group">
            <form name="addUser" action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>/add-transaction" method="post">
                <table class="table">
                <tr>
                    <th scope="col">Name</th>
                    <th scope="col">Money Flow</th>
                </tr>
                    <tr>
                        <td><input class="form-control" id="exampleFormControlInput1" type="text" name="transactionName"></td>
                        <td><input class="form-control" id="exampleFormControlInput1" type="text" name="transactionMoneyFlow"></td>
                        <td><button type="submit" class="btn btn-primary" value="Save">Save</button>
                            <button type="reset" class="btn btn-primary">Clear</button></td>
                    </tr>
                </table>
            </form>
        </div>
    </fieldset>
    <a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>" class="btn btn-primary" role="button">Return to task</a>
</@layout.page>