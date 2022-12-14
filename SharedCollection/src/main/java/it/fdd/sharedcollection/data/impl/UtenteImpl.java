package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Utente;

public class UtenteImpl extends DataItemImpl<Integer> implements Utente {

    private String nickname;
    private String email;
    private String password;
    private String nome;
    private String cognome;
    private int token;
    private String link;

    public UtenteImpl() {
        super();
        this.nickname = "";
        this.email = "";
        this.password = "";
        this.nome = "";
        this.cognome = "";
        this.token = 0;
        this.link = "";
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public String getEmail() {
        return this.email;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getNome() {
        return this.nome;
    }

    @Override
    public String getCognome() {
        return this.cognome;
    }

    @Override
    public int getToken() {
        return this.token;
    }

    @Override
    public String getLink() {
        return this.link;
    }

    @Override
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public void setToken(int token) {
        this.token = token;
    }

}
