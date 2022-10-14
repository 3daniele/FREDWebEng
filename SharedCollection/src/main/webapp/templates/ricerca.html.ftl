<link rel="stylesheet" href="style/default-assets/multi-select.css" xmlns="http://www.w3.org/1999/html">
<div class="row">
    <div class="col-12 col-md-2 col-lg-2" style="margin-left: 10px;">
        <h3 style="margin-left: 16px;">Filtri:</h3>
        <div class="card">
            <h5 class="card-header">
                Autori:
            </h5>
            <div class="card-body">
                <select class="selectpicker" data-live-search="true" id="autoreID" name="autoreID" multiple>
                    <#list autori as autore>
                        <option value="${autore.key}" id="autoreID" name="autoreID">${autore.nomeArte}</option>
                    </#list>
                </select>
                <div class="form-text">
                    Seleziona gli autori tra cui filtrare.
                </div>
            </div>
            <h5 class="card-header">
                Utenti:
            </h5>
            <div class="card-body">
                <#list utenti as utente>
                    <p class="card-text">
                        <input class="form-check-input" type="checkbox" value="${utente.key}" id="${utente.key}"
                               name="utenteID">
                        <label class="form-check-label" for="${utente.key}">
                            ${utente.nickname}
                        </label>
                    </p>
                </#list>
            </div>
            <h5 class="card-header">
                Generi:
            </h5>
            <div class="card-body">
                <select class="selectpicker" data-live-search="true" id="genereID" name="genereID" multiple>
                    <#list generi as genere>
                        <option value="${genere.key}" id="genereID" name="genereID">${genere.nome}</option>
                    </#list>
                </select>
                <div class="form-text">
                    Seleziona i generi tra cui cercare.
                </div>
            </div>
            <h5 class="card-header">
                Formati:
            </h5>
            <div class="card-body">
                <div class="row">
                    <div class="col-6 mb-2">
                        <input type="checkbox" class="btn-check" id="Vinile" name="Vinile" autocomplete="off">
                        <label class="btn btn-outline-success" for="Vinile">Vinile</label>
                    </div>
                    <div class="col-6 mb-2">
                        <input type="checkbox" class="btn-check" id="CD" name="CD" autocomplete="off">
                        <label class="btn btn-outline-success" for="CD">CD</label>
                    </div>
                    <div class="col-6 mb-2">
                        <input type="checkbox" class="btn-check" id="Digitale" name="Digitale" autocomplete="off">
                        <label class="btn btn-outline-success" for="Digitale">Digitale</label>
                    </div>
                    <div class="col-6 mb-2">
                        <input type="checkbox" class="btn-check" id="Cassetta" name="Cassetta" autocomplete="off">
                        <label class="btn btn-outline-success" for="Cassetta">Cassetta</label>
                    </div>
                    <div class="col-6 mb-2">
                        <input type="checkbox" class="btn-check" id="Altro" name="Altro" autocomplete="off">
                        <label class="btn btn-outline-success" for="Altro">Altro</label>
                    </div>
                </div>
            </div>
            <h5 class="card-header">
                Stato:
            </h5>
            <div class="card-body">
                <div class="row">
                    <div class="col-6 mb-2">
                        <input type="checkbox" class="btn-check" id="ottimo" name="Ottimo" autocomplete="off">
                        <label class="btn btn-outline-danger" for="ottimo">Ottimo</label><br>
                    </div>
                    <div class="col-6 mb-2">
                        <input type="checkbox" class="btn-check" id="buono" name="Buono" autocomplete="off">
                        <label class="btn btn-outline-danger" for="buono">Buono</label><br>
                    </div>
                    <div class="col-6 mb-2">
                        <input type="checkbox" class="btn-check" id="discreto" name="Discreto" autocomplete="off">
                        <label class="btn btn-outline-danger" for="discreto">Discreto</label><br>
                    </div>
                    <div class="col-6 mb-2">
                        <input type="checkbox" class="btn-check" id="sufficiente" name="Sufficiente" autocomplete="off">
                        <label class="btn btn-outline-danger" for="sufficiente">Sufficiente</label><br>
                    </div>
                    <div class="col-6 mb-2">
                        <input type="checkbox" class="btn-check" id="pessimo" name="Pessimo" autocomplete="off">
                        <label class="btn btn-outline-danger" for="pessimo">Pessimo</label><br>
                    </div>
                </div>
            </div>
        </div>
    </div>
    <div class="col-12 col-md-3 col-lg-3">
        <#assign n = 5>
        <#list collezioni as collezione>
            <div class="col-12 col-md-4 entre wow fadeInUp" data-wow-delay="0.2s"
                 style="cursor:pointer"
                 onclick="location.href='collezione?numero=${collezione.key}'">
                <div class="poca-music-area style-2 d-flex align-items-center flex-wrap">
                    <div class="poca-music-thumbnail">
                        <div class="portrait" style="height: 400px;  width:100%;">
                            <img src="images/templateimg/bg-img/${n}.jpg" alt="" style="width:100%; height: 100%">
                        </div>

                    </div>
                    <div class="poca-music-content text-center">
                        <h2>${collezione.nome}</h2>
                        <noscript>
                            <style type="text/css">
                                .pagecontainer {
                                    display: none;
                                }
                            </style>
                            <div class="noscriptmsg">
                                <a href="disco?numero=${collezione.key}"
                                   class="btn poca-btn mt-10">Visualizza</a>
                            </div>
                        </noscript>
                    </div>
                </div>
            </div>
            <#assign n++>
        </#list>
    </div>
</div>

<script src="js/jquerySelect.min.js"></script>
<script src="js/bootstrapSelect.bundle.min.js"></script>
<script src="js/bootstrap-select.min.js"></script>