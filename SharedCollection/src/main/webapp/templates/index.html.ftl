<!DOCTYPE html>
<html lang="it">

<head>
    <meta charset="UTF-8">
    <meta name="description" content="Poca - Podcast &amp; Audio Template">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">

    <title>SharedCollection</title>

    <link rel="icon" href="images/templateimg/core-img/favicon.ico">

    <link rel="stylesheet" href="style/style.css">
</head>

<body>
<!-- ===== HEADER ===== -->
<header class="header-area sticky">
    <div class="main-header-area sticky">
        <div class="classy-nav-container left breakpoint-on">
            <nav class="classy-navbar justify-content-between" id="pocaNav">
                <a class="nav-brand" href="home"><img src="images/templateimg/core-img/logo.png"></a>
                <div class="classy-navbar-toggler">
                    <span class="navbarToggler"><span></span><span></span><span></span></span>
                </div>
                <div class="classy-menu">
                    <div class="classycloseIcon">
                        <div class="cross-wrap"><span class="top"></span><span class="bottom"></span></div>
                    </div>
                    <div class="classynav">
                        <ul id="nav">
                            <#if display??>
                                <li class="current-item"><a href="home">Home</a></li>
                            <#else>
                                <li><a href="home">Home</a></li>
                            </#if>
                            <#if collezioniPath??>
                                <li class="current-item"><a href="collezioni">Collezioni</a></li>
                            <#else>
                                <li><a href="collezioni">Collezioni</a></li>
                            </#if>
                            <#if utentiPath??>
                                <li class="current-item"><a href="showUtenti">Utenti</a></li>
                            <#else>
                                <li><a href="showUtenti">Utenti</a></li>
                            </#if>
                            <#if session??>
                                <li class="cn-dropdown-item"><a href="#">${username}</a>
                                    <ul class="dropdown">
                                        <li><a href="utente?id=${userid}">Profilo</a></li>
                                        <li><a href="newCollezione">Nuova Collezione</a></li>
                                        <li><a href="logout">Logout</a></li>
                                    </ul>
                                    <span class="dd-trigger"></span>
                                </li>

                            <#else>
                                <#if loginPath??>
                                    <li class="current-item"><a href="login">Login</a></li>
                                <#else>
                                    <li><a href="login">Login</a></li>
                                </#if>
                                <#if registrationPath??>
                                    <li class="current-item"><a href="register">Registrati</a></li>
                                <#else>
                                    <li><a href="register">Registrati</a></li>
                                </#if>
                            </#if>
                        </ul>
                        <div class="top-search-area">
                            <form action="home" method="post">
                                <input type="search" name="top-search-bar" class="form-control"
                                       placeholder="Cerca">
                            </form>
                        </div>
                    </div>
                </div>
            </nav>
        </div>
    </div>
</header>

<!-- ===== CAROSELLO ===== -->
<section class="welcome-area">
    <div class="welcome-slides owl-carousel">
        <#if display??>
            <div class="welcome-welcome-slide bg-img bg-overlay"
                 style="background-image:url(images/templateimg/bg-img/1.jpg)">
                <div class="container h-100">
                    <div class="row h-100 align-items-center">
                        <div class="col-12">
                            <div class="welcome-text">
                                <h2 data-animation="fadeInUp" data-delay="100ms">La collezione del momento</h2>
                            </div>
                            <div class="poca-music-area mt-100 d-flex align-items-center flex-wrap"
                                 data-animation="fadeInUp" style="cursor:pointer"
                                 onclick="location.href='collezione?numero=${ultima_collezione.key}'">
                                <div class="poca-music-thumbnail">
                                    <img src="images/templateimg/bg-img/27.jpg" alt="">
                                </div>
                                <div class="poca-music-content">
                                    <span class="music-published-date">${ultima_collezione.dataCreazione}</span>
                                    <h2>${ultima_collezione.nome}</h2>
                                    <div class="music-meta-data">
                                        <p>By <a href="utente?id=${ultima_collezione.utente.key}" class="music-author">
                                                <#list utenti as utente>
                                                    <#if (utente.key=ultima_collezione.utente.key)>
                                                        ${utente.nickname}
                                                    </#if>
                                                </#list>
                                            </a>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </#if>
    </div>
</section>

