<div class="container">
    <#if error??>
        <div class="alert alert-danger text-center" role="alert">
            ${error}
        </div>
    </#if>
    <form action="modificaCondivisione" method="post">
        <div>
            <div class="mb-3">
                <h5 class="form-label">Cerca utente</h5>

                <input type="text" class="form-control" id="username" name="username"/>
                <div class="form-text">
                    Inserisci lo username dell'utente da cercare.
                </div>
            </div>
        </div>
        <div class="text-center">
            <input type="submit" class="btn poca-btn mt-20" value="Cerca" name="cercaUtente">
        </div>
    </form>

    <div class="mt-40">
        <#if cercato??>
            <h2>
                Risultati della ricerca:
            </h2>
            <#if (utenti_cercati?? && utenti_cercati.key != userid)>
            <table class="table custom-table">
                <thead class="thead-dark">
                <tr>
                    <th scope="col">#</th>
                    <th scope="col">Username</th>
                    <th scope="col">Email</th>
                    <th scope="col">Nome</th>
                    <th scope="col">Cognome</th>
                    <th scope="col"></th>
                </tr>
                </thead>
                <tbody>
                    <tr>
                        <th scope="row">1</th>
                        <td>
                            ${utenti_cercati.nickname}
                        </td>
                        <td>${utenti_cercati.email}</td>
                        <td>${utenti_cercati.nome}</td>
                        <td>${utenti_cercati.cognome}</td>
                        <td>
                            <form action="modificaCondivisione" method="post">
                                <input type="hidden" id="collezioneIDA" name="collezioneIDA" value="${collezione_key}">
                                <input type="hidden" id="utenteIDA" name="utenteIDA" value="${utenti_cercati.key}">
                                <button type="submit" class="btn btn-success" id="aggiungiUtente"
                                        name="aggiungiUtente"><img src="images/templateimg/imgFont/check.svg"
                                                                  alt="modifica" width="24" height="24"
                                                                  class="text-light" ></button>
                            </form>
                        </td>
                    </tr>
                </tbody>
            </table>
                <#else>
                <p>Non è stato trovato nessun utente con lo username inserito.</p>
                </#if>
        </#if>
    </div>

    <div class="mt-40">
        <h2>
            Utenti autorizzati:
        </h2>
        <#if (utenti_autorizzati?size > 0) >
        <table class="table custom-table">
            <thead class="thead-dark">
            <tr>
                <th scope="col">#</th>
                <th scope="col">Username</th>
                <th scope="col">Email</th>
                <th scope="col">Nome</th>
                <th scope="col">Cognome</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <#assign n=1>
            <#list utenti_autorizzati as utente>
                <tr>
                    <th scope="row">${n}</th>
                    <td>
                        ${utente.nickname}
                    </td>
                    <td>${utente.email}</td>
                    <td>${utente.nome}</td>
                    <td>${utente.cognome}</td>
                    <td>
                        <form action="modificaCondivisione" method="post">
                            <input type="hidden" id="collezioneIDE" name="collezioneIDE" value="${collezione_key}">
                            <input type="hidden" id="utenteIDE" name="utenteIDE" value="${utente.key}">
                            <button type="submit" class="btn btn-danger" id="eliminaUtente"
                                    name="eliminaUtente"><img src="images/templateimg/imgFont/trash3-fill.svg"
                                                              alt="modifica" width="24" height="24"
                                                              class="text-light" fill="currentColor"></button>
                        </form>
                    </td>
                </tr>
                <#assign n++>
            </#list>
            </tbody>
        </table>
            <#else>
            <p>La collezione non è ancora stata condivisa, cerca un utente per poterla condividere</p>
        </#if>

    </div>
    <div class="mt-40">
        <div class="text-center">
            <a class="btn poca-btn mt-20" href="modificaCollezione?numero=${collezione_key}">Torna alla modifica</a>
        </div>
    </div>

</div>