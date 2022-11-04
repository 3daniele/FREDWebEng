package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.impl.ListaDischiImpl;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Disco;
import it.fdd.sharedcollection.data.model.ListaDischi;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImportaDisco extends SharedCollectionBaseController {

    private int collezione_key = 0;
    private int user_key = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            if (request.getParameter("c") != null) {
                collezione_key = SecurityLayer.checkNumeric(request.getParameter("c"));
                action_default(request, response);
            } else {
                if (request.getParameter("cercaDisco") != null) {
                    action_cerca(request, response);
                } else if (request.getParameter("aggiungiDisco") != null) {
                    action_disco(request, response);
                } else if (request.getParameter("add") != null) {
                    action_add(request, response);
                }
                response.sendRedirect("collezioni");
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException | TemplateManagerException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataException e) {
            throw new RuntimeException(e);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, TemplateManagerException {

        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession sessione = request.getSession(true);

            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("collezioniPath", true);

            if (SecurityLayer.checkSession(request) == null) {
                response.sendRedirect("home");
            } else {
                request.setAttribute("session", true);
                request.setAttribute("username", sessione.getAttribute("username"));
                request.setAttribute("email", sessione.getAttribute("email"));
                request.setAttribute("userid", sessione.getAttribute("userid"));
                user_key = (Integer) sessione.getAttribute("userid");
            }

            Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);

            request.setAttribute("page_title", "Modifica condivisione per " + collezione.getNome());
            request.setAttribute("collezione", collezione);
            request.setAttribute("collezione_key", collezione_key);

            if (collezione.getUtente().getKey() == user_key) {
                res.activate("importa_disco.html.ftl", request, response);
            } else {
                response.sendRedirect("collezioni");
            }

        } catch (Exception ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_cerca(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException, TemplateManagerException, ServletException {

        String nome = request.getParameter("nome");

        List<Disco> dischi_ = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi();
        List<Disco> dischi = new ArrayList<>();

        for (Disco disco : dischi_) {
            if (disco.getNome().toLowerCase().contains(nome.toLowerCase())) {
                dischi.add(disco);
            }
        }

        request.setAttribute("ricerca", true);
        request.setAttribute("dischi", dischi);

        action_default(request, response);
    }

    private void action_disco(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException, TemplateManagerException, ServletException {

        int disco_key = SecurityLayer.checkNumeric(request.getParameter("discoID"));

        Disco disco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(disco_key);
        request.setAttribute("infoDisco", true);
        request.setAttribute("disco", disco);

        action_default(request, response);
    }

    private void action_add(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException, TemplateManagerException, ServletException {

        int disco_key = SecurityLayer.checkNumeric(request.getParameter("discoID"));
        String numeroCopie = (request.getParameter("numeroCopie"));
        String formato = request.getParameter("formato");
        String stato = request.getParameter("stato");
        String barcode = request.getParameter("barcode");
        String imgCopertina = "images/templateimg/core-img/disco_default.jpeg";
        String msg_err;

        Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);
        Disco disco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(disco_key);

        request.setAttribute("infoDisco", true);
        request.setAttribute("disco", disco);

        if (numeroCopie.equals("Seleziona quantità")) {
            msg_err = "Inserisci il numero di copie in possesso!";
            request.setAttribute("error", msg_err);
            action_default(request, response);
        }

        if (stato.equals("Seleziona stato")) {
            msg_err = "Inserisci lo stato del disco in possesso!";
            request.setAttribute("error", msg_err);
            action_default(request, response);
        }

        if (formato.equals("Seleziona formato")) {
            msg_err = "Inserisci il formato del disco in possesso!";
            request.setAttribute("error", msg_err);
            action_default(request, response);
        }

        if (((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDisco(collezione_key, disco_key, formato) == null) {
            // insert
            ListaDischi listaDischi = new ListaDischiImpl();
            listaDischi.setCollezione(collezione);
            listaDischi.setDisco(disco);
            listaDischi.setNumeroCopie(Integer.parseInt(numeroCopie));
            listaDischi.setFormato(formato);
            listaDischi.setStato(stato);
            listaDischi.setBarcode(barcode);
            listaDischi.setImgCopertina(imgCopertina);
            ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().storeListaDischi(listaDischi);
        } else {
            msg_err = "Formato del disco già presente!";
            request.setAttribute("error", msg_err);
            action_default(request, response);
        }

        response.sendRedirect("modificaCollezione?numero=" + collezione_key);
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
        return "Servlet per l'importazione di un disco";
    }
}
