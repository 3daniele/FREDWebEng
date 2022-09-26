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

public class ProfiloUtente extends SharedCollectionBaseController {
    private void action_error(HttpServletRequest request, HttpServletResponse response) {
        if (request.getAttribute("exception") != null) {
            (new FailureResult(getServletContext())).activate((Exception) request.getAttribute("exception"), request, response);
        } else {
            (new FailureResult(getServletContext())).activate((String) request.getAttribute("message"), request, response);
        }
    }

    private void action_default(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException, TemplateManagerException {
        TemplateResult res = new TemplateResult(getServletContext());
        HttpSession sessione = request.getSession(true);
        //aggiungiamo al template un wrapper che ci permette di chiamare la funzione stripSlashes
        //add to the template a wrapper object that allows to call the stripslashes function
        Utente profilo = null;
        request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
        request.setAttribute("utentiPath", true);
        try {
            Integer user_key = (Integer) request.getAttribute("idutente");
            profilo = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(user_key);
            request.setAttribute("page_title", profilo.getNickname().toUpperCase());
            request.setAttribute("profilo", profilo);
            request.setAttribute("n_dati", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenteInfo(profilo.getKey()));


            /* STATISTICHE */
            List<Collezione> collezioni = new ArrayList<>();
            collezioni.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezioniByUtente(profilo.getKey()));

            List<ListaDischi> listaDischi = new ArrayList<>();
            for (Collezione i : collezioni) {
                listaDischi.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getDischiByCollezione(i.getKey()));
            }

            List<ListaBrani> listaBrani = new ArrayList<>();
            for (ListaDischi i : listaDischi) {
                listaBrani.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaBraniDAO().getListeBrani(i.getDisco().getKey()));
            }
            //Generi
            List<ListaGeneri> listaGeneri = new ArrayList<>();
            for (ListaBrani i : listaBrani) {
                listaGeneri.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaGeneriDAO().getListaGeneriByCanzone(i.getCanzone().getKey()));
            }

            List<Genere> generi = new ArrayList<>();
            for (ListaGeneri i : listaGeneri) {
                Genere g = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getGenereDAO().getGenere(i.getGenere().getKey());

                if (!generi.contains(g))
                    generi.add(g);

            }

            //Mi serve il numero totale dei brani = listaBrani.size()
            //Devo calcolarmi le occorrenze di ogni singolo genere per ogni canzone
            List<Integer> occorrenze = new ArrayList<>();
            int sum = 0;
            for (Genere i : generi) {
                sum = 0;
                for (ListaGeneri j : listaGeneri) {
                    if (i.getKey() == j.getGenere().getKey())
                        sum++;
                }
                occorrenze.add(sum);
            }

            //Calcolo delle percentuali
            List<String> percentuali = new ArrayList<>();
            for (Integer i : occorrenze) {
                percentuali.add(i * 100 / listaBrani.size() + "%");
            }

            request.setAttribute("generi", generi);
            request.setAttribute("percentuali", percentuali);
            /* ------------------------------------------------------------------------------------------------------------------------------------------------------------- */

            List<ListaArtisti> listaArtisti = new ArrayList<>();
            for (ListaBrani i : listaBrani) {
                listaArtisti.addAll(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaArtistiDAO().getListaArtistiByCanzone(i.getCanzone().getKey()));
            }

            List<Artista> artisti = new ArrayList<>();
            for (ListaArtisti i : listaArtisti) {
                Artista a = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtista(i.getArtista().getKey());
                if (!artisti.contains(a))
                    artisti.add(a);
            }
            List<Integer> occorrenzeArtisti = new ArrayList<>();
            for (Artista i : artisti) {
                sum = 0;
                for (ListaArtisti j : listaArtisti) {
                    if (i.getKey() == j.getArtista().getKey()) {
                        sum++;
                    }
                }
                occorrenzeArtisti.add(sum);
            }

            //Calcolo delle percentuali
            List<String> percentualiArtisiti = new ArrayList<>();
            for (Integer i : occorrenzeArtisti) {
                percentualiArtisiti.add(i * 100 / listaBrani.size() + "%");
            }

            request.setAttribute("artisti", artisti);
            request.setAttribute("percentualiA", percentualiArtisiti);

            /* Ultime collezioni pubbliche */
            int a = 1;
            List<Collezione> collezioniPubbliche = new ArrayList<>();
            for (int i = 1; i <= collezioni.size(); i++) {
                Collezione c = collezioni.get(collezioni.size() - i);
                if (a < 4 && c.getCondivisione().equals("pubblica")) {
                    collezioniPubbliche.add(c);
                    a++;
                }
            }

            request.setAttribute("ultimeCollezioniPubbliche", collezioniPubbliche);


        } catch (DataException ex) {
            request.setAttribute("message", "Data access exception: " + ex.getMessage());
            action_error(request, response);
        }


        if (SecurityLayer.checkSession(request) != null) {
            request.setAttribute("session", true);
            request.setAttribute("username", sessione.getAttribute("username"));
            request.setAttribute("email", sessione.getAttribute("email"));
            request.setAttribute("userid", sessione.getAttribute("userid"));
        }

        res.activate("profilo_utente.html.ftl", request, response);
    }

    @Override
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException {

        int user_key;
        try {
            if (request.getParameter("id") != null) {
                user_key = SecurityLayer.checkNumeric(request.getParameter("id"));
                request.setAttribute("idutente", user_key);


                action_default(request, response);
            } else {
                response.sendRedirect("showUtenti");
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
        return " Profilo utente servlet";
    }
}
