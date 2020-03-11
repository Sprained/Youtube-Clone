package com.men.youtube_clone.activitys;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;
import com.men.youtube_clone.R;
import com.men.youtube_clone.adapter.AdapterVideo;
import com.men.youtube_clone.api.YoutubeService;
import com.men.youtube_clone.helper.RetrofitConfig;
import com.men.youtube_clone.helper.YoutubeConfig;
import com.men.youtube_clone.listener.RecyclerItemClickListener;
import com.men.youtube_clone.model.Item;
import com.men.youtube_clone.model.Resultado;
import com.men.youtube_clone.model.Video;
import com.miguelcatalan.materialsearchview.MaterialSearchView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recycler;
    private MaterialSearchView searchView;

    private List<Item> videos = new ArrayList<>();
    private Resultado resultado;
    private AdapterVideo adapterVideo;

    //Retrofit
    private Retrofit retrofit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recycler = findViewById(R.id.recycler);
        searchView = findViewById(R.id.searchView);

        //Configurações Iniciais
        retrofit = RetrofitConfig.getRetrofit();

        //Configuração da toolbar
        Toolbar toolbar = findViewById(R.id.toobar);
        toolbar.setTitle("Youtube-Cosmonautas");
        setSupportActionBar(toolbar);

        //Recuperar recyclerview
        listVideos("");

        //Metodos SearchView
        searchView.setOnQueryTextListener(new MaterialSearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                listVideos(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        searchView.setOnSearchViewListener(new MaterialSearchView.SearchViewListener() {
            @Override
            public void onSearchViewShown() {

            }

            @Override
            public void onSearchViewClosed() {
                listVideos("");
            }
        });

    }

    private void listVideos(String pesquisar){
        String q = pesquisar.replaceAll(" ", "+");
        YoutubeService youtubeService = retrofit.create(YoutubeService.class);
        youtubeService.listVideos("snippet", "date", "30", YoutubeConfig.YOUTUBE_KEY, YoutubeConfig.CANAL_ID, q).enqueue(new Callback<Resultado>() {
            @Override
            public void onResponse(Call<Resultado> call, Response<Resultado> response) {
                Log.d("resultado", "resultado: " +response.toString());
                if(response.isSuccessful()){
                    resultado = response.body();
                    videos = resultado.items;
                    configRecycler();
                }
            }

            @Override
            public void onFailure(Call<Resultado> call, Throwable t) {

            }
        });
    }

    public void configRecycler(){
        adapterVideo = new AdapterVideo(videos, this);
        recycler.setHasFixedSize(true);
        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapterVideo);

        //abrir video
        recycler.addOnItemTouchListener(new RecyclerItemClickListener(this, recycler, new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Item video = videos.get(position);
                String idVideo = video.id.videoId;

                Intent i = new Intent(MainActivity.this, PlayerActivity.class);
                i.putExtra("idVideo", idVideo);
                startActivity(i);
            }

            @Override
            public void onLongItemClick(View view, int position) {

            }

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        }));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);

        MenuItem item = menu.findItem(R.id.menu_search);
        searchView.setMenuItem(item);

        return true;
    }
}
