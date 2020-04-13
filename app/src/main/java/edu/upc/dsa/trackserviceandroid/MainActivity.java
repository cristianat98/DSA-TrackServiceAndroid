package edu.upc.dsa.trackserviceandroid;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView canciones;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        canciones = (TextView)findViewById(R.id.canciones);
    }

    public void add (View v){

        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .build();

        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://localhost:8080/dsaApp/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();

        TrackService service = retrofit.create(TrackService.class);

        Call<List<Tracks>> tracks = service.listTracks();


        tracks.enqueue(new Callback<List<Tracks>>() {
            @Override
            public void onResponse(Call<List<Tracks>> call, Response<List<Tracks>> response) {
                List<Tracks> result = response.body();
                String texto = "Canciones:";

                for (Tracks t : result) {
                    texto = texto + t.singer + ",";
                }

                texto = texto + "]";
                canciones.setText(texto);
            }

            @Override
            public void onFailure(Call<List<Tracks>> call, Throwable t) {
                canciones.setText("ERROR");
            }
        });
    }
}
