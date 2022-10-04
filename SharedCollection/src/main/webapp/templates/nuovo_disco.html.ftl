<div class="container">

    <div class="row">
        <div class="row">
            <label class="form-label text-danger">
                <h2 class="text-danger">Informazioni disco:</h2>
            </label>
            <div class="row">
                <div class="input-group mb-3">
                    <input type="text" class="form-control" name="${disco.key}"
                           id="${disco.key}" value="${disco.nome}" readonly>
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
                <div class="input-group mb-3">
                    <label class="form-label">
                        <h5>Etichetta:</h5>
                    </label>
                    <input type="text" class="form-control" name="${disco.key}"
                           id="${disco.key}" value="${disco.etichetta}" readonly>
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-6">
                <div class="input-group mb-3">
                    <label class="form-label">
                        <h5>Data di uscita:</h5>
                    </label>
                    <input type="text" class="form-control" name="${disco.key}"
                           id="${disco.key}" value="${disco.anno}">
                </div>
            </div>
        </div>
        <br>
        <div class="row">
            <div class="col-12 col-md-6 col-lg-6">
                <div class="input-group mb-3">
                    <label class="form-label">
                        <h5>Numero copie</h5>
                    </label>
                    <input type="number" class="form-control"
                           value="${infoDisco.numeroCopie}"
                           min="1" id="numeroCopie" name="numeroCopie">
                </div>
            </div>
            <div class="col-12 col-md-6 col-lg-6">
                <div class="input-group mb-3">
                    <label class="form-label">
                        <h5>Stato</h5>
                    </label>
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
            <div class="input-group mb-3">
<label class="form-label">
                <h5>Barcode</h5>
            </label>
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
    </div>
    <hr>
    <br>
    <div class="row">
        <div class="col-12 col-md-4 col-lg-4">
            <div class="col-12 col-md-11 col-lg-11">
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
            </div>
            <hr>
            <br>
            <div class="row">
                <div class="input-group mb-3">
                    <label class="input-group-text" for="inputGroupFile01" style="width: 83px;">Copertina</label>
                    <input type="file" class="form-control" id="imgCopertina"
                           name="imgCopertina"
                            <#if (infoDisco.imgCopertina??)>
                        value="${infoDisco.imgCopertina}"
                            </#if>>
                </div>
            </div>
            <br>
            <div class="row">
                <div class="input-group mb-3">
                    <label class="input-group-text" for="inputGroupFile01" style="width: 83px;">Frontale</label>
                    <input type="file" class="form-control" id="imgFronte" name="imgFronte">
                </div>
            </div>
            <br>
            <div class="row">
                <div class="input-group mb-3">
                    <label class="input-group-text" for="inputGroupFile01" style="width: 83px;">Retro</label>
                    <input type="file" class="form-control" id="imgRetro" name="imgRetro">
                </div>
            </div>
            <br>
            <div class="row">
                <div class="input-group mb-3">
                    <label class="input-group-text" for="inputGroupFile01" style="width: 83px;">Libretto</label>
                    <input type="file" class="form-control" id="imgLibretto"
                           name="imgLibretto">
                </div>
            </div>
        </div>
        <div class="col-12 col-md-8 col-lg-8">
            <div class="table-responsive text-danger">
                <h2 class="text-danger">Lista brani</h2>
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
            </div>
        </div>
    </div>
</div>