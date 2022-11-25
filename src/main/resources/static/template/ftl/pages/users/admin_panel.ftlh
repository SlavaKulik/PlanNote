<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link" href="/logout">Change account</a>
            </div>
        </div>
    </nav>
    <fieldset>
        <legend>
            All users
        </legend>
    </fieldset>
    <table class="table">
    <tr>
        <th scope="col">Name</th>
        <th scope="col">Position</th>
        <th scope="col">Account score</th>
        <th scope="col">Account status</th>
    </tr>
    <#list model["userList"] as user>
        <tr>
            <td>${user.userName}</td>
            <td>${user.userPosition}</td>
            <td>
                ${user.userScore}
                <form action="/admin-panel/${user.id}/edit-score" method="post">
                    <select class="form-control" id="exampleFormControlSelect1" name="userScore">
                        <option value="Newbie">Newbie</option>
                        <option value="Beginner">Beginner</option>
                        <option value="Office plankton">Office plankton</option>
                        <option value="Medium-level worker">Medium-level worker</option>
                        <option value="Advanced-level worker">Advanced-level worker</option>
                        <option value="Conveyor">Conveyor</option>
                    </select>
                    <button type="submit" class="btn btn-primary" value="Save">Save</button>
                </form>
            </td>
            <td>
                ${user.accStatus}
                <form action="/admin-panel/${user.id}/edit-status" method="post">
                    <select class="form-control" id="exampleFormControlSelect1" name="accStatus">
                        <option value="free_user">Free user</option>
                        <option value="premium_user">Premium user</option>
                    </select>
                    <button type="submit" class="btn btn-primary" value="Save">Save</button>
                </form>
            </td>
        </tr>
    </#list>
    <#if error??>
        <p class="lead"><em>${error}</em></p>
    </#if>
</@layout.page>