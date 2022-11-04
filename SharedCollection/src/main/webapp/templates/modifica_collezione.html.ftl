<link rel="stylesheet" href="style/default-assets/multi-select.css" xmlns="http://www.w3.org/1999/html">
<div class="row">
    <#if error??>
        <div class="alert alert-danger text-center" role="alert">
            ${error}
        </div>
    </#if>
    <!--MODIFICA COLLEZIONE-->
    <div class="col-12 col-md-6 col-lg-6">
        <form action="modificaCollezione" method="post">
            <h2 class="text-danger">Informazioni:</h2>
            <h5 class="form-label">Nuova Collezione</h5>

            <input type="text" class="form-control" id="nome" name="nome" value="${collezione.nome}">
            <div class="form-text">
                Modifica il nome della collezione.
            </div>
            <br>
            <h5 class="form-label">Condivisione</h5>

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
                <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}">
                Modifica il tipo di condivisione.
            </div>
            <div class="text-end">
                <input type="submit" class="btn btn-danger text-end" value="Salva" name="modifica_collezione">
            </div>
        </form>
    </div>

    <!--MODIFICA UTENTI AUTORIZZATI-->
    <div class="col-12 col-md-6 col-lg-6">
        <h2 class="text-danger">Condivisione:</h2>
        <h5 class="form-label">Condivisa con</h5>

        <ul>
            <#if (utenti_autorizzati?size > 0)>
                <#list utenti_autorizzati as utente>
                    <p>${utente.nickname}</p>
                </#list>
            <#else>
                <p>Attualmente la collezione non è stata condivisa con altri utenti</p>
            </#if>
        </ul>

        <form action="modificaCondivisione" method="get" class="text-end">
            <input type="hidden" name="collezioneId" id="collezioneId" value="${collezione_key}">
            <input type="submit" class="btn btn-danger" name="modifica" value="Gestisci condivisione">
        </form>
    </div>
</div>
<hr>
<br>
<div class="row">

    <!--MODIFICA DISCHI-->
    <div class="row">
        <div class="col-12 col-lg-8 col-md-8">
            <h2 class="text-danger">Lista Dischi:</h2>
        </div>
        <div class="col-12 col-lg-4 col-md-4 text-end">
            <a href="importaDisco?c=${collezione.key}" class="btn btn-danger">
                Importa disco
            </a>
            <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#newModal">
                Nuovo disco
            </button>
        </div>

        <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}">
    </div>
    <br>
    <div class="row">
        <#if !(dettagliDischi?size>0)>
            <div class="col-12 col-md-6 col-lg-6">
                Nessun disco presente...
            </div>
            <br>
        </#if>
        <#list dettagliDischi as dettaglio>
            <#assign formato = "formato">
            <#list dischi as disco>
                <#if (disco.key = dettaglio.disco.key && dettaglio.formato != formato)>
                    <#assign formato = dettaglio.formato>
                    <div class="col-12 col-md-4 entre wow fadeInUp" data-wow-delay="0.2s">
                        <form action="modificaCollezione" method="POST">
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
                                        <input type="hidden" name="listaDiscoID" id="listaDiscoID"
                                               value="${dettaglio.key}">
                                    </div>
                                    <br>
                                    <a href="disco?numero=${disco.key}&collezione=${collezione_key}&formato=${dettaglio.formato}"
                                       class="btn btn-success mt-10"><img src="images/templateimg/imgFont/arrow-up.svg"
                                                                          alt="Bootstrap" width="24" height="24"></a>
                                    <a href="modificaDisco?numero=${disco.key}&collezione=${collezione_key}&formato=${dettaglio.formato}"
                                       class="btn btn-secondary">
                                        <img src="images/templateimg/imgFont/pencil-fill.svg" alt="Bootstrap"
                                             width="24" height="24" class="text-light">
                                    </a>
                                    <button type="submit" class="btn btn-danger" value="Elimina" id="elimina_disco"
                                            name="elimina_disco">
                                        <img
                                                src="images/templateimg/imgFont/trash3-fill.svg" alt="elimina"
                                                width="24"
                                                height="24">
                                    </button>
                                </div>
                            </div>
                        </form>
                    </div>
                </#if>
            </#list>
        </#list>
    </div>
