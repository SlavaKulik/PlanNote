<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <fieldset>
        <legend>
            Add user
        </legend>
        <form name="LogIn" action="/registration" method="post">
            Login : <input type="text" name="userName"/> <br/>
            Password : <input type="password" name="userPassword"/> <br/>
            Position : <input type="text" name="userPosition"/> <br/>
            Account status : <input type="text" name="accountStatus"/> <br/>
            User score : <input type="text" name="userScore"/> <br/>
            User status : <input type="text" name="userStatus"/> <br/>
            <input type="submit" value="  Save  " />
            <input type="reset" value="  Clear  " />
        </form>
    </fieldset>
    <a href="/login">Login after registration</a>
</@layout.page>