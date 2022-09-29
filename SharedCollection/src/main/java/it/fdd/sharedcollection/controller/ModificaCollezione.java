package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.impl.CollezioneImpl;
import it.fdd.sharedcollection.data.impl.ListaDischiImpl;
import it.fdd.sharedcollection.data.impl.UtentiAutorizzatiImpl;
import it.fdd.sharedcollection.data.model.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ModificaCollezione extends SharedCollectionBaseController {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        int collezione_key;
        try {
            if (request.getParameter("numero") != null) {
                collezione_key = SecurityLayer.checkNumeric(request.getParameter("numero"));
                request.setAttribute("collezioneID", collezione_key);
                action_default(request, response);
            } else {
                if (request.getParameter("modifica_collezione") != null) {
                    action_collezione(request, response);
                }
                if (request.getParameter("modificaUtenti") != null) {
                    action_utenti(request, response);
                }
                if (request.getParameter("modifica_disco") != null) {
                    action_disco(request, response);
                }
                if (request.getParameter("elimina_disco") != null) {
                    action_delete(request, response);
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

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {

        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession sessione = request.getSession(true);
            int user_key = 0;

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

            Integer collezioneID = (Integer) request.getAttribute("collezioneID");
            Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezioneID);

            request.setAttribute("page_title", "Modifica " + collezione.getNome());
            request.setAttribute("collezione", collezione);
            request.setAttribute("lista_utenti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti());
            request.setAttribute("utenti_autorizzati", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByCollezione(collezioneID));

            List<ListaDischi> dettagliDischi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getDischiByCollezione(collezioneID);
            List<Disco> disco = new ArrayList<>();
            for (ListaDischi i : dettagliDischi) {
                disco.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(i.getDisco().getKey()));
            }

            request.setAttribute("dettagliDischi", dettagliDischi);
            request.setAttribute("dischi", disco);
            request.setAttribute("collezione_key", collezioneID);

            List<Disco> lista_dischi = new ArrayList<>();
            lista_dischi.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi());

            request.setAttribute("lista_dischi", lista_dischi);

            if (collezione.getUtente().getKey() == user_key) {
                res.activate("modifica_collezione.html.ftl", request, response);
            } else {
                response.sendRedirect("collezioni");
            }
        } catch (Exception ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }
    }

    private void action_collezione(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, ServletException, TemplateManagerException {

        String nome = request.getParameter("nome");
        String condivisione = request.getParameter("condivisione");
        Integer collezioneid = SecurityLayer.checkNumeric(request.getParameter("collezioneID"));
        String error_msg = "";

        Collezione uCollezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezioneid);

        if (!nome.isEmpty()) {
            //Attributi collezione
            uCollezione.setNome(nome);
            uCollezione.setCondivisione(condivisione);
            ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().storeCollezione(uCollezione);

            response.sendRedirect("modificaCollezione?numero=" + uCollezione.getKey());
        } else {
            error_msg = "Alcuni  campi sono vuoti";
            request.setAttribute("exception", error_msg);
            action_default(request, response);
        }
    }

    private void action_utenti(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, ServletException, TemplateManagerException {
        Integer collezioneid = SecurityLayer.checkNumeric(request.getParameter("collezioneID"));

        List<Integer> idutentiAutorizzatiV = new ArrayList<>();
        idutentiAutorizzatiV.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByCollezione(collezioneid));

        List<Utente> utentiAutorizzatiV = new ArrayList<>();
        for (Integer i : idutentiAutorizzatiV) {
            utentiAutorizzatiV.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(i));
        }

        String[] usernames = request.getParameterValues("utentiS");

        List<Utente> utentiAutorizzatiN = new ArrayList<>();

        if (usernames != null) {
            for (int i = 0; i < usernames.length; i++) {
                utentiAutorizzatiN.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(Integer.parseInt(usernames[i])));
            }
        }

        //elimino gli utenti non più presenti
        for (Utente i : utentiAutorizzatiV) {
            if (!utentiAutorizzatiN.contains(i)) {
                ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().deleteUtenteAutorizzato(collezioneid, i.getKey());
            }
        }

        if (!utentiAutorizzatiN.isEmpty()) {
            //inserisco i nuovi utenti
            for (Utente i : utentiAutorizzatiN) {
                if (!utentiAutorizzatiV.contains(i)) {
                    UtentiAutorizzatiImpl u = new UtentiAutorizzatiImpl();
                    u.setCollezione(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezioneid));
                    u.setUtente(i);
                    ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().storeUtentiAutorizzati(u);
                }
            }
        }

        response.sendRedirect("modificaCollezione?numero=" + collezioneid);


    }

    private void action_disco(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {

        int disco_key = SecurityLayer.checkNumeric(request.getParameter("discoID"));
        int collezione_key = SecurityLayer.checkNumeric(request.getParameter("collezioneID"));
        int numeroCopie = SecurityLayer.checkNumeric(request.getParameter("numeroCopie"));
        String formato = request.getParameter("formato");
        String stato = request.getParameter("stato");
        String barcode = request.getParameter("barcode");
        String imgCopertina = "https://www.musicaccia.com/wp-content/uploads/2018/02/disco_vinile_che_esplode.jpg";
        String imgFronte = null;
        String imgRetro = null;
        String imgLibretto = null;

        Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);
        Disco disco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(disco_key);

        if (((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDisco(collezione_key, disco_key, formato) == null) {
            // insert
            ListaDischi listaDischi = new ListaDischiImpl();
            listaDischi.setCollezione(collezione);
            listaDischi.setDisco(disco);
            listaDischi.setNumeroCopie(numeroCopie);
            listaDischi.setFormato(formato);
            listaDischi.setStato(stato);
            listaDischi.setBarcode(barcode);
            listaDischi.setImgCopertina(imgCopertina);
            listaDischi.setImgFronte(imgFronte);
            listaDischi.setImgRetro(imgRetro);
            listaDischi.setImgLibretto(imgLibretto);
            ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().storeListaDischi(listaDischi);
        } else {
            // update
            ListaDischi listaDischi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDisco(collezione_key, disco_key, formato);
            listaDischi.setNumeroCopie(numeroCopie);
            listaDischi.setStato(stato);
            listaDischi.setBarcode(barcode);
            listaDischi.setImgCopertina(imgCopertina);
            listaDischi.setImgFronte(imgFronte);
            listaDischi.setImgRetro(imgRetro);
            listaDischi.setImgLibretto(imgLibretto);
            ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().storeListaDischi(listaDischi);
        }

        response.sendRedirect("modificaCollezione?numero=" + collezione_key);
    }

    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {

        int listaDisco_key = SecurityLayer.checkNumeric(request.getParameter("listaDiscoID"));
        System.out.println(listaDisco_key);
        ListaDischi listaDischi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDischi(listaDisco_key);
        int collezione_key = listaDischi.getCollezione().getKey();

        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().deleteListaDischi(listaDischi);

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
        return "Servlet per la modifica di una nuova collezione";
    }

}
