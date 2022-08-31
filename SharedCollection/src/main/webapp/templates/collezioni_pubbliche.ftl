<#if collezioni_home??>
<div class="row poca-portfolio">
  <#list collezioni_home as collezione>
  <div class="col-12 col-md-4 single_gallery_item entre wow fadeInUp animated" data-wow-delay="0.2s"
       style="visibility: visible; animation-delay: 0.2s; position: absolute; left: 0%; top: 0px;">
    <div class="poca-music-area style-2 d-flex align-items-center flex-wrap">
      <div class="poca-music-thumbnail">
        <img src="images/templateimg/bg-img/5.jpg" alt="">
      </div>
      <div class="poca-music-content text-center">
        <span class="music-published-date mb-2">${collezione.dataCreazione}</span>
        <h2>${collezione.nome}</h2>
        <div class="music-meta-data">
          <p>By <a href="#" class="music-author">
            <#list utenti as utente>
            <#if (utente.key=collezione.utente.key)>
            ${utente.nickname}
          </#if>
          </#list></a>
        </div>
      </div>
    </div>
  </div>
</#list>
</div>
<#else>
<h1>Buongiornissimo</h1>
</#if>