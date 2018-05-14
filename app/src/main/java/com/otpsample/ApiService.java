package com.otpsample;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

/**
 * Created by Rajesh Kumar on 14-05-2018.
 */
public interface ApiService {


    @GET
    Observable<String> getData(@Url String action, @QueryMap Map<String, String> fields);
}
