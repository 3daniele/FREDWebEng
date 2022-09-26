<?xml version="1.0" encoding="ISO-8859-1"?>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
    <head>
        <title>My Newspaper</title>
        <link rel="stylesheet" href="style/normalize.css" type="text/css"/>        
        <link rel="stylesheet" href="style/grid.css" type="text/css"/>
        <link rel="stylesheet" href="style/default.css" type="text/css"/>        
    </head>
    <body>
        <div class="container">
            <div class="row" id="header">
                <div class="sixteen columns">Prova
                <div class="subtitle">${page_title!"Untitled page"}</div>
                </div>
                
            </div>
            <div class="row" id="body">

                <div class="three columns">
                    <div class="vertical menu">
                    <ul>
                        <li><a href="issues">newspaper homepage</a></li>
                        <li><a href="compose">compose issue</a></li>
                        <li><a href="write">write article</a></li>
                    </ul>
                        </div>
                </div>
                <div class="thirteen columns" >
                    <#include content_tpl>
                </div>
            </div>
            <div class="row" id="footer">
                <div class="sixteen columns">Copyright &copy; 2014 ${defaults.author}. Page compiled on ${compiled_on?datetime}</div>
            </div>
        </div>
    </body>
</html>