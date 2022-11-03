<link rel="stylesheet" href="style/default-assets/multi-select.css">
<div class="container">
    <#if error??>
        <div class="alert alert-danger text-center" role="alert">
            ${error}
        </div>
    </#if>
    <form action="newCollezione" method="post">
        <div>
            <div class="mb-3">
                <h5 class="form-label">Nuova collezione</h5>

                <input type="text" class="form-control" id="nome" name="nome">
                <div class="form-text">
                    Inserisci il nome della collezione.
                </div>
            </div>
            <div class="mb-3">
                <h5 class="form-label">Condivisione</h5>

                <select id="condivisione" name="condivisione" class="form-select">
                    <option value="privata" selected>Privata</option>
                    <option value="pubblica">Pubblica</option>
                </select>
                <div class="form-text">
                    Seleziona il tipo di condivisione.
                </div>
            </div>
        </div>
        <div>
            <input type="submit" class="btn poca-btn mt-70" value="Salva" name="nuova_collezione">
        </div>
    </form>
</div>

<script src="js/jquerySelect.min.js"></script>
<script src="js/bootstrapSelect.bundle.min.js"></script>
<script src="js/bootstrap-select.min.js"></script>