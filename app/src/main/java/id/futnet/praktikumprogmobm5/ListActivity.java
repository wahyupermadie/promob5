package id.futnet.praktikumprogmobm5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewDebug;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.List;

import id.futnet.praktikumprogmobm5.adapter.MemberAdapter;
import id.futnet.praktikumprogmobm5.api.ApiInterface;
import id.futnet.praktikumprogmobm5.api.ApiMember;
import id.futnet.praktikumprogmobm5.model.MemberList;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListActivity extends AppCompatActivity implements MemberAdapter.ListItemClick{
    private RecyclerView mRecycleView;
    private RecyclerView.LayoutManager layoutManager;
    private List<MemberList> memberList;
    private MemberAdapter memberAdapter;
    private ApiInterface apiInterface;
    Gson gson;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        mRecycleView = (RecyclerView)findViewById(R.id.RV_member);
        layoutManager = new LinearLayoutManager(this);
        mRecycleView.setLayoutManager(layoutManager);
        mRecycleView.setHasFixedSize(true);
        getMember();
    }

    private void getMember() {
        apiInterface = ApiMember.getApiMember().create(ApiInterface.class);
        Call<List<MemberList>> call = apiInterface.getMember();

        call.enqueue(new Callback<List<MemberList>>() {
            @Override
            public void onResponse(Call<List<MemberList>> call, Response<List<MemberList>> response) {
                memberList = response.body();
                setMemberAdapter();
            }
            @Override
            public void onFailure(Call<List<MemberList>> call, Throwable t) {

            }
        });
    }
    private void setMemberAdapter(){
        memberAdapter = new MemberAdapter(memberList,ListActivity.this,this);
        mRecycleView.setAdapter(memberAdapter);
    }
    @Override
    public void onItemClick(int position) {
        int member = memberList.get(position).getId();
        String id_member = String.valueOf(member);
//        Toast.makeText(this, member1, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this,DetailMemberActivity.class);
        intent.putExtra("id_member", id_member);
        startActivity(intent);
    }
}
