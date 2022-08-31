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
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class ProfiloUtente extends SharedCollectionBaseController{
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession sessione = request.getSession(true);
            //aggiungiamo al template un wrapper che ci permette di chiamare la funzione stripSlashes
            //add to the template a wrapper object that allows to call the stripslashes function
            request.setAttribute("session", false);

            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("utentiPath", true);


            if (SecurityLayer.checkSession(request) != null) {
                request.setAttribute("session", true);
                request.setAttribute("username",sessione.getAttribute("username"));
                request.setAttribute("email",sessione.getAttribute("email"));
            }

            res.activate("profilo_utente.ftl", request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        int user_key;
        Utente profilo;
        try {
            if (request.getParameter("id") != null) {
                user_key = SecurityLayer.checkNumeric(request.getParameter("id"));

                profilo = ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtente(user_key);
                request.setAttribute("page_title", profilo.getNickname().toUpperCase());
                request.setAttribute("profilo",profilo);
                request.setAttribute("n_dati", ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getUtenteDAO().getUtenteInfo(profilo.getKey()));
                action_default(request,response);
            } else {
                response.sendRedirect("showUtenti");
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataException ex){
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return " Profilo utente servlet";
    }
}
