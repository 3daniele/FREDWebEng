<div class="container">
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
            <p>Copie in possesso: <b>${infoDisco.numeroCopie}</b></p>
            <p>Formato: <b>${infoDisco.formato}</b></p>
            <p>Stato: <b>${infoDisco.stato}</b></p>
            <p>Proprietario: <a href="utente?id=${collezione.utente.key}"><b>${collezione.utente.nickname}</b></a></p>
        </div>
        <div class="col-12 col-md-8 col-lg-8">
            <div class="table-responsive">
                <h2>Lista brani</h2>
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