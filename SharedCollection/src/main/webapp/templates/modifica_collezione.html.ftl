<link rel="stylesheet" href="style/default-assets/multi-select.css">
<div class="row">

    <!--MODIFICA COLLEZIONE-->
    <div class="col-12 col-md-6 col-lg-6">
        <form action="modificaCollezione" method="post">
            <h2 class="text-danger">Informazioni:</h2>
            <label class="form-label">
                <h5>Nome collezione</h5>
            </label>
            <input type="text" class="form-control" id="nome" name="nome" value="${collezione.nome}"/>
            <div class="form-text">
                Modifica il nome della collezione.
            </div>
            <br>
            <label class="form-label">
                <h5>Condivisione</h5>
            </label>
            <select id="condivisione" name="condivisione" class="form-select">
                <#if (collezione.condivisione == "privata")>
                    <option value="privata" selected>Privata</option>
                    <option value="pubblica">Pubblica</option>
                <#else>
                    <option value="privata">Privata</option>
                    <option value="pubblica" selected>Pubblica</option>
                </#if>
            </select>
            <div class="form-text">
                <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}"/>
                Modifica il tipo di condivisione.
            </div>
            <div class="text-end">
                <input type="submit" class="btn btn-danger text-end" value="Salva" name="modifica_collezione">
            </div>
        </form>
    </div>

    <!--MODIFICA UTENTI AUTORIZZATI-->
    <div class="col-12 col-md-6 col-lg-6">
        <form action="modificaCollezione" method="post">
            <h2 class="text-danger">Condivisione:</h2>
            <label class="form-label">
                <h5>Utenti</h5>
            </label>
            <select class="selectpicker" multiple data-live-search="true" id="utentiS" name="utentiS">
                <#list lista_utenti as utente>
                    <#if (utente.key != userid)>
                        <#if utenti_autorizzati?seq_contains(utente.key)>
                            <option value="${utente.key}" id="${utente.key}" selected>${utente.nickname}</option>
                        <#else>
                            <option value="${utente.key}" id="${utente.key}">${utente.nickname}</option>
                        </#if>
                    </#if>
                </#list>
            </select>
            <div class="form-text">
                <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}"/>
                Seleziona gli utenti con cui condividere la tua collezione.
            </div>
            <br><br><br><br><br><br>
            <div class="text-end">
                <input type="submit" class="btn btn-danger" value="Salva" id="modificaUtenti" name="modificaUtenti">
            </div>
        </form>
    </div>
</div>
<hr>
<br>
<div class="row">

    <!--MODIFICA DISCHI-->
    <div class="row">
        <h2 class="text-danger">Lista Dischi:</h2>
        <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}"/>
    </div>
    <div class="row">
        <#list dettagliDischi as dettaglio>
            <#list dischi as disco>
                <#if (disco.key = dettaglio.disco.key)>
                    <div class="col-12 col-md-4 entre wow fadeInUp" data-wow-delay="0.2s"
                         style="cursor:pointer"
                         onclick="location.href='disco?numero=${disco.key}&collezione=${collezione_key}'">
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
                                        .pagecontainer {
                                            display: none;
                                        }
                                    </style>
                                    <div class="noscriptmsg">
                                        <a href="disco?numero=${disco.key}&collezione=${collezione_key}"
                                           class="btn poca-btn mt-10">Visualizza</a>
                                    </div>
                                </noscript>
                            </div>
                        </div>
                    </div>
                </#if>
            </#list>
        </#list>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>
