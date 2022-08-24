package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.impl.UtenteImpl;
import it.fdd.sharedcollection.data.model.Utente;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class Registrazione extends SharedCollectionBaseController{
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
            request.setAttribute("page_title", "Registrazione");
            request.setAttribute("registrationPath", true);
            request.setAttribute("utenti", ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtenti());
            res.activate("registrazione.ftl.html", request, response);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_register(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");

        if(username.isEmpty() || email.isEmpty() || password.isEmpty() || password2.isEmpty()){
            request.setAttribute("dataC", "true");
        }else{
            request.setAttribute("dataC", "false");
        }

        if(!password.equals(password2)){
            request.setAttribute("passwordC", "true");
        }else {
            request.setAttribute("passwordC", "false");
        }

        if(!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty() && password.equals(password2)){

            Utente utente = new UtenteImpl();
            utente.setCognome(cognome);
            utente.setEmail(email);
            utente.setNome(nome);
            utente.setPassword(password);
            utente.setNickname(username);

            int userID = 0;

            try{
                utente = ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getUtenteDAO().storeUtente(utente);
            }catch (DataException ex){
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
                action_error(request, response);
            }

            //una volta registrato effettuiamo il login
            try {
                utente = ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getUtenteDAO().login(email, password);
            } catch (DataException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
                action_error(request, response);
            }

            if (utente != null) {
                userID = utente.getKey();
                SecurityLayer.createSession(request, email, userID);
            } else {
                request.setAttribute("message", "Email e/o password errati");
                action_error(request, response);
            }

            //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
            if (request.getParameter("referrer") != null) {
                response.sendRedirect(request.getParameter("referrer"));
            } else {
                response.sendRedirect("home");
            }
        } else {
            request.setAttribute("exception", new Exception("Login failed"));
            action_error(request, response);
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (request.getParameter("register") != null) {
                action_register(request, response);
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
