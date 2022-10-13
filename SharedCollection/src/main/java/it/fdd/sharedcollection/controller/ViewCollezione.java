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
            request.setAttribute("collezione_key", collezione_key);


            //Sezione statistiche
            /* ------------------------------------------------------------------------------------------------------------------------------------------------------------- */
            List<ListaBrani> listaBrani = new ArrayList<>();
            for (ListaDischi i: dettagliDischi) {
                listaBrani.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaBraniDAO().getListeBrani(i.getDisco().getKey()));
            }
            //Generi
            List<ListaGeneri> listaGeneri = new ArrayList<>();
            for (ListaBrani i: listaBrani) {
                listaGeneri.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaGeneriDAO().getListaGeneriByCanzone(i.getCanzone().getKey()));
            }

            List<Genere> generi = new ArrayList<>();
            for (ListaGeneri i: listaGeneri) {
                Genere g =((SharedCollectionDataLayer) request.getAttribute("datalayer")).getGenereDAO().getGenere(i.getGenere().getKey());

                if(!generi.contains(g))
                    generi.add(g);

            }

            //Mi serve il numero totale dei brani = listaBrani.size()
            //Devo calcolarmi le occorrenze di ogni singolo genere per ogni canzone
            List<Integer> occorrenze = new ArrayList<>();
            int sum=0;
            for (Genere i: generi) {
                sum = 0;
                for (ListaGeneri j: listaGeneri) {
                    if(i.getKey() == j.getGenere().getKey())
                        sum++;
                }
                occorrenze.add(sum);
            }

            //Calcolo delle percentuali
            List<String> percentuali = new ArrayList<>();
            for (Integer i: occorrenze) {
                percentuali.add(i*100/listaBrani.size()+"%");
            }

            request.setAttribute("generi",generi);
            request.setAttribute("percentuali", percentuali);
            /* ------------------------------------------------------------------------------------------------------------------------------------------------------------- */

            List<ListaArtisti> listaArtisti = new ArrayList<>();
            for (ListaBrani i: listaBrani) {
                listaArtisti.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaArtistiDAO().getListaArtistiByCanzone(i.getCanzone().getKey()));
            }

            List<Artista> artisti = new ArrayList<>();
            for (ListaArtisti i: listaArtisti) {
                Artista a = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtista(i.getArtista().getKey());
                if (!artisti.contains(a))
                    artisti.add(a);
            }
            List<Integer> occorrenzeArtisti = new ArrayList<>();
            for (Artista i: artisti){
                sum = 0;
                for(ListaArtisti j : listaArtisti){
                    if(i.getKey() == j.getArtista().getKey()){
                        sum++;
                    }
                }
                occorrenzeArtisti.add(sum);
            }

            //Calcolo delle percentuali
            List<String> percentualiArtisiti = new ArrayList<>();
            for(Integer i : occorrenzeArtisti){
                percentualiArtisiti.add(i*100/listaBrani.size()+"%");
            }

            request.setAttribute("artisti",artisti);
            request.setAttribute("percentualiA", percentualiArtisiti);

            res.activate("collezione.html.ftl", request, response);
        } catch (DataException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        }

    }

    private void action_delete(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {

        int listaDisco_key = SecurityLayer.checkNumeric(request.getParameter("listaDiscoID"));

        ListaDischi listaDischi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDischi(listaDisco_key);
        int collezione_key = listaDischi.getCollezione().getKey();

        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().deleteListaDischi(listaDischi);

        response.sendRedirect("collezione?numero=" + collezione_key);
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
                if (request.getParameter("elimina_disco") != null) {
                    System.out.println("nope");
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
