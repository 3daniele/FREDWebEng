<link rel="stylesheet" href="style/default-assets/multi-select.css">
<div class="container">
    <#if error??>
        <div class="alert alert-danger text-center" role="alert">
            ${error}
        </div>
    </#if>
    <form action="newArtista" method="post">
        <div>
            <div class="mb-3">

                    <h5 class="form-label">Nome</h5>

                <input type="text" class="form-control" id="nome" name="nome"
                        <#if nome??>
                            value="${nome}"
                        </#if>
                />
                <div class="form-text">
                    Inserisci il nome dell'artista.
                </div>
            </div>
            <div class="mb-3">
                <h5 class="form-label">Cognome</h5>

                <input type="text" class="form-control" id="cognome" name="cognome"
                        <#if cognome??>
                            value="${cognome}"
                        </#if>
                />
                <div class="form-text">
                    Inserisci il cognome dell'artista.
                </div>
            </div>
            <div class="mb-3">
                <h5 class="form-label">Nome d'arte</h5>

                <input type="text" class="form-control" id="nomeArte" name="nomeArte"
                        <#if nomeArte??>
                            value="${nomeArte}"
                        </#if>
                />
                <div class="form-text">
                    Inserisci il nome d'arte dell'artista.
                </div>
            </div>
            <input type="submit" class="btn poca-btn mt-70" value="Salva" name="nuovo_artista">
        </div>
    </form>
</div>

<script src="js/jquerySelect.min.js"></script>
<script src="js/bootstrapSelect.bundle.min.js"></script>
<script src="js/bootstrap-select.min.js"></script>