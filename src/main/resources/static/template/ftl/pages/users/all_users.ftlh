<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <form name="searchUser" action="/users/find-users-by-name" method="get">
        Name : <input type="text" name="userName"/> <br/>
        <input type="submit" value="  Save  " />
        <input type="reset" value="  Clear  " />
    </form>
    <#import "../../partial/user_list.ftl" as users>
    <@users.user />
</@layout.page>