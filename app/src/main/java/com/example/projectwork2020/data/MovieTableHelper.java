package com.example.projectwork2020.data;

import android.provider.BaseColumns;

public class MovieTableHelper implements BaseColumns {

    public static final String TABLE_NAME = "movie";
    public static final String PAGINA = "pagina";
    public static final String IMG_COPERTINA = "img_copertina";
    public static final String IMG_DESCRIZIONE = "img_descrizione";
    public static final String TITOLO = "titolo";

    public static final String CREATE = "CREATE TABLE " + TABLE_NAME + " ( " +
            _ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
            TITOLO + " TEXT, " +
            PAGINA + " INTEGER, " +
            IMG_COPERTINA + " TEXT, " +
            IMG_DESCRIZIONE + " TEXT ) ;";


}

