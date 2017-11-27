package id.futnet.praktikumprogmobm5;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindInt;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.futnet.praktikumprogmobm5.adapter.MemberAdapter;
import id.futnet.praktikumprogmobm5.api.ApiInterface;
import id.futnet.praktikumprogmobm5.api.ApiMember;
import id.futnet.praktikumprogmobm5.model.MemberList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class DetailMemberActivity extends AppCompatActivity {
    String response;
    String nama, email, sex, image;
    List<MemberList> memberList = new ArrayList<>();
    private ApiInterface apiInterface;
    @BindView(R.id.TV_emailLengkap) TextView txtEmail;
    @BindView(R.id.TV_sexLengkap) TextView txtSex;
    @BindView(R.id.TV_namaLengkap) TextView txtNama;
    @BindView(R.id.IV_memberDetail) ImageView imageView;
    @OnClick(R.id.btnUpdate) public void updateUser(View view){
        Intent intent = new Intent(this,UpdateActivity.class);
        response = getIntent().getStringExtra("id_member");
        intent.putExtra("id",response);
        intent.putExtra("nama", nama);
        intent.putExtra("email",email);
        intent.putExtra("image",image);
        intent.putExtra("sex",sex);
        startActivity(intent);
    }
    @OnClick(R.id.btnDelete)  public void deleteUser(View view) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Peringatan");
        alertDialogBuilder
                .setMessage("Apakah Anda yakin ingin mengapus data ini?")
                .setCancelable(false)
                .setPositiveButton("Hapus",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {

                        apiInterface = ApiMember.getApiMember().create(ApiInterface.class);
                        Call<MemberList> call = apiInterface.getDetele(response);
                        call.enqueue(new Callback<MemberList>() {
                            @Override
                            public void onResponse(Call<MemberList> call, Response<MemberList> response) {
                                response.body().getSuccess();
                                Toast.makeText(DetailMemberActivity.this, response.body().getSuccess(),
                                        Toast.LENGTH_SHORT).show();
                            }
                            @Override
                            public void onFailure(Call<MemberList> call, Throwable t) {

                            }
                        });
                    }
                })
                .setNegativeButton("Batal",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog,int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }
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
            }

            @Override
            public void onFailure(Call<MemberList> call, Throwable t) {
                Toast.makeText(DetailMemberActivity.this, "GAGAL", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void setView(){
        txtNama.setText(nama);
        txtEmail.setText(email);
        txtSex.setText(sex);
        Glide.with(this).load("http://192.168.43.105:8000/images/"+image).into(imageView);
    }

}
