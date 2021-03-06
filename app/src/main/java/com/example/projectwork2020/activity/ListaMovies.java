package com.example.projectwork2020.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.CursorLoader;
import androidx.loader.content.Loader;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.app.SearchManager;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.FilterQueryProvider;
import android.widget.ListView;


import android.widget.Toast;

import com.example.projectwork2020.adapter.MovieAdapter;
import com.example.projectwork2020.R;
import com.example.projectwork2020.api.IWebServer;
import com.example.projectwork2020.api.MoviePageResult;
import com.example.projectwork2020.api.WebService;
import com.example.projectwork2020.data.MovieProvider;
import com.example.projectwork2020.data.MovieTableHelper;
import com.example.projectwork2020.fragment.AirPlaneDialog;
import com.example.projectwork2020.fragment.OfflineDialog;

public class ListaMovies extends AppCompatActivity implements AirPlaneDialog.IAirPlaneDialog, LoaderManager.LoaderCallbacks<Cursor> {
    
    private ListView mList;
    private MovieAdapter mAdapter;
    public static final int MY_LOADER_ID = 0;
    private WebService webService;
    private int mPage = 1;
    private String query = "";

    private IWebServer webServerListener = new IWebServer() {
        @Override
        public void onMovieFetched(boolean success, MoviePageResult movies, int errorCode, String errorMessage) {
                Log.d("success", "funge");
                if (success) {
                    for (int i = 0; i < movies.getMovieResult().size(); i++) {
                        ContentValues vValues = new ContentValues();
                        vValues.put(MovieTableHelper.PAGINA, movies.getPage());
                        vValues.put(MovieTableHelper.TITOLO, movies.getMovieResult().get(i).getTitle());
                        vValues.put(MovieTableHelper.DESCRIZIONE, movies.getMovieResult().get(i).getOverview());
                        vValues.put(MovieTableHelper.IMG_COPERTINA, movies.getMovieResult().get(i).getPoster_path());
                        vValues.put(MovieTableHelper.IMG_DESCRIZIONE, movies.getMovieResult().get(i).getBackdrop_path());
                        vValues.put(MovieTableHelper.ID_FILM, movies.getMovieResult().get(i).getId());
                        if(getContentResolver().query(MovieProvider.MOVIES_URI, null,
                                MovieTableHelper.TITOLO + " = \"" + movies.getMovieResult().get(i).getTitle() + "\"",
                                null, null).getCount() == 0)
                            getContentResolver().insert(MovieProvider.MOVIES_URI, vValues);
                    }
                    Log.d("asda", "" + movies.getPage());

                } else {
                    Log.d("erroreAPI", errorMessage + " : " + errorCode);
                }
            }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_movies);
        getSupportActionBar().setTitle("Movies");

        if(savedInstanceState != null){
            mPage = savedInstanceState.getInt("PAGINA");
        }

        final SwipeRefreshLayout pullToRefresh = findViewById(R.id.pullToRefresh);
        mList = findViewById(R.id.listViewFilm);
        mList.setDivider(null);
        webService = WebService.getInstance();

        mAdapter = new MovieAdapter(this, null);
        setListaFilm();

        controlloInternet();

        pullToRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                if(isNetworkAvailable()){
                    webService.getMovie(webServerListener, mPage);
                    gestioneEndlessScroll();
                    pullToRefresh.setRefreshing(false);
                } else {
                    Toast.makeText(ListaMovies.this, "ATTENZIONE!! nessuna connesione", Toast.LENGTH_LONG).show();
                    pullToRefresh.setRefreshing(false);
                }

            }
        });
    }

    private void controlloInternet()
    {
        if (isNetworkAvailable()){
            if(getContentResolver().query(MovieProvider.MOVIES_URI, null, null, null, null).getCount() == 0)
            {
                webService.getMovie(webServerListener, mPage);
                gestioneEndlessScroll();
            }

        } else if (!isNetworkAvailable()) {
            Cursor vCursor = getContentResolver().query(MovieProvider.MOVIES_URI, null, null, null);
            if(vCursor.getCount() == 0)
            {
                OfflineDialog vDialog = new OfflineDialog();
                vDialog.show(getSupportFragmentManager(), null);
            }
            else
            {
                Toast.makeText(ListaMovies.this, "ATTENZIONE! nessuna connesione", Toast.LENGTH_LONG).show();
            }

        }
        if (isAirplaneModeOn(ListaMovies.this)){
            AirPlaneDialog vDialog =
                    new AirPlaneDialog("ATTENZIONE!",
                            "La modalità aereo è attivata, potrai usare l'applicazione in modalità offline.");
            vDialog.show(getSupportFragmentManager(), null);

        }
    }


    private void gestioneEndlessScroll(){
        mList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(view.getLastVisiblePosition()/10 == mPage-1){
                    webService.getMovie(webServerListener, ++mPage);
                }
            }
        });
    }

    private void setListaFilm() {
        mList.setAdapter(mAdapter);
        getSupportLoaderManager().initLoader(MY_LOADER_ID, null, this);
    }

    @NonNull
    @Override
    public Loader onCreateLoader(int id, @Nullable Bundle args) {
            return new CursorLoader(this, MovieProvider.MOVIES_URI, null, null, null,
                    MovieTableHelper.PAGINA + " ASC");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(query != "")
        {
            Cursor vCursor = getContentResolver().query(MovieProvider.MOVIES_URI, null,
                    MovieTableHelper.TITOLO + " LIKE '%" + query + "%'", null, null);
            mAdapter.swapCursor(vCursor);
        }
    }

    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor data) {
        mAdapter.changeCursor(data);
    }


    @Override
    public void onLoaderReset(@NonNull Loader loader) {
        mAdapter.changeCursor(null);
    }

    private boolean isNetworkAvailable(){
        boolean have_WIFI= false;
        boolean have_MobileData = false;
        ConnectivityManager connectivityManager = (ConnectivityManager)getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo[] networkInfos = connectivityManager.getAllNetworkInfo();
        for(NetworkInfo info:networkInfos){
            if (info.getTypeName().equalsIgnoreCase("WIFI"))if (info.isConnected())have_WIFI=true;
            if (info.getTypeName().equalsIgnoreCase("MOBILE DATA"))if (info.isConnected())have_MobileData=true;
        }
        return have_WIFI||have_MobileData;
    }

    private static boolean isAirplaneModeOn(Context context) {
        return Settings.System.getInt(context.getContentResolver(),
                Settings.Global.AIRPLANE_MODE_ON, 0) != 0;
    }

    @Override
    public void onResponse(boolean aResponse) {
        if(aResponse)
        {
            Intent intent=new Intent(Settings.ACTION_WIRELESS_SETTINGS);
            startActivity(intent);
        }

    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.my_menu, menu);
        MenuItem item = menu.findItem(R.id.search_icon);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String movieTitle) {
                query = movieTitle;
                mAdapter.getFilter().filter(movieTitle);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String movieTitle) {
                query = movieTitle;
                mAdapter.getFilter().filter(query);
                return true;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putInt("PAGINA", mPage);
        super.onSaveInstanceState(outState);
    }
}
