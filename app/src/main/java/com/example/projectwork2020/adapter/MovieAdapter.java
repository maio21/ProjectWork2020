package com.example.projectwork2020.adapter;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
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
        ImageView vImmagine1 = view.findViewById(R.id.imageView);
        ImageView vImmagine2 = view.findViewById(R.id.imageView2);

        final TextView vIdSx = view.findViewById(R.id.textViewIDSx);
        final int vIdSxInt = Integer.parseInt((String) vIdSx.getText());

        final TextView vIdDx = view.findViewById(R.id.textViewIDDx);
        final int vIdDxInt = Integer.parseInt((String) vIdDx.getText());


        LinearLayout linearLayoutSx = view.findViewById(R.id.linearLayoutSx);
        linearLayoutSx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vIntent = new Intent(context, DetailMovies.class);
                Bundle vBundle = new Bundle();
                vBundle.putInt("_ID", vIdSxInt);

                vIntent.putExtras(vBundle);
                context.startActivity(vIntent);
            }
        });

        LinearLayout linearLayoutDx = view.findViewById(R.id.linearLayoutDx);
        linearLayoutDx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent vIntent = new Intent(context, DetailMovies.class);
                Bundle vBundle = new Bundle();
                vBundle.putInt("_ID", vIdDxInt);

                vIntent.putExtras(vBundle);
                context.startActivity(vIntent);
            }
        });



        Glide.with(context).load(cursor.getString(cursor.getColumnIndex(MovieTableHelper.IMG_COPERTINA))).into(vImmagine1);
        vIdSx.setText(cursor.getString(cursor.getColumnIndex(MovieTableHelper._ID)));
        cursor.moveToNext();
        if(cursor.isAfterLast())
        {
            return;
        }
        else
        {
            Glide.with(context).load(cursor.getString(cursor.getColumnIndex(MovieTableHelper.IMG_COPERTINA))).into(vImmagine2);
            vIdDx.setText(cursor.getString(cursor.getColumnIndex(MovieTableHelper._ID)));
        }
    }
}
