package com.connxun.elinetv.adapter.Mind;

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
import com.connxun.elinetv.entity.VideoEtivity;
import com.connxun.elinetv.entity.order.VC;
import com.connxun.elinetv.observer.NotifyListenerManager;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.observer.TestActivity;
import com.connxun.elinetv.view.user.MyPurse.MyPurseActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/1/6.
 * 通用配置的adapter
 */

public class MyPurseBoldAdapter extends RecyclerView.Adapter<MyPurseBoldAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    ArrayList<VC> arrayList;
    private int selectedPosition = -1;// 选中的位置
    private OnItemClickListener mOnItemClickListener = null;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public MyPurseBoldAdapter(Context context, List<VC> arrayList) {
        this.context = context;
        this.arrayList = (ArrayList<VC>) arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_layout_my_purse_bold, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        holder.tvBold.setText(" "+arrayList.get(position).getVc()+" 金币");
        holder.tvBoldSend.setText(" 赠送 "+arrayList.get(position).getGiveVc()+" 金币");
        holder.btnBuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1,arrayList.get(position)), MyPurseActivity.class);//传参数通知
            }
        });

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
        TextView tvBold;
        TextView tvBoldSend;
        Button btnBuy;

        public ViewHolder(View itemView) {
            super(itemView);
            tvBold = itemView.findViewById(R.id.tv_item_my_purse_bold);
            tvBoldSend = itemView.findViewById(R.id.tv_item_my_purse_bold_send);
            btnBuy = itemView.findViewById(R.id.btn_item_my_purse_buy_one);
        }
    }
}
