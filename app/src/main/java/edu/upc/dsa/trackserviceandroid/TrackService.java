package edu.upc.dsa.trackserviceandroid;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface TrackService {
    @GET("tracks")
    Call<List<Tracks>> listTracks();
}
