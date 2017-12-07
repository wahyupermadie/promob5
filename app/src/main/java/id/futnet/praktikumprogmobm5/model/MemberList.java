package id.futnet.praktikumprogmobm5.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by Wahyu Permadi on 11/17/2017.
 */

public class MemberList {
    private int id;
    private String Nama;
    private String Email;
    private String Tinggi;
    private int sex_id;
    public String getName_sex() {
        return name_sex;
    }
    public void setName_sex(String name_sex) {
        this.name_sex = name_sex;
    }
    private String name_sex;
    private String Hobi;
    private String Picture;

    public int getSex_id() {
        return sex_id;
    }

    public void setSex_id(int sex_id) {
        this.sex_id = sex_id;
    }

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
