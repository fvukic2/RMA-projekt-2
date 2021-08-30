package com.vukic.rma_projekt_2.model.dao;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.vukic.rma_projekt_2.model.Serija;

@Database(entities = {Serija.class}, version = 2, exportSchema = false)
public abstract class SerijaBaza extends RoomDatabase {

    public abstract SerijaDAO serijaDAO();
    private static SerijaBaza instance;

    public static SerijaBaza getInstance(Context context){
        if (instance == null){
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),SerijaBaza.class,
                    "serija-baza"
            ).allowMainThreadQueries().fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
