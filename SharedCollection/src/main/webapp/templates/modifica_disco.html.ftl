<link rel="stylesheet" href="style/default-assets/multi-select.css" xmlns="http://www.w3.org/1999/xhtml">

<!-- FORM MODIFICA DATI DISCO -->
<form action="modificaDisco" method="post">
    <div class="row">
        <div class="row">
            <#if error_??>
                <div class="alert alert-danger text-center" role="alert">
                    ${error_}
                </div>
            </#if>

            <h2 class="form-label text-danger">Informazioni disco:</h2>

            <div class="row">
                <h5 class="form-label">Titolo:</h5>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" name="titolo"
                           id="titolo" value="${disco.nome}"
                            <#if (userid != disco.creatore.key)>
                                readonly
                            </#if>
                    >
                    <input type="hidden" name="discoID" id="discoID"
                           value="${disco.key}">
                    <input type="hidden" name="collezioneID" id="collezioneID"
                           value="${collezione.key}">
                    <input type="hidden" name="formato" id="formato"
                           value="${infoDisco.formato}">
                </div>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-12 col-md-6 col-lg-6">
                <h5 class="form-label">Etichetta:</h5>
                <div class="input-group mb-3">
                    <input type="text" class="form-control" name="etichetta"
                           id="etichetta" value="${disco.etichetta}"
                            <#if (userid != disco.creatore.key)>
                                readonly
                            </#if>
                    >
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-6">
                <h5 class="form-label">Data uscita</h5>

                <div class="input-group mb-3">
                    <input type="date" class="form-control" name="anno"
                           id="anno" value='${disco.anno?iso_local}'
                            <#if (userid != disco.creatore.key)>
                                readonly
                            </#if>
                    >
                </div>
            </div>
        </div>
        <br>

        <br>
        <div class="row">
            <div class="col-12 col-md-6 col-lg-6">
                <h5 class="form-label">Numero copie :</h5>
                <div class="input-group mb-3">
                    <input type="number" class="form-control"
                           value="${infoDisco.numeroCopie}"
                           min="1" id="numeroCopie" name="numeroCopie">
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-6">
                <h5 class="form-label">Stato :</h5>
                <div class="input-group mb-3">
                    <select class="form-select" aria-label="Default select example"
                            id="stato" name="stato">
                        <#if (infoDisco.stato == "Ottimo")>
                            <option value="Ottimo" selected>Ottimo</option>
                        <#else>
                            <option value="Ottimo">Ottimo</option>
                        </#if>
                        <#if (infoDisco.stato == "Buono")>
                            <option value="Buono" selected>Buono</option>
                        <#else>
                            <option value="Buono">Buono</option>
                        </#if>
                        <#if (infoDisco.stato == "Discreto")>
                            <option value="Discreto" selected>Discreto</option>
                        <#else>
                            <option value="Discreto">Discreto</option>
                        </#if>
                        <#if (infoDisco.stato == "Sufficiente")>
                            <option value="Sufficiente" selected>Sufficiente</option>
                        <#else>
                            <option value="Sufficiente">Sufficiente</option>
                        </#if>
                        <#if (infoDisco.stato == "Pessimo")>
                            <option value="Pessimo" selected>Pessimo</option>
                        <#else>
                            <option value="Pessimo">Pessimo</option>
                        </#if>
                    </select>
                </div>
            </div>
        </div>
        <br>
        <div class="row">
            <h5 class="form-label">Barcode :</h5>
            <div class="input-group mb-3">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" id="barcode"
                           name="barcode"
                            <#if (infoDisco.barcode??)>
                        value="${infoDisco.barcode}"
                            </#if>>
                </div>
            </div>

        </div>
        <br>
        <div class="row text-end">
            <div class="col-9">

            </div>
            <div class="col-3">
                <input type="submit" class="btn btn-danger" id="updateDisco" name="updateDisco" value="Aggiorna">
            </div>
        </div>
    </div>
