<link rel="stylesheet" href="style/default-assets/multi-select.css">
<div class="container">
    <form action="newCollezione" method="post">
        <div>
            <div class="mb-3">
                <label class="form-label">
                    <h5>Nome collezione</h5>
                </label>
                <input type="text" class="form-control" id="nome" name="nome"/>
                <div class="form-text">
                    Inserisci il nome della collezione.
                </div>
            </div>
            <div class="mb-3">
                <label class="form-label">
                    <h5>Condivisione</h5>
                </label>
                <select id="condivisione" name="condivisione" class="form-select">
                    <option value="privata" selected>Privata</option>
                    <option value="pubblica">Pubblica</option>
                </select>
                <div class="form-text">
                    Seleziona il tipo di condivisione.
                </div>
            </div>
        </div>
        <hr>
        <div>
            <div class="mb-3">
                <label class="form-label">
                    <h5>Utenti</h5>
                </label>
                <select class="selectpicker" multiple data-live-search="true" id="utentiS" name="utentiS">
                    <#list lista_utenti as utente>
                        <#if (utente.key != userid)>
                            <option value="${utente.key}" id="${utente.key}">${utente.nickname}</option>
                        </#if>
                    </#list>
                </select>
                <div class="form-text">
                    Seleziona gli utenti con cui condividere la tua collezione.
                </div>
            </div>
            <div class="mb-3">
                <label class="form-label">
                    <h5>Dischi</h5>
                </label>
                <select class="selectpicker" multiple data-live-search="true" id="dischiS" name="dischiS">
                    <#list lista_dischi as disco>
                        <option value="${disco.key}" id="${disco.key}">${disco.nome}</option>
                    </#list>
                </select>
                <div class="form-text">
                    Seleziona i dischi da aggiungere alla tua collezione.
                </div>
            </div>
            <input type="submit" class="btn poca-btn mt-70" value="Salva" name="nuova_collezione">
        </div>
    </form>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>