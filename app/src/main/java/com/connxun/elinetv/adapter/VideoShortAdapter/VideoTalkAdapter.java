package com.connxun.elinetv.adapter.VideoShortAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.entity.Video.Talk;
import com.connxun.elinetv.entity.VideoEtivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/1/6.
 * 通用配置的adapter
 */

public class VideoTalkAdapter extends RecyclerView.Adapter<VideoTalkAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    ArrayList<Talk> arrayList;
    private int selectedPosition = -1;// 选中的位置
    private OnItemClickListener mOnItemClickListener = null;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public VideoTalkAdapter(Context context, List<Talk> arrayList) {
        this.context = context;
        this.arrayList = (ArrayList<Talk>) arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_play_video_talk, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        String coverPath = arrayList.get(position).getAvatar();
//        String userPhotoPath = arrayList.get(position).getAvatar();
        if(coverPath != null){
            //创建将要下载的图片的URI
            Uri imageUri = Uri.parse(coverPath);
            //开始下载
            holder.ivCover.setImageURI(imageUri);
        }

        holder.tvUserName.setText(arrayList.get(position).getNickName());
        holder.tvTalkText.setText(arrayList.get(position).getContent());
        holder.tvLikeNumber.setText(arrayList.get(position).getLoveNum()+"");

        int loveState = arrayList.get(position).getLoveState();
        if(loveState == 0){
            holder.ivLike.setBackgroundResource(R.drawable.icon_play_video_like_ok);
        }else {
            holder.ivLike.setBackgroundResource(R.drawable.icon_talk_no_like);
        }
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
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
        ImageView ivLike;
        TextView tvUserName;
        TextView tvTalkText;
        TextView tvLikeNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_item_talk_user_photo);
            ivLike = itemView.findViewById(R.id.iv_item_talk_like);
            tvTalkText = itemView.findViewById(R.id.tv_item_talk_text);
            tvUserName = itemView.findViewById(R.id.tv_item_talk_name);
            tvLikeNumber = itemView.findViewById(R.id.tv_item_talk_like_num);
        }
    }
}
