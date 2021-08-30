package com.vukic.rma_projekt_2.viewmodel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import com.vukic.rma_projekt_2.model.Serija;
import com.vukic.rma_projekt_2.model.dao.SerijaBaza;
import com.vukic.rma_projekt_2.model.dao.SerijaDAO;

@Getter
@Setter
public class SerijaViewModel extends AndroidViewModel {

    SerijaDAO serijaDAO;
    private Serija serija;
    private LiveData<List<Serija>> serije;

    public SerijaViewModel(Application application) {
        super(application);
        serijaDAO = SerijaBaza.getInstance(application.getApplicationContext()).serijaDAO();
    }

    public LiveData<List<Serija>> getSerije() {
        serije = serijaDAO.dohvatiSerije();
        return serije;
    }

    //public void setSerija(Serija serija) {
    //this.serija = serija;
    //}

    public void dodajNovuSeriju(){
        serijaDAO.dodajNovuSeriju(serija);
    }

    public void promjeniSeriju(){
        serijaDAO.promjeniSeriju(serija);
    }

    public void obrisiSeriju(){
        serijaDAO.obrisiSeriju(serija);
    }

    public void obrisiSveSerije(){
        serijaDAO.obrisiSveSerije();
    }

}
