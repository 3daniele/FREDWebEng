<link rel="stylesheet" href="style/default-assets/multi-select.css">
<div class="row">

    <!--MODIFICA COLLEZIONE-->
    <div class="col-12 col-md-6 col-lg-6">
        <form action="modificaCollezione" method="post">
            <h2 class="text-danger">Informazioni:</h2>
            <label class="form-label">
                <h5>Nome collezione</h5>
            </label>
            <input type="text" class="form-control" id="nome" name="nome" value="${collezione.nome}"/>
            <div class="form-text">
                Modifica il nome della collezione.
            </div>
            <br>
            <label class="form-label">
                <h5>Condivisione</h5>
            </label>
            <select id="condivisione" name="condivisione" class="form-select">
                <#if (collezione.condivisione == "privata")>
                    <option value="privata" selected>Privata</option>
                    <option value="pubblica">Pubblica</option>
                <#else>
                    <option value="privata">Privata</option>
                    <option value="pubblica" selected>Pubblica</option>
                </#if>
            </select>
            <div class="form-text">
                <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}" />
                Modifica il tipo di condivisione.
            </div>
            <div class="text-end">
                <input type="submit" class="btn btn-danger text-end" value="Salva" name="modifica_collezione">
            </div>
        </form>
    </div>

    <!--MODIFICA UTENTI AUTORIZZATI-->
    <div class="col-12 col-md-6 col-lg-6">
        <form action="modificaUtenti" method="post">
            <h2 class="text-danger">Condivisione:</h2>
            <label class="form-label">
                <h5>Utenti</h5>
            </label>
            <select class="selectpicker" multiple data-live-search="true" id="utentiS" name="utentiS">
                <#list lista_utenti as utente>
                    <#if (utente.key != userid)>
                        <#if utenti_autorizzati?seq_contains(utente.key)>
                            <option value="${utente.key}" id="${utente.key}" selected>${utente.nickname}</option>
                        <#else>
                            <option value="${utente.key}" id="${utente.key}">${utente.nickname}</option>
                        </#if>
                    </#if>
                </#list>
            </select>
            <div class="form-text">
                <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}"/>
                Seleziona gli utenti con cui condividere la tua collezione.
            </div>
            <br><br><br><br><br><br>
            <div class="text-end">
                <input type="submit" class="btn btn-danger" value="Salva" name="modifica_utenti">
            </div>
        </form>
    </div>
</div>
<hr>
<br>
<div class="row">

    <!--MODIFICA DISCHI-->
    <div class="col-12 col-md-6 col-lg-6">
        <h2 class="text-danger">Lista Dischi:</h2>
        <input type="hidden" name="collezioneID" id="collezioneID" value="${collezione.key}"/>
    </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.1.1/jquery.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.1.1/js/bootstrap.bundle.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/bootstrap-select/1.13.1/js/bootstrap-select.min.js"></script>
