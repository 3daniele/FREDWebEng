package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class Home extends SharedCollectionBaseController {
    private String path="";

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {

        TemplateResult res = new TemplateResult(getServletContext());
        request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
        request.setAttribute("page_title", "Homepage");
        request.setAttribute("session", false);
        HttpSession sessione = request.getSession(true);

        path = request.getRequestURL().toString();
        if (path.equals("http://localhost:8080/SharedCollection_war/") || path.equals("http://localhost:8080/SharedCollection_war/home")){
            request.setAttribute("display", true);
        }else {
            request.setAttribute("display", false);
        }

        if (SecurityLayer.checkSession(request) != null) {
           request.setAttribute("session", true);
           request.setAttribute("username",sessione.getAttribute("username"));
           request.setAttribute("email",sessione.getAttribute("email"));
        }

        try {
            request.setAttribute("collezioni_home", ((SharedCollectionDataLayer)request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniPubbliche());
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }

        res.activate("index.ftl.html", request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        try {
            action_default(request, response);
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
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
        return "Write Article servlet";
    }// </editor-fold>
}
