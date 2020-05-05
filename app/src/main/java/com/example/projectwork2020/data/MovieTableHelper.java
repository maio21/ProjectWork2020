package com.example.projectwork2020.data;

import android.provider.BaseColumns;

public class MovieTableHelper implements BaseColumns {

    public static final String TABLE_NAME = "movie";
    public static final String PAGINA = "pagina";
    public static final String IMG_COPERTINA = "img_copertina";
    public static final String IMG_DESCRIZIONE = "img_descrizione";
    public static final String TITOLO = "titolo";
    public static final String DESCRIZIONE = "descrizione";
    public static final String ID_FILM = "id_film";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            ID_FILM + " INTEGER, " +
            TITOLO + " TEXT, " +
            PAGINA + " INTEGER, " +
            DESCRIZIONE + " TEXT, " +
            IMG_COPERTINA + " TEXT, " +
            IMG_DESCRIZIONE + " TEXT ) ;";


}

