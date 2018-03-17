package com.connxun.elinetv.adapter.Live;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.entity.LiveModel;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/1/6.
 */

public class HotRecommendedAdapter extends RecyclerView.Adapter<HotRecommendedAdapter.ViewHolder> {
    Context context;
    ArrayList<LiveModel> arrayList;

    public HotRecommendedAdapter(Context context, List<LiveModel> arrayList) {
        this.context = context;
        this.arrayList = (ArrayList<LiveModel>) arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_live_hot_recommendedor, parent, false));
        return holder;
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.tv_item_hot_user_name.setText("第" + position + "个");
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public List<LiveModel> getList() {
        arrayList.clear();
        return arrayList;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_hot_user_name;
        TextView tv_item_hot_anchor_user_name;
        SimpleDraweeView ivCavar;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_item_hot_user_name = itemView.findViewById(R.id.tv_item_hot_user_name);
            ivCavar = itemView.findViewById(R.id.iv_item_hot_cover);

        }
    }
}
