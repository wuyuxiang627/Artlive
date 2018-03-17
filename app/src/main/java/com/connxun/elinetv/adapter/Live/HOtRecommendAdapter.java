package com.connxun.elinetv.adapter.Live;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.VideoShortAdapter.SoundTrackAdapter;
import com.connxun.elinetv.entity.LiveModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by connxun-16 on 2018/1/6.
 */

/**
 * 推荐主播
 */
public class HOtRecommendAdapter extends RecyclerView.Adapter<HOtRecommendAdapter.ViewHolder>implements View.OnClickListener{
    Context context;
    List<LiveModel> arrayList;
    private SoundTrackAdapter.OnItemClickListener mOnItemClickListener = null;

    public HOtRecommendAdapter(Context context, List<LiveModel> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_live_hot_recommendedor, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if(arrayList.get(position).getCover() != null){
            Uri uri = Uri.parse(arrayList.get(position).getCover());
            holder.ivCavar.setImageURI(uri);
        }

        holder.tv_item_hot_anchor_user_name.setText(arrayList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public List<LiveModel> getList() {
        arrayList.clear();
        return arrayList;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public void setOnItemClickListener(SoundTrackAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tv_item_hot_anchor_user_name;
        SimpleDraweeView ivCavar;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_item_hot_anchor_user_name = itemView.findViewById(R.id.tv_item_hot_user_name);
            ivCavar = itemView.findViewById(R.id.iv_item_hot_cover);

        }
    }


}
