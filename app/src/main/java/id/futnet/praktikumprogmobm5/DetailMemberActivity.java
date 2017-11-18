package id.futnet.praktikumprogmobm5;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import id.futnet.praktikumprogmobm5.adapter.MemberAdapter;
import id.futnet.praktikumprogmobm5.api.ApiInterface;
import id.futnet.praktikumprogmobm5.api.ApiMember;
import id.futnet.praktikumprogmobm5.model.MemberList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailMemberActivity extends AppCompatActivity {
    String response;
    String nama, email, sex, image;
    List<MemberList> memberList = new ArrayList<>();
    private ApiInterface apiInterface;
    @BindView(R.id.TV_namaLengkap) TextView txtNama;
    @BindView(R.id.IV_memberDetail) ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_member);
        response = getIntent().getStringExtra("id_member");
        ButterKnife.bind(this);
        getDetail();
    }
    private void getDetail(){
        apiInterface = ApiMember.getApiMember().create(ApiInterface.class);
        Call<MemberList> call = apiInterface.getDetail(response);
        call.enqueue(new Callback<MemberList>() {
            @Override
            public void onResponse(Call<MemberList> call, Response<MemberList> response) {
                response.body();
                nama = response.body().getNama();
                email = response.body().getEmail();
                sex = response.body().getKelamin();
                image = response.body().getPicture();
                setView();
//                Toast.makeText(DetailMemberActivity.this,"sukses" , Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<MemberList> call, Throwable t) {
                Toast.makeText(DetailMemberActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setView(){
        txtNama.setText(nama);
        Glide.with(this).load("http://192.168.43.105:8000/images/"+image).into(imageView);
    }

}
