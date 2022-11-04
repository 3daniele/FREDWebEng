package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.impl.DiscoImpl;
import it.fdd.sharedcollection.data.impl.ListaDischiImpl;
import it.fdd.sharedcollection.data.impl.UtentiAutorizzatiImpl;
import it.fdd.sharedcollection.data.model.*;
import it.fdd.sharedcollection.data.utility.UtilityMethods;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

public class ModificaCollezione extends SharedCollectionBaseController {

    private int collezione_key = 0;
    private int disco_key = 0;
    private int user_key = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            if (request.getParameter("numero") != null) {
                collezione_key = SecurityLayer.checkNumeric(request.getParameter("numero"));
                request.setAttribute("collezioneID", collezione_key);
                action_default(request, response);
            } else {
                if (request.getParameter("modifica_collezione") != null) {
                    action_collezione(request, response);
                } else if (request.getParameter("modificaUtenti") != null) {
                    action_utenti(request, response);
                } else if (request.getParameter("elimina_disco") != null) {
                    action_delete(request, response);
                } else if (request.getParameter("nuovo_disco") != null) {
                    action_nuovoDisco(request, response);
                } else {
                    action_disco(request, response);
                }

                response.sendRedirect("collezioni");
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataException e) {
            throw new RuntimeException(e);
        }

    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {

        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession sessione = request.getSession(true);

            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("collezioniPath", true);

            if (SecurityLayer.checkSession(request) == null) {
                response.sendRedirect("home");
            } else {
                request.setAttribute("session", true);
                request.setAttribute("username", sessione.getAttribute("username"));
                request.setAttribute("email", sessione.getAttribute("email"));
                request.setAttribute("userid", sessione.getAttribute("userid"));
                user_key = (Integer) sessione.getAttribute("userid");
            }

            Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);

            request.setAttribute("page_title", "Modifica " + collezione.getNome());
            request.setAttribute("collezione", collezione);
            request.setAttribute("lista_utenti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti());
            request.setAttribute("lista_artisti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtisti());

            List<ListaDischi> dettagliDischi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getDischiByCollezione(collezione_key);
            List<Disco> disco = new ArrayList<>();
            for (ListaDischi i : dettagliDischi) {
                disco.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(i.getDisco().getKey()));
            }

            request.setAttribute("dettagliDischi", dettagliDischi);
            request.setAttribute("dischi", disco);
            request.setAttribute("collezione_key", collezione_key);

            List<Disco> lista_dischi = new ArrayList<>();
            lista_dischi.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi());

            request.setAttribute("lista_dischi", lista_dischi);

            List<Integer> listautentiid = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByCollezione(collezione_key);
            List<Utente> utentiAutorizzati = new ArrayList<>();
            for (Integer i: listautentiid) {
                utentiAutorizzati.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(i));
            }

            request.setAttribute("utenti_autorizzati", utentiAutorizzati);



            if (collezione.getUtente().getKey() == user_key) {
                res.activate("modifica_collezione.html.ftl", request, response);
            } else {
                response.sendRedirect("collezioni");
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_collezione(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, ServletException, TemplateManagerException {

        String nome = request.getParameter("nome");
        String condivisione = request.getParameter("condivisione");
        String error_msg = "";

        Collezione uCollezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);

        if (!nome.isEmpty()) {
            //Attributi collezione
            uCollezione.setNome(nome);
            uCollezione.setCondivisione(condivisione);
            ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().storeCollezione(uCollezione);

            response.sendRedirect("modificaCollezione?numero=" + uCollezione.getKey());
        } else {
            error_msg = "Inserisci un nome per la collezione!";
            request.setAttribute("error", error_msg);
            action_default(request, response);
        }
    }

    private void action_utenti(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, TemplateManagerException {

        Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);

        List<Integer> idutentiAutorizzatiV = new ArrayList<>(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByCollezione(collezione_key));

        List<Utente> utentiAutorizzatiV = new ArrayList<>();
        for (Integer i : idutentiAutorizzatiV) {
            utentiAutorizzatiV.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(i));
        }

        String[] usernames = request.getParameterValues("utentiS");

        List<Utente> utentiAutorizzatiN = new ArrayList<>();

        if (usernames != null) {
            for (String username : usernames) {
                utentiAutorizzatiN.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(Integer.parseInt(username)));
            }
        }

        //elimino gli utenti non più presenti
        for (Utente i : utentiAutorizzatiV) {
            if (!utentiAutorizzatiN.contains(i)) {
                ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().deleteUtenteAutorizzato(collezione_key, i.getKey());
                // email di notifica
                try {
                    String text = " ha rimosso la condivisione con lei della sua collezione ";
                    UtilityMethods.sharingEmail(System.getenv("FILE_DIRECTORY") + "/" + i.getNickname() + ".txt", i, collezione, text);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }

        if (!utentiAutorizzatiN.isEmpty()) {
            //inserisco i nuovi utenti
            for (Utente i : utentiAutorizzatiN) {
                if (!utentiAutorizzatiV.contains(i)) {
                    UtentiAutorizzatiImpl u = new UtentiAutorizzatiImpl();
                    u.setCollezione(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key));
                    u.setUtente(i);
                    ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().storeUtentiAutorizzati(u);
                    // email di notifica
                    Utente utente = u.getUtente();
                    try {
                        String text = " ha condiviso con lei la sua collezione ";
                        UtilityMethods.sharingEmail(System.getenv("FILE_DIRECTORY") + "/" + utente.getNickname() + ".txt", utente, collezione, text);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        response.sendRedirect("modificaCollezione?numero=" + collezione_key);
    }

    private void action_nuovoDisco(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {

        Disco disco = new DiscoImpl();
        disco.setNome(request.getParameter("nome"));
        disco.setEtichetta(request.getParameter("etichetta"));
        disco.setAnno(Date.valueOf(request.getParameter("anno")));
        disco.setArtista(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtista(Integer.parseInt(request.getParameter("artistaID"))));
        disco.setCreatore(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(user_key));

        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().storeDisco(disco);

        disco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getLast();


        ListaDischi listaDischi = new ListaDischiImpl();
        listaDischi.setDisco(disco);
        listaDischi.setCollezione(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key));
        listaDischi.setBarcode(request.getParameter("barcode"));
        listaDischi.setImgCopertina("images/templateimg/core-img/disco_default.jpeg");
        listaDischi.setStato(request.getParameter("stato"));
        listaDischi.setFormato(request.getParameter("formato"));
        listaDischi.setNumeroCopie(Integer.parseInt(request.getParameter("numeroCopie")));

        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().storeListaDischi(listaDischi);

        response.sendRedirect("modificaDisco?numero=" + disco.getKey() + "&collezione=" + collezione_key + "&formato=" + listaDischi.getFormato());
    }

    private void action_disco(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException, TemplateManagerException, ServletException {

        int disco_key = SecurityLayer.checkNumeric(request.getParameter("discoID"));
        int numeroCopie = SecurityLayer.checkNumeric(request.getParameter("numeroCopie"));
        String formato = request.getParameter("formato");
        String stato = request.getParameter("stato");
        String barcode = request.getParameter("barcode");
        String imgCopertina = "images/templateimg/core-img/disco_default.jpeg";

        Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);
        Disco disco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(disco_key);

        if (((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDisco(collezione_key, disco_key, formato) == null) {
            // insert
            ListaDischi listaDischi = new ListaDischiImpl();
            listaDischi.setCollezione(collezione);
            listaDischi.setDisco(disco);
            listaDischi.setNumeroCopie(numeroCopie);
            listaDischi.setFormato(formato);
            listaDischi.setStato(stato);
            listaDischi.setBarcode(barcode);
            listaDischi.setImgCopertina(imgCopertina);
            ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().storeListaDischi(listaDischi);
        }

        response.sendRedirect("modificaCollezione?numero=" + collezione_key);

    }

    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {

        int listaDisco_key = SecurityLayer.checkNumeric(request.getParameter("listaDiscoID"));

        ListaDischi listaDischi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDischi(listaDisco_key);
        int collezione_key = listaDischi.getCollezione().getKey();

        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().deleteListaDischi(listaDischi);

        response.sendRedirect("modificaCollezione?numero=" + collezione_key);
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Servlet per la modifica di una collezione";
    }

}
