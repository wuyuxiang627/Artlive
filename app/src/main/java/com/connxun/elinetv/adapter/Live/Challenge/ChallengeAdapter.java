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

public class ChallengeAdapter extends RecyclerView.Adapter<ChallengeAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    ArrayList<ChallengeTypeEntity> arrayList;
    private int selectedPosition = -1;// 选中的位置
    private OnItemClickListener mOnItemClickListener = null;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public ChallengeAdapter(Context context, ArrayList<ChallengeTypeEntity> arrayList) {
        this.context = context;
        this.arrayList =  arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_challenge, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        ChallengeTypeEntity typeEntity = arrayList.get(position);
//        switch (typeEntity.getId())
//        {
//            case 1:
//                holder.rlType.setCardBackgroundColor(Color.parseColor("#F8ABFF"));
//                holder.ivCover.setBackgroundResource(R.drawable.icon_challenge_type_singe);
//                holder.tvTalkText.setText(typeEntity.getText());
//                holder.tvUserName.setText(typeEntity.getName());
//                break;
//            case 2:
//                holder.rlType.setCardBackgroundColor(Color.parseColor("#6DCEFF"));
//                holder.ivCover.setBackgroundResource(R.drawable.icon_challenge_type_rap);
//                holder.tvTalkText.setText(typeEntity.getText());
//                holder.tvUserName.setText(typeEntity.getName());
//                break;
//            case 3:
//                holder.rlType.setCardBackgroundColor(Color.parseColor("#FDAA6F"));
//                holder.ivCover.setBackgroundResource(R.drawable.icon_challenge_type_dance);
//                holder.tvTalkText.setText(typeEntity.getText());
//                holder.tvUserName.setText(typeEntity.getName());
//                break;
//            case 4:
//                holder.rlType.setCardBackgroundColor(Color.parseColor("#63E52A"));
//                holder.ivCover.setBackgroundResource(R.drawable.icon_challenge_type_music);
//                holder.tvTalkText.setText(typeEntity.getText());
//                holder.tvUserName.setText(typeEntity.getName());
//                break;
//            case 5:
//                holder.rlType.setCardBackgroundColor(Color.parseColor("#A78FFF"));
//                holder.ivCover.setBackgroundResource(R.drawable.icon_challenge_type_perform);
//                holder.tvTalkText.setText(typeEntity.getText());
//                holder.tvUserName.setText(typeEntity.getName());
//                break;
//            case 6:
//                holder.rlType.setCardBackgroundColor(Color.parseColor("#7FE2FF"));
//                holder.ivCover.setBackgroundResource(R.drawable.icon_challenge_type_eloquence);
//                holder.tvTalkText.setText(typeEntity.getText());
//                holder.tvUserName.setText(typeEntity.getName());
//                break;
//        }



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
        ImageView ivCover;
        TextView tvUserName;
        TextView tvTalkText;
        CardView rlType;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCover = itemView.findViewById(R.id.iv_item_challenge_type_img);
            tvTalkText = itemView.findViewById(R.id.tv_item_challenge_type_text);
            tvUserName = itemView.findViewById(R.id.tv_item_challenge_type_name);
            rlType = itemView.findViewById(R.id.rl_challenge_type);
        }
    }
}