</div>

<!-- Modal nuovo disco -->
<div class="modal fade modal-lg" id="newModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="modificaCollezione" method="POST">
                <div class="modal-header">
                    <h5 class="modal-title text-danger" id="exampleModalLabel">Nuovo disco:</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <h5 class="form-label">Nome</h5>

                        <div class="input-group mb-3">
                            <input type="text" class="form-control" aria-label="Sizing example input"
                                   aria-describedby="inputGroup-sizing-default" id="nome" name="nome">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-12 col-lg-6 col-md-6">
                            <h5 class="form-label">Etichetta</h5>

                            <div class="input-group mb-3">
                                <input type="text" class="form-control" aria-label="Sizing example input"
                                       aria-describedby="inputGroup-sizing-default" id="etichetta" name="etichetta">
                            </div>
                        </div>
                        <div class="col-12 col-lg-6 col-md-6">
                            <h5 class="form-label">Data di uscita</h5>

                            <div class="input-group mb-3">
                                <input type="date" class="form-control" aria-label="Sizing example input"
                                       aria-describedby="inputGroup-sizing-default" id="anno" name="anno">
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <h5 class="form-label">Artista</h5>

                        <select class="selectpicker" data-live-search="true" id="artistaID" name="artistaID">
                            <option selected>Seleziona artista</option>
                            <#list lista_artisti as artista>
                                <option value="${artista.key}" id="${artista.key}">${artista.nomeArte}</option>
                            </#list>
                            <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}"/>
                        </select>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-12 col-md-4 col-lg-4">
                            <h5 class="form-label">Numero copie</h5>

                            <select class="form-select" aria-label="Default select example" id="numeroCopie"
                                    name="numeroCopie">
                                <option selected>Seleziona quantità</option>
                                <option value="1">1</option>
                                <option value="2">2</option>
                                <option value="3">3</option>
                                <option value="4">4</option>
                                <option value="5">5</option>
                                <option value="6">6</option>
                                <option value="7">7</option>
                                <option value="8">8</option>
                                <option value="9">9</option>
                                <option value="10">10</option>
                            </select>
                        </div>
                        <div class="col-12 col-md-4 col-lg-4">
                            <h5 class="form-label">Formato</h5>

                            <select class="form-select" aria-label="Default select example" id="formato" name="formato">
                                <option selected>Seleziona formato</option>
                                <option value="Vinile">Vinile</option>
                                <option value="CD">CD</option>
                                <option value="Digitale">Digitale</option>
                                <option value="Cassetta">Cassetta</option>
                                <option value="Altro">Altro</option>
                            </select>
                        </div>
                        <div class="col-12 col-md-4 col-lg-4">
                            <h5 class="form-label">Stato</h5>

                            <select class="form-select" aria-label="Default select example" id="stato" name="stato">
                                <option selected>Seleziona stato</option>
                                <option value="Ottimo">Ottimo</option>
                                <option value="Buono">Buono</option>
                                <option value="Discreto">Discreto</option>
                                <option value="Sufficiente">Sufficiente</option>
                                <option value="Pessimo">Pessimo</option>
                            </select>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <h5 class="form-label">Barcode</h5>

                        <div class="input-group mb-3">
                            <input type="text" class="form-control" aria-label="Sizing example input"
                                   aria-describedby="inputGroup-sizing-default" id="barcode" name="barcode">
                        </div>
                    </div>
                    <br>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                    <input type="submit" class="btn btn-danger text-end" value="Salva" name="nuovo_disco">
                </div>
            </form>
        </div>
    </div>
</div>

<script src="js/jquerySelect.min.js"></script>
<script src="js/bootstrapSelect.bundle.min.js"></script>
<script src="js/bootstrap-select.min.js"></script>