<#import "../../layouts/main.ftl" as layout>
<@layout.page>
    <fieldset>
        <legend>
            Something went wrong here...
        </legend>
        <form name="redirection" action="<#if url??>${url}</#if>" method="get">
            <table class="table">
                <tr>
                    <th scope="col"></th>
                    <th scope="col"></th>
                </tr>
                <tr>
                    <td><#if error??>
                            <p class="lead"><em>${error}</em></p>
                        </#if></td>
                    <td><button  type="submit" class="btn btn-primary" value="Go back">Go back</button>
                </tr>
            </table>
        </form>
    </fieldset>
</@layout.page>