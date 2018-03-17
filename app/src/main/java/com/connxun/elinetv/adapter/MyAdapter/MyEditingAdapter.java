package com.connxun.elinetv.adapter.MyAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.entity.user.UserDataEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/3/9.
 */

public class MyEditingAdapter extends RecyclerView.Adapter<MyEditingAdapter.ViewHolder> implements View.OnClickListener {
    Context context;
    List<UserDataEntity> datalist = new ArrayList<>();
    private int selectedPosition = -1;// 选中的位置
    private OnItemClickListener mOnItemClickListener = null;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public MyEditingAdapter(Context context, List<UserDataEntity> datalist) {
        this.context = context;
        this.datalist = datalist;
    }


    @Override
    public MyEditingAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_editing, parent, false);
        MyEditingAdapter.ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(MyEditingAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        String coverPath = datalist.get(position).getAvatar();
        //String userPhotoPath = followlist.get(position).getAvatart();
        if (coverPath != null) {
            //创建将要下载的图片的URI
            Uri imageUri = Uri.parse(coverPath);
            //开始下载
            holder.ivCover.setImageURI(imageUri);
            //my_editing_avatar
        }

        holder.tv_editing_nick_name.setText(datalist.get(position).getNickName());
        holder.tv_editing_birthday.setText(datalist.get(position).getBirthday());
        holder.tv_editing_city.setText(datalist.get(position).getCity());
        holder.tv_editing_userNo.setText(datalist.get(position).getUserNo());
    }

    @Override
    public int getItemCount() {
        return datalist.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(MyEditingAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivCover;
        SimpleDraweeView ivUserPhoto;
        TextView tv_editing_nick_name;
        TextView tv_editing_sex;
        TextView tv_editing_birthday;
        TextView tv_editing_city;
        TextView tv_editing_userNo;
        ImageView my_editing_avatar;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_item_edit_cover);
            tv_editing_nick_name = itemView.findViewById(R.id.tv_editing_nick_name);
            tv_editing_sex = itemView.findViewById(R.id.tv_editing_sex);
            tv_editing_birthday = itemView.findViewById(R.id.tv_editing_birthday);
            tv_editing_city = itemView.findViewById(R.id.tv_editing_city);
            tv_editing_userNo = itemView.findViewById(R.id.tv_editing_userNo);
            my_editing_avatar = itemView.findViewById(R.id.my_editing_avatar);

        }
    }

}
