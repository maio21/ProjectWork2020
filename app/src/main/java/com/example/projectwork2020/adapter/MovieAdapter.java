package com.example.projectwork2020.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.projectwork2020.R;
import com.example.projectwork2020.activity.DetailMovies;
import com.example.projectwork2020.activity.ListaMovies;
import com.example.projectwork2020.data.MovieProvider;
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
    public void bindView(View view, final Context context, Cursor cursor) {
        final ImageView vImmagine1 = view.findViewById(R.id.imageView);
        final ImageView vImmagine2 = view.findViewById(R.id.imageView2);
        //vImmagine1.setTag(cursor.getInt(cursor.getColumnIndex(MovieTableHelper._ID)));
        Log.d("id",""+cursor.getCount());

       int cursorPosition = cursor.getPosition() * 2;

       if(cursorPosition >= cursor.getCount())
           return;


       cursor.moveToPosition(cursorPosition);

       Glide.with(context)
                .load("https://image.tmdb.org/t/p/w500/"+ cursor.getString(cursor.getColumnIndex(MovieTableHelper.IMG_COPERTINA)))
                .into(vImmagine1);

       if(cursorPosition + 1 >= cursor.getCount())
           return;

       cursor.moveToPosition(cursorPosition + 1);

            Glide.with(context)
                    .load("https://image.tmdb.org/t/p/w500/" + cursor.getString(cursor.getColumnIndex(MovieTableHelper.IMG_COPERTINA)))
                    .into(vImmagine2);


    }

    @Override
    public int getCount() {
        return (getCursor().getCount() % 2 == 0)? getCursor().getCount()/2: getCursor().getCount()/2+1;
    }
}
