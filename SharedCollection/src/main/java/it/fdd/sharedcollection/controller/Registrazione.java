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
import it.fdd.sharedcollection.data.utility.EmailTypes;
import it.fdd.sharedcollection.data.utility.UtilityMethods;
import it.fdd.sharedcollection.utility.BCrypt;


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

            if (SecurityLayer.checkSession(request) != null) {
                request.setAttribute("session", true);
                response.sendRedirect("home");
            }

            request.setAttribute("utenti", ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtenti());
            res.activate("registrazione.ftl", request, response);
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
        String error_msg = "";
        boolean valid = true;


        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty() && password.equals(password2)) {


            // Sanitizzo tutti i campi e controllo se i campi sono validi
            username = SecurityLayer.removeSpecialChars(username);

            if (!SecurityLayer.isEmailValid(email)) {
                request.setAttribute("exception", new Exception("Registrazione failed"));
                action_error(request, response);
                valid = false;
            }
            if (!password.equals(password2)) {
                request.setAttribute("exception", new Exception("Registrazione failed"));
                action_error(request, response);
                valid = false;
            }
            // Controllo se l'email e l'username sono presenti nel DB
            try {

                Utente exists_email = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(email);


                try {
                    Utente exists_username = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenteByUsername(username);
                    if (exists_email != null || exists_username != null) {

                        valid = false;
                    }
                    if (valid) {

                        Utente utente = new UtenteImpl();
                        utente.setCognome(cognome);
                        utente.setEmail(email);
                        utente.setNome(nome);
                        utente.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                        utente.setNickname(username);

                        System.out.println(utente.getPassword());

                        int userID = 0;


                        try {
                            utente = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().storeUtente(utente);
                            System.out.println(utente.getPassword());
                            UtilityMethods.sendEmailWithCodes(this.getServletContext().getRealPath("/WEB-INF/links.txt"), utente, "Conferma la tua email cliccando sul link in basso", EmailTypes.CONFIRM_EMAIL);

                        } catch (DataException ex) {
                            request.setAttribute("message", "Data access exception: " + ex.getMessage());
                            action_error(request, response);
                        } catch (Exception e) {
                            throw new RuntimeException(e);
                        }
                        System.out.println(utente.getPassword());
                        //una volta registrato effettuiamo il login
                        try {
                            String my_pass = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getPassword(utente.getEmail());
                            if (BCrypt.checkpw(password, my_pass)) {
                                if (my_pass == null) {
                                    // Email non trovata
                                    request.setAttribute("error", "Credenziali non corrette.");
                                    action_default(request, response);
                                }
                                utente = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(utente.getEmail());
                            }
                        } catch (DataException ex) {
                            request.setAttribute("message", "Data access exception: " + ex.getMessage());
                            action_error(request, response);
                        } catch (ServletException e) {
                            throw new RuntimeException(e);
                        } catch (TemplateManagerException e) {
                            throw new RuntimeException(e);
                        }

                        if (utente != null) {
                            userID = utente.getKey();
                            SecurityLayer.createSession(request, utente.getNickname(), userID, email);

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
                    }
                } catch (DataException ex) {
                    request.setAttribute("message", "Data access exception: " + ex.getMessage());
                    response.sendRedirect("home");
                }
            } catch (DataException ex) {
                request.setAttribute("message", "Data access exception: " + ex.getMessage());
                response.sendRedirect("home");
            }


        } else {
            request.setAttribute("exception", new Exception(error_msg));
            response.sendRedirect("home");

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
