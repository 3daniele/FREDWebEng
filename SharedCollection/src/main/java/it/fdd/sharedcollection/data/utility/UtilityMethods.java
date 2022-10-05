package it.fdd.sharedcollection.data.utility;


import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Utente;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


public class UtilityMethods {
    /**
     * Simula l'invio di una email all'utente in base ai parametri inseriti
     *
     * @param file
     * @param me
     * @param emailText
     * @throws IOException
     * @throws Exception
     */
    public static void sendEmailWithCodes(String file, Utente me, String emailText) throws IOException, Exception {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

            writer.write("[ " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " ]  " + me.getEmail() + ":");
            writer.newLine();
            writer.write("--------------------------------- Sharedcollection Web Site ----------------------------------");
            writer.newLine();
            writer.write("Gentile " + me.getCognome() + " " + me.getNome() + ",");
            writer.newLine();
            writer.write(emailText);

            String verification_code = it.fdd.sharedcollection.utility.BCrypt.hashpw(me.getEmail(), it.fdd.sharedcollection.utility.BCrypt.gensalt());
            String refer_code = SecurityLayer.encrypt(me.getEmail(), SecurityLayer.getStaticEncrypyionKey());
            writer.newLine();

            writer.write(" http://localhost:8080/SharedCollection_war_exploded/confermaEmail?verification_code=" + verification_code + "&refer_code=" + refer_code);
            me.setLink(verification_code);
            System.out.println(me.getLink());

            writer.newLine();
            writer.write("--------------------------------- Sharedcollection Web Site ----------------------------------");
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
        }
    }

    public static void sharingEmail(String file, Utente utente, Collezione collezione, String text) throws IOException, Exception {

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {

            writer.write("[ " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " ]  " + utente.getEmail() + ":");
            writer.newLine();
            writer.write("--------------------------------- Sharedcollection Web Site ----------------------------------");
            writer.newLine();
            writer.write("Gentile " + utente.getCognome() + " " + utente.getNome() + ",");
            writer.newLine();
            writer.write("Le informiamo che l'utente " + collezione.getUtente().getNickname() + text + "'" + collezione.getNome() + "'.");
            writer.newLine();
            writer.write("Le auguriamo una buona giornata!");
            writer.newLine();
            writer.write("--------------------------------- Sharedcollection Web Site ----------------------------------");
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
            writer.newLine();
        }
    }
}
