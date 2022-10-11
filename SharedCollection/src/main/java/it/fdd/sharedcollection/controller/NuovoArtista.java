package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.impl.ArtistaImpl;
import it.fdd.sharedcollection.data.model.Artista;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NuovoArtista extends SharedCollectionBaseController {

    private int disco_key;
    private int collezione_key;
    private String formato;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (request.getParameter("n") != null) {
                disco_key = SecurityLayer.checkNumeric(request.getParameter("n"));
                collezione_key = SecurityLayer.checkNumeric(request.getParameter("c"));
                formato = request.getParameter("f");
                action_default(request, response);
            }
            if (request.getParameter("nuovo_artista") != null) {
                action_artista(request, response);
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

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession sessione = request.getSession(true);

            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("collezioniPath", true);
            request.setAttribute("page_title", "Nuovo Artista");

            if (SecurityLayer.checkSession(request) == null) {
                response.sendRedirect("home");
            } else {
                request.setAttribute("session", true);
                request.setAttribute("username", sessione.getAttribute("username"));
                request.setAttribute("email", sessione.getAttribute("email"));
                request.setAttribute("userid", sessione.getAttribute("userid"));
            }

            res.activate("nuovo_artista.html.ftl", request, response);

        } catch (Exception ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_artista(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, ServletException, TemplateManagerException {

        String nome = request.getParameter("nome");
        String cognome = request.getParameter("cognome");
        String nomeArte = request.getParameter("nomeArte");
        String error_msg;

        if (nome.isEmpty()) {
            error_msg = "Inserisci il nome dell'artista!";
            request.setAttribute("error", error_msg);
            request.setAttribute("cognome", cognome);
            request.setAttribute("nomeArte", nomeArte);
            action_default(request, response);
            return;
        }
        if (cognome.isEmpty()) {
            error_msg = "Inserisci il cognome dell'artista!";
            request.setAttribute("error", error_msg);
            request.setAttribute("nome", nome);
            request.setAttribute("nomeArte", nomeArte);
            action_default(request, response);
            return;
        }
        if (nomeArte.isEmpty()) {
            error_msg = "Inserisci il nome d'arte dell'artista!";
            request.setAttribute("error", error_msg);
            request.setAttribute("cognome", cognome);
            request.setAttribute("nome", nome);
            action_default(request, response);
            return;
        }
        if (((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtista(nomeArte) != null) {
            error_msg = "Artista già presente!";
            request.setAttribute("error", error_msg);
            request.setAttribute("cognome", cognome);
            request.setAttribute("nome", nome);
            request.setAttribute("nomeArte", nomeArte);
            action_default(request, response);
            return;
        }

        Artista artista = new ArtistaImpl();
        artista.setNome(nome);
        artista.setCognome(cognome);
        artista.setNomeArte(nomeArte);

        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().storeArtista(artista);

        response.sendRedirect("modificaDisco?numero=" + disco_key + "&collezione=" + collezione_key + "&formato=" + formato);
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    /**
     * Returns a short description of the servlet.
     */
    public String getServletInfo() {
        return "Servlet per la creazione di un nuovo artista";
    }
}
