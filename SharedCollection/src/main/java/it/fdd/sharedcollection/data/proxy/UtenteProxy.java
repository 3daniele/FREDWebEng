package it.fdd.sharedcollection.data.proxy;

import it.fdd.sharedcollection.data.impl.UtenteImpl;
import it.fdd.framework.data.DataItemProxy;
import it.fdd.framework.data.DataLayer;

public class UtenteProxy extends UtenteImpl implements DataItemProxy {

    protected boolean modified;
    protected DataLayer dataLayer;

    public UtenteProxy(DataLayer d) {
        super();
        //dependency injection
        this.dataLayer = d;
        this.modified = false;
    }


    @Override
    public void setKey(Integer key) {
        super.setKey(key);
        this.modified = true;
    }


    @Override
    public void setNome(String nome) {
        super.setNome(nome);
        this.modified = true;
    }

    @Override
    public void setCognome(String cognome) {
        super.setCognome(cognome);
        this.modified = true;
    }

    @Override
    public void setNickname(String nickname) {
        super.setNickname(nickname);
        this.modified = true;
    }

    @Override
    public void setEmail(String email) {
        super.setEmail(email);
        this.modified = true;
    }

    @Override
    public void setPassword(String password) {
        super.setPassword(password);
        this.modified = true;
    }

    //METODI DEL PROXY
    //PROXY-ONLY METHODS

    public void setModified(boolean dirty) {
        this.modified = dirty;
    }

    public boolean isModified() {
        return modified;
    }
}
