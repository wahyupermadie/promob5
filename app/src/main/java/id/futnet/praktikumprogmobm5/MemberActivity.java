package id.futnet.praktikumprogmobm5;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.futnet.praktikumprogmobm5.api.ApiInterface;
import id.futnet.praktikumprogmobm5.api.ApiMember;
import id.futnet.praktikumprogmobm5.model.MemberList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MemberActivity extends AppCompatActivity {

    private RadioButton radioSexButton;
    private ProgressDialog progress;
    private int selectedId;
    private String mSex;
    private ApiInterface apiInterface;
    private final static int IMG_REQUEST = 777;
    private Bitmap bitmap;
    @BindView(R.id.IV_avatar) ImageView ivAvatar;
    @BindView(R.id.ET_your_full_name) EditText txtName;
    @BindView(R.id.ET_your_email_address) EditText txtEmail;
    @BindView(R.id.RG_jenisKelamin) RadioGroup rgSex;
    @OnClick(R.id.Btn_avatar) void selectImage(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,IMG_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==IMG_REQUEST && resultCode==RESULT_OK && data != null)
        {
            Uri path = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),path);
                ivAvatar.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @OnClick(R.id.ButtomRegister) void validate(){
        selectedId = rgSex.getCheckedRadioButtonId();
        if (txtName.getText().toString().length()==0){
            txtName.setError("Nama diperlukan!");
        }
        else if(txtEmail.getText().toString().length()==0) {
            txtEmail.setError("Email diperlukan!");
        }else if(selectedId == -1){
            Toast.makeText(this, "Isi Jenis Kelamin", Toast.LENGTH_SHORT).show();
        }else{
            radioSexButton = (RadioButton) findViewById(selectedId);
            mSex = radioSexButton.getText().toString();
            register();
        }
    }
    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
    private void register() {
        //membuat progress dialog
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();
        //mengambil data dari edittext
        String nama = txtName.getText().toString();
        String email = txtEmail.getText().toString();
        String sex = mSex;
        String image = imageToString();
        apiInterface = ApiMember.getApiMember().create(ApiInterface.class);
        Call<JSONObject> call = apiInterface.register(nama,email,sex,image);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                try {
                    JSONArray river = response.body().getJSONArray("success");
                    Toast.makeText(MemberActivity.this, river.toString(), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                Toast.makeText(MemberActivity.this, "SUKSES"+response, Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }

            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Toast.makeText(MemberActivity.this, "GAGAL"+t, Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member);
        ButterKnife.bind(this);
    }
}
