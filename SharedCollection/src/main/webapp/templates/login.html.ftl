<div class="container-fluid h-custom">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <div class="col-md-9 col-lg-6 col-xl-5">
            <img src="images/templateimg/core-img/login.png"
                 class="img-fluid" alt="Sample image">
        </div>
        <div class="col-md-8 col-lg-6">
            <#if error??>
            <div class="alert alert-danger" role="alert">
                ${error}
            </div>
            </#if>
            <#if confirmEmail??>
                <div class="alert alert-success" role="alert">
                    ${confirmEmail}
                </div>
            </#if>
            <form method="POST" action="login">
                <!-- Email input -->
                <div class="form-outline mb-4">
                    <input type="email" name="email" class="form-control form-control-lg"
                           placeholder="Inserisci la tua email"
                           <#if email??>
                               value="${email}"
                           </#if>
                    >
                </div>

                <!-- Password input -->
                <div class="form-outline mb-3">
                    <input type="password" name="password" class="form-control form-control-lg"
                           placeholder="Inserisci la tua password"/>
                </div>

                <div class="d-flex justify-content-between align-items-center">
                    <!-- Checkbox -->
                    <div class="form-check mb-0">
                        <input class="form-check-input me-2" type="checkbox" value=""
                               id="form2Example3"/>
                        <label class="form-check-label" for="form2Example3">
                            Remember me
                        </label>
                    </div>
                    <a href="#!" class="text-body">Forgot password?</a>
                </div>
                <div class="text-center text-lg-start mt-4 pt-2">
                    <button type="submit" class="btn poca-btn btn-lg" name="login">Login</button>
                    <p class="small fw-bold mt-2 pt-1 mb-0">Don't have an account? <a href="register" class="link-danger">Register</a>
                    </p>
                </div>

            </form>
        </div>
    </div>
</div>