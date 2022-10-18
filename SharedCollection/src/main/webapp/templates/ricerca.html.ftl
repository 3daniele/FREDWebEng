<form action="search" method="post">
    <div class="row">
        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon3">Cerca:</span>
            <#if keyword??>
                <input type="text" class="form-control" id="campo" name="campo" aria-describedby="basic-addon3"
                       value="${keyword}">
            <#else>
                <input type="text" class="form-control" id="campo" name="campo" aria-describedby="basic-addon3">
            </#if>
        </div>
    </div>
    <div class="row">
        <div class="col-12 col-md-5 col-lg-5">
            <div class="card" style="border: none">
                <div class="card-body">
                    <h5 class="card-title">Formato:</h5>
                    <input type="radio" class="btn-check" id="Vinile" name="formato" value="Vinile" autocomplete="off">
                    <label class="btn btn-outline-success" for="Vinile">Vinile</label>
                    <input type="radio" class="btn-check" id="CD" name="formato" value="CD" autocomplete="off">
                    <label class="btn btn-outline-success" for="CD">CD</label>
                    <input type="radio" class="btn-check" id="Digitale" name="formato" value="Digitale"
                           autocomplete="off">
                    <label class="btn btn-outline-success" for="Digitale">Digitale</label>
                    <input type="radio" class="btn-check" id="Cassetta" name="formato" value="Cassetta"
                           autocomplete="off">
                    <label class="btn btn-outline-success" for="Cassetta">Cassetta</label>
                    <input type="radio" class="btn-check" id="Altro" name="formato" value="Altro" autocomplete="off">
                    <label class="btn btn-outline-success" for="Altro">Altro</label>
                </div>
            </div>
        </div>
        <div class="col-12 col-md-2 col-lg-2">

        </div>
        <div class="col-12 col-md-5 col-lg-5">
            <div class="card" style="border: none">
                <div class="card-body">
                    <h5 class="card-title">Stato:</h5>
                    <input type="radio" class="btn-check" id="ottimo" name="stato" value="Ottimo" autocomplete="off">
                    <label class="btn btn-outline-danger" for="ottimo">Ottimo</label>
                    <input type="radio" class="btn-check" id="buono" name="stato" value="Buono" autocomplete="off">
                    <label class="btn btn-outline-danger" for="buono">Buono</label>
                    <input type="radio" class="btn-check" id="discreto" name="stato" value="Discreto"
                           autocomplete="off">
                    <label class="btn btn-outline-danger" for="discreto">Discreto</label>
                    <input type="radio" class="btn-check" id="sufficiente" name="stato" value="Sufficiente"
                           autocomplete="off">
                    <label class="btn btn-outline-danger" for="sufficiente">Sufficiente</label>
                    <input type="radio" class="btn-check" id="pessimo" name="stato" value="Pessimo" autocomplete="off">
                    <label class="btn btn-outline-danger" for="pessimo">Pessimo</label>
                </div>
            </div>
        </div>
    </div>
    <br>
    <div class="row">
        <div class="col-lg-3 col-md-3">

        </div>
        <div class="col-12 col-lg-5 col-md-5">
            <input type="submit" class="btn btn-danger" id="filtra" name="filtra" value="Filtra" style="width: 100%">
        </div>
        <div class="col-lg-3 col-md-3">

        </div>
    </div>