</form>
<hr>
<br>

<!-- VISUALIZZAZIONE DETTAGLI DEL DISCO -->
<!-- CAROSELLO IMMAGINI -->
<div class="row">
    <div class="col-12 col-md-3 col-lg-3">
        <#if error??>
            <div class="alert alert-danger text-center" role="alert">
                ${error}
            </div>
        </#if>
        <!-- Inizio Carousel -->
        <div id="carouselExampleIndicators" class="carousel slide" data-bs-ride="true">
            <div class="carousel-indicators">
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="0"
                        class="active" aria-current="true" aria-label="Slide 1"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="1"
                        aria-label="Slide 2"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="2"
                        aria-label="Slide 3"></button>
                <button type="button" data-bs-target="#carouselExampleIndicators" data-bs-slide-to="3"
                        aria-label="Slide 4"></button>
            </div>
            <div class="carousel-inner">
                <div class="carousel-item active">
                    <img src="${infoDisco.imgCopertina}" class="d-block w-100" alt="Immagine copertina">
                </div>
                <#if infoDisco.imgFronte??>
                    <div class="carousel-item">
                        <img src="${infoDisco.imgFronte}" class="d-block w-100" alt="Immagine fronte">
                    </div>
                </#if>
                <#if infoDisco.imgRetro??>
                    <div class="carousel-item">
                        <img src="${infoDisco.imgRetro}" class="d-block w-100" alt="Immagine retro">
                    </div>
                </#if>
                <#if infoDisco.imgLibretto??>
                    <div class="carousel-item">
                        <img src="${infoDisco.imgLibretto}" class="d-block w-100" alt="Immagine libretto">
                    </div>
                </#if>
            </div>
            <button class="carousel-control-prev" type="button" data-bs-target="#carouselExampleIndicators"
                    data-bs-slide="prev">
                <span class="carousel-control-prev-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Previous</span>
            </button>
            <button class="carousel-control-next" type="button" data-bs-target="#carouselExampleIndicators"
                    data-bs-slide="next">
                <span class="carousel-control-next-icon" aria-hidden="true"></span>
                <span class="visually-hidden">Next</span>
            </button>
        </div>
        <!-- Fine Carousel -->
        <hr>
        <br>

        <!-- FORM PER IL CARICAMENTO DELLE IMMAGINI -->
        <form action="modificaDisco" method="POST" enctype="multipart/form-data">
            <div class="row">
                <div class="input-group mb-3">
                    <label class="input-group-text" style="width: 83px;">Copertina</label>
                    <input type="file" id="imgCopertina" name="imgCopertina"
                           class="form-control"
                    >
                </div>
            </div>
            <br>
            <div class="row">
                <div class="input-group mb-3">
                    <label class="input-group-text" style="width: 83px;">Frontale</label>
                    <input type="file" id="imgFronte" name="imgFronte"
                           class="form-control"
                    >
                </div>
            </div>
            <br>
            <div class="row">
                <div class="input-group mb-3">
                    <label class="input-group-text" style="width: 83px;">Retro</label>
                    <input type="file" id="imgRetro" name="imgRetro"
                           class="form-control"
                    >
                </div>
            </div>
            <br>
            <div class="row">
                <div class="input-group mb-3">
                    <label class="input-group-text" style="width: 83px;">Libretto</label>
                    <input type="file" id="imgLibretto" name="imgLibretto"
                           class="form-control">
                </div>
            </div>
            <br>
            <input type="submit" class="btn btn-danger" name="caricaImg" id="caricaImg" value="Carica">
        </form>
    </div>

    <!-- LISTA DEI BRANI -->
    <div class="col-12 col-md-9 col-lg-9">
        <div class="table-responsive text-danger">
            <div class="row">
                <div class="col">
                    <h2 class="text-danger">Lista brani</h2>
                </div>
                <#if (userid == disco.creatore.key)>
                    <div class="col text-end">
                        <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                data-bs-target="#newBranoModal">
                            Nuova traccia
                        </button>
                    </div>
                </#if>
            </div>

            <#if !(listaBrani?size>0)>
                <a class="text-black">Nessun brano presente...</a>
                <br>
            </#if>
            <#if (listaBrani?size>0)>
                <#assign n = 0>
                <table class="table custom-table">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Nome</th>
                        <th scope="col">Durata</th>
                        <th scope="col">Autore</th>
                        <th scope="col">Genere</th>
                        <#if (userid == disco.creatore.key)>
                            <th scope="col"></th>
                            <th scope="col"></th>
                        </#if>
                    </tr>
                    </thead>
                    <tbody>
                    <#list listaBrani as brano>
                        <#assign n++>
                        <tr>
                            <th scope="row">${n}</th>
                            <td>${brano.canzone.nome}</td>
                            <td>${brano.canzone.durata?substring(3)}</td>
                            <td>
                                <#list listaArtisti as artisti>
                                    <#if (artisti.canzone.key = brano.canzone.key)>
                                        ${artisti.artista.nomeArte}
                                        <br>
                                    </#if>
                                </#list>
                            </td>
                            <td>
                                <#list listaGeneri as generi>
                                    <#if (generi.canzone.key = brano.canzone.key)>
                                        ${generi.genere.nome}
                                        <br>
                                    </#if>
                                </#list>
                            </td>
                            <#if (userid == disco.creatore.key)>
                                <td class="text-end">
                                    <button type="button" class="btn btn-secondary" data-bs-toggle="modal"
                                            data-bs-target="#editModal${brano.canzone.key}">
                                        <img src="images/templateimg/imgFont/pencil-fill.svg" alt="Bootstrap"
                                             width="16" height="16" class="text-light">
                                    </button>
                                </td>
                                <td>
                                    <form action="modificaDisco" method="post">
                                        <input type="hidden" id="branoID" name="branoID" value="${brano.key}">
                                        <button type="submit" class="btn btn-danger" id="deleteBrano"
                                                name="deleteBrano">
                                            <img
                                                    src="images/templateimg/imgFont/trash3-fill.svg" alt="elimina"
                                                    width="16"
                                                    height="16">
                                        </button>
                                    </form>
                                </td>
                            </#if>
                        </tr>
                    </#list>
                    </tbody>
                </table>
            </#if>
        </div>
    </div>
