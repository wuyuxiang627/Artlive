package com.connxun.elinetv.adapter.User;

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
import com.connxun.elinetv.entity.order.WithdrawalsList;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/1/6.
 * 通用配置的adapter
 */

public class PresenteRecordAdapter extends RecyclerView.Adapter<PresenteRecordAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    ArrayList<WithdrawalsList> arrayList;
    private int selectedPosition = -1;// 选中的位置
    private OnItemClickListener mOnItemClickListener = null;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public PresenteRecordAdapter(Context context, List<WithdrawalsList> arrayList) {
        this.context = context;
        this.arrayList = (ArrayList<WithdrawalsList>) arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_presente_record, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        WithdrawalsList entity = arrayList.get(position);
        long first = arrayList.get(position).getBeforeBalance();
        long two = arrayList.get(position).getAfterBalance();
//        (first-two)/10;

        holder.tvMoney.setText("提现金额 : "+(first-two)+" 元");

        holder.tvText.setText("收款人: "+entity.getName() + " 账号: "+ entity.getAlipay());
        if(entity.getState()== 0){
            holder.tvType.setText("审核中");
        }else {
            holder.tvType.setText("提现成功");
        }
        String arrays[] = entity.getCreateDate().split(" ");
        holder.tvTime.setText(arrays[0]);






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
        TextView tvMoney;
        TextView tvText;
        TextView tvType;
        TextView tvTime;

        public ViewHolder(View itemView) {
            super(itemView);
            tvMoney = itemView.findViewById(R.id.tv_withdrawals_money);
            tvText = itemView.findViewById(R.id.tv_withdrawals_tex);
            tvType = itemView.findViewById(R.id.tv_withdrawals_type);
            tvTime = itemView.findViewById(R.id.tv_withdrawals_time);
        }
    }
}
