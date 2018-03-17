package com.connxun.elinetv.adapter.VideoShortAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.entity.VideoEtivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/1/6.
 * 通用配置的adapter
 */

public class VideoUniversalWaterFallAdapter extends RecyclerView.Adapter<VideoUniversalWaterFallAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    ArrayList<VideoEtivity> arrayList = new ArrayList<>();
    private int selectedPosition = -1;// 选中的位置
    private OnItemClickListener mOnItemClickListener = null;
    private List<Integer> mHeights;

    public void getRandomHeight(List<VideoEtivity> mList){
        mHeights = new ArrayList<>();
        for(int i=0; i < mList.size();i++){
            //随机的获取一个范围为200-600直接的高度
            mHeights.add((int)(700+Math.random()*300));
        }

    }


    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public VideoUniversalWaterFallAdapter(Context context) {
        this.context = context;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_video_universal, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Log.e("fsdfadapter","positon: "+ position);
        Log.e("fsdfadapter","arrayList.size(): "+ arrayList.size());

        ViewGroup.LayoutParams layoutParams = holder.itemView.getLayoutParams();
        layoutParams.height = mHeights.get(position);
        holder.itemView.setLayoutParams(layoutParams);


        String coverPath = arrayList.get(position).getCover();
        String userPhotoPath = arrayList.get(position).getAvatar();


        if(coverPath != null){
            //创建将要下载的图片的URI
            Uri imageUri = Uri.parse(coverPath);
            //开始下载
            holder.ivCover.setImageURI(imageUri);

        }

        if(userPhotoPath != null){
            //创建将要下载的图片的URI
            Uri imageUserUri = Uri.parse(userPhotoPath);
            //开始下载
            holder.ivUserPhoto.setImageURI(imageUserUri);
        }

        holder.tvVideoTitle.setText(arrayList.get(position).getTitle());
//        holder.tvLikeNumber.setText(arrayList.get(position).getViewNum());

    }

    public List<VideoEtivity> getList() {
        arrayList.clear();
        return arrayList;
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
        TextView tvVideoTitle;
        TextView tvLikeNumber;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_item_vidoe_universal_video_cover);
            ivUserPhoto = itemView.findViewById(R.id.iv_item_video_universal_user_photo);
            ivLike = itemView.findViewById(R.id.iv_item_video_universal_like);
            tvVideoTitle = itemView.findViewById(R.id.tv_item_video_universal_video_title);
            tvLikeNumber = itemView.findViewById(R.id.tv_item_video_universal_like_number);
        }
    }
}
