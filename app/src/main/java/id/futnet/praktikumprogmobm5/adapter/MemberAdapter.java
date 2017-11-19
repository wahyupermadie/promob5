package id.futnet.praktikumprogmobm5.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import id.futnet.praktikumprogmobm5.R;
import id.futnet.praktikumprogmobm5.model.MemberList;
import retrofit2.Callback;

/**
 * Created by Wahyu Permadi on 11/17/2017.
 */

public class MemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{

    private List<MemberList> memberList;
    private Context context;
    private final ListItemClick mItemClick;

    public MemberAdapter(List<MemberList> memberList, Context context, ListItemClick mItemClick) {
        this.memberList = memberList;
        this.context = context;
        this.mItemClick = mItemClick;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_member,parent,false);
        return new Member(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ((Member)holder).TV_name.setText(memberList.get(position).getNama());
        ((Member)holder).TV_email.setText(memberList.get(position).getEmail());
        ((Member)holder).TV_hobi.setText(memberList.get(position).getHobi());
        ((Member)holder).TV_kelamin.setText(memberList.get(position).getKelamin());
        Glide.with(context).load("http://192.168.43.105:8000/images/"+memberList.get(position).getPicture()).into(((Member)holder).IV_member);
    }

    @Override
    public int getItemCount() {
        return memberList.size();
    }


    private class Member extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView TV_name;
        TextView TV_email;
        TextView TV_kelamin;
        TextView TV_hobi;
        CircleImageView IV_member;
        public Member(View view) {
            super(view);
            TV_kelamin = view.findViewById(R.id.TV_kelamin);
            TV_name = view.findViewById(R.id.TV_name);
            TV_email = view.findViewById(R.id.TV_email);
            IV_member = view.findViewById(R.id.IV_member);
            TV_hobi = view.findViewById(R.id.TV_hobi);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int position = getAdapterPosition();
            mItemClick.onItemClick(position);
        }
    }

    public interface ListItemClick {
        void onItemClick(int position);
    }
}
