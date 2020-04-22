package com.example.projectwork2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
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
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        mTitolo = findViewById(R.id.textViewTitolo);
        mDescrizione = findViewById(R.id.textViewDescrizione);
        mImmagine = findViewById(R.id.imageView3);

        mDescrizione.setMovementMethod(new ScrollingMovementMethod());

        if(getIntent().getExtras() != null)
        {
            mId = getIntent().getExtras().getInt("_ID");
            mId ++;
            Cursor vCursor = getContentResolver().query(Uri.parse(MovieProvider.MOVIES_URI + "/" + mId), null, null, null);
            vCursor.moveToNext();
            String vTitolo = vCursor.getString(vCursor.getColumnIndex(MovieTableHelper.TITOLO));
            String vDescrizione = vCursor.getString(vCursor.getColumnIndex(MovieTableHelper.DESCRIZIONE));
            String vImmagine = "https://image.tmdb.org/t/p/w500/" + vCursor.getString(vCursor.getColumnIndex(MovieTableHelper.IMG_DESCRIZIONE));

            mTitolo.setText(vTitolo);
            mDescrizione.setText(vDescrizione);
            //Glide.with(DetailMovies.this).load(vImmagine).apply(new RequestOptions().override(1000, 200)).into(mImmagine);
            //Glide.with(DetailMovies.this).load(vImmagine).override(2000, 1000).centerCrop().into(mImmagine);
            Glide.with(DetailMovies.this).load(vImmagine).into(mImmagine);
        }
    }
}