<#if !dispaly??>
    <br>
</#if>
<section class="poca-latest-epiosodes section-padding-80">
    <div class="container">
        <div class="row">
            <div class="col-12">
                <div class="section-heading text-center">
                    <h2>${page_title!"Untitled page"}</h2>
                    <div class="line"></div>
                </div>
            </div>
        </div>
    </div>

    <!-- ===== COLLEZIONI ===== -->
    <div class="container">
        <#include content_tpl>
    </div>
</section>

<!-- ===== BANNER FINALE ===== -->
<section class="poca-newsletter-area bg-img bg-overlay pt-50"
         style="background-image:url(images/templateimg/bg-img/15.jpg)">
    <div class="container">
        <div class="row align-items-center">
            <div class="col-12 col-lg-12">
                <div class="newsletter-content mb-50">
                    <h2>SharedCollection</h2>
                </div>
            </div>
        </div>
    </div>
</section>

<!-- ===== FOOTER ===== -->
<footer class="footer-area section-padding-80-0">
    <div class="container">
        <div class="row">
            <div class="col-12 col-sm-6 col-lg-3">
                <div class="single-footer-widget mb-80">
                    <h4 class="widget-title">Guarda anche:</h4>
                    <div class="single-latest-episodes">
                        <a href="home" class="episodes-title">Home</a>
                    </div>
                    <div class="single-latest-episodes">
                        <a href="collezioni" class="episodes-title">Collezioni</a>
                    </div>
                    <div class="single-latest-episodes">
                        <a href="showUtenti" class="episodes-title">Utenti</a>
                    </div>
                    <#if session??>
                    <#else>
                        <div class="single-latest-episodes">
                            <a href="login" class="episodes-title">Login</a>
                        </div>
                        <div class="single-latest-episodes">
                            <a href="register" class="episodes-title">Registrati</a>
                        </div>
                    </#if>
                </div>
            </div>
            <div class="col-12 col-sm-12 col-lg-6">
                <div class="single-footer-widget mb-80">
                    <h4 class="widget-title">Chi siamo?</h4>
                    <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nulla vitae nibh nec libero tincidunt
                        dapibus vel eget arcu. Nam dignissim euismod lacus. Fusce dapibus, mi vel laoreet sollicitudin,
                        arcu massa ullamcorper augue, in molestie nibh odio vel magna.</p>
                </div>
            </div>
            <div class="col-12 col-sm-6 col-lg-3">
                <div class="single-footer-widget mb-80">
                    <h4 class="widget-title">Follow Us</h4>
                    <div class="footer-social-info">
                        <a href="#" class="facebook text-light" data-toggle="tooltip" data-placement="top" title="Facebook">
                            <img src="images/templateimg/imgFont/facebook.svg" alt="Bootstrap" width="26" height="26">
                        </a>
                        <a href="#" class="twitter text-light" data-toggle="tooltip" data-placement="top" title="Twitter">
                            <img src="images/templateimg/imgFont/twitter.svg" alt="Bootstrap" width="26" height="26">
                        </a>
                        <a href="#" class="pinterest text-light" data-toggle="tooltip" data-placement="top"
                           title="Pinterest">
                            <img src="images/templateimg/imgFont/pinterest.svg" alt="Bootstrap" width="26"
                                 height="26">
                        </a>
                        <a href="#" class="instagram text-light" data-toggle="tooltip" data-placement="top"
                           title="Instagram">
                            <img src="images/templateimg/imgFont/instagram.svg" alt="Bootstrap" width="26"
                                 height="26">
                        </a>
                        <a href="#" class="youtube text-light" data-toggle="tooltip" data-placement="top" title="YouTube">
                            <img src="images/templateimg/imgFont/youtube.svg" alt="Bootstrap" width="26"
                                 height="26">
                        </a>
                    </div>
                    <div class="copywrite-content">
                        <p>&copy;
                            Copyright 2022
                            All rights reserved | This
                            template is made with <i class="fa fa-heart-o" aria-hidden="true"></i> by ${defaults.author}
                        </p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</footer>


<script src="js/jquery.min.js"></script>

<script src="js/bootstrap.min.js"></script>

<script src="js/poca.bundle.js"></script>

<script src="js/default-assets/active.js"></script>

</body>

</html>