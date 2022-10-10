package it.fdd.sharedcollection.controller;

import it.fdd.framework.result.FailureResult;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import it.fdd.framework.data.DataException;
import it.fdd.framework.result.SplitSlashesFmkExt;
import it.fdd.framework.result.TemplateManagerException;
import it.fdd.framework.result.TemplateResult;
import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.dao.SharedCollectionDataLayer;
import it.fdd.sharedcollection.data.impl.DiscoImpl;
import it.fdd.sharedcollection.data.model.*;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.sql.Date;
import java.util.Iterator;
import java.util.List;

public class ModificaDisco extends SharedCollectionBaseController {

    public int collezione_key = 0;
    public int disco_key = 0;
    public String formato;

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
                if (request.getParameter("updateDisco") != null) {
                    action_modifica(request, response);
                } else {
                    action_immagini(request, response);
                }
                response.sendRedirect("collezioni");
            }
        } catch (NumberFormatException ex) {
            request.setAttribute("message", "Invalid number submitted");
            action_error(request, response);
        } catch (IOException | DataException ex) {
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
        HttpSession sessione = request.getSession(true);

        request.setAttribute("strip_slashes", new SplitSlashesFmkExt());
        request.setAttribute("collezioniPath", true);

        if (SecurityLayer.checkSession(request) != null) {
            request.setAttribute("session", true);
            request.setAttribute("username", sessione.getAttribute("username"));
            request.setAttribute("email", sessione.getAttribute("email"));
            request.setAttribute("userid", sessione.getAttribute("userid"));
        } else {
            response.sendRedirect("collezioni");
        }

        try {
            Collezione collezione = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getCollezioneDAO().getCollezione(collezione_key);
            Disco disco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(disco_key);
            List<ListaBrani> listaBrani = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaBraniDAO().getListeBrani(disco_key);
            ListaDischi infoDisco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDisco(collezione_key, disco_key, formato);
            List<Artista> lista_artisti = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getArtistaDAO().getArtisti();
            List<Genere> lista_generi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getGenereDAO().getListaGeneri();
            List<Canzone> canzoni = new ArrayList<>();
            List<ListaArtisti> artisti = new ArrayList<>();
            List<ListaGeneri> generi = new ArrayList<>();

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
            request.setAttribute("lista_artisti", lista_artisti);
            request.setAttribute("lista_generi", lista_generi);
            request.setAttribute("canzoni", canzoni);
            request.setAttribute("listaArtisti", artisti);
            request.setAttribute("listaGeneri", generi);

            res.activate("modifica_disco.html.ftl", request, response);
        } catch (DataException ex) {
            request.setAttribute("exception", ex);
            action_error(request, response);
        } catch (TemplateManagerException e) {
            throw new RuntimeException(e);
        }

    }

    private void action_modifica(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException {

        String titolo = request.getParameter("titolo");
        String etichetta = request.getParameter("etichetta");
        Date anno = Date.valueOf(request.getParameter("anno"));

        int numeroCopie = Integer.parseInt(request.getParameter("numeroCopie"));
        String stato = request.getParameter("stato");
        String barcode = request.getParameter("barcode");

        HttpSession sessione = request.getSession(true);
        int userID = 0;

        if (SecurityLayer.checkSession(request) != null) {
            userID = Integer.parseInt(sessione.getAttribute("userid").toString());
        }

        Disco disco = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().getDisco(disco_key);

        if (userID == disco.getCreatore().getKey()) {
            // update disco
            disco.setNome(titolo);
            disco.setEtichetta(etichetta);
            disco.setAnno(anno);
            ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getDiscoDAO().storeDisco(disco);
        }

        ListaDischi listaDischi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDisco(collezione_key, disco_key, formato);
        //update listaDIschi
        listaDischi.setNumeroCopie(numeroCopie);
        listaDischi.setStato(stato);
        listaDischi.setBarcode(barcode);
        ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().storeListaDischi(listaDischi);

        response.sendRedirect("modificaDisco?numero=" + disco_key + "&collezione=" + collezione_key + "&formato=" + formato);

    }

    private void action_immagini(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException {

        String imgCopertina = "images/templateimg/core-img/disco_default.jpeg";
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
                    new File(request.getServletContext().getRealPath("/images/upload-img") + File.separator + collezione_key).mkdir();

                    String fileName = new File(item.getName()).getName();
                    System.out.println("fileName: " + fileName);
                    String filePath = uploadPath + File.separator + collezione_key + File.separator + fileName;
                    String filePath_ = request.getServletContext().getRealPath("/images/upload-img") + File.separator + collezione_key + File.separator + fileName;
                    System.out.println("filePath: " + filePath);
                    System.out.println("filePath_: " + filePath_);

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
                }
            }
            PrintWriter out = response.getWriter();
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("error", "Uno o più file superano le dimesioni consetite");
            action_default(request, response);
        }

        ListaDischi listaDischi = ((SharedCollectionDataLayer) request.getAttribute("datalayer")).getListaDischiDAO().getListaDisco(collezione_key, disco_key, formato);
        if (!imgCopertina.equals("images/templateimg/core-img/disco_default.jpeg")) {
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

        response.sendRedirect("modificaDisco?numero=" + disco_key + "&collezione=" + collezione_key + "&formato=" + formato);
    }

    private void action_updateBrano(HttpServletRequest request, HttpServletResponse response) throws IOException, DataException {

    }

}
