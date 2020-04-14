package com.example.projectwork2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.projectwork2020.MovieAdapter;
import com.example.projectwork2020.R;
import com.example.projectwork2020.data.MovieProvider;
import com.example.projectwork2020.data.MovieTableHelper;

public class DetailMovies extends AppCompatActivity {

    TextView mTitolo, mDescrizione;
    ImageView mImmagine;
    int mId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movies);

        mTitolo = findViewById(R.id.textViewTitolo);
        mDescrizione = findViewById(R.id.textViewDescrizione);
        mImmagine = findViewById(R.id.imageView3);

        if(getIntent().getExtras() != null)
        {
            mId = getIntent().getExtras().getInt("_ID");
            Cursor vCursor = getContentResolver().query(Uri.parse(MovieProvider.MOVIES_URI + "/" + mId), null, null, null);
            vCursor.moveToNext();
            String vTitolo = vCursor.getString(vCursor.getColumnIndex(MovieTableHelper.TITOLO));
            String vDescrizione = vCursor.getString(vCursor.getColumnIndex(MovieTableHelper.DESCRIZIONE));
            String vImmagine = vCursor.getString(vCursor.getColumnIndex(MovieTableHelper.IMG_DESCRIZIONE));

            mTitolo.setText(vTitolo);
            mDescrizione.setText(vDescrizione);
            //mImmagine = FARE GLIDE PER IMMAGINE
        }
    }
}
