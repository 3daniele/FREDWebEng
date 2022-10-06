<div class="table-responsive">
    <#if session??>
        <br>
        <div class="row">
            <div class="col-10">
                <h2>Le mie collezioni</h2>
            </div>
            <div class="col-2">
                <a href="newCollezione" class="btn btn-outline-success" role="button">Nuova collezione</a>
            </div>
        </div>

        <#if (collezioniPersonali?size>0)>
            <#assign n = 0>
            <table class="table custom-table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Lista dischi</th>
                    <th scope="col">Tipo</th>
                    <th scope="col">Autore</th>
                    <th scope="col">Data creazione</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <#list collezioniPersonali as collezione>
                    <#assign n++>
                    <tr style="cursor:pointer" onclick="location.href='collezione?numero=${collezione.key}'">
                        <th scope="row">${n}</th>
                        <td>${strip_slashes(collezione.nome)}&nbsp</td>
                        <td>
                            <#assign i = 0>
                            <#list dischi as disco>
                                <#if (disco.collezione.key = collezione.key)>
                                    ${disco.disco.nome}
                                    <#assign i++>
                                    <br>
                                </#if>
                            </#list>
                            <#if (i = 0)>
                                Nessun disco presente...
                            </#if>
                        </td>
                        <td>${strip_slashes(collezione.condivisione)}&nbsp</td>
                        <td>${strip_slashes(collezione.utente.nickname)}&nbsp</td>
                        <td>${strip_slashes(collezione.dataCreazione)}&nbsp</td>
                        <td>
                            <noscript>
                                <style type="text/css">
                                    .pagecontainer {
                                        display: none;
                                    }
                                </style>
                                <a href="collezione?numero=${collezione.key}"
                                   class="btn btn-success">
                                    <img src="images/templateimg/imgFont/arrow-up.svg"
                                         alt="Bootstrap" width="24" height="24" ></a>


                            </noscript>
                            <a href="modificaCollezione?numero=${collezione.key}" class="btn btn-secondary"
                               role="button"><img src="images/templateimg/imgFont/pencil-fill.svg" alt="Bootstrap"
                                                  width="24" height="24" class="text-light" fill="currentColor"></a>
                            <button type="submit" class="btn btn-danger"><img
                                        src="images/templateimg/imgFont/trash3-fill.svg" alt="elimina" width="24"
                                        height="24"></button>
                        </td>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>

        <br>
        <h2>Collezioni condivise con me</h2>
        <#if (collezioniCondivise?size>0)>
            <#assign n = 0>
            <table class="table custom-table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Lista dischi</th>
                    <th scope="col">Tipo</th>
                    <th scope="col">Autore</th>
                    <th scope="col">Data creazione</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                <#list collezioniCondivise as collezione>
                    <#assign n++>
                    <tr style="cursor:pointer" onclick="location.href='collezione?numero=${collezione.key}'">
                        <th scope="row">${n}</th>
                        <td>${strip_slashes(collezione.nome)}&nbsp</td>
                        <td>
                            <#assign i = 0>
                            <#list dischi as disco>
                                <#if (disco.collezione.key = collezione.key)>
                                    ${disco.disco.nome}
                                    <#assign i++>
                                    <br>
                                </#if>
                            </#list>
                            <#if (i = 0)>
                                Nessun disco presente...
                            </#if>
                        </td>
                        <td>${strip_slashes(collezione.condivisione)}&nbsp</td>
                        <td>${strip_slashes(collezione.utente.nickname)}&nbsp</td>
                        <td>${strip_slashes(collezione.dataCreazione)}&nbsp</td>
                        <noscript>
                            <style type="text/css">
                                .pagecontainer {
                                    display: none;
                                }
                            </style>
                            <div class="noscriptmsg">
                                <td>
                                    <a href="collezione?numero=${collezione.key}"
                                       class="btn btn-success">
                                        <img src="images/templateimg/imgFont/arrow-up.svg"
                                             alt="Bootstrap" width="24" height="24" f></a>
                                </td>
                            </div>
                        </noscript>
                    </tr>
                </#list>
                </tbody>
            </table>
        </#if>
    </#if>

    <br>
    <h2>Collezioni pubbliche</h2>
    <#if (collezioniPubbliche?size>0)>
        <#assign n = 0>
        <table class="table custom-table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">#</th>
                <th scope="col">Nome</th>
                <th scope="col">Lista dischi</th>
                <th scope="col">Autore</th>
                <th scope="col">Data creazione</th>
                <noscript>
                    <style type="text/css">
                        .pagecontainer {
                            display: none;
                        }
                    </style>
                    <div class="noscriptmsg">
                        <th scope="col"></th>
                    </div>
                </noscript>
            </tr>
            </thead>
            <tbody>
            <#list collezioniPubbliche as collezione>
                <#assign n++>
                <tr style="cursor:pointer" onclick="location.href='collezione?numero=${collezione.key}'">
                    <th scope="row">${n}</th>
                    <td>${strip_slashes(collezione.nome)}&nbsp</td>
                    <td>
                        <#assign i = 0>
                        <#list dischi as disco>
                            <#if (disco.collezione.key = collezione.key)>
                                ${disco.disco.nome}
                                <#assign i++>
                                <br>
                            </#if>
                        </#list>
                        <#if (i = 0)>
                            Nessun disco presente...
                        </#if>
                    </td>
                    <td>${strip_slashes(collezione.utente.nickname)}&nbsp</td>
                    <td>${strip_slashes(collezione.dataCreazione)}&nbsp</td>
                    <noscript>
                        <style type="text/css">
                            .pagecontainer {
                                display: none;
                            }
                        </style>
                        <div class="noscriptmsg">
                            <td>
                                <a href="collezione?numero=${collezione.key}"
                                   class="btn btn-success">
                                    <img src="images/templateimg/imgFont/arrow-up.svg"
                                         alt="Bootstrap" width="24" height="24" f></a>
                            </td>
                        </div>
                    </noscript>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
</div>