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
import it.fdd.sharedcollection.data.impl.DiscoImpl;
import it.fdd.sharedcollection.data.impl.ListaDischiImpl;
import it.fdd.sharedcollection.data.impl.UtentiAutorizzatiImpl;
import it.fdd.sharedcollection.data.model.*;
import it.fdd.sharedcollection.data.utility.UtilityMethods;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import java.io.*;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ModificaCollezione extends SharedCollectionBaseController {

    public int collezione_key = 0;
    public int disco_key = 0;
    int user_key = 0;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException {

        try {
            if (request.getParameter("numero") != null) {
                collezione_key = SecurityLayer.checkNumeric(request.getParameter("numero"));
                request.setAttribute("collezioneID", collezione_key);
                action_default(request, response);
            } else {
                if (request.getParameter("modifica_collezione") != null) {
                    action_collezione(request, response);
                } else if (request.getParameter("modificaUtenti") != null) {
                    action_utenti(request, response);
                } else if (request.getParameter("elimina_disco") != null) {
                    action_delete(request, response);
                } else if (request.getParameter("nuovo_disco") != null) {
                    action_nuovoDisco(request, response);
                } else {
                    action_disco(request, response);
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

            Integer collezioneID = (Integer) request.getAttribute("collezioneID");
            Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezioneID);

            request.setAttribute("page_title", "Modifica " + collezione.getNome());
            request.setAttribute("collezione", collezione);
            request.setAttribute("lista_utenti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtenti());
            request.setAttribute("utenti_autorizzati", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByCollezione(collezioneID));
            request.setAttribute("lista_artisti", ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtisti());

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
        int collezioneid = SecurityLayer.checkNumeric(request.getParameter("collezioneID"));
        String error_msg = "";

        Collezione uCollezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezioneid);

        if (!nome.isEmpty()) {
            //Attributi collezione
            uCollezione.setNome(nome);
            uCollezione.setCondivisione(condivisione);
            ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().storeCollezione(uCollezione);

            response.sendRedirect("modificaCollezione?numero=" + uCollezione.getKey());
        } else {
            error_msg = "Inserisci un nome per la collezione!";
            request.setAttribute("error", error_msg);
            action_default(request, response);
        }
    }

    private void action_utenti(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException, TemplateManagerException {

        int collezioneid = SecurityLayer.checkNumeric(request.getParameter("collezioneID"));
        Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezioneid);

        List<Integer> idutentiAutorizzatiV = new ArrayList<>(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().getUtentiAutorizzatiByCollezione(collezioneid));

        List<Utente> utentiAutorizzatiV = new ArrayList<>();
        for (Integer i : idutentiAutorizzatiV) {
            utentiAutorizzatiV.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(i));
        }

        String[] usernames = request.getParameterValues("utentiS");

        List<Utente> utentiAutorizzatiN = new ArrayList<>();

        if (usernames != null) {
            for (String username : usernames) {
                utentiAutorizzatiN.add(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(Integer.parseInt(username)));
            }
        }

        //elimino gli utenti non più presenti
        for (Utente i : utentiAutorizzatiV) {
            if (!utentiAutorizzatiN.contains(i)) {
                ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtentiAutorizzatiDAO().deleteUtenteAutorizzato(collezioneid, i.getKey());
                // email di notifica
                try {
                    String text = " ha rimosso la condivisione con lei della sua collezione ";
                    UtilityMethods.sharingEmail(System.getenv("FILE_DIRECTORY") + "/email" + i.getNickname() + ".txt", i, collezione, text);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
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
                    // email di notifica
                    Utente utente = u.getUtente();
                    try {
                        String text = " ha condiviso con lei la sua collezione ";
                        UtilityMethods.sharingEmail(System.getenv("FILE_DIRECTORY") + "/email" + utente.getNickname() + ".txt", utente, collezione, text);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }

        response.sendRedirect("modificaCollezione?numero=" + collezioneid);
    }

    private void action_nuovoDisco(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {

        Disco disco = new DiscoImpl();
        disco.setNome(request.getParameter("nome"));
        disco.setEtichetta(request.getParameter("etichetta"));
        disco.setAnno(Date.valueOf(request.getParameter("anno")));
        disco.setArtista(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtista(Integer.parseInt(request.getParameter("artistaID"))));
        disco.setCreatore(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getUtenteDAO().getUtente(user_key));

        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().storeDisco(disco);

        disco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getLast();


        ListaDischi listaDischi = new ListaDischiImpl();
        listaDischi.setDisco(disco);
        listaDischi.setCollezione(((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key));
        listaDischi.setBarcode(request.getParameter("barcode"));
        listaDischi.setImgCopertina("images/templateimg/core-img/disco_default.jpeg");
        listaDischi.setStato(request.getParameter("stato"));
        listaDischi.setFormato(request.getParameter("formato"));
        listaDischi.setNumeroCopie(Integer.parseInt(request.getParameter("numeroCopie")));

        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().storeListaDischi(listaDischi);

        response.sendRedirect("modificaDisco?numero=" + disco.getKey() + "&collezione=" + collezione_key + "&formato=" + listaDischi.getFormato());
    }

    private void action_disco(HttpServletRequest request, HttpServletResponse response) throws DataException, IOException {

        int disco_key = 0; //SecurityLayer.checkNumeric(request.getParameter("discoID"));
        //int collezione_key = 0; //SecurityLayer.checkNumeric(request.getParameter("collezioneID"));
        int numeroCopie = 0; //SecurityLayer.checkNumeric(request.getParameter("numeroCopie"));
        String formato = null;
        String stato = null;
        String barcode = null;
        String imgCopertina = "/images/templateimg/core-img/disco_default.jpeg";
        String imgFronte = null;
        String imgRetro = null;
        String imgLibretto = null;

        final long serialVersionUID = 1L;

        final int THRESHOLD_SIZE = 1024 * 1024 * 3;
        final int MAX_FILE_SIZE = 1024 * 1024 * 15;
        final int MAX_REQUEST_SIZE = 1024 * 1024 * 20;


        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(THRESHOLD_SIZE);
        factory.setRepository(new File(System.getProperty("java.io.tmpdir")));
        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setFileSizeMax(MAX_FILE_SIZE);
        upload.setSizeMax(MAX_REQUEST_SIZE);
        String uploadPath = System.getenv("PROJECT_IMG") + "/upload-img";

        File uploaddir = new File(uploadPath);
        if (!uploaddir.exists()) {
            uploaddir.mkdirs();
        }

        try {
            List formItems = upload.parseRequest(request);
            Iterator it = formItems.iterator();
            // iterates over form's fields
            while (it.hasNext()) {
                FileItem item = (FileItem) it.next();
                // processes only fields that are not form fields
                if (!item.isFormField()) {
                    //creazione cartella per le immagini della collezione
                    new File(uploadPath + File.separator + collezione_key).mkdir();
                    new File(request.getServletContext().getRealPath("/images/upload-img/") + collezione_key).mkdir();

                    String fileName = new File(item.getName()).getName();
                    String filePath = uploadPath + File.separator + collezione_key + File.separator + fileName;
                    String filePath_ = request.getServletContext().getRealPath("/images/upload-img") + File.separator + collezione_key + File.separator + fileName;


                    File storeFile = new File(filePath);
                    File storeFile_ = new File(filePath_);
                    // saves the file on disk
                    item.write(storeFile);
                    item.write(storeFile_);

                    if ("imgCopertina".equals(item.getFieldName())) {
                        imgCopertina = "images/upload-img" + File.separator + collezione_key + File.separator + fileName;
                    }

                    if ("imgFronte".equals(item.getFieldName())) {
                        imgFronte = "images/upload-img" + File.separator + collezione_key + File.separator + fileName;
                    }

                    if ("imgRetro".equals(item.getFieldName())) {
                        imgRetro = "images/upload-img" + File.separator + collezione_key + File.separator + fileName;
                    }

                    if ("imgLibretto".equals(item.getFieldName())) {
                        imgLibretto = "images/upload-img" + File.separator + collezione_key + File.separator + fileName;
                    }
                } else {
                    if ("formato".equals(item.getFieldName()))
                        formato = item.getString();
                    else if ("stato".equals(item.getFieldName()))
                        stato = item.getString();
                    else if ("barcode".equals(item.getFieldName()))
                        barcode = item.getString();
                    else if ("discoID".equals(item.getFieldName()))
                        disco_key = Integer.parseInt(item.getString());
                        // else if ("collezioneID".equals(item.getFieldName()))
                        //    collezione_key = Integer.parseInt(item.getString());
                    else if ("numeroCopie".equals(item.getFieldName()))
                        numeroCopie = Integer.parseInt(item.getString());
                }
            }

            PrintWriter out = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }


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
            if (!imgCopertina.equals("https://www.musicaccia.com/wp-content/uploads/2018/02/disco_vinile_che_esplode.jpg")) {
                listaDischi.setImgCopertina(imgCopertina);
            }
            if (imgFronte != null) {
                listaDischi.setImgFronte(imgFronte);
            }
            if (imgRetro != null) {
                listaDischi.setImgRetro(imgRetro);
            }
            if (imgLibretto != null) {
                listaDischi.setImgLibretto(imgLibretto);
            }
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
