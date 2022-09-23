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
import it.fdd.sharedcollection.data.impl.CollezioneImpl;
import it.fdd.sharedcollection.data.impl.UtentiAutorizzatiImpl;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;

import java.io.IOException;
import java.util.List;

public class ModificaCollezione extends SharedCollectionBaseController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        int collezione_key;
        try {
            if (request.getParameter("numero") != null) {
                collezione_key = SecurityLayer.checkNumeric(request.getParameter("numero"));
                request.setAttribute("collezioneID", collezione_key);
                action_default(request, response);
            } else {
                if (request.getParameter("modifica_collezione") != null) {
                    action_collezione(request, response);
                }

                if (request.getParameter("modifica_utenti") != null) {
                    action_utenti(request, response);
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

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {

        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession sessione = request.getSession(true);
            int user_key = 0;

            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("collezioniPath", true);

            if (SecurityLayer.checkSession(request) == null) {
                response.sendRedirect("home");
            }else{
                request.setAttribute("session", true);
                request.setAttribute("username",sessione.getAttribute("username"));
                request.setAttribute("email",sessione.getAttribute("email"));
                request.setAttribute("userid", sessione.getAttribute("userid"));
                user_key = (Integer)sessione.getAttribute("userid");
            }

            Integer collezioneID = (Integer)request.getAttribute("collezioneID");
            Collezione collezione = ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezioneID);

            request.setAttribute("page_title", "Modifica "+collezione.getNome());
            request.setAttribute("collezione", collezione);
            request.setAttribute("lista_utenti", ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtenti());
            request.setAttribute("utenti_autorizzati", ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByCollezione(collezioneID));

            if (collezione.getUtente().getKey() == user_key) {
                res.activate("modifica_collezione.ftl", request, response);
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
        Integer collezioneid = SecurityLayer.checkNumeric(request.getParameter("collezioneID"));
        String error_msg = "";

        Collezione uCollezione = ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezioneid);

        if (!nome.isEmpty()) {
            //Attributi collezione
            uCollezione.setNome(nome);
            uCollezione.setCondivisione(condivisione);
            ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getCollezioneDAO().storeCollezione(uCollezione);

            response.sendRedirect("modificaCollezione?numero="+uCollezione.getKey());
        } else {
            error_msg="Alcuni  campi sono vuoti";
            request.setAttribute("exception", error_msg);
            action_default(request, response);
        }
    }

    private void action_utenti(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, ServletException, TemplateManagerException {

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
        return "Servlet per la modifica di una nuova collezione";
    }

}
