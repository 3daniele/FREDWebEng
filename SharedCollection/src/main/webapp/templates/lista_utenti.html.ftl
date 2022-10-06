<h2>Elenco utenti in ordine alfabetico</h2>
<hr>
<div class="row">
    <form action="showUtenti" method="GET">
        <select class="form-select" aria-label="Default select example" name="filter" id="filter"
                onchange="this.form.submit()">
            <#if filter??>
                <option <#if (filter=0)>selected</#if> value="0">Filtra per lettera</option>
                <option <#if (filter=1)>selected</#if> value="1">A</option>
                <option <#if (filter=2)>selected</#if> value="2">B</option>
                <option <#if (filter=3)>selected</#if> value="3">C</option>
                <option <#if (filter=4)>selected</#if> value="4">D</option>
                <option <#if (filter=5)>selected</#if> value="5">E</option>
                <option <#if (filter=6)>selected</#if> value="6">F</option>
                <option <#if (filter=7)>selected</#if> value="7">G</option>
                <option <#if (filter=8)>selected</#if> value="8">H</option>
                <option <#if (filter=9)>selected</#if> value="9">I</option>
                <option <#if (filter=10)>selected</#if> value="10">J</option>
                <option <#if (filter=11)>selected</#if> value="11">K</option>
                <option <#if (filter=12)>selected</#if> value="12">L</option>
                <option <#if (filter=13)>selected</#if> value="13">M</option>
                <option <#if (filter=14)>selected</#if> value="14">N</option>
                <option <#if (filter=15)>selected</#if> value="15">O</option>
                <option <#if (filter=16)>selected</#if> value="16">P</option>
                <option <#if (filter=17)>selected</#if> value="17">Q</option>
                <option <#if (filter=18)>selected</#if> value="18">R</option>
                <option <#if (filter=19)>selected</#if> value="19">S</option>
                <option <#if (filter=20)>selected</#if> value="20">T</option>
                <option <#if (filter=21)>selected</#if> value="21">U</option>
                <option <#if (filter=22)>selected</#if> value="22">V</option>
                <option <#if (filter=23)>selected</#if> value="23">W</option>
                <option <#if (filter=24)>selected</#if> value="24">X</option>
                <option <#if (filter=25)>selected</#if> value="25">Y</option>
                <option <#if (filter=26)>selected</#if> value="26">Z</option>
            <#else>
                <option selected value="0">Filtra per lettera</option>
                <option value="1">A</option>
                <option value="2">B</option>
                <option value="3">C</option>
                <option value="4">D</option>
                <option value="5">E</option>
                <option value="6">F</option>
                <option value="7">G</option>
                <option value="8">H</option>
                <option value="9">I</option>
                <option value="10">J</option>
                <option value="11">K</option>
                <option value="12">L</option>
                <option value="13">M</option>
                <option value="14">N</option>
                <option value="15">O</option>
                <option value="16">P</option>
                <option value="17">Q</option>
                <option value="18">R</option>
                <option value="19">S</option>
                <option value="20">T</option>
                <option value="21">U</option>
                <option value="22">V</option>
                <option value="23">W</option>
                <option value="24">X</option>
                <option value="25">Y</option>
                <option value="26">Z</option>
            </#if>
        </select>
    </form>
</div>
<div class="row">
    <br>
</div>
<div class="row">
    <#if filter??>
        <#if (filter=0)>
            <h4>A</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("a") >
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>B</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("b")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>C</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("c")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>D</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("d")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>E</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("e")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>F</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("f")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>G</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("g")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>H</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("h")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>I</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("i")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>J</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("j")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>K</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("k")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>L</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("l")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>M</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("m")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>N</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("n")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>O</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("o")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>P</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("p")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>Q</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("q")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>R</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("r")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>S</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("s")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>T</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("t")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>U</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("u")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>V</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("v")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>W</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("w")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>X</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("x")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>y</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("y")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
            <h4>Z</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("z")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
            <hr>
        </#if>
        <#if (filter=1)>
            <h4>A</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("a")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=2)>
            <h4>B</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("b")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=3)>
            <h4>C</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("c")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=4)>
            <h4>D</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("d")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=5)>
            <h4>E</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("e")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=6)>
            <h4>F</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("f")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=7)>
            <h4>G</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("g")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=8)>
            <h4>H</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("h")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=9)>
            <h4>I</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("i")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=10)>
            <h4>J</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("j")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=11)>
            <h4>K</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("k")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=12)>
            <h4>L</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("l")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=13)>
            <h4>M</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("m")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=14)>
            <h4>N</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("n")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=15)>
            <h4>O</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("o")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=16)>
            <h4>P</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("p")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=17)>
            <h4>Q</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("q")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=18)>
            <h4>R</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("r")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=19)>
            <h4>S</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("s")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=20)>
            <h4>T</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("t")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=21)>
            <h4>U</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("u")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=22)>
            <h4>V</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("v")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=23)>
            <h4>W</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("w")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=24)>
            <h4>X</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("x")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=25)>
            <h4>Y</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("y")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <#if (filter=26)>
            <h4>Z</h4>
            <hr>
            <ul>
                <#list utenti as utente>
                    <li>
                        <#if utente.nickname?lower_case?starts_with("z")>
                            <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                        </#if>
                    </li>
                </#list>
            </ul>
        </#if>
        <!-- ********************************************************************************************************* -->
    <#else>
        <h4>A</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("a")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>B</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("b")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>C</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("c")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>D</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("d")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>E</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("e")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>F</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("f")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>G</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("g")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>H</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("h")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>I</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("i")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>J</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("j")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>K</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("k")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>L</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("l")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>M</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("m")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>N</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("n")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>O</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("o")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>P</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("p")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>Q</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("q")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>R</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("r")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>S</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("s")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>T</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("t")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>U</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("u")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>V</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("v")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>W</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("w")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>X</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("x")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>y</h4>
        <hr>
        <ul>
            <#list utenti as utente>
                <li>
                    <#if utente.nickname?lower_case?starts_with("y")>
                        <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                    </#if>
                </li>
            </#list>
        </ul>
        <hr>
        <h4>Z</h4>
        <hr>
        <#list utenti as utente>
            <li>
                <#if utente.nickname?lower_case?starts_with("z")>
                    <h6><a href="utente?id=${utente.key}">${utente.nickname}</a></h6>
                </#if>
            </li>
        <#else>
        </#list>
    </#if>
    </ul>
    <hr>
</div>
