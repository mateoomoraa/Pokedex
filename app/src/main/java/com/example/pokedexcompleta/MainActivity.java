package com.example.pokedexcompleta;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ArrayList<Pokemon> pokemons = new ArrayList<>();
    ArrayList<String> nombres = new ArrayList<>();
    static ArrayList<String> urlsImg = new ArrayList<>();
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.listadoPkm);
        System.out.println(" hola mundoooo");

        new Thread(new Runnable() {
            @Override
            public void run() {



                        try {
                            Document resultadoCompleto = Jsoup.connect("https://www.pokemon.com/es/pokedex/").get();
                            //le das a seleccionar elemento en la herramienta de desarrolladores, te vas a ul, click derecho, copyxpath
                            nombres = (ArrayList<String>) resultadoCompleto.select("[href^=/es/pokedex/]").eachText();
                            nombres.remove(0);  //en esta pagina el elemento 0 no vale
                            for (int i = 0 ; i<nombres.size(); i++) {
                                String numPkm = String.format("%03d", i+1);
                                urlsImg.add("https://assets.pokemon.com/assets/cms2/img/pokedex/full/" + i + ".png");
                                pokemons.add(new Pokemon(nombres.get(i)));  //confirmar la lista de nombres
                            }


                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                CustomAdapter adapter = new CustomAdapter(pokemons, MainActivity.this);
                                listView.setAdapter(adapter);
                            }
                        });



            }
        }).start();


    }
}