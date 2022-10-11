package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.sharedcollection.data.utility.BCrypt;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Login extends SharedCollectionBaseController {

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
            //aggiungiamo al template un wrapper che ci permette di chiamare la funzione stripSlashes
            //add to the template a wrapper object that allows to call the stripslashes function

            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("loginPath", true);
            request.setAttribute("page_title", "Login");
            request.setAttribute("utenti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti());

            if (SecurityLayer.checkSession(request) != null) {
                request.setAttribute("session", true);
                response.sendRedirect("home");
            }

            res.activate("login.html.ftl", request, response);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_login(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, ServletException, TemplateManagerException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");

        if (!email.isEmpty() && !password.isEmpty()) {

            Utente utente = null;
            int userID = 0;

            try {
                String my_pass = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getPassword(email);

                if (my_pass == null) {
                    request.setAttribute("error", "Credenziali non corrette.");
                    request.setAttribute("email", email);
                    action_default(request, response);
                }
                if (BCrypt.checkpw(password, my_pass)) {

                    try {
                        utente = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(email);

                    } catch (DataException ex) {
                        request.setAttribute("message", "Data access exception: " + ex.getMessage());
                        action_error(request, response);
                    }


                    if (utente != null) {
                        userID = utente.getKey();
                        if (utente.getToken() == 1) {
                            SecurityLayer.createSession(request, utente.getNickname(), userID, email);
                        } else {
                            request.setAttribute("error", "Verifica la tua email prima di proseguire!");
                            request.setAttribute("email", email);
                            action_default(request, response);
                        }

                    } else {
                        request.setAttribute("error", "Email e/o password errati!");
                        request.setAttribute("email", email);
                        action_default(request, response);
                    }

                    //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
                    if (request.getParameter("referrer") != null) {
                        response.sendRedirect(request.getParameter("referrer"));
                    } else {
                        response.sendRedirect("home");
                    }
                } else {
                    request.setAttribute("error", "Email e/o password errati!");
                    request.setAttribute("email", email);
                    action_default(request, response);
                }
            } catch (DataException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
                action_error(request, response);
            }
        } else {
            request.setAttribute("error", "Compila tutti i campi!");
            request.setAttribute("email", email);
            action_default(request, response);
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (request.getParameter("login") != null) {
                action_login(request, response);
            } else {
                String https_redirect_url = String.valueOf(SecurityLayer.checkHttps(request));
                request.setAttribute("https-redirect", https_redirect_url);
                action_default(request, response);
            }
        } catch (IOException | DataException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Short description";
    }
}
