package com.otpsample;



import android.util.Log;

import java.util.HashMap;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Rajesh Kumar on 14-05-2018.
 */
public class FetchResponse  {

    public static FetchResponse instance;

    public static FetchResponse getInstance(){

        if(null==instance){
            instance = new FetchResponse();
        }

        return instance;
    }





    public void getResponse(String otp){

        fetchData(otp).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<String>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.e("subcribe is ","<><<>");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e("onNext is ","<><<>"+s);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("onError is ","<><<>");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete is ","<><<>");
                    }
                });


    }



    private Observable<String > fetchData(String otp){
        return   (new RetrofitClient().getClient(API.baseurl).create(ApiService.class))
                .getData(API.baseurl, getParams(otp))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());

    }

    private HashMap<String ,String> getParams(String otp){

        HashMap<String ,String > params = new HashMap<>();
        params.put("Mobile", "7799214213");
        params.put("Password", "RAJESH");
        params.put("Message",otp);
        params.put("To", "7981755973");
        params.put("Key", "rajesY2M1iWZInjNQXCARw3x");




        return params;
    }


}
