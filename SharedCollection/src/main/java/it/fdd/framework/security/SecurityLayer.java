package it.fdd.framework.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.Calendar;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class SecurityLayer {
    private static final String STATIC_KEY = "StaticKey";
    //--------- SESSION SECURITY ------------
    //questa funzione esegue una serie di controlli di sicurezza
    //sulla sessione corrente. Se la sessione non è valida, la cancella
    //e ritorna null, altrimenti la aggiorna e la restituisce

    //this method executed a set of standard chacks on the current session.
    //If the session exists and is valid, it is rerutned, otherwise
    //the session is invalidated and the method returns null
    public static HttpSession checkSession(HttpServletRequest r) {
        boolean check = true;

        HttpSession s = r.getSession(false);
        //per prima cosa vediamo se la sessione è attiva
        //first, let's see is the sessione is active
        if (s == null) {
            return null;
        }

        //check sulla validità  della sessione
        //second, check is the session contains valid data
        if (s.getAttribute("userid") == null) {
            check = false;
            //check sull'ip del client
            //check if the client ip chage
        } else {
            if(s.getAttribute("email") == null)
            {
                check = false;
            }
            else {
                if(s.getAttribute("username") == null){
                    check = false;
                }
            }
        }
        if (!check) {
            s.invalidate();
            return null;
        } else {
            return s;
        }
    }

    public static HttpSession createSession(HttpServletRequest request, String username, int userid,String email) {
        HttpSession s = request.getSession(true);
        s.setAttribute("username", username);
        s.setAttribute("email", email);
        s.setAttribute("userid", userid);
        return s;
    }

    public static void disposeSession(HttpServletRequest request) {
        HttpSession s = request.getSession(false);
        if (s != null) {
            s.invalidate();
        }
    }

    //--------- DATA SECURITY ------------
    //questa funzione aggiunge un backslash davanti a
    //tutti i caratteri "pericolosi", usati per eseguire
    //SQL injection attraverso i parametri delle form

    //this function adds backslashes in front of
    //all the "malicious" charcaters, usually exploited
    //to perform SQL injection through form parameters
    public static String addSlashes(String s) {
        return s.replaceAll("(['\"\\\\])", "\\\\$1");
    }

    //questa funzione rimuove gli slash aggiunti da addSlashes
    //this function removes the slashes added by addSlashes
    public static String stripSlashes(String s) {
        return s.replaceAll("\\\\(['\"\\\\])", "$1");
    }

    public static int checkNumeric(String s) throws NumberFormatException {
        //convertiamo la stringa in numero, ma assicuriamoci prima che sia valida
        //convert the string to a number, ensuring its validity
        if (s != null) {
            //se la conversione fallisce, viene generata un'eccezione
            //if the conversion fails, an exception is raised
            return Integer.parseInt(s);
        } else {
            throw new NumberFormatException("String argument is null");
        }
    }

    //--------- CONNECTION SECURITY ------------
    //questa funzione verifica se il protocollo HTTPS è attivo
    //checks if the HTTPS protocol is in use
    public static boolean checkHttps(HttpServletRequest r) {
        return r.isSecure();
        //metodo "fatto a mano" che funziona solo se il server trasmette gli header corretti
        //the following is an "handmade" alternative, which works only if the server sends correct headers
        //String httpsheader = r.getHeader("HTTPS");
        //return (httpsheader != null && httpsheader.toLowerCase().equals("on"));
    }

    //questa funzione ridirige il browser sullo stesso indirizzo
    //attuale, ma con protocollo https
    //this function redirects the browser on the current address, but
    //with https protocol
    public static void redirectToHttps(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        //estraiamo le parti della request url
        String server = request.getServerName();
        //int port = request.getServerPort();
        String context = request.getContextPath();
        String path = request.getServletPath();
        String info = request.getPathInfo();
        String query = request.getQueryString();

        //ricostruiamo la url cambiando il protocollo e la porta COME SPECIFICATO NELLA CONFIGURAZIONE DI TOMCAT
        //rebuild the url changing port and protocol AS SPECIFIED IN THE SERVER CONFIGURATION
        String newUrl = "https://" + server + ":8443" + context + path + (info != null ? info : "") + (query != null ? "?" + query : "");
        try {
            //ridirigiamo il client
            //redirect
            response.sendRedirect(newUrl);
        } catch (IOException ex) {
            try {
                //in caso di problemi tentiamo prima di inviare un errore HTTP standard
                //in case of problems, first try to send a standard HTTP error message
                response.sendError(response.SC_INTERNAL_SERVER_ERROR, "Cannot redirect to HTTPS, blocking request");
            } catch (IOException ex1) {
                //altrimenti generiamo un'eccezione
                //otherwise, raise an exception
                throw new ServletException("Cannot redirect to https!");
            }
        }
    }

    public static String encrypt(String phrase, String key) throws Exception {
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(StandardCharsets.UTF_8));
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
        byte[] dataBytes = phrase.getBytes(StandardCharsets.UTF_8);
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return Base64.getEncoder().encodeToString(cipher.doFinal(dataBytes));
    }

    public static String getStaticEncrypyionKey() {
        return SecurityLayer.STATIC_KEY;
    }
    public static String removeSpecialChars(String input) {
        return input.replaceAll("[^\\p{L}0-9']", "");
    }
    public static boolean isEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }

}
