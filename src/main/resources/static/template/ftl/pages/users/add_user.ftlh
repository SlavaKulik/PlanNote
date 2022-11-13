<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <fieldset>
        <legend>
            Add user
        </legend>
        <table class="table">
            <tr>
                <th scope="col">Login</th>
                <th scope="col">Password</th>
                <th scope="col">Position</th>
            </tr>
            <tr>
                <form name="LogIn" action="/registration" method="post">
                    <td><input type="text" name="userName" style="min-width: 200px" class="form-control" id="exampleFormControlInput1" required/></td>
                    <td><input type="password" name="userPassword" style="min-width: 200px" class="form-control" id="exampleFormControlInput1" required/></td>
                    <td><input type="text" name="userPosition" style="min-width: 200px" class="form-control" id="exampleFormControlInput1" required/></td>
                    <td><input type="text" name="accountStatus" value="free_user" hidden/> <br/></td>
                    <td><input type="text" name="userScore" value="Newbie" hidden/> <br/></td>
                    <td><input type="text" name="userStatus" value="Project member" hidden/> <br/></td>
                    <td><button type="submit" class="btn btn-primary" value="Save">Save</button>
                    <button type="reset" class="btn btn-primary">Clear</button></td>
                </form>
            </tr>
        </table>
    </fieldset>
    <a href="/login">Login after registration</a>
</@layout.page>