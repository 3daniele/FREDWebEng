package it.univaq.f4i.iw.ex.newspaper.data.proxy;

import it.univaq.f4i.iw.ex.newspaper.data.dao.ArticleDAO;
import it.univaq.f4i.iw.ex.newspaper.data.dao.ImageDAO;
import it.univaq.f4i.iw.ex.newspaper.data.impl.IssueImpl;
import it.univaq.f4i.iw.ex.newspaper.data.model.Article;
import it.univaq.f4i.iw.ex.newspaper.data.model.Image;
import it.univaq.f4i.iw.framework.data.DataException;
import it.univaq.f4i.iw.framework.data.DataItemProxy;
import it.univaq.f4i.iw.framework.data.DataLayer;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


public class NonToccare extends IssueImpl implements DataItemProxy  {

    protected boolean modified;
    protected DataLayer dataLayer;

    public IssueProxy(DataLayer d) {
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
    public void setNumber(int number) {
        super.setNumber(number);
        this.modified = true;
    }



    @Override
    public void setDate(Date date) {
        super.setDate(date);
        this.modified = true;
    }

    @Override
    public List<Article> getArticles() {
        if (super.getArticles() == null) {
            try {
                super.setArticles(((ArticleDAO) dataLayer.getDAO(Article.class)).getArticles(this));
            } catch (DataException ex) {
                Logger.getLogger(IssueProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getArticles();
    }

    @Override
    public void setArticles(List<Article> articles) {
        super.setArticles(articles);
        this.modified = true;
    }

    @Override
    public List<Image> getImages() {
        if (super.getImages() == null) {
            try {
                super.setImages(((ImageDAO) dataLayer.getDAO(Image.class)).getImages(this));
            } catch (DataException ex) {
                Logger.getLogger(IssueProxy.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return super.getImages();
    }

    @Override
    public void setImages(List<Image> images) {
        super.setImages(images);
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