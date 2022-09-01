package it.fdd.sharedcollection.data.utility;


import it.fdd.framework.security.SecurityLayer;
import it.fdd.sharedcollection.data.model.Utente;
import it.fdd.sharedcollection.data.utility.EmailTypes;

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
     * @param type
     * @throws IOException
     * @throws Exception
     */
    public static void sendEmailWithCodes(String file, Utente me, String emailText, EmailTypes type) throws IOException, Exception {
        System.out.println(file);
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
            String direct_to = null;
            boolean link = true;
            switch (type) {
                case CONFIRM_EMAIL:
                    direct_to = "confirmEmail";
                    break;
                case PASSWORD_RECOVERY_EMAIL:
                    direct_to = "forgot";
                    break;
                case DAILY_EMAIL:
                    link = false;
                    break;
                default:
                    return;
            }

            writer.write("[ " + LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME) + " ] Sended to " + me.getEmail() + ":");
            writer.newLine();
            writer.write("--------------------------------- Sharedcollection Web Site ----------------------------------");
            writer.newLine();
            writer.write("Gentile " + me.getCognome() + me.getNome() + ",");
            writer.newLine();
            writer.write(emailText);

            if (link) {
                String verification_code = it.fdd.sharedcollection.utility.BCrypt.hashpw(me.getEmail(), it.fdd.sharedcollection.utility.BCrypt.gensalt());
                String refer_code = SecurityLayer.encrypt(me.getEmail(), SecurityLayer.getStaticEncrypyionKey());
                writer.newLine();
                writer.write("/Sharedcollection/" + direct_to + "?verification_code=" + verification_code + "&refer_code=" + refer_code);
            }

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