</div>

<!-- MODAL PROVVISORIO PER LA NUOVA TRACCIA -->
<div class="modal fade" id="newBranoModal" tabindex="-1" aria-hidden="true">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <form action="modificaDisco" method="POST">
                <div class="modal-header">
                    <h1 class="modal-title fs-5">Nuovo brano</h1>
                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                            aria-label="Close"></button>
                </div>
                <div class="modal-body">
                    <div class="row">
                        <h5 class="form-label">Nome</h5>

                        <div class="input-group">
                            <input type="text" class="form-control"
                                   name="nome"
                            >
                        </div>
                        <div class="form-text mb-3">
                            Inserisci il nome del brano.
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-12 col-md-6 col-lg-6">
                            <h5 class="form-label">Durata :</h5>
                            <div class="input-group">
                                <input type="text" class="form-control"
                                       name="durata"
                                       placeholder="00:00"
                                >
                            </div>
                            <div class="form-text">
                                Inserisci la durata del brano (es. 03:45).
                            </div>
                        </div>
                        <div class="col-12 col-md-6 col-lg-6">
                            <h5 class="form-label">Genere</h5>
                            <select class="selectpicker" multiple
                                    data-live-search="true"
                                    id="selectGeneri" name="selectGeneri">
                                <#list lista_generi as genere>
                                    <option value="${genere.key}"
                                    >${genere.nome}
                                    </option>
                                </#list>
                            </select>
                            <div class="form-text">
                                Seleziona i generi da aggiungere al brano.
                            </div>
                        </div>
                    </div>
                    <br>
                    <div class="row">
                        <div class="col-12 col-md-6 col-lg-6">
                            <h5 class="form-label">Artisti</h5>
                            <select class="selectpicker mb-3" data-live-search="true" disabled>
                                <option value="${disco.artista.key}" selected>${disco.artista.nomeArte}</option>
                            </select>
                            <input type="hidden" value="${disco.artista.key}" name="selectArtisti1">
                            <br>
                            <select class="selectpicker mb-3" data-live-search="true"
                                    id="selectArtisti2" name="selectArtisti2">
                                <option value="0" selected>Seleziona l'artista</option>
                                <#list lista_artisti as artista>
                                    <option value="${artista.key}"
                                            id="${artista.key}1">${artista.nomeArte}
                                    </option>
                                </#list>
                            </select>
                            <br>
                            <select class="selectpicker mb-3" data-live-search="true"
                                    id="selectArtisti3" name="selectArtisti3">
                                <option value="0" selected>Seleziona l'artista</option>
                                <#list lista_artisti as artista>
                                    <option value="${artista.key}"
                                            id="${artista.key}2">${artista.nomeArte}
                                    </option>
                                </#list>
                            </select>
                            <br>
                            <select class="selectpicker mb-3" data-live-search="true"
                                    id="selectArtisti4" name="selectArtisti4">
                                <option value="0" selected>Seleziona l'artista</option>
                                <#list lista_artisti as artista>
                                    <option value="${artista.key}"
                                            id="${artista.key}3">${artista.nomeArte}
                                    </option>
                                </#list>
                            </select>
                        </div>
                        <div class="col-12 col-md-6 col-lg-6">
                            <h5 class="form-label">Ruolo</h5>

                            <select class="selectpicker mb-3" aria-label="Default select example" disabled>
                                <option value="Entrambi" selected>Entrambi</option>
                            </select>
                            <input type="hidden" value="Entrambi" name="ruolo1">
                            <br>
                            <select class="selectpicker mb-3" aria-label="Default select example"
                                    id="ruolo2" name="ruolo2">
                                <option value="" selected>Seleziona il ruolo per l'artista</option>
                                <option value="Compositore">Compositore</option>
                                <option value="Musicista">Musicista</option>
                                <option value="Entrambi">Entrambi</option>
                            </select>
                            <br>
                            <select class="selectpicker mb-3" aria-label="Default select example"
                                    id="ruolo3" name="ruolo3">
                                <option value="" selected>Seleziona il ruolo per l'artista</option>
                                <option value="Compositore">Compositore</option>
                                <option value="Musicista">Musicista</option>
                                <option value="Entrambi">Entrambi</option>
                            </select>
                            <br>
                            <select class="selectpicker mb-3" aria-label="Default select example"
                                    id="ruolo4" name="ruolo4">
                                <option value="" selected>Seleziona il ruolo per l'artista</option>
                                <option value="Compositore">Compositore</option>
                                <option value="Musicista">Musicista</option>
                                <option value="Entrambi">Entrambi</option>
                            </select>
                        </div>
                        <div class="form-text">
                            Seleziona gli artisti da aggiungere al brano.<br>
                            Non hai trovato il tuo artista? <a
                                    href="newArtista?n=${disco.key}&c=${collezione.key}&f=${infoDisco.formato}">clicca
                                qui per aggiungerlo</a>
                        </div>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary"
                            data-bs-dismiss="modal">
                        Chiudi
                    </button>
                    <button type="submit" class="btn btn-danger" name="newBrano"
                            id="newBrano">Salva
                    </button>
                </div>
            </form>
        </div>
    </div>
</div>

<#if (listaBrani?size>0)>
    <#list listaBrani as brano>
        <!-- MODAL PER LA MODIFICA DEL BRANO -->
        <div class="modal fade" id="editModal${brano.canzone.key}" tabindex="-1"
             aria-hidden="true">
            <div class="modal-dialog modal-lg">
                <div class="modal-content">
                    <form action="modificaDisco" method="POST">
                        <div class="modal-header">
                            <h1 class="modal-title fs-5">Modifica
                                brano</h1>
                            <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                        </div>
                        <div class="modal-body">
                            <div class="row">
                                <h5 class="form-label">Nome</h5>
                                <div class="input-group">
                                    <input type="text" class="form-control"
                                           name="nome"
                                           value="${brano.canzone.nome}"
                                    >
                                    <input type="hidden" name="canzoneID" id="canzoneID" value="${brano.canzone.key}">
                                </div>
                                <div class="form-text mb-3">
                                    Inserisci il nome del brano.
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <div class="col-12 col-md-6 col-lg-6">
                                    <h5 class="form-label">Durata</h5>
                                    <div class="input-group">
                                        <input type="text" class="form-control"
                                               name="durata"
                                               value="${brano.canzone.durata?substring(3)}"
                                        >
                                    </div>
                                    <div class="form-text">
                                        Inserisci la durata del brano (es. 03:45).
                                    </div>
                                </div>
                                <div class="col-12 col-md-6 col-lg-6">
                                    <h5 class="form-label">Genere:</h5>

                                    <select class="selectpicker" multiple
                                            data-live-search="true"
                                            id="selectGeneri1" name="selectGeneri">
                                        <#list lista_generi as genere>
                                            <#assign selezionato = false>
                                            <#list listaGeneri as selectGenere>
                                                <#if (!selezionato && selectGenere.canzone.key == brano.canzone.key && genere.key == selectGenere.genere.key)>
                                                    <#assign selezionato = true>
                                                </#if>
                                            </#list>
                                            <#if selezionato>
                                                <option value="${genere.key}"
                                                        id="${genere.key}a" selected>${genere.nome}
                                                </option>
                                            <#else>
                                                <option value="${genere.key}"
                                                        id="${genere.key}a">${genere.nome}
                                                </option>
                                            </#if>
                                        </#list>
                                    </select>
                                    <div class="form-text">
                                        Seleziona i generi da aggiungere al brano.
                                    </div>
                                </div>
                            </div>
                            <br>
                            <div class="row">
                                <h5 class="form-label">Artisti</h5>
                                <select class="selectpicker" multiple data-live-search="true"
                                        id="selectArtisti" name="selectArtisti">
                                    <#list lista_artisti as artista>
                                        <#assign selezionato = false>
                                        <#list listaArtisti as artisti>
                                            <#if (!selezionato && artisti.canzone.key == brano.canzone.key && artista.key == artisti.artista.key)>
                                                <#assign selezionato = true>
                                            </#if>
                                        </#list>
                                        <#if selezionato>
                                            <option value="${artista.key}"
                                                    id="${artista.key}n"
                                                    selected>${artista.nomeArte}
                                            </option>
                                        <#else>
                                            <option value="${artista.key}"
                                                    id="${artista.key}n">${artista.nomeArte}
                                            </option>
                                        </#if>
                                    </#list>
                                </select>
                                <div class="form-text">
                                    Seleziona gli artisti da aggiungere al brano.<br>
                                    Non hai trovato il tuo artista? <a
                                            href="newArtista?n=${disco.key}&c=${collezione.key}&f=${infoDisco.formato}">clicca
                                        qui per aggiungerlo</a>
                                </div>
                            </div>
                        </div>
                        <div class="modal-footer">
                            <button type="button" class="btn btn-secondary"
                                    data-bs-dismiss="modal">
                                Chiudi
                            </button>
                            <button type="submit" class="btn btn-danger" name="updateBrano"
                                    id="updateBrano">Salva
                            </button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <!-- ***************** FINE MODAL ***************** -->
    </#list>
</#if>

<script src="js/jquerySelect.min.js"></script>
<script src="js/bootstrapSelect.bundle.min.js"></script>
<script src="js/bootstrap-select.min.js"></script>