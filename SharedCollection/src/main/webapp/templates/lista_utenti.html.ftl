<h2>Elenco utenti in ordine alfabetico</h2>
<hr>
<div class="row">
    <form action="showUtenti" method="POST">
        <select class="form-select" aria-label="Default select example" name="filter" id="filter"
                onchange="this.form.submit()">
            <#if filter??>
                <option value="a">Filtra per lettera</option>
                <option <#if (filter="a")>selected</#if> value="a">A</option>
                <option <#if (filter="b")>selected</#if> value="b">B</option>
                <option <#if (filter="c")>selected</#if> value="c">C</option>
                <option <#if (filter="d")>selected</#if> value="d">D</option>
                <option <#if (filter="e")>selected</#if> value="e">E</option>
                <option <#if (filter="f")>selected</#if> value="f">F</option>
                <option <#if (filter="g")>selected</#if> value="g">G</option>
                <option <#if (filter="h")>selected</#if> value="h">H</option>
                <option <#if (filter="i")>selected</#if> value="i">I</option>
                <option <#if (filter="j")>selected</#if> value="j">J</option>
                <option <#if (filter="k")>selected</#if> value="k">K</option>
                <option <#if (filter="l")>selected</#if> value="l">L</option>
                <option <#if (filter="m")>selected</#if> value="m">M</option>
                <option <#if (filter="n")>selected</#if> value="n">N</option>
                <option <#if (filter="o")>selected</#if> value="o">O</option>
                <option <#if (filter="p")>selected</#if> value="p">P</option>
                <option <#if (filter="q")>selected</#if> value="q">Q</option>
                <option <#if (filter="r")>selected</#if> value="r">R</option>
                <option <#if (filter="s")>selected</#if> value="s">S</option>
                <option <#if (filter="t")>selected</#if> value="t">T</option>
                <option <#if (filter="u")>selected</#if> value="u">U</option>
                <option <#if (filter="v")>selected</#if> value="v">V</option>
                <option <#if (filter="w")>selected</#if> value="w">W</option>
                <option <#if (filter="x")>selected</#if> value="x">X</option>
                <option <#if (filter="y")>selected</#if> value="y">Y</option>
                <option <#if (filter="z")>selected</#if> value="z">Z</option>
            <#else>
                <option selected value="a">Filtra per lettera</option>
                <option value="a">A</option>
                <option value="b">B</option>
                <option value="c">C</option>
                <option value="d">D</option>
                <option value="e">E</option>
                <option value="f">F</option>
                <option value="g">G</option>
                <option value="h">H</option>
                <option value="i">I</option>
                <option value="j">J</option>
                <option value="k">K</option>
                <option value="l">L</option>
                <option value="m">M</option>
                <option value="n">N</option>
                <option value="o">O</option>
                <option value="p">P</option>
                <option value="q">Q</option>
                <option value="r">R</option>
                <option value="s">S</option>
                <option value="t">T</option>
                <option value="u">U</option>
                <option value="v">V</option>
                <option value="w">W</option>
                <option value="x">X</option>
                <option value="y">Y</option>
                <option value="z">Z</option>
            </#if>
        </select>
        <div class="form-text">Seleziona una lettera per filtrare gli utenti</div>
    </form>
</div>
<div class="row">
    <br>
</div>
<div class="row">
    <#if filter??>
        <h4>${filter?upper_case}</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>

                    <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>

                </li>
            </#list>
        </ul>
        <!-- ********************************************************************************************************* -->
    </#if>
    </ul>
    <hr>
</div>
