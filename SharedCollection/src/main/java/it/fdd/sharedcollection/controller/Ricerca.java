package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
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
            HttpSession sessione = request.getSession(true);

            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("page_title", "Cerca");
            request.setAttribute("ricerca", true);

            String keyword = request.getParameter("campo") != null ? request.getParameter("campo") : request.getParameter("campo_");

            if (keyword != null) {

                int user_key = 0;

                if (SecurityLayer.checkSession(request) != null) {
                    request.setAttribute("session", true);
                    request.setAttribute("username", sessione.getAttribute("username"));
                    request.setAttribute("email", sessione.getAttribute("email"));
                    request.setAttribute("userid", sessione.getAttribute("userid"));
                    user_key = Integer.parseInt(sessione.getAttribute("userid").toString());
                }

                List<Collezione> collezioni_ = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniPubbliche();
                List<Disco> dischi_ = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi();
                List<Utente> utenti_ = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti();
                List<Artista> artisti_ = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtisti();

                // aggiunta dischi che contengono la parola cercata
                List<Disco> dischi = new ArrayList<>();
                for (Disco disco : dischi_) {
                    if (disco.getNome().toLowerCase().contains(keyword.toLowerCase())) {
                        dischi.add(disco);
                    }
                }

                // aggiunta utenti che contengono la parola cercata
                List<Utente> utenti = new ArrayList<>();
                for (Utente utente : utenti_) {
                    if (utente.getNickname().toLowerCase().contains(keyword.toLowerCase())) {
                        utenti.add(utente);
                    }
                }

                // aggiunta artisti che contengono la parola cercata
                List<Artista> artisti = new ArrayList<>();
                for (Artista artista : artisti_) {
                    if (artista.getNomeArte().toLowerCase().contains(keyword.toLowerCase())) {
                        artisti.add(artista);
                    }
                }

                // aggiunta collezioni che contengono la parola cercata
                List<Collezione> collezioni = new ArrayList<>();
                for (Collezione collezione : collezioni_) {
                    if (collezione.getNome().toLowerCase().contains(keyword.toLowerCase()) && collezione.getCondivisione().equalsIgnoreCase("pubblica")) {
                        collezioni.add(collezione);
                    }
                }

                List<UtentiAutorizzati> utentiAutorizzati = new ArrayList<>();
                // aggiunta collezioni dell'utente se loggato
                if (user_key != 0) {
                    collezioni.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniByUtente(user_key));
                    utentiAutorizzati.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByUser(user_key));
                }

                // aggiunta collezioni condivise con l'utente se loggato
                if (!utentiAutorizzati.isEmpty()) {
                    collezioni.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniCondivise(utentiAutorizzati));
                }

                // aggiunta collezioni degli utenti che corrispondono alla parola cercata
                for (Utente utente : utenti) {
                    collezioni.addAll((((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniByUtenteForRicerca(utente.getKey())));
                }

                HashSet<ListaArtisti> listaArtisti = new HashSet<>();
                HashSet<Canzone> canzoni = new HashSet<>();
                HashSet<ListaBrani> listaBrani = new HashSet<>();
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
                List<Collezione> collezioniDischi = new ArrayList<>();
                for (ListaDischi listaDisco : listaDischi) {
                    collezioniDischi.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(listaDisco.getCollezione()));
                }
                for (Collezione collezione : collezioniDischi) {
                    if (!(collezioni.contains(collezione)) && collezione.getCondivisione().equalsIgnoreCase("pubblica")) {
                        collezioni.add(collezione);
                    }
                }

                request.setAttribute("keyword", keyword);
                request.setAttribute("user_key", user_key);
                request.setAttribute("collezioni", collezioni);
                request.setAttribute("dischi", dischi);
                request.setAttribute("dettagliDischi", listaDischi);
                request.setAttribute("utenti", utenti);

            }

            res.activate("ricerca.html.ftl", request, response);

        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        try {
            action_default(request, response);
        } catch (IOException | TemplateManagerException e) {
            throw new RuntimeException(e);
        }
    }

    public String getServletInfo() {
        return "Servlet per la ricerca";
    }
}
