<form action="search" method="post">
    <div class="row">
        <div class="input-group mb-3">
            <span class="input-group-text" id="basic-addon3">Cerca:</span>
            <#if keyword??>
                <input type="text" class="form-control" id="campo_" name="campo_" aria-describedby="basic-addon3"
                       value="${keyword}">
            <#else>
                <input type="text" class="form-control" id="campo_" name="campo_" aria-describedby="basic-addon3">
            </#if>
        </div>
    </div>

    <br>
    <div class="row">
        <div class="col-lg-3 col-md-3">
        </div>
        <div class="col-12 col-lg-5 col-md-5">
            <input type="submit" class="btn btn-danger" id="filtra" name="filtra" value="Cerca" style="width: 100%">
        </div>
        <div class="col-lg-3 col-md-3">

        </div>
    </div>
</form>

<#if keyword??>
    <!-- COLLEZIONI -->
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
                            <span class="music-published-date mb-2">${collezione.dataCreazione}</span>
                            <h2>${collezione.nome}</h2>
                            <div class="music-meta-data">
                                <p>By <a href="utente?id=${collezione.utente.key}" class="music-author">
                                        ${collezione.utente.nickname}</a></p>
                            </div>
                            <div class="likes-share-download d-flex align-items-center justify-content-between">
                                <i class="" aria-hidden="true">${collezione.condivisione?upper_case}</i>
                                <div>
                                    <i class="" aria-hidden="true">
                                        <#if collezione.utente.key = user_key>
                                            PERSONALE
                                        <#elseif collezione.condivisione="privata">
                                            CONDIVISA CON TE
                                        </#if>
                                    </i>
                                </div>
                            </div>
                            <noscript>
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
                            <noscript>
                                <div class="noscriptmsg">
                                    <a href="utente?id=${utente.key}"
                                       class="btn poca-btn mt-10">Visualizza</a>
                                </div>
                            </noscript>
                        </div>
                    </div>
                </div>
            </#list>
        </#if>
    </div>
</#if>