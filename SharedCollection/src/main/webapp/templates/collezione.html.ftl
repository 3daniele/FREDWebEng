<div class="row">
    <h2>Dettagli collezione</h2>
    <hr>
    <div class="row">
        <div class="col-12 col-md-6 col-lg-6">
            <div class="row">
                <h3>Generi:</h3>
            </div>
                <#assign n=0>
                <#list generi as genere>
                    <p>${genere.nome}</p>
                    <div class="progress mb-2">
                        <div class="progress-bar bg-danger" role="progressbar" aria-label="Danger striped example" style="width: ${percentuali[n]}" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100">${percentuali[n]}</div>
                    </div>
                    <#assign n++>
                </#list>
        </div>
        <div class="col-12 col-md-6 col-lg-6">
            <div class="row">
                <h3>Artisti:</h3>
            </div>
            <#assign n=0>
            <#list artisti as artista>
                <p>${artista.nomeArte}</p>
                <div class="progress mb-2">
                    <div class="progress-bar bg-success" role="progressbar" aria-label="Danger striped example" style="width: ${percentualiA[n]}" aria-valuenow="100" aria-valuemin="0" aria-valuemax="100">${percentualiA[n]}</div>
                </div>
                <#assign n++>
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
    <#list dettagliDischi as dettaglio>
        <#list dischi as disco>
            <#if (disco.key = dettaglio.disco.key)>
                <div class="col-12 col-md-4 entre wow fadeInUp" data-wow-delay="0.2s"
                     style="cursor:pointer" onclick="location.href='disco?numero=${disco.key}&collezione=${collezione_key}'">
                    <div class="poca-music-area style-2 d-flex align-items-center flex-wrap">
                        <div class="poca-music-thumbnail">
                            <img src="${dettaglio.imgCopertina}" alt="">
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
                                    .pagecontainer {display:none;}
                                </style>
                                <div class="noscriptmsg">
                                    <a href="disco?numero=${disco.key}&collezione=${collezione_key}" class="btn poca-btn mt-10">Visualizza</a>
                                </div>
                            </noscript>
                        </div>
                    </div>
                </div>
            </#if>
        </#list>
    </#list>
</div>
