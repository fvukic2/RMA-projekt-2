package com.vukic.rma_projekt_2.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "serija")
public class Serija {

    @PrimaryKey(autoGenerate = true)
    @NonNull
    private int id;

    @NonNull
    private int imeSerije;
    private String zanr;
    private long datum;
    private String redatelj;
    private String kratakOpis;
}
