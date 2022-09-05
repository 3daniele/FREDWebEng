<div class="row">
    <h3>Lista dischi</h3>
    <hr>
</div>
<div class="row poca-portfolio">
    <#list dettagliDischi as dettaglio>
        <#list dischi as disco>
            <#if (disco.key = dettaglio.disco.key)>
                <div class="col-12 col-md-4 single_gallery_item entre wow fadeInUp animated" data-wow-delay="0.2s"
                     style="visibility: visible; animation-delay: 0.2s; position: absolute; left: 0%; top: 0px; cursor:pointer"
                     onclick="location.href='disco?numero=${disco.key}'">
                    <div class="poca-music-area style-2 d-flex align-items-center flex-wrap">
                        <div class="poca-music-thumbnail">
                            <img src="${dettaglio.imgCopertina}" alt="">
                        </div>
                        <div class="poca-music-content text-center">
                            <span class="music-published-date mb-2">${disco.anno}</span>
                            <h2>${disco.nome}</h2>
                            <div class="music-meta-data">
                                <p>By <a href="#" class="music-author">${disco.artista.nomeArte}</a>
                            </div>
                            <div class="likes-share-download d-flex align-items-center justify-content-between">
                                <a href="#"><i class="" aria-hidden="true"></i>${dettaglio.formato}</a>
                                <div>
                                    <a href="#"><i class="" aria-hidden="true"></i>${dettaglio.stato}</a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </#if>
        </#list>
    </#list>
</div>
<hr>
<div class="row">
    <h3>Dettagli collezione</h3>
</div>