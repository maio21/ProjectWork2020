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
        ImageView vImmagine = view.findViewById(R.id.imageView3);
        TextView vTitolo = view.findViewById(R.id.textViewTitolo);
        TextView vDescrizione = view.findViewById(R.id.textViewDescrizione);

        //DA FARE GLIDE
        //vImmagine =
        vTitolo.setText(cursor.getString(cursor.getColumnIndex(MovieTableHelper.TITOLO)));
        vDescrizione.setText(cursor.getString(cursor.getColumnIndex(MovieTableHelper.DESCRIZIONE)));

    }
}
