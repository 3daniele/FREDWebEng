package it.fdd.sharedcollection.data.model;

import it.fdd.framework.data.DataItem;

public interface ListaDischi extends DataItem<Integer> {

    Collezione getCollezione();

    Disco getDisco();

    int getNumeroCopie();

    String getStato();

    String getFormato();

    String getBarcode();

    String getImgCopertina();

    String getImgFronte();

    String getImgRetro();

    String getImgLibretto();

    void setCollezione(Collezione collezione);

    void setDisco(Disco disco);

    void setNumeroCopie(int numeroCopie);

    void setStato(String stato);

    void setFormato(String formato);

    void setBarcode(String barcode);

    void setImgCopertina(String imgCopertina);

    void setImgFronte(String imgFronte);

    void setImgRetro(String imgRetro);

    void setImgLibretto(String imgLibretto);

}
