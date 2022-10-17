package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;

public class Ricerca extends SharedCollectionBaseController {

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("page_title", request.getParameter("campo").toUpperCase());
            request.setAttribute("ricerca", true);

            request.setAttribute("generi", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getGenereDAO().getListaGeneri());
            request.setAttribute("autori", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtisti());
            request.setAttribute("utenti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti());

            String keyword = request.getParameter("campo");

            // aggiunta collezioni che contengono la parola cercata
            HashSet<Collezione> collezioni = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniByNome(keyword);

            // aggiunta utenti che contengono la parola cercata
            HashSet<Utente> utenti = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti(keyword);

            // aggiunta collezioni degli utenti che corrispondono alla parola cercata
            for (Utente utente : utenti) {
                collezioni.addAll((((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniByUtente(utente.getKey())));
            }

            // aggiunta artisti che contengono la parola cercata
            HashSet<Artista> artisti = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtisti(keyword);

            HashSet<ListaArtisti> listaArtisti = new HashSet<>();
            HashSet<Canzone> canzoni = new HashSet<>();
            HashSet<ListaBrani> listaBrani = new HashSet<>();
            HashSet<Disco> dischi = new HashSet<>();
            HashSet<ListaDischi> listaDischi = new HashSet<>();

            // aggiunta dischi degli artisti che corrispondono alla parola cercata
            for (Artista artista : artisti) {
                listaArtisti.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaArtistiDAO().getListaArtisti(artista));
                dischi.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi(artista));
            }

            // dettagli dischi degli artisti che corrispondono alla parola cercata
            for (Disco disco : dischi) {
                listaDischi.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListeDischi(disco));
            }

            // aggiunta canzoni degli artisti che corrispondono alla parola cercata
            for (ListaArtisti listaArtista : listaArtisti) {
                canzoni.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCanzoneDAO().getCanzone(listaArtista.getCanzone().getKey()));
            }

            // aggiunta dischi in cui compaiono gli artisti che corrispondono alla parola cercata
            for (Canzone canzone : canzoni) {
                listaBrani.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaBraniDAO().getListeBrani(canzone));
            }

            // dettagli dischi in cui compaiono gli artisti che corrispondono alla parola cercata
            for (ListaBrani listaBrano : listaBrani) {
                listaDischi.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListeDischi(listaBrano.getDisco()));
                dischi.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(listaBrano.getDisco().getKey()));
            }

            // aggiunta collezioni dei dischi aggiunti
            for (ListaDischi listaDisco : listaDischi) {
                collezioni.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(listaDisco.getCollezione()));
            }

            request.setAttribute("keyword", keyword);
            request.setAttribute("collezioni", collezioni);
            request.setAttribute("dischi", dischi);
            request.setAttribute("dettagliDischi", listaDischi);
            request.setAttribute("utenti", utenti);

            res.activate("ricerca.html.ftl", request, response);

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_default(request, response);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (TemplateManagerException e) {
            throw new RuntimeException(e);
        }
    }

    public String getServletInfo() {
        return "Servlet per la ricerca";
    }
}
