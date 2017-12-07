package id.futnet.praktikumprogmobm5;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import id.futnet.praktikumprogmobm5.api.ApiInterface;
import id.futnet.praktikumprogmobm5.api.ApiMember;
import id.futnet.praktikumprogmobm5.model.MemberList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UpdateActivity extends AppCompatActivity {
    private ApiInterface apiInterface;
    private final static int IMG_REQUEST = 777;
    private Bitmap bitmap;
    private int selectedId;
    private ProgressDialog progress;
    String mSex;
    RadioButton radioSexButton;
    String id, nama, email = null, sex, image;
    @BindView(R.id.RG_jenisKelamin) RadioGroup rgSex;
    @BindView(R.id.IV_avatar) ImageView ivAvatar;
    @BindView(R.id.ET_your_full_name) EditText txtName;
    @BindView(R.id.ET_your_email_address) EditText txtEmail;
    @BindView(R.id.RB_laki) RadioButton rbMen;
    @BindView(R.id.RB_wanita) RadioButton rbWomen;
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

    private String imageToString(){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] imgByte = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imgByte,Base64.DEFAULT);
    }
    @OnClick(R.id.ButtomUpdate) public void submitAction(View view){
        //membuat progress dialog
        progress = new ProgressDialog(this);
        progress.setCancelable(false);
        progress.setMessage("Loading ...");
        progress.show();
        String namax = txtName.getText().toString();
        String emailx = txtEmail.getText().toString();
        String newsex = mSex;
        String imagex = "";
        if (bitmap != null){
            imagex = imageToString();
        }
        apiInterface = ApiMember.getApiMember().create(ApiInterface.class);
        Call<JSONObject> call = apiInterface.getUpdate(id,namax,emailx,imagex,newsex);
        call.enqueue(new Callback<JSONObject>() {
            @Override
            public void onResponse(Call<JSONObject> call, Response<JSONObject> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(UpdateActivity.this, "Data Berhasil Di Update",
                            Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent();
                    setResult(Activity.RESULT_OK, intent);
                    UpdateActivity.this.finish();
                }
                else{
                    Toast.makeText(UpdateActivity.this, "ERROR", Toast.LENGTH_SHORT).show();
                }
                progress.dismiss();
            }
            @Override
            public void onFailure(Call<JSONObject> call, Throwable t) {
                Toast.makeText(UpdateActivity.this, "ERROR"+t, Toast.LENGTH_SHORT).show();
                progress.dismiss();
            }
        });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        ButterKnife.bind(this);
        Intent intent = getIntent();
        id = intent.getStringExtra("id");
        nama = intent.getStringExtra("nama");
        email = intent.getStringExtra("email");
        sex = intent.getStringExtra("sex");
        image = intent.getStringExtra("image");
        txtName.setText(nama);
        txtEmail.setText(email);
        if(sex.equals("1")){
            rbMen.setChecked(true);
        }else if(sex.equals("2")){
            rbWomen.setChecked(true);
        }
        else{
            //else
        }
        selectedId = rgSex.getCheckedRadioButtonId();
        if(selectedId == -1){
            Toast.makeText(this, "Isi Jenis Kelamin", Toast.LENGTH_SHORT).show();
        }else{
            radioSexButton = (RadioButton) findViewById(selectedId);
            mSex = radioSexButton.getText().toString();
        }
        Glide.with(this).load("http://192.168.43.105:8000/images/"+image).into(ivAvatar);
    }
}
