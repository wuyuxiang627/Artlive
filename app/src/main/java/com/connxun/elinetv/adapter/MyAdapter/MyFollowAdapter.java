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
import com.connxun.elinetv.adapter.VideoShortAdapter.VideoTalkAdapter;
import com.connxun.elinetv.entity.Video.Talk;
import com.connxun.elinetv.entity.user.UserFollowEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/3/6.
 */

public class MyFollowAdapter extends RecyclerView.Adapter<MyFollowAdapter.ViewHolder> implements View.OnClickListener {
    Context context;
    List<UserFollowEntity> followlist = new ArrayList<>();
    private int selectedPosition = -1;// 选中的位置
    private OnItemClickListener mOnItemClickListener = null;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public MyFollowAdapter(Context context, List<UserFollowEntity> followlist) {
        this.context = context;
        this.followlist = followlist;
    }

    @Override
    public MyFollowAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_follow, parent, false);
        MyFollowAdapter.ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;

    }

    @Override
    public void onBindViewHolder(MyFollowAdapter.ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        String coverPath = followlist.get(position).getAvatart();
        //String userPhotoPath = followlist.get(position).getAvatart();
        if (coverPath != null) {
            //创建将要下载的图片的URI
            Uri imageUri = Uri.parse(coverPath);
            //开始下载
            holder.ivUserPhoto.setImageURI(imageUri);
        }

        holder.tv_item_follow_name.setText(followlist.get(position).getNickName());


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

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivCover;
        SimpleDraweeView ivUserPhoto;
        TextView tv_item_follow_name;

        public ViewHolder(View itemView) {
            super(itemView);
            ivUserPhoto = itemView.findViewById(R.id.iv_item_follow_photo);
          //  iv_item_my_follow = itemView.findViewById(R.id.iv_item_my_follow);
            tv_item_follow_name = itemView.findViewById(R.id.tv_item_follow_name);
           // tv_item_fans = itemView.findViewById(R.id.tv_item_fans);
           // tv_item_my_follow = itemView.findViewById(R.id.tv_item_my_follow);

        }
    }
}
