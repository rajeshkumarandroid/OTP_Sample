package com.otpsample;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Rajesh kumar on 13-07-2017.
 */

public class RetrofitClient extends AppCompatActivity  {
    private static Retrofit retrofit = null;
    private static RetrofitClient uniqInstance;
    Context ctx;

    public static RetrofitClient getInstance() {
        if (uniqInstance == null) {
            uniqInstance = new RetrofitClient();
        }
        return uniqInstance;
    }

    public static Retrofit getClient(String baseUrl) {
        final OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(1, TimeUnit.MINUTES)
                .connectTimeout(1, TimeUnit.MINUTES)
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(new RetrofitConverter())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        return retrofit;
    }

}
