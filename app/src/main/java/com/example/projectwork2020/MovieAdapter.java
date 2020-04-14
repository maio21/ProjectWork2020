package com.example.projectwork2020;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectwork2020.data.MovieTableHelper;

public class MovieAdapter extends CursorAdapter {


    public MovieAdapter(Context context, Cursor c) {
        super(context, c);
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        View vView = LayoutInflater.from(context).inflate(R.layout.cell_movies, viewGroup, false);
        return vView;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {
        ImageView vImmagine1 = view.findViewById(R.id.imageView);
        ImageView vImmagine2 = view.findViewById(R.id.imageView2);

        //DA FARE GLIDE
        //vImmagine1 = FARE IL GLIDE DELLE IMMAGINE
        //vImmagine2 = FARE IL GLIDE DELLA IMMAGINE

    }
}