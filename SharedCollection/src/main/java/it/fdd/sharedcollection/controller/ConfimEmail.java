package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.model.Utente;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ConfimEmail extends SharedCollectionBaseController {
    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            if (request.getParameter("verification_code") != null) {
                confirmEmail(request, response);
            }

        } catch (DataException | TemplateManagerException | IOException e) {
            request.setAttribute("exception", e);
            action_error(request, response);
        }

    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {

        try {
            TemplateResult res = new TemplateResult(getServletContext());

            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("loginPath", true);
            request.setAttribute("page_title", "Login");
            request.setAttribute("utenti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti());

            if (SecurityLayer.checkSession(request) != null) {
                request.setAttribute("session", true);
                response.sendRedirect("home");
            }
            request.setAttribute("confirmEmail", request.getAttribute("confirmEmail"));
            res.activate("login.html.ftl", request, response);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }


    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }


    private void confirmEmail(HttpServletRequest request, HttpServletResponse response) throws DataException, TemplateManagerException, IOException, ServletException {

        String link = request.getParameter("verification_code");
        Utente utente = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getLink(link);

        if (utente != null) {
            if (utente.getToken() == 0) {
                int token = 1;
                ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().insertToken(utente, token);
                TemplateResult results = new TemplateResult(getServletContext());
                request.setAttribute("confirmEmail", "Email confermata correttamente!");
                action_default(request, response);
                //response.sendRedirect("login");
            } else {
                request.setAttribute("confirmEmail", "Email già confermata!");
                action_default(request, response);
            }
        } else {
            response.sendRedirect("home");
        }
    }
}