</form>
<#if keyword??>
    <div class="row">
        <h3>Collezioni:</h3>
        <#if !(collezioni?size>0)>
            <p>Nessuna collezione trovata per ${keyword}...</p>
        </#if>
        <#if collezioni??>
            <#assign n = 5>
            <#list collezioni as collezione>
                <div class="col-12 col-md-4 col-lg-4 entre wow fadeInUp" data-wow-delay="0.2s"
                     style="cursor:pointer"
                     onclick="location.href='collezione?numero=${collezione.key}'">
                    <div class="poca-music-area style-2 d-flex align-items-center flex-wrap">
                        <div class="poca-music-thumbnail">
                            <div class="portrait">
                                <img src="images/templateimg/bg-img/${n}.jpg" alt=""
                                     style="width:100%; height: 100%">
                            </div>
                        </div>
                        <div class="poca-music-content text-center">
                            <h2>${collezione.nome}</h2>
                            <noscript>
                                <style type="text/css">
                                    .pagecontainer {
                                        display: none;
                                    }
                                </style>
                                <div class="noscriptmsg">
                                    <a href="collezione?numero=${collezione.key}"
                                       class="btn poca-btn mt-10">Visualizza</a>
                                </div>
                            </noscript>
                        </div>
                    </div>
                </div>
                <#assign n++>
            </#list>
        </#if>
    </div>
    <hr>
    <!-- DISCHI -->
    <div class="row">
        <h3>Dischi:</h3>
        <#if !(dischi?size>0)>
            <p>Nessun disco trovato per ${keyword}...</p>
        </#if>
        <#if dischi??>
            <#list dettagliDischi as dettaglio>
                <#assign formato = "formato">
                <#list dischi as disco>
                    <#if (disco.key = dettaglio.disco.key && dettaglio.formato != formato)>
                        <#assign formato = dettaglio.formato>
                        <div class="col-12 col-md-4 col-lg-4 entre wow fadeInUp" data-wow-delay="0.2s"
                             style="cursor:pointer"
                             onclick="location.href='disco?numero=${disco.key}&collezione=${dettaglio.collezione.key}&formato=${dettaglio.formato}'">
                            <div class="poca-music-area style-2 d-flex align-items-center flex-wrap">
                                <div class="poca-music-thumbnail">
                                    <div class="portrait" style="height: 400px;  width:100%;">
                                        <img src="${dettaglio.imgCopertina}" alt=""
                                             style="width:100%; height: 100%">
                                    </div>

                                </div>
                                <div class="poca-music-content text-center">
                                    <span class="music-published-date mb-2">${disco.anno}</span>
                                    <h2>${disco.nome}</h2>
                                    <div class="music-meta-data">
                                        <p>By <a href="#" class="music-author">${disco.artista.nomeArte}</a>
                                    </div>
                                    <div class="likes-share-download d-flex align-items-center justify-content-between">
                                        <a href="#"><i class="" aria-hidden="true"></i>${dettaglio.formato}</a>
                                        <div>
                                            <a href="#"><i class="" aria-hidden="true"></i>${dettaglio.stato}</a>
                                        </div>
                                    </div>
                                    <noscript>
                                        <style type="text/css">
                                            .pagecontainer {
                                                display: none;
                                            }
                                        </style>
                                        <div class="noscriptmsg">
                                            <a href="disco?numero=${disco.key}&collezione=${dettaglio.collezione.key}&formato=${dettaglio.formato}"
                                               class="btn poca-btn mt-10">Visualizza</a>
                                        </div>
                                    </noscript>
                                </div>
                            </div>
                        </div>
                    </#if>
                </#list>
            </#list>
        </#if>
    </div>
    <hr>
    <!-- UTENTI -->
    <div class="row">
        <h3>Utenti:</h3>
        <#if !(utenti?size>0)>
            <p>Nessun utente trovato per ${keyword}...</p>
        </#if>
        <#if utenti??>
            <#list utenti as utente>
                <div class="col-6 col-md-4 col-lg-4">
                    <div class="card" style="cursor:pointer"
                         onclick="location.href='utente?id=${utente.key}'">
                        <div class="card-body text-center">
                            <h5 class="card-title text">${utente.nickname}</h5>
                        </div>
                        <noscript>
                            <style type="text/css">
                                .pagecontainer {
                                    display: none;
                                }
                            </style>
                            <div class="noscriptmsg">
                                <a class="btn poca-btn mt-10" href="utente?id=${utente.key}">Vai!</a>
                            </div>
                        </noscript>
                    </div>

                </div>
            </#list>
        </#if>
    </div>
</#if>