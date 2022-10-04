<link rel="stylesheet" href="style/default-assets/multi-select.css" xmlns="http://www.w3.org/1999/html">
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
            <noscript>
                <style type="text/css">
                    .pagecontainer {
                        display: none;
                    }
                </style>
                <div class="noscriptmsg">
                    Impossibile modificare la condivisione, attiva il javascript!
                </div>
            </noscript>
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
        <div class="col-12 col-lg-8 col-md-8">
            <h2 class="text-danger">Lista Dischi:</h2>
        </div>
        <div class="col-12 col-lg-4 col-md-4 text-end">
            <button type="button" class="btn btn-danger" data-bs-toggle="modal" data-bs-target="#importModal">
                Importa disco
            </button>
            <a href="nuovoDisco?collezione=${collezione.key}" class="btn btn-danger">Nuovo disco</a>
        </div>

        <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}"/>
    </div>
    <br>
    <div class="row">
        <#list dettagliDischi as dettaglio>
            <#assign formato = "formato">
            <#list dischi as disco>
                <#if (disco.key = dettaglio.disco.key && dettaglio.formato != formato)>
                    <#assign formato = dettaglio.formato>
                    <div class="col-12 col-md-4 entre wow fadeInUp" data-wow-delay="0.2s">
                        <form action="modificaCollezione" method="POST">
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
                                        <input type="hidden" name="listaDiscoID" id="listaDiscoID"
                                               value="${dettaglio.key}"/>
                                    </div>
                                    <br>
                                    <a href="disco?numero=${disco.key}&collezione=${collezione_key}&formato=${dettaglio.formato}"
                                       class="btn poca-btn mt-10">Visualizza</a>
                                    <br><br>
                                    <button type="button" class="btn btn-secondary" data-bs-toggle="modal" data-bs-target="#modificaDiscoModal${disco.key}">
                                        Modifica
                                    </button>
                                    <input type="submit" class="btn btn-danger" value="Elimina" id="elimina_disco" name="elimina_disco">
                                </div>
                            </div>
                        </form>
                    </div>
                    <!-- Modal modifica disco -->
                    <div class="modal fade modal-lg" id="modificaDiscoModal${disco.key}" tabindex="-1"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-content">
                                <form action="modificaCollezione" method="POST" enctype="multipart/form-data">
                                    <div class="modal-header">
                                        <h5 class="modal-title text-danger" id="exampleModalLabel">Modifica disco:</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        <div class="row">
                                            <label class="form-label">
                                                <h5>Dischi</h5>
                                            </label>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control" name="${disco.key}"
                                                       id="${disco.key}" value="${disco.nome}" readonly>
                                                <input type="hidden" name="discoID" id="discoID"
                                                       value="${disco.key}"/>
                                                <input type="hidden" name="collezioneID" id="collezioneID"
                                                       value="${collezione.key}"/>
                                                <input type="hidden" name="formato" id="formato"
                                                       value="${dettaglio.formato}"/>
                                            </div>
                                        </div>
                                        <br>
                                        <div class="row">
                                            <div class="col-12 col-md-6 col-lg-6">
                                                <label class="form-label">
                                                    <h5>Numero copie</h5>
                                                </label>
                                                <input type="number" class="form-control"
                                                       value="${dettaglio.numeroCopie}"
                                                       min="1" id="numeroCopie" name="numeroCopie">
                                            </div>
                                            <div class="col-12 col-md-6 col-lg-6">
                                                <label class="form-label">
                                                    <h5>Stato</h5>
                                                </label>
                                                <select class="form-select" aria-label="Default select example"
                                                        id="stato" name="stato">
                                                    <option selected>Seleziona stato</option>
                                                    <#if (dettaglio.stato == "Ottimo")>
                                                        <option value="Ottimo" selected>Ottimo</option>
                                                    <#else>
                                                        <option value="Ottimo">Ottimo</option>
                                                    </#if>
                                                    <#if (dettaglio.stato == "Buono")>
                                                        <option value="Buono" selected>Buono</option>
                                                    <#else>
                                                        <option value="Buono">Buono</option>
                                                    </#if>
                                                    <#if (dettaglio.stato == "Discreto")>
                                                        <option value="Discreto" selected>Discreto</option>
                                                    <#else>
                                                        <option value="Discreto">Discreto</option>
                                                    </#if>
                                                    <#if (dettaglio.stato == "Sufficiente")>
                                                        <option value="Sufficiente" selected>Sufficiente</option>
                                                    <#else>
                                                        <option value="Sufficiente">Sufficiente</option>
                                                    </#if>
                                                    <#if (dettaglio.stato == "Pessimo")>
                                                        <option value="Pessimo" selected>Pessimo</option>
                                                    <#else>
                                                        <option value="Pessimo">Pessimo</option>
                                                    </#if>
                                                </select>
                                            </div>
                                        </div>
                                        <br>
                                        <div class="row">
                                            <label class="form-label">
                                                <h5>Barcode</h5>
                                            </label>
                                            <div class="input-group mb-3">
                                                <input type="text" class="form-control"
                                                       aria-label="Sizing example input"
                                                       aria-describedby="inputGroup-sizing-default" id="barcode"
                                                       name="barcode"
                                                        <#if (dettaglio.barcode??)>
                                                    value="${dettaglio.barcode}"
                                                        </#if>>
                                            </div>
                                        </div>
                                        <br>
                                        <div class="row">
                                            <label class="form-label">
                                                <h5>Immagine di copertina:</h5>
                                            </label>
                                            <div class="input-group mb-3">
                                                <label class="input-group-text" for="inputGroupFile01">Upload</label>
                                                <input type="file" class="form-control" id="imgCopertina"
                                                       name="imgCopertina"
                                                        <#if (dettaglio.imgCopertina??)>
                                                    value="${dettaglio.imgCopertina}"
                                                        </#if>>
                                            </div>
                                        </div>
                                        <br>
                                        <div class="row">
                                            <label class="form-label">
                                                <h5>Immagine frontale:</h5>
                                            </label>
                                            <div class="input-group mb-3">
                                                <label class="input-group-text" for="inputGroupFile01">Upload</label>
                                                <input type="file" class="form-control" id="imgFronte" name="imgFronte">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="row">
                                            <label class="form-label">
                                                <h5>Immagine retro:</h5>
                                            </label>
                                            <div class="input-group mb-3">
                                                <label class="input-group-text" for="inputGroupFile01">Upload</label>
                                                <input type="file" class="form-control" id="imgRetro" name="imgRetro">
                                            </div>
                                        </div>
                                        <br>
                                        <div class="row">
                                            <label class="form-label">
                                                <h5>Immagine libretto:</h5>
                                            </label>
                                            <div class="input-group mb-3">
                                                <label class="input-group-text" for="inputGroupFile01">Upload</label>
                                                <input type="file" class="form-control" id="imgLibretto"
                                                       name="imgLibretto">
                                            </div>
                                        </div>
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi
                                        </button>
                                        <input type="submit" class="btn btn-danger text-end" value="Salva"
                                               name="modifica_disco">
                                    </div>
                                </form>
                            </div>
                        </div>
                    </div>
                </#if>
            </#list>
        </#list>
    </div>
