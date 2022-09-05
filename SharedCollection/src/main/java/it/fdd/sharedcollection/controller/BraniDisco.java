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
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BraniDisco extends SharedCollectionBaseController {

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        int disco_key;
        try {
            if (request.getParameter("numero") != null) {
                disco_key = SecurityLayer.checkNumeric(request.getParameter("numero"));
                request.setAttribute("discoID", disco_key);
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

        request.setAttribute("page_title", "Lista Brani");
        request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
        request.setAttribute("collezioniPath", true);

        try {
            Integer disco_key = (Integer) request.getAttribute("discoID");
            Disco disco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(disco_key);
            List<ListaBrani> listaBrani = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaBraniDAO().getListeBrani(disco_key);
            List<Canzone> canzoni = new ArrayList<>();
            List<ListaArtisti> artisti = new ArrayList<>();
            List<ListaGeneri> generi = new ArrayList<>();

            for (ListaBrani brano : listaBrani) {
                canzoni.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCanzoneDAO().getCanzone(brano.getCanzone().getKey()));
                artisti.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaArtistiDAO().getListaArtistiByCanzone(brano.getCanzone().getKey()));
                generi.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaGeneriDAO().getListaGeneriByCanzone(brano.getCanzone().getKey()));
            }

            request.setAttribute("disco", disco);
            request.setAttribute("listaBrani", listaBrani);
            request.setAttribute("canzoni", canzoni);
            request.setAttribute("listaArtisti", artisti);
            request.setAttribute("listaGeneri", generi);

            res.activate("lista_brani_disco.ftl", request, response);
        } catch (DataException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException e) {
            throw new RuntimeException(e);
        }
    }
}
