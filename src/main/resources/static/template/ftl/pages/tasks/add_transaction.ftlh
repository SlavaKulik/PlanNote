<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <fieldset>
        <legend>
            Add transaction
        </legend>
        <form name="addUser" action="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>/add-transaction" method="post">
            Name: <input type="text" name="transactionName">
            Money flow: <input type="text" name="transactionMoneyFlow">
            <input type="submit" value="  Save  " />
            <input type="reset" value="  Clear  " />
        </form>
    </fieldset>
    <a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks">Return to tasks</a>
</@layout.page>