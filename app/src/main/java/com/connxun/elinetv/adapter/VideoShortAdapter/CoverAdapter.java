package com.connxun.elinetv.adapter.VideoShortAdapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.connxun.elinetv.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/1/6.
 * 配乐
 */

public class CoverAdapter extends RecyclerView.Adapter<CoverAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    ArrayList<Bitmap> arrayList;
    private OnItemClickListener mOnItemClickListener = null;


    public CoverAdapter(Context context,List<Bitmap> arrayList) {
        this.context = context;
        this.arrayList = (ArrayList<Bitmap>) arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_video_edit_cover, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        holder.ivCd.setImageBitmap(arrayList.get(position));
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
        ImageView ivCd;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCd = itemView.findViewById(R.id.iv_item_video_edit_cover);
        }
    }
}
