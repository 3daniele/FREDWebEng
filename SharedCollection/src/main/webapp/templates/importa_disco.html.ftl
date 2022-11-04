<div class="container">
    <#if error??>
        <div class="alert alert-danger text-center" role="alert">
            ${error}
        </div>
    </#if>
    <form action="importaDisco" method="post">
        <div>
            <div class="mb-3">
                <h5 class="form-label">Nome</h5>
                <input type="text" class="form-control" id="nome" name="nome">
                <div class="form-text">
                    Inserisci il nome del disco da ricercare.
                </div>
            </div>
            <input type="submit" class="btn btn-danger" value="Cerca" name="cercaDisco">
        </div>
    </form>

    <!-- RICERCA DISCO -->
    <#if ricerca??>
        <div class="mt-40">
            <#if (dischi?? && dischi?size>0)>
                <h2>
                    Risultati della ricerca:
                </h2>
                <table class="table custom-table">
                    <thead class="thead-dark">
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col">Nome</th>
                        <th scope="col">Etichetta</th>
                        <th scope="col">Anno</th>
                        <th scope="col">Artista</th>
                        <th scope="col"></th>
                    </tr>
                    </thead>
                    <tbody>
                    <#assign n=1>
                    <#list dischi as disco>
                        <tr>
                            <th scope="row">${n}</th>
                            <td>
                                ${disco.nome}
                            </td>
                            <td>${disco.etichetta}</td>
                            <td>${disco.anno}</td>
                            <td>${disco.artista.nomeArte}</td>
                            <td>
                                <form action="importaDisco" method="post">
                                    <input type="hidden" id="collezioneID" name="collezioneID"
                                           value="${collezione_key}">
                                    <input type="hidden" id="discoID" name="discoID" value="${disco.key}">
                                    <button type="submit" class="btn btn-success" id="aggiungiDisco"
                                            name="aggiungiDisco">
                                        <img src="images/templateimg/imgFont/check.svg" alt="aggiungi" width="24"
                                             height="24" class="text-light" fill="currentColor">
                                    </button>
                                </form>
                            </td>
                        </tr>
                        <#assign n++>
                    </#list>
                    </tbody>
                </table>
            <#else>
                <p>Non è stato trovato nessun disco corrispondente alla parola cercata.</p>
            </#if>
        </div>
    </#if>

    <!-- IMPORTA DISCO -->
    <#if infoDisco??>
        <form action="importaDisco" method="post">
            <div class="row">
                <div class="row">
                    <h2 class="form-label text-danger">Informazioni disco:</h2>

                    <div class="row">
                        <h5 class="form-label">Titolo:</h5>
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" name="titolo"
                                   id="titolo" value="${disco.nome}" readonly>
                            <input type="hidden" name="discoID" id="discoID"
                                   value="${disco.key}"/>
                            <input type="hidden" name="collezioneID" id="collezioneID"
                                   value="${collezione.key}"/>
                        </div>
                    </div>
                </div>
                <br>
                <div class="row">
                    <div class="col-12 col-md-6 col-lg-6">
                        <h5 class="form-label">Etichetta:</h5>
                        <div class="input-group mb-3">
                            <input type="text" class="form-control" name="etichetta"
                                   id="etichetta" value="${disco.etichetta}" readonly>
                        </div>
                    </div>
                    <div class="col-12 col-md-6 col-lg-6">
                        <h5 class="form-label">Data uscita</h5>

                        <div class="input-group mb-3">
                            <input type="date" class="form-control" name="anno"
                                   id="anno" value='${disco.anno?iso_local}' readonly>
                        </div>
                    </div>
                </div>
                <br>
                <br>
                <div class="row">
                    <div class="col-12 col-md-4 col-lg-4">
                        <h5 class="form-label">Numero Copie</h5>
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
                </div>
            </div>
            <br>
            <div class="row">
                <h5 class="form-label">Barcode :</h5>
                <div class="input-group mb-3">
                    <div class="input-group mb-3">
                        <input type="text" class="form-control"
                               aria-label="Sizing example input"
                               aria-describedby="inputGroup-sizing-default" id="barcode"
                               name="barcode"
                        >
                    </div>
                </div>
            </div>
            <br>
            <div class="row text-end">
                <div class="col-9">
                </div>
                <div class="col-3">
                    <input type="submit" class="btn btn-danger" id="add" name="add" value="Aggiungi">
                </div>
            </div>
        </form>
    </#if>

</div>