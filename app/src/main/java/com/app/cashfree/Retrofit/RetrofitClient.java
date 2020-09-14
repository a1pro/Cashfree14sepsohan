package com.app.cashfree.Retrofit;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    Context context;
    public static String pref = "mypref";
    public  static SharedPreferences sharedPreferences;
    private static final String BASE_URL = "https://payout-gamma.cashfree.com/payout/";
    public static String token_fresh;

    public RetrofitClient(Context context, String token_fresh){
        this.context=context;
        sharedPreferences=context.getSharedPreferences(RetrofitClient.pref,Context.MODE_PRIVATE);
        this.token_fresh=token_fresh;
    }



    public static Retrofit retroInit() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        return new Retrofit.Builder()
                .baseUrl("http://a1professionals.net/chessFree/api/")
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }




    public static Retrofit retroInitHeader(final String token) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // token = sharedPreferences.getString("token", "");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
//                String token = sharedPreferences.getString("token", "");
              /*  String token="";
                if(sharedPreferences.getString("token", "")!=null){
                     token = sharedPreferences.getString("token", "");
                }*/

                requestBuilder.header("Authorization", "Bearer " + token); // <-- this is the important line
                requestBuilder.header("Accept", "application/json"); // <-- this is the important line
                requestBuilder.header("Content-Type", "application/x-www-form-urlencoded"); // <-- this is the important line
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        httpClient.connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        OkHttpClient client = httpClient.build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .build();
    }

    public static Retrofit retroInitHeaderAuthorisation(final String signature) {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        // OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();

        // token = sharedPreferences.getString("token", "");
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();
        httpClient.addInterceptor(new Interceptor() {
            @NotNull
            @Override
            public okhttp3.Response intercept(@NotNull Interceptor.Chain chain) throws IOException {
                Request original = chain.request();
                // Request customization: add request headers
                Request.Builder requestBuilder = original.newBuilder();
//                String token = sharedPreferences.getString("token", "");
                requestBuilder.header("X-Client-Id", "CF25608CMCUZXRXDFTEMAA"); // <-- this is the important line
                requestBuilder.header("X-Client-Secret", "e403f64786d5de474538d8b2f60b12a2c3983e85"); // <-- this is the important line// <-- this is the important line
                requestBuilder.header("X-Cf-Signature", signature); // <-- this is the important line// <-- this is the important line
                Request request = requestBuilder.build();
                return chain.proceed(request);
            }
        });

        httpClient.connectTimeout(2, TimeUnit.MINUTES)
                .readTimeout(2, TimeUnit.MINUTES)
                .writeTimeout(2, TimeUnit.MINUTES)
                .build();
        OkHttpClient client = httpClient.build();
        return new Retrofit.Builder()
                .baseUrl(BASE_URL).client(client)
                .addConverterFactory(GsonConverterFactory.create()).client(client)
                .build();
    }




    public boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        return cm.getActiveNetworkInfo() != null;
    }



    public String getToken() {
        return getSharedPref().getString("token",null);
    }

    public   void storeToken(String access_token) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", access_token);
        editor.commit();
    }

    private SharedPreferences getSharedPref()
    {
        return sharedPreferences;
    }

}
