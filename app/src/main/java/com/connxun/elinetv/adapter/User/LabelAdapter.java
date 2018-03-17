package com.connxun.elinetv.adapter.User;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.entity.Video.Talk;
import com.connxun.elinetv.entity.user.LabelEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/1/6.
 * 通用配置的adapter
 */

public class LabelAdapter extends RecyclerView.Adapter<LabelAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    ArrayList<LabelEntity> arrayList;
    private int selectedPosition = -1;// 选中的位置
    private OnItemClickListener mOnItemClickListener = null;
    private int type;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public LabelAdapter(Context context,int type, List<LabelEntity> arrayList) {
        this.context = context;
        this.type = type;
        this.arrayList = (ArrayList<LabelEntity>) arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_label, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        if(type == 1){
            holder.ivLabelFinish.setVisibility(View.GONE);
        }
        holder.btnLabelName.setText(arrayList.get(position).getName());
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
        TextView btnLabelName;
        ImageView ivLabelFinish;

        public ViewHolder(View itemView) {
            super(itemView);
            ivLabelFinish = itemView.findViewById(R.id.iv_label_finish);
            btnLabelName = itemView.findViewById(R.id.btn_label_text);
        }
    }
}
