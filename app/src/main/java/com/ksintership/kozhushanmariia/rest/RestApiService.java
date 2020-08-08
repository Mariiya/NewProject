package com.ksintership.kozhushanmariia.rest;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Url;

public interface RestApiService {
    String LOG_TAG = RestApiService.class.getSimpleName();

    @GET("/search")
    Call<DeezerResponse> search(@Query("q") String query);

    @GET()
    Call<DeezerResponse> reloadTrackList(@Url String nextList);

    @GET()
    Call<ResponseBody> loadTrackPreview(@Url String preview);
}
