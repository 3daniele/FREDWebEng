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
            request.setAttribute("page_title", "CERCA");
            request.setAttribute("ricerca", true);

            request.setAttribute("generi", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getGenereDAO().getListaGeneri());
            request.setAttribute("autori", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtisti());
            request.setAttribute("utenti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti());

            String keyword = request.getParameter("campo");
            String formato = request.getParameter("formato");
            String stato = request.getParameter("stato");

            boolean flagFormato = false;
            boolean flagStato = false;

            if (formato != null) {
                flagFormato = true;
            }

            if (stato != null) {
                flagStato = true;
            }

            int user_key = 0;

            if (SecurityLayer.checkSession(request) != null) {
                request.setAttribute("session", true);
                request.setAttribute("username", sessione.getAttribute("username"));
                request.setAttribute("email", sessione.getAttribute("email"));
                request.setAttribute("userid", sessione.getAttribute("userid"));
                user_key = Integer.parseInt(sessione.getAttribute("userid").toString());
            }

            // aggiunta collezioni che contengono la parola cercata
            HashSet<Collezione> collezioni = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniByNome(keyword);

            // aggiunta collezioni dell'utente se loggato
            List<UtentiAutorizzati> utentiAutorizzati = new ArrayList<>();
            if (user_key != 0) {
                collezioni.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniByUtente(user_key));
                utentiAutorizzati.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByUser(user_key));
            }

            // aggiunta collezioni condivise con l'utente se loggato
            if (!utentiAutorizzati.isEmpty()) {
                collezioni.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniCondivise(utentiAutorizzati));
            }

            // aggiunta utenti che contengono la parola cercata
            HashSet<Utente> utenti = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti(keyword);

            // aggiunta collezioni degli utenti che corrispondono alla parola cercata
            for (Utente utente : utenti) {
                collezioni.addAll((((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniByUtenteForRicerca(utente.getKey())));
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
            request.setAttribute("user_key", user_key);
            request.setAttribute("collezioni", collezioni);
            request.setAttribute("dischi", dischi);
            request.setAttribute("dettagliDischi", listaDischi);
            request.setAttribute("utenti", utenti);
            request.setAttribute("flagStato", flagStato);
            request.setAttribute("flagFormato", flagFormato);
            request.setAttribute("stato", stato);
            request.setAttribute("formato", formato);
            request.setAttribute("allUtenti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti());

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
