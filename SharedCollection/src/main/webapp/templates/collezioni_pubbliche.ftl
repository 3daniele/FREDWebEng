<#if collezioni_home??>
    <#assign n = 5>
    <div class="row poca-portfolio" style="position: relative; height: 1139px;">
        <#list collezioni_home as collezione>
            <div class="col-12 col-md-4 single_gallery_item entre wow fadeInUp animated" data-wow-delay="0.2s"
                 style="visibility: visible; animation-delay: 0.2s; position: absolute; left: 0%; top: 0px; cursor:pointer;"
                 onclick="location.href='collezione?numero=${collezione.key}'">
                <div class="poca-music-area style-2 d-flex align-items-center flex-wrap">
                    <div class="poca-music-thumbnail">
                        <img src="images/templateimg/bg-img/${n}.jpg" alt="">
                    </div>
                    <div class="poca-music-content text-center">
                        <span class="music-published-date mb-2">${collezione.dataCreazione}</span>
                        <h2>${collezione.nome}</h2>
                        <div class="music-meta-data">

                            <#list utenti as utente>
                                <#if (utente.key=collezione.utente.key)>
                                    <p>By <a href="utente?id=${utente.key}" class="music-author">
                                            ${utente.nickname}
                                        </a></p>
                                </#if>
                            </#list>
                        </div>
                    </div>
                </div>
            </div>
            <#assign n++>
        </#list>
    </div>
    <div class="col-12 text-center">
        <a href="collezioni" class="btn poca-btn mt-70">MOSTRA ALTRO</a>
    </div>
<#else>
    <h1>Buongiornissimo</h1>
</#if>