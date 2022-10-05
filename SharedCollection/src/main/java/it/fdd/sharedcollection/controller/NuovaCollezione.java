package it.fdd.sharedcollection.controller;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.FailureResult;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.impl.CollezioneImpl;
import it.fdd.sharedcollection.data.impl.ListaDischiImpl;
import it.fdd.sharedcollection.data.impl.UtentiAutorizzatiImpl;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.ListaDischi;
import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.sharedcollection.data.model.UtentiAutorizzati;
import it.fdd.sharedcollection.data.utility.UtilityMethods;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

public class NuovaCollezione extends SharedCollectionBaseController {
    private Integer user_key;

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        try {
            TemplateResult res = new TemplateResult(getServletContext());
            HttpSession sessione = request.getSession(true);

            request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
            request.setAttribute("collezioniPath", true);
            request.setAttribute("page_title", "Nuova collezione");

            if (SecurityLayer.checkSession(request) == null) {
                response.sendRedirect("home");
            } else {
                request.setAttribute("session", true);
                request.setAttribute("username", sessione.getAttribute("username"));
                request.setAttribute("email", sessione.getAttribute("email"));
                request.setAttribute("userid", sessione.getAttribute("userid"));
                user_key = (Integer) sessione.getAttribute("userid");
            }


            request.setAttribute("lista_utenti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti());
            request.setAttribute("lista_dischi", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDischi());

            res.activate("nuova_collezione.html.ftl", request, response);

        } catch (Exception ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }

    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {
        try {
            if (request.getParameter("nuova_collezione") != null) {
                action_collezione(request, response);
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

    private void action_collezione(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, ServletException, TemplateManagerException {

        String nome = request.getParameter("nome");
        String condivisione = request.getParameter("condivisione");
        String[] utentiS = request.getParameterValues("utentiS");
        String[] dischiS = request.getParameterValues("dischiS");
        String error_msg = "";


        if (!nome.isEmpty()) {

            Collezione collezione = new CollezioneImpl();

            //Attributi collezione
            collezione.setNome(nome);
            collezione.setCondivisione(condivisione);
            collezione.setUtente(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(user_key));
            ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().storeCollezione(collezione);

            int collezione_key = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getLast().getKey();

            if (utentiS != null) {
                //Attributi utentiAutorizzati
                for (String utenti : utentiS) {
                    UtentiAutorizzati utentiAutorizzati = new UtentiAutorizzatiImpl();
                    utentiAutorizzati.setCollezione(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key));
                    utentiAutorizzati.setUtente(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(Integer.parseInt(utenti)));
                    ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().storeUtentiAutorizzati(utentiAutorizzati);
                    // email di notifica
                    Utente utente = utentiAutorizzati.getUtente();
                    try {
                        String text = " ha condiviso con lei la sua collezione ";
                        UtilityMethods.sharingEmail(System.getenv("FILE_DIRECTORY") + "/email" + utente.getNickname() + ".txt", utente, collezione, text);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            if (dischiS != null) {
                for (String dischi : dischiS) {
                    ListaDischi listaDischi = new ListaDischiImpl();
                    listaDischi.setCollezione(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key));
                    listaDischi.setDisco(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(Integer.parseInt(dischi)));
                    listaDischi.setImgCopertina("images/templateimg/core-img/disco_default.jpeg");
                    ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().storeListaDischi(listaDischi);
                }
            }

            response.sendRedirect("collezioni");
        } else {
            error_msg = "Inserisci un nome per la tua collezione!";
            request.setAttribute("error", error_msg);
            action_default(request, response);
        }
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
        return "Servlet per la creazione di una nuova collezione";
    }
}
