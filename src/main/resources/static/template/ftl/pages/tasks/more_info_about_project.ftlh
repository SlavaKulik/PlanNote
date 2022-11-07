<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <table class="datatable">
        <tr>
            <th>Name</th>
            <th>Money flow</th>
        </tr>
        <#list model["transactionList"] as transactions>
            <tr>
                <td>${transactions.transactionName}</td>
                <td>${transactions.transactionMoneyFlow}</td>
            </tr>
        </#list>
    </table>
    <p><a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks/<#if taskId??>${taskId}</#if>/add-transaction">Add transaction</a></p>
    <p><a href="/my-projects/<#if projectId??>${projectId}</#if>/tasks">Return to tasks</a></p>
</@layout.page>