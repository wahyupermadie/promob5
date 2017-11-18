package id.futnet.praktikumprogmobm5.api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Wahyu Permadi on 11/17/2017.
 */

public class ApiMember {

    public static final String BASE_URL = "http://192.168.43.105:8000/";
    public static Retrofit retrofit;

    public static Retrofit getApiMember(){
        if(retrofit == null){
            retrofit = new Retrofit.Builder().baseUrl(BASE_URL).
                    addConverterFactory(GsonConverterFactory.create()).build();
        }
        return retrofit;
    }
}
