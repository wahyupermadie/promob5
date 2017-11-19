package id.futnet.praktikumprogmobm5.api;


import org.json.JSONObject;

import java.util.List;

import id.futnet.praktikumprogmobm5.model.MemberList;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by Wahyu Permadi on 11/17/2017.
 */

public interface ApiInterface {
    @GET("member/{id}")
    Call<MemberList> getDetail(@Path("id") String id);

    @GET("member")
    Call<List<MemberList>> getMember();

    @FormUrlEncoded
    @POST("member")
    Call<JSONObject> register(@Field("Nama") String nama,
                              @Field("Email") String email,
                              @Field("Sex") String sex,
                              @Field("Picture") String image);

    @DELETE("member/{id}")
    Call<MemberList> getDetele(@Path("id") String id);


}
