package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;

public interface Utente extends DataItem<Integer> {

    String getNickname();

    String getEmail();

    String getPassword();

    String getNome();

    String getCognome();

    int getToken();

    String getLink();

    void setNickname(String nickname);

    void setEmail(String email);

    void setPassword(String password);

    void setNome(String nome);

    void setCognome(String cognome);

    void setToken(int token);

    void setLink(String link);


}
