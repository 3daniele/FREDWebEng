package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.model.*;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InfoDisco extends SharedCollectionBaseController {

    private int user_key = 0;
    private int disco_key;
    private int collezione_key;
    String formato;

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            if (request.getParameter("numero") != null) {
                disco_key = SecurityLayer.checkNumeric(request.getParameter("numero"));
                collezione_key = SecurityLayer.checkNumeric(request.getParameter("collezione"));
                formato = request.getParameter("formato");
                request.setAttribute("discoID", disco_key);
                request.setAttribute("collezioneID", collezione_key);
                request.setAttribute("formato", formato);
                action_default(request, response);
            } else {
                if (request.getParameter("elimina_disco") != null) {
                    action_delete(request, response);
                }
                response.sendRedirect("collezioni");
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (DataException e) {
            throw new RuntimeException(e);
        }
    }

    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException {

        TemplateResult res = new TemplateResult(getServletContext());
        HttpSession sessione = request.getSession(true);

        request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
        request.setAttribute("collezioniPath", true);

        if (SecurityLayer.checkSession(request) != null) {
            request.setAttribute("session", true);
            request.setAttribute("username", sessione.getAttribute("username"));
            request.setAttribute("email", sessione.getAttribute("email"));
            request.setAttribute("userid", sessione.getAttribute("userid"));
            user_key = Integer.parseInt(sessione.getAttribute("userid").toString());
        }

        try {
            Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);
            Disco disco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(disco_key);
            List<Integer> utentiAutorizzati = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByCollezione(collezione_key);
            List<ListaBrani> listaBrani = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaBraniDAO().getListeBrani(disco_key);
            ListaDischi infoDisco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDisco(collezione_key, disco_key, formato);
            List<Canzone> canzoni = new ArrayList<>();
            List<ListaArtisti> artisti = new ArrayList<>();
            List<ListaGeneri> generi = new ArrayList<>();

            if (collezione.getCondivisione().equals("privata") && user_key != collezione.getUtente().getKey() && !utentiAutorizzati.contains(user_key)) {
                response.sendRedirect("home");
            }

            request.setAttribute("page_title", disco.getNome());

            for (ListaBrani brano : listaBrani) {
                canzoni.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCanzoneDAO().getCanzone(brano.getCanzone().getKey()));
                artisti.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaArtistiDAO().getListaArtistiByCanzone(brano.getCanzone().getKey()));
                generi.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaGeneriDAO().getListaGeneriByCanzone(brano.getCanzone().getKey()));
            }

            request.setAttribute("collezione", collezione);
            request.setAttribute("disco", disco);
            request.setAttribute("listaBrani", listaBrani);
            request.setAttribute("infoDisco", infoDisco);
            request.setAttribute("canzoni", canzoni);
            request.setAttribute("listaArtisti", artisti);
            request.setAttribute("listaGeneri", generi);

            res.activate("info_disco.html.ftl", request, response);
        } catch (DataException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException e) {
            throw new RuntimeException(e);
        }
    }

    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {

        int listaDisco_key = SecurityLayer.checkNumeric(request.getParameter("listaDiscoID"));

        ListaDischi listaDischi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDischi(listaDisco_key);

        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().deleteListaDischi(listaDischi);

        response.sendRedirect("collezione?numero=" + collezione_key);
    }
}
