package it.fdd.sharedcollection.data.impl;

import it.fdd.framework.data.DataItemImpl;
import it.fdd.sharedcollection.data.model.Collezione;
import it.fdd.sharedcollection.data.model.Disco;
import it.fdd.sharedcollection.data.model.ListaDischi;

public class ListaDischiImpl extends DataItemImpl<Integer> implements ListaDischi {

    private Collezione collezione;
    private Disco disco;
    private int numeroCopie;
    private String stato;
    private String formato;
    private String barcode;
    private String imgCopertina;
    private String imgFronte;
    private String imgRetro;
    private String imgLibretto;

    public ListaDischiImpl() {
        super();
        this.collezione = null;
        this.disco = null;
        this.numeroCopie = 0;
        this.stato = "";
        this.imgCopertina = "";
        this.imgFronte = "";
        this.imgRetro = "";
        this.imgLibretto = "";
    }

    @Override
    public Collezione getCollezione() {
        return this.collezione;
    }

    @Override
    public Disco getDisco() {
        return this.disco;
    }

    @Override
    public int getNumeroCopie() {
        return this.numeroCopie;
    }

    @Override
    public String getStato() {
        return this.stato;
    }

    @Override
    public String getFormato() {
        return this.formato;
    }

    @Override
    public String getBarcode() {
        return this.barcode;
    }

    @Override
    public String getImgCopertina() {
        return this.imgCopertina;
    }

    @Override
    public String getImgFronte() {
        return this.imgFronte;
    }

    @Override
    public String getImgRetro() {
        return this.imgRetro;
    }

    @Override
    public String getImgLibretto() {
        return this.imgLibretto;
    }

    @Override
    public void setCollezione(Collezione collezione) {
        this.collezione = collezione;
    }

    @Override
    public void setDisco(Disco disco) {
        this.disco = disco;
    }

    @Override
    public void setNumeroCopie(int numeroCopie) {
        this.numeroCopie = numeroCopie;
    }

    @Override
    public void setStato(String stato) {
        this.stato = stato;
    }

    @Override
    public void setFormato(String formato) {
        this.formato = formato;
    }

    @Override
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    @Override
    public void setImgCopertina(String imgCopertina) {
        this.imgCopertina = imgCopertina;
    }

    @Override
    public void setImgFronte(String imgFronte) {
        this.imgFronte = imgFronte;
    }

    @Override
    public void setImgRetro(String imgRetro) {
        this.imgRetro = imgRetro;
    }

    @Override
    public void setImgLibretto(String imgLibretto) {
        this.imgLibretto = imgLibretto;
    }
}
