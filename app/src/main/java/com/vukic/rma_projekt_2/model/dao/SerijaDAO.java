package com.vukic.rma_projekt_2.model.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.vukic.rma_projekt_2.model.Serija;

import java.util.List;

@Dao
public interface SerijaDAO {

    @Query("SELECT * FROM serija order by datum DESC")
    LiveData<List<Serija>> dohvatiSerije();

    @Insert
    void dodajNovuSeriju(Serija serija);

    @Update
    void promjeniSeriju(Serija serija);

    @Delete
    void obrisiSeriju(Serija serija);

    @Query("DELETE FROM serija")
    void obrisiSveSerije();

}
