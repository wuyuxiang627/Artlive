package com.connxun.elinetv.adapter.Beauty;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.entity.BeautyType;

import java.util.ArrayList;

public class BeautyTypeAdaper extends RecyclerView.Adapter<BeautyTypeAdaper.ViewHolder> implements View.OnClickListener{
    private ArrayList<BeautyType> datas;
    private Context context;
    private int selectedPosition = -1;// 选中的位置
    public BeautyTypeAdaper(Context context,ArrayList<BeautyType> datas) {
        this.datas = datas;
        this.context = context;
    }

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    private OnItemClickListener mOnItemClickListener = null;

    //define interface
    public static interface OnItemClickListener {
        void onItemClick(View view , int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup,  int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.item_beauty_type, viewGroup, false);
        ViewHolder vh = new ViewHolder(view);
        //将创建的View注册点击事件
        view.setOnClickListener(this);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder,  int position) {
        viewHolder.mTextView.setText(datas.get(position).getText());
        //将position保存在itemView的Tag中，以便点击时进行获取
        viewHolder.itemView.setTag(position);
        viewHolder.ivImg.setBackgroundResource(datas.get(position).getImg());
        if(selectedPosition == position){
            viewHolder.iv_item_beauty_img_back.setVisibility(View.VISIBLE);
            datas.get(position).setChecked(false);
        }else{
            viewHolder.iv_item_beauty_img_back.setVisibility(View.INVISIBLE);
        }
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


    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView mTextView;
        public ImageView ivImg;
        public ImageView iv_item_beauty_img_back;
        public ViewHolder(View view){
            super(view);
            mTextView = (TextView) view.findViewById(R.id.tv_item_beauty_text);
            ivImg = view.findViewById(R.id.iv_item_beauty_img);
            iv_item_beauty_img_back = view.findViewById(R.id.iv_item_beauty_img_back);
        }
    }
}