package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.impl.UtentiAutorizzatiImpl;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.sharedcollection.data.utility.UtilityMethods;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModificaCondivisione extends SharedCollectionBaseController {

    private int collezione_key = 0;
    private int disco_key = 0;
    private int user_key = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            if (request.getParameter("c") != null) {
                collezione_key = SecurityLayer.checkNumeric(request.getParameter("c"));
                request.setAttribute("collezioneID", collezione_key);
                action_default(request, response);
            } else {
                if (request.getParameter("cercaUtente") != null) {
                    action_ricerca(request, response);
                } else if (request.getParameter("aggiungiUtente") != null) {
                    action_aggiunta(request, response);
                } else if (request.getParameter("eliminaUtente") != null) {
                    action_delete(request, response);
                } else {
                    response.sendRedirect("collezioni");
                }
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

            request.setAttribute("page_title", "Modifica condivisione per " + collezione.getNome());
            request.setAttribute("collezione", collezione);

            request.setAttribute("collezione_key", collezione_key);


            List<Integer> listautentiid = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByCollezione(collezione_key);
            List<Utente> utentiAutorizzati = new ArrayList<>();
            for (Integer i : listautentiid) {
                utentiAutorizzati.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(i));
            }

            request.setAttribute("utenti_autorizzati", utentiAutorizzati);

            if (collezione.getUtente().getKey() == user_key) {
                res.activate("modifica_condivisione.html.ftl", request, response);
            } else {
                response.sendRedirect("collezioni");
            }

        } catch (Exception ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_ricerca(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, ServletException, TemplateManagerException {

        String nome = request.getParameter("username");

        List<Utente> utenti_ = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti();
        List<Utente> utenti = new ArrayList<>();

        for (Utente utente : utenti_) {
            if (utente.getNickname().toLowerCase().contains(nome.toLowerCase())) {
                utenti.add(utente);
            }
        }

        request.setAttribute("cercato", true);
        request.setAttribute("utenti_cercati", utenti);

        action_default(request, response);

    }

    private void action_aggiunta(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, TemplateManagerException {

        Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);
        Integer utenteid = SecurityLayer.checkNumeric(request.getParameter("utenteIDA"));

        Utente utenteE = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(utenteid);

        //elimino gli utenti non più presenti
        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().deleteUtenteAutorizzato(collezione_key, utenteE.getKey());


        UtentiAutorizzatiImpl u = new UtentiAutorizzatiImpl();
        u.setCollezione(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key));
        u.setUtente(utenteE);
        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().storeUtentiAutorizzati(u);
        // email di notifica
        Utente utente = u.getUtente();
        try {
            String text = " ha condiviso con lei la sua collezione ";
            UtilityMethods.sharingEmail(System.getenv("FILE_DIRECTORY") + "/" + utenteE.getNickname() + ".txt", utenteE, collezione, text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        request.setAttribute("cercato", false);
        response.sendRedirect("modificaCondivisione?collezioneId=" + collezione_key);
    }

    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {
        Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);
        Integer utenteid = SecurityLayer.checkNumeric(request.getParameter("utenteIDE"));

        Utente utenteE = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(utenteid);

        //elimino gli utenti non più presenti
        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().deleteUtenteAutorizzato(collezione_key, utenteE.getKey());

        // email di notifica
        try {
            String text = " ha rimosso la condivisione con lei della sua collezione ";
            UtilityMethods.sharingEmail(System.getenv("FILE_DIRECTORY") + "/" + utenteE.getNickname() + ".txt", utenteE, collezione, text);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        response.sendRedirect("modificaCondivisione?collezioneId=" + collezione_key);
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
        return "Servlet per la modifica della condivisione di una collezione";
    }

}
