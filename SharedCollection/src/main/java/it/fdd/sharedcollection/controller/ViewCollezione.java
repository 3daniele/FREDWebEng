package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Disco;
import it.fdd.sharedcollection.data.model.ListaDischi;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ViewCollezione extends SharedCollectionBaseController {

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
        request.setAttribute("collezioniPath", true);

        try {
            Integer collezione_key = (Integer) request.getAttribute("collezioneID");
            Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);

            List<Integer> users = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByCollezione(collezione_key);
            HttpSession sessione = request.getSession(true);
            int userID = 0;
            if (SecurityLayer.checkSession(request) != null) {
                userID = (int) sessione.getAttribute("userid");

                request.setAttribute("session", true);
                request.setAttribute("username", sessione.getAttribute("username"));
                request.setAttribute("email", sessione.getAttribute("email"));
                request.setAttribute("userid", sessione.getAttribute("userid"));
            }
            if (collezione.getCondivisione().equals("pubblica") || collezione.getUtente().getKey() == userID || users.contains(userID)) {
                request.setAttribute("page_title", collezione.getNome().toUpperCase());
                request.setAttribute("collezione", collezione);
            } else {
                response.sendRedirect("collezioni");
            }

            List<ListaDischi> dettagliDischi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getDischiByCollezione(collezione_key);
            List<Disco> disco = new ArrayList<>();
            for (ListaDischi i : dettagliDischi) {
                disco.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(i.getDisco().getKey()));
            }

            request.setAttribute("dettagliDischi", dettagliDischi);
            request.setAttribute("dischi", disco);

            res.activate("collezione.ftl", request, response);
        } catch (DataException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }

    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        int collezione_key;
        try {
            if (request.getParameter("numero") != null) {
                collezione_key = SecurityLayer.checkNumeric(request.getParameter("numero"));
                request.setAttribute("collezioneID", collezione_key);
                action_default(request, response);
            } else {
                response.sendRedirect("collezioni");
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
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet collezione";
    }
}
