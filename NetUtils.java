package com.clock.systemui.utils;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import java.util.concurrent.TimeUnit;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetUtils {

    private static NetUtils mClient;

    private OkHttpClient.Builder mBuilder;

    private NetUtils() {
        initOkHttp();
    }

    public static NetUtils getInstance() {
        if (mClient == null) {
            synchronized (NetUtils.class) {
                if (mClient == null) {
                    mClient = new NetUtils();
                }
            }
        }
        return mClient;
    }

    public <T> T create(Class<T> service, String baseUrl) {
        checkNotNull(service, "service is null");
        checkNotNull(baseUrl, "baseUrl is null");

        return new Retrofit.Builder()
                .baseUrl(baseUrl)                                           //baseurl路径
                .client(mBuilder.build())                                   //okhttp
                .addConverterFactory(GsonConverterFactory.create())         //Gson
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())  //Rx
                .build()
                .create(service);                                           //接口，返回service接口
    }

    private <T> T checkNotNull(T object, String message) {
        if (object == null) {
            throw new NullPointerException(message);
        }
        return object;
    }

    private void initOkHttp() {

        mBuilder = new OkHttpClient.Builder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS);
    }
}

