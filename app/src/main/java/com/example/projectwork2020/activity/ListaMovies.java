package com.example.projectwork2020.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.projectwork2020.R;

public class ListaMovies extends AppCompatActivity {
    
    ListView mList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_movies);
        
        mList = findViewById(R.id.listViewFilm);
        
        mList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                //FARE INTENT ALLA LISTA DETAIL
            }
        });
        
        aggiornaListaFilm();
    }

    private void aggiornaListaFilm() {
    }
}
