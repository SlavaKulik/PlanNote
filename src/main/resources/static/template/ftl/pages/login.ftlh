<#import "../layouts/main.ftl" as layout>
<@layout.page>
    <section class="vh-100" style="background-color: #508bfc;">
        <div class="container py-5 h-100">
            <div class="row d-flex justify-content-center align-items-center h-100">
                <div class="col-12 col-md-8 col-lg-6 col-xl-5">
                    <div class="card shadow-2-strong" style="border-radius: 1rem;">
                        <div class="card-body p-5 text-center">

                            <h3 class="mb-5">Sign in</h3>
                            <form role="form" action="/login" method="post">
                                <div class="form-outline mb-4">
                                    <input type="text" name="username" id="typeEmailX-2" class="form-control form-control-lg" required/>
                                </div>

                                <div class="form-outline mb-4">
                                    <input type="password" name="password" id="typePasswordX-2" class="form-control form-control-lg" required/>
                                </div>

                                <button class="btn btn-primary btn-lg btn-block" type="submit">Login</button>
                            </form>
                            <hr class="my-4">

                            <a class="btn btn-info" href="/registration" role="button" style="background-color: blue; color: white">Or you can register an account</a>
                            <#if error??>
                                <hr class="my-4">
                                <p class="lead"><em>${error}</em></p>
                            </#if>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </section>
</@layout.page>
