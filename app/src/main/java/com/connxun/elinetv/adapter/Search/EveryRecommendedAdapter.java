package com.connxun.elinetv.adapter.Search;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.connxun.elinetv.R;

import java.util.ArrayList;

/**
 * Created by connxun-16 on 2018/1/6.
 */

public class EveryRecommendedAdapter extends RecyclerView.Adapter<EveryRecommendedAdapter.ViewHolder> {
    Context context;
    ArrayList<String> arrayList;

    public EveryRecommendedAdapter(Context context, ArrayList<String> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        ViewHolder holder = new ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_search_every_recommend, parent, false));
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

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tv_item_hot_user_name;

        public ViewHolder(View itemView) {
            super(itemView);
            tv_item_hot_user_name = itemView.findViewById(R.id.iv_item_search_recommend_name);

        }
    }
}
