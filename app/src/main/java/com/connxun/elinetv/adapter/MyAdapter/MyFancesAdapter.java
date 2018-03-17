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
import com.connxun.elinetv.entity.user.UserFollowEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/3/7.
 */

public class MyFancesAdapter extends RecyclerView.Adapter<MyFancesAdapter.ViewHolder> implements View.OnClickListener {
    Context context;
    List<UserFollowEntity> followlist = new ArrayList<>();
    private int selectedPosition = -1;// 选中的位置
    private MyFancesAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public MyFancesAdapter(Context context, List<UserFollowEntity> followlist) {
        this.context = context;
        this.followlist = followlist;
    }


    @Override
    public MyFancesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_fans, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        String coverPath = followlist.get(position).getAvatart();
//        String userPhotoPath = arrayList.get(position).getAvatar();
        if (coverPath != null) {
            //创建将要下载的图片的URI
            Uri imageUri = Uri.parse(coverPath);
            //开始下载
            holder.ivUserPhoto.setImageURI(imageUri);
        }

        holder.tv_item_fans_name.setText(followlist.get(position).getNickName());
        /*holder.tv_item_my_follow.setText(followlist.get(position).getAvatart());
        holder.tv_item_fans.setText(followlist.get(position).getUserNo()+"");*/
    }


    @Override
    public int getItemCount() {
        return followlist.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(MyFancesAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivCover;
        SimpleDraweeView ivUserPhoto;
        TextView tv_item_fans_name, tv_item_fans;

        public ViewHolder(View itemView) {
            super(itemView);
            ivUserPhoto = itemView.findViewById(R.id.iv_item_fans_photo);
            tv_item_fans_name = itemView.findViewById(R.id.tv_item_fans_name);
            tv_item_fans = itemView.findViewById(R.id.tv_item_fans);

        }
    }
}