</div>


<!-- Modal importa disco -->
<div class="modal fade modal-lg" id="importModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <form action="modificaCollezione" method="POST" enctype="multipart/form-data">
                <div class="modal-header">
                    <h5 class="modal-title text-danger" id="exampleModalLabel">Importa disco:</h5>
                    <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <label class="form-label">
                            <h5>Dischi</h5>
                        </label>
                        <select class="selectpicker" data-live-search="true" id="discoID" name="discoID">
                            <option selected>Seleziona disco</option>
                            <#list lista_dischi as disco>
                                <option value="${disco.key}" id="${disco.key}">${disco.nome}</option>
                            </#list>
                            <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}"/>
                        </select>
                        <div class="form-text">
                            Seleziona i dischi da aggiungere alla tua collezione.
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-12 col-md-4 col-lg-4">
                            <label class="form-label">
                                <h5>Numero copie</h5>
                            </label>
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
                            <label class="form-label">
                                <h5>Formato</h5>
                            </label>
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
                            <label class="form-label">
                                <h5>Stato</h5>
                            </label>
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
                        <label class="form-label">
                            <h5>Barcode</h5>
                        </label>
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" aria-label="Sizing example input"
                                   aria-describedby="inputGroup-sizing-default" id="barcode" name="barcode">
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <label class="form-label">
                            <h5>Immagine di copertina:</h5>
                        </label>
                        <div class="input-group mb-3">
                            <label class="input-group-text" for="inputGroupFile01">Upload</label>
                            <input type="file" class="form-control" id="imgCopertina" name="imgCopertina">
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <label class="form-label">
                            <h5>Immagine frontale:</h5>
                        </label>
                        <div class="input-group mb-3">
                            <label class="input-group-text" for="inputGroupFile01">Upload</label>
                            <input type="file" class="form-control" id="imgFronte" name="imgFronte">
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <label class="form-label">
                            <h5>Immagine retro:</h5>
                        </label>
                        <div class="input-group mb-3">
                            <label class="input-group-text" for="inputGroupFile01">Upload</label>
                            <input type="file" class="form-control" id="imgRetro" name="imgRetro">
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <label class="form-label">
                            <h5>Immagine libretto:</h5>
                        </label>
                        <div class="input-group mb-3">
                            <label class="input-group-text" for="inputGroupFile01">Upload</label>
                            <input type="file" class="form-control" id="imgLibretto" name="imgLibretto">
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Chiudi</button>
                    <input type="submit" class="btn btn-danger text-end" value="Salva" name="modifica_disco">
                </div>
            </form>
        </div>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>
