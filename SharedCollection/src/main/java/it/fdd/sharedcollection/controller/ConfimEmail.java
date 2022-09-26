package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
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


    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
        return;
    }


    private void confirmEmail(HttpServletRequest request, HttpServletResponse response) throws DataException, TemplateManagerException, IOException {

        String verification_code = request.getParameter("verification_code");

        System.out.println(verification_code);
        String link = verification_code;
        System.out.println(link);
        Utente utente = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getLink(link);
        System.out.println(utente.getKey());

        if (utente != null) {
            if (utente.getToken() == 0) {
                int token = 1;
                ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().insertToken(utente, token);
                TemplateResult results = new TemplateResult(getServletContext());
                response.sendRedirect("login");
            } else {
                String error_msg = "Email già verificata";
                request.setAttribute("exception", error_msg);
            }
        } else {
            String error_msg = "Codice errato";
            request.setAttribute("exception", error_msg);

        }
    }
}
