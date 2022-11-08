<div class="row">
    <div class="col-12 col-md-6 col-lg-6">
        <h2>Dettagli collezione</h2>
    </div>
    <#if userid??>
        <#if (userid == collezione.utente.key)>
            <div class="col-12 col-md-6 col-lg- text-end">
                <form action="collezioni" method="post">
                    <a href="modificaCollezione?numero=${collezione.key}" class="btn btn-secondary"
                       role="button">
                        <img src="images/templateimg/imgFont/pencil-fill.svg" alt="modifica"
                             width="24" height="24" class="text-light" >
                    </a>
                    <input type="hidden" id="collezioneID" name="collezioneID" value="${collezione.key}">
                    <button type="submit" class="btn btn-danger" id="eliminaCollezione"
                            name="eliminaCollezione">
                        <img src="images/templateimg/imgFont/trash3-fill.svg"
                             alt="modifica"
                             width="24" height="24" class="text-light"
                             >
                    </button>
                </form>
            </div>
        </#if>
    </#if>
    <hr>
    <div class="row">
        <div class="col-12 col-md-6 col-lg-6">
            <div class="row">
                <h3>Generi:</h3>
            </div>
            <#if !(generi?size>0)>
                Statistiche non disponibili...
                <br>
            </#if>
            <#assign n=0>
            <#list generi as genere>
                <#if (n<3)>
                    <p>${genere.nome}</p>
                    <div class="progress mb-2">
                        <div class="progress-bar bg-danger" role="progressbar" aria-label="Danger striped example"
                             style="width: ${percentuali[n]}" aria-valuenow="100" aria-valuemin="0"
                             aria-valuemax="100">${percentuali[n]}</div>
                    </div>
                    <#assign n++>
                </#if>
            </#list>
        </div>
        <div class="col-12 col-md-6 col-lg-6">
            <div class="row">
                <h3>Artisti:</h3>
            </div>
            <#if !(artisti?size>0)>
                Statistiche non disponibili...
                <br>
            </#if>
            <#assign n=0>
            <#list artisti as artista>
                <#if (n<3)>
                    <p>${artista.nomeArte}</p>
                    <div class="progress mb-2">
                        <div class="progress-bar bg-success" role="progressbar" aria-label="Danger striped example"
                             style="width: ${percentualiA[n]}" aria-valuenow="100" aria-valuemin="0"
                             aria-valuemax="100">${percentualiA[n]}</div>
                    </div>
                    <#assign n++>
                </#if>
            </#list>
        </div>
    </div>
</div>
<hr>
<div class="row">
    <h2>Lista dischi</h2>
    <hr>
</div>
<div class="row">
    <#if !(dettagliDischi?size>0)>
        <div class="col-12 col-md-6 col-lg-6">
            Nessun disco presente...
            <br>
        </div>
    </#if>
    <#list dettagliDischi as dettaglio>
        <#assign formato = "formato">
        <#list dischi as disco>
            <#if (disco.key = dettaglio.disco.key && dettaglio.formato != formato)>
                <#assign formato = dettaglio.formato>
                <div class="col-12 col-md-4 entre wow fadeInUp" data-wow-delay="0.2s"
                     style="cursor:pointer"
                     onclick="location.href='disco?numero=${disco.key}&collezione=${collezione_key}&formato=${dettaglio.formato}'">
                    <div class="poca-music-area style-2 d-flex align-items-center flex-wrap">
                        <div class="poca-music-thumbnail">
                            <div class="portrait" style="height: 400px;  width:100%;">
                                <img src="${dettaglio.imgCopertina}" alt="" style="width:100%; height: 100%">
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
                                    <a href="disco?numero=${disco.key}&collezione=${collezione_key}&formato=${dettaglio.formato}"
                                       class="btn poca-btn mt-10">Visualizza</a>
                                </div>
                            </noscript>
                            <#if userid??>
                                <#if (userid == collezione.utente.key)>
                                    <br>
                                    <form action="collezione" method="POST">
                                        <a href="modificaDisco?numero=${disco.key}&collezione=${collezione_key}&formato=${dettaglio.formato}"
                                           class="btn btn-secondary">
                                            <img src="images/templateimg/imgFont/pencil-fill.svg" alt="Bootstrap"
                                                 width="24" height="24" class="text-light" >
                                        </a>
                                        <input type="hidden" name="listaDiscoID" id="listaDiscoID"
                                               value="${dettaglio.key}">
                                        <button type="submit" class="btn btn-danger" value="Elimina" id="elimina_disco"
                                                name="elimina_disco">
                                            <img
                                                    src="images/templateimg/imgFont/trash3-fill.svg" alt="elimina"
                                                    width="24"
                                                    height="24">
                                        </button>
                                    </form>
                                </#if>
                            </#if>
                        </div>
                    </div>
                </div>
            </#if>
        </#list>
    </#list>
</div>
