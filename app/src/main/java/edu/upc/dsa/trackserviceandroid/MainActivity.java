package edu.upc.dsa.trackserviceandroid;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager layoutManager;
    TextView canciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        layoutManager = new LinearLayoutManager(this);
        canciones = (TextView)findViewById(R.id.canciones);
    }

    public void add (View v){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        TrackService service = retrofit.create(TrackService.class);

        Call<List<Tracks>> tracks = service.listTracks();


        tracks.enqueue(new Callback<List<Tracks>>() {
            @Override
            public void onResponse(Call<List<Tracks>> call, Response<List<Tracks>> response) {
                List<Tracks> result = response.body();
                setContentView(R.layout.lista_tracks);
                recyclerView = (RecyclerView) findViewById(R.id.my_recycler_view);
                // use this setting to
                // improve performance if you know that changes
                // in content do not change the layout size
                // of the RecyclerView
                recyclerView.setHasFixedSize(true);
                // use a linear layout manager

                recyclerView.setLayoutManager(layoutManager);
                // define an adapter
                mAdapter = new MyAdapter(result);
                recyclerView.setAdapter(mAdapter);
            }

            @Override
            public void onFailure(Call<List<Tracks>> call, Throwable t) {
                canciones.setText("ERROR");
            }
        });
    }

    public void atras (View v){

        setContentView(R.layout.activity_main);
    }
}

