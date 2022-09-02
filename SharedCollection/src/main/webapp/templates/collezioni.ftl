<div class="table-responsive">
    <#if session??>
        <br>
        <h2>Le mie collezioni</h2>
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
                    <td><a href="modificaCollezione?numero=${collezione.key}" class="btn btn-outline-danger" role="button">Modifica</a></td>
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
                    </tr>
                </#list>
            </tbody>
        </table>
    </#if>
</div>