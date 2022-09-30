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

public class Registrazione extends SharedCollectionBaseController {
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

            request.setAttribute("utenti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti());
            res.activate("registrazione.html.ftl", request, response);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }


    private void action_register(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, ServletException, TemplateManagerException {

        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String password = request.getParameter("password");
        String password2 = request.getParameter("password2");
        String error_msg = "";


        if (!username.isEmpty() && !email.isEmpty() && !password.isEmpty() && !password2.isEmpty()) {


            // Sanitizzo tutti i campi e controllo se i campi sono validi
            username = SecurityLayer.removeSpecialChars(username);

            if (!SecurityLayer.isEmailValid(email)) {

                error_msg = "Email non valida";
                request.setAttribute("exception", error_msg);
                action_default(request, response);

            }
            if (!password.equals(password2)) {
                request.setAttribute("exception", new Exception("Password non corrispondenti"));
                action_default(request, response);

            }
            // Controllo se l'email e l'username sono presenti nel DB
            try {
                Utente exists_email = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(email);
                try {
                    Utente exists_username = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenteByUsername(username);
                    if (exists_email != null) {
                        error_msg = "Email esistente";
                        request.setAttribute("exception", error_msg);
                        action_default(request, response);
                    }
                    if (exists_username != null) {
                        error_msg = "Username esistente";
                        request.setAttribute("exception", error_msg);
                        action_default(request, response);
                    }
                    Utente utente = new UtenteImpl();
                    utente.setCognome(cognome);
                    utente.setEmail(email);
                    utente.setNome(nome);
                    utente.setPassword(BCrypt.hashpw(password, BCrypt.gensalt()));
                    utente.setNickname(username);
                    int userID = 0;
                    try {
                        utente = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().storeUtente(utente);
                        UtilityMethods.sendEmailWithCodes(System.getenv("FILE_DIRECTORY")+"/links.txt", utente, "Conferma la tua email cliccando sul link in basso", EmailTypes.CONFIRM_EMAIL);


                        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().insertLink(utente, utente.getLink());


                    } catch (DataException ex) {
                        request.setAttribute("exception", "Data access exception: " + ex.getMessage());
                        action_error(request, response);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                    //una volta registrato effettuiamo il login
                    try {
                        String my_pass = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getPassword(utente.getEmail());
                        if (BCrypt.checkpw(password, my_pass)) {
                            if (my_pass == null) {
                                // Email non trovata
                                error_msg = "Credenziali non corrette";
                                request.setAttribute("exception", error_msg);
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
                        if (utente.getToken() == 1) {
                            userID = utente.getKey();
                            SecurityLayer.createSession(request, utente.getNickname(), userID, email);
                        } else {
                            error_msg = "controlla la tua email e verifica l'email";
                            request.setAttribute("exception", error_msg);
                        }
                    } else {
                        error_msg = "Email e/o password errati";
                        request.setAttribute("exception", error_msg);
                        action_default(request, response);


                    }

                    //se è stato trasmesso un URL di origine, torniamo a quell'indirizzo
                    if (request.getParameter("referrer") != null) {
                        response.sendRedirect(request.getParameter("referrer"));
                    } else {
                        response.sendRedirect("home");
                    }

                } catch (DataException ex) {
                    error_msg = "Alcuni  campi sono vuoti";
                    request.setAttribute("exception", error_msg);
                    action_default(request, response);

                }
            } catch (DataException ex) {
                error_msg = "Alcuni  campi sono vuoti";
                request.setAttribute("exception", error_msg);
                action_default(request, response);


            }


        } else {
            error_msg = "Alcuni  campi sono vuoti";
            request.setAttribute("exception", error_msg);
            action_default(request, response);

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
