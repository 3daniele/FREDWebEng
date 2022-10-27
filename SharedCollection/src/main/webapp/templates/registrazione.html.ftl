<div class="container-fluid h-custom">
    <div class="row d-flex justify-content-center align-items-center h-100">
        <#if error??>
            <div class="row">
                <div class="alert alert-danger col-12 text-center" role="alert">
                    ${error}
                </div>
            </div>
        </#if>
        <div class="col-md-8 col-lg-6">
            <form method="POST" action="register">
                <!-- Username input -->
                <div class="form-outline mb-4">
                    <input type="text" name="username" class="form-control form-control-lg"
                           placeholder="Username"
                            <#if username??>
                                value="${username}"
                            </#if>
                    />
                </div>

                <!-- Email input -->
                <div class="form-outline mb-4">
                    <input type="email" name="email" class="form-control form-control-lg"
                           placeholder="Email"
                            <#if email??>
                                value="${email}"
                            </#if>
                    />
                </div>

                <!-- nome input -->
                <div class="form-outline mb-3">
                    <input type="text" name="nome" class="form-control form-control-lg"
                           placeholder="Nome"
                            <#if nome??>
                                value="${nome}"
                            </#if>
                    />
                </div>

                <!-- cognome input -->
                <div class="form-outline mb-3">
                    <input type="text" name="cognome" class="form-control form-control-lg"
                           placeholder="Cognome"
                            <#if cognome??>
                                value="${cognome}"
                            </#if>
                    />
                </div>

                <!-- Password input -->
                <div class="form-outline mb-3">
                    <input type="password" name="password" class="form-control form-control-lg"
                           placeholder="Password"/>
                </div>

                <!-- Password input -->
                <div class="form-outline mb-3">
                    <input type="password" name="password2" class="form-control form-control-lg"
                           placeholder="Conferma password"/>
                </div>

                <div class="d-flex justify-content-between align-items-center">
                </div>
                <div class="text-center text-lg-start mt-4 pt-2">
                    <button type="submit" class="btn poca-btn btn-lg" name="register">Registrati</button>
                    <p class="small fw-bold mt-2 pt-1 mb-0">
                        Hai già un account? <a href="login" class="link-danger">Login</a>
                    </p>
                </div>

            </form>
        </div>
        <div class="col-md-9 col-lg-6 col-xl-5">
            <img src="images/templateimg/core-img/login.png"
                 class="img-fluid" alt="Sample image">
        </div>
    </div>
</div>