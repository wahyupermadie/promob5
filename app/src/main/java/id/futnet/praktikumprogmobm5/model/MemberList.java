package id.futnet.praktikumprogmobm5.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Wahyu Permadi on 11/17/2017.
 */

public class MemberList {
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNama() {
        return Nama;
    }

    public void setNama(String nama) {
        Nama = nama;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getTinggi() {
        return Tinggi;
    }

    public void setTinggi(String tinggi) {
        Tinggi = tinggi;
    }

    public String getKelamin() {
        return Kelamin;
    }

    public void setKelamin(String kelamin) {
        Kelamin = kelamin;
    }

    public String getHobi() {
        return Hobi;
    }

    public void setHobi(String hobi) {
        Hobi = hobi;
    }

    public String getPicture() {
        return Picture;
    }

    public void setPicture(String picture) {
        Picture = picture;
    }

    @SerializedName("id")
    @Expose
    private int id;
    private String Nama;
    private String Email;
    private String Tinggi;
    private String Kelamin;
    private String Hobi;
    private String Picture;

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    private String success;
    private String error;
}
