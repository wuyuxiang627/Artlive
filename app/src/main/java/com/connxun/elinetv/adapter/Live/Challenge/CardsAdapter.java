package com.connxun.elinetv.adapter.Live.Challenge;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.entity.ChallengeTypeEntity;

import java.util.ArrayList;

/**
 * Created by connxun-16 on 2018/1/6.
 * 通用配置的adapter
 */

public class CardsAdapter extends RecyclerView.Adapter<CardsAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    ArrayList<ChallengeTypeEntity> arrayList;
    private int selectedPosition = -1;// 选中的位置
    private OnItemClickListener mOnItemClickListener = null;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public CardsAdapter(Context context, ArrayList<ChallengeTypeEntity> arrayList) {
        this.context = context;
        this.arrayList =  arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_challenge_type_cards, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        ChallengeTypeEntity typeEntity = arrayList.get(position);
//                holder.rlType.setBackgroundColor(Color.parseColor("#F8ABFF"));
                holder.ivCover.setCardBackgroundColor((Color.parseColor(typeEntity.getText())));
                holder.tvUserName.setText(typeEntity.getName());

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
        TextView tvUserName;
        CardView ivCover;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.item_challenge_type_card);
            tvUserName = itemView.findViewById(R.id.item_challenge_type_card_title);
        }
    }
}
