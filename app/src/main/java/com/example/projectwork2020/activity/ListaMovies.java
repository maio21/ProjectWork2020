package com.example.projectwork2020.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.GridLayoutAnimationController;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;

import com.example.projectwork2020.adapter.MovieAdapter;
import com.example.projectwork2020.R;
import com.example.projectwork2020.api.IWebServer;
import com.example.projectwork2020.api.Movie;
import com.example.projectwork2020.api.MoviePageResult;
import com.example.projectwork2020.api.WebService;
import com.example.projectwork2020.data.MovieProvider;
import com.example.projectwork2020.data.MovieTableHelper;

import java.util.List;

public class ListaMovies extends AppCompatActivity implements LoaderManager.LoaderCallbacks<Cursor> {
    
    ListView mList;
    MovieAdapter mAdapter;
    public static final int MY_LOADER_ID = 0;
    private WebService webService;

    private IWebServer webServerListener = new IWebServer() {
        @Override
        public void onMovieFetched(boolean success, MoviePageResult movies, int errorCode, String errorMessage) {
              if(success){

                  Log.d("asda", ""+ movies.getPage());

              }  else {
                  Log.d("erroreAPI", errorMessage + " : " + errorCode);
                  //aggiornaListaFilm();
              }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_movies);

        // chiamata al Web Service
        webService = WebService.getInstance();
        Log.d("asdaNO", "no");
        webService.getMovie(webServerListener);
        Log.d("asdaSI", "si");

        mList = findViewById(R.id.listViewFilm);

        
//        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                Intent vIntent = new Intent(ListaMovies.this, DetailMovies.class);
//                Bundle vBundle = new Bundle();
//                vBundle.putInt("_ID", (int) l);
//
//                vIntent.putExtras(vBundle);
//                startActivity(vIntent);
//            }
//        });
        
        aggiornaListaFilm();
    }

    private void aggiornaListaFilm() {
        mAdapter = new MovieAdapter(this, null);
        mList.setAdapter(mAdapter);

        getSupportLoaderManager().initLoader(MY_LOADER_ID, null, this);

    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
        return new CursorLoader(this, MovieProvider.MOVIES_URI, null, null, null, null);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mAdapter.changeCursor(null);
    }

}
