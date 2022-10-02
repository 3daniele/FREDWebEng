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
                            <a href="modificaCollezione?numero=${collezione.key}" class="btn btn-outline-danger"
                               role="button">Modifica</a>
                        </td>
                        <noscript>
                            <style type="text/css">
                                .pagecontainer {
                                    display: none;
                                }
                            </style>
                            <div class="noscriptmsg">
                                <td>
                                    <a href="collezione?numero=${collezione.key}" class="btn btn-outline-danger">Visualizza</a>
                                </td>
                            </div>
                        </noscript>
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
                                    <a href="collezione?numero=${collezione.key}" class="btn btn-outline-danger">Visualizza</a>
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
                                <a href="collezione?numero=${collezione.key}" class="btn btn-outline-danger">Visualizza</a>
                            </td>
                        </div>
                    </noscript>
                </tr>
            </#list>
            </tbody>
        </table>
    </#if>
</div>