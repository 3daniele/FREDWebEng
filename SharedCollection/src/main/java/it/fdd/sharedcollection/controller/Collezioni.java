package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.ListaDischi;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Collezioni extends SharedCollectionBaseController {

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
            HttpSession sessione = request.getSession(true);

            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("page_title", "Collezioni");
            request.setAttribute("collezioniPath", true);
            request.setAttribute("collezioniPubbliche", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniPubbliche());
            request.setAttribute("dischi", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListeDischi());

            if (SecurityLayer.checkSession(request) != null) {
                int userID = (int) sessione.getAttribute("userid");
                List<UtentiAutorizzati> users = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByUser(userID);

                request.setAttribute("session", true);
                request.setAttribute("username", sessione.getAttribute("username"));
                request.setAttribute("email", sessione.getAttribute("email"));
                request.setAttribute("userid", sessione.getAttribute("userid"));
                request.setAttribute("collezioniPersonali", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniByUtente(userID));
                request.setAttribute("collezioniCondivise", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniCondivise(users));
            }
            res.activate("collezioni.html.ftl", request, response);
        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        request.setAttribute("page_title", "Collezioni");

        try {
            if (request.getParameter("eliminaCollezione") != null) {
                action_eliminaCollezione(request, response);
            } else {
                action_default(request, response);
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }catch (DataException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }
    }

    private void action_eliminaCollezione(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {
        Integer collezioneid = Integer.parseInt(request.getParameter("collezioneID"));
        Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezioneid);
        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().deleteCollezione(collezione);

        response.sendRedirect("collezioni");
    }


    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet collezioni";
    }
}
