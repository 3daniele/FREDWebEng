<div class="table-responsive">
    <h2>Brani di ${disco.nome}</h2>
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