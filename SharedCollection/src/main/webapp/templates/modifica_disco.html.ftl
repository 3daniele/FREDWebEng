<link rel="stylesheet" href="style/default-assets/multi-select.css" xmlns="http://www.w3.org/1999/html">
<div class="container">
    <!-- Form iniziale -->
    <form action="modificaDisco" method="post">
        <div class="row">
            <div class="row">
                <label class="form-label text-danger">
                    <h2 class="text-danger">Informazioni disco:</h2>
                </label>
                <div class="row">
                    <label class="form-label">
                        <h5>Titolo:</h5>
                    </label>
                    <div class="input-group mb-3">
                        <input type="text" class="form-control" name="titolo"
                               id="titolo" value="${disco.nome}"
                                <#if (userid != disco.creatore.key)>
                                    readonly
                                </#if>
                        >
                        <input type="hidden" name="discoID" id="discoID"
                               value="${disco.key}"/>
                        <input type="hidden" name="collezioneID" id="collezioneID"
                               value="${collezione.key}"/>
                        <input type="hidden" name="formato" id="formato"
                               value="${infoDisco.formato}"/>
                    </div>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="col-12 col-md-6 col-lg-6">
                    <label class="form-label">
                        <h5>Etichetta:</h5>
                    </label>
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
                    <label class="form-label">
                        <h5>Data di uscita:</h5>
                    </label>
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
                    <label class="form-label">
                        <h5>Numero copie:</h5>
                    </label>
                    <div class="input-group mb-3">
                        <input type="number" class="form-control"
                               value="${infoDisco.numeroCopie}"
                               min="1" id="numeroCopie" name="numeroCopie">
                    </div>
                </div>
                <div class="col-12 col-md-6 col-lg-6">
                    <label class="form-label">
                        <h5>Stato:</h5>
                    </label>
                    <div class="input-group mb-3">
                        <select class="form-select" aria-label="Default select example"
                                id="stato" name="stato">
                            <option selected>Seleziona stato</option>
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
                <label class="form-label">
                    <h5>Barcode:</h5>
                </label>
                <div class="input-group mb-3">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control"
                               aria-label="Sizing example input"
                               aria-describedby="inputGroup-sizing-default" id="barcode"
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
            <!-- CARICAMENTO IMMAGINI -->
            <form action="modificaDisco" method="POST" enctype="multipart/form-data">
                <div class="row">
                    <div class="input-group mb-3">
                        <label class="input-group-text" for="inputGroupFile01" style="width: 83px;">Copertina</label>
                        <input type="file" class="form-control" id="imgCopertina" name="imgCopertina"
                               class="form-control form-control-lg"
                                <#if (infoDisco.imgCopertina??)>
                            value="${infoDisco.imgCopertina}"
                                </#if>>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="input-group mb-3">
                        <label class="input-group-text" for="inputGroupFile01" style="width: 83px;">Frontale</label>
                        <input type="file" class="form-control" id="imgFronte" name="imgFronte"
                               class="form-control form-control-lg"
                                <#if (infoDisco.imgFronte??)>
                            value="${infoDisco.imgFronte}"
                                </#if>>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="input-group mb-3">
                        <label class="input-group-text" for="inputGroupFile01" style="width: 83px;">Retro</label>
                        <input type="file" class="form-control" id="imgRetro" name="imgRetro"
                               class="form-control form-control-lg"
                                <#if (infoDisco.imgRetro??)>
                            value="${infoDisco.imgRetro}"
                                </#if>>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="input-group mb-3">
                        <label class="input-group-text" for="inputGroupFile01" style="width: 83px;">Libretto</label>
                        <input type="file" class="form-control" id="imgLibretto" name="imgLibretto"
                               class="form-control form-control-lg"
                                <#if (infoDisco.imgLibretto??)>
                            value="${infoDisco.imgLibretto}"
                                </#if>>
                    </div>
                </div>
                <br>
                <input type="submit" class="btn btn-danger" name="caricaImg" id="caricaImg" value="Carica">
            </form>
        </div>

        <!-- TABLE lista brani -->
        <div class="col-12 col-md-9 col-lg-9">
            <div class="table-responsive text-danger">
                <div class="row">
                    <div class="col">
                        <h2 class="text-danger">Lista brani</h2>
                    </div>
                    <#if (userid == disco.creatore.key)>
                        <div class="col text-end">
                            <button type="button" class="btn btn-danger" data-bs-toggle="modal"
                                    data-bs-target="#exampleModal">
                                Nuova traccia
                            </button>
                        </div>
                    </#if>

                </div>

                <#if (listaBrani?size>0)>
                    <#if (userid == disco.creatore.key)>
                        <#assign n = 0>
                        <table class="table custom-table">

                            <thead class="thead-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Durata</th>
                                <th scope="col">Autore</th>
                                <th scope="col">Genere</th>
                                <th scope="col"></th>
                                <th scope="col"></th>
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
                                    <td class="text-end">
                                        <button type="button" class="btn btn-secondary" data-bs-toggle="modal"
                                                data-bs-target="#editModal">
                                            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                                 fill="currentColor" class="bi bi-pencil-fill" viewBox="0 0 16 16">
                                                <path d="M12.854.146a.5.5 0 0 0-.707 0L10.5 1.793 14.207 5.5l1.647-1.646a.5.5 0 0 0 0-.708l-3-3zm.646 6.061L9.793 2.5 3.293 9H3.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.5h.5a.5.5 0 0 1 .5.5v.207l6.5-6.5zm-7.468 7.468A.5.5 0 0 1 6 13.5V13h-.5a.5.5 0 0 1-.5-.5V12h-.5a.5.5 0 0 1-.5-.5V11h-.5a.5.5 0 0 1-.5-.5V10h-.5a.499.499 0 0 1-.175-.032l-.179.178a.5.5 0 0 0-.11.168l-2 5a.5.5 0 0 0 .65.65l5-2a.5.5 0 0 0 .168-.11l.178-.178z"/>
                                            </svg>
                                        </button>
                                    </td>
                                    <td>
                                        <button type="submit" class="btn btn-danger">
                                            <svg
                                                    xmlns="http://www.w3.org/2000/svg" width="16" height="16"
                                                    fill="currentColor" class="bi bi-trash3-fill"
                                                    viewBox="0 0 16 16">
                                                <path d="M11 1.5v1h3.5a.5.5 0 0 1 0 1h-.538l-.853 10.66A2 2 0 0 1 11.115 16h-6.23a2 2 0 0 1-1.994-1.84L2.038 3.5H1.5a.5.5 0 0 1 0-1H5v-1A1.5 1.5 0 0 1 6.5 0h3A1.5 1.5 0 0 1 11 1.5Zm-5 0v1h4v-1a.5.5 0 0 0-.5-.5h-3a.5.5 0 0 0-.5.5ZM4.5 5.029l.5 8.5a.5.5 0 1 0 .998-.06l-.5-8.5a.5.5 0 1 0-.998.06Zm6.53-.528a.5.5 0 0 0-.528.47l-.5 8.5a.5.5 0 0 0 .998.058l.5-8.5a.5.5 0 0 0-.47-.528ZM8 4.5a.5.5 0 0 0-.5.5v8.5a.5.5 0 0 0 1 0V5a.5.5 0 0 0-.5-.5Z"/>
                                            </svg>
                                        </button>
                                    </td>
                                </tr>
                                <!-- Modal modifica brano -->
                                <div class="modal fade" id="editModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                                     aria-hidden="true">
                                    <div class="modal-dialog">
                                        <div class="modal-content">
                                            <form action="modificaDisco" method="POST">
                                                <div class="modal-header">
                                                    <h1 class="modal-title fs-5" id="exampleModalLabel">Modifica
                                                        brano</h1>
                                                    <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                            aria-label="Close"></button>
                                                </div>
                                                <div class="modal-body">
                                                    <div class="row">
                                                        <label class="form-label">
                                                            <h5>Nome</h5>
                                                        </label>
                                                        <div class="input-group">
                                                            <input type="text" class="form-control"
                                                                   aria-label="Sizing example input"
                                                                   aria-describedby="inputGroup-sizing-default"
                                                                   id="nome"
                                                                   name="nome"
                                                                   value="${brano.canzone.nome}"
                                                            >
                                                        </div>
                                                        <div class="form-text mb-3">
                                                            Inserisci il nome del brano.
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="row">
                                                        <div class="col-12 col-md-6 col-lg-6">
                                                            <label class="form-label">
                                                                <h5>Durata</h5>
                                                            </label>
                                                            <div class="input-group">
                                                                <input type="text" class="form-control"
                                                                       aria-label="Sizing example input"
                                                                       aria-describedby="inputGroup-sizing-default"
                                                                       id="durata"
                                                                       name="durata"
                                                                       value="${brano.canzone.durata?substring(3)}"
                                                                >
                                                            </div>
                                                            <div class="form-text">
                                                                Inserisci la durata del brano (es. 03:45).
                                                            </div>
                                                        </div>
                                                        <div class="col-12 col-md-6 col-lg-6">
                                                            <label class="form-label">
                                                                <h5>Genere</h5>
                                                            </label>
                                                            <select class="selectpicker" multiple
                                                                    data-live-search="true"
                                                                    id="genereID" name="genereID">
                                                                <#list lista_generi as genere>
                                                                    <#list listaGeneri as selectGenere>
                                                                        <#if (selectGenere.canzone.key = brano.canzone.key && genere.nome==selectGenere.genere.nome)>
                                                                            <option value="${genere.key}"
                                                                                    id="${genere.key}"
                                                                                    selected>${genere.nome}
                                                                            </option>
                                                                        <#else>
                                                                            <option value="${genere.key}"
                                                                                    id="${genere.key}">${genere.nome}
                                                                            </option>
                                                                        </#if>
                                                                    </#list>
                                                                </#list>
                                                            </select>
                                                            <div class="form-text">
                                                                Seleziona i generi da aggiungere al brano.
                                                            </div>
                                                        </div>
                                                    </div>
                                                    <br>
                                                    <div class="row">
                                                        <label class="form-label">
                                                            <h5>Artisti</h5>
                                                        </label>
                                                        <select class="selectpicker" multiple data-live-search="true"
                                                                id="artistaID" name="artistaID">
                                                            <#list lista_artisti as artista>
                                                                <#if listaArtisti?seq_contains(artista)>
                                                                    <option value="${artista.key}"
                                                                            id="${artista.key}"
                                                                            selected>${artista.nomeArte}</option>
                                                                <#else>
                                                                    <option value="${artista.key}"
                                                                            id="${artista.key}">${artista.nomeArte}</option>
                                                                </#if>
                                                            </#list>
                                                        </select>
                                                        <div class="form-text">
                                                            Seleziona gli artisti da aggiungere al brano.
                                                        </div>
                                                    </div>
                                                </div>
                                                <div class="modal-footer">
                                                    <button type="button" class="btn btn-secondary"
                                                            data-bs-dismiss="modal">
                                                        Chiudi
                                                    </button>
                                                    <button type="button" class="btn btn-danger" name="updateBrano"
                                                            id="updateBrano">Salva
                                                    </button>
                                                </div>
                                            </form>
                                        </div>
                                    </div>
                                </div>
                            </#list>
                            </tbody>
                        </table>

                    <#else>
                        <#assign n = 0>
                        <table class="table custom-table">
                            <thead class="thead-dark">
                            <tr>
                                <th scope="col">#</th>
                                <th scope="col">Nome</th>
                                <th scope="col">Durata</th>
                                <th scope="col">Autore</th>
                                <th scope="col">Genere</th>
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
                                </tr>
                            </#list>
                            </tbody>
                        </table>
                    </#if>
                </#if>
            </div>
        </div>
    </div>
</div>


<!-- Modal -->
<div class="modal fade" id="exampleModal" tabindex="-1" aria-labelledby="exampleModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h1 class="modal-title fs-5" id="exampleModalLabel">Modal title</h1>
                <button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
            </div>
            <div class="modal-body">
                ...
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
                <button type="button" class="btn btn-primary">Save changes</button>
            </div>
        </div>
    </div>
</div>

<script src="js/jquerySelect.min.js"></script>
<script src="js/bootstrapSelect.bundle.min.js"></script>
<script src="js/bootstrap-select.min.js"></script>