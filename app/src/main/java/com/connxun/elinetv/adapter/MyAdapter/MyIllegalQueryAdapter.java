package com.connxun.elinetv.adapter.MyAdapter;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.connxun.elinetv.R;
import com.connxun.elinetv.entity.user.UserIllegalQueryEntity;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/3/8.
 */

public class MyIllegalQueryAdapter extends RecyclerView.Adapter<MyIllegalQueryAdapter.ViewHolder> implements View.OnClickListener {
    Context context;
    List<UserIllegalQueryEntity> followlist = new ArrayList<>();
    private int selectedPosition = -1;// 选中的位置
    private MyIllegalQueryAdapter.OnItemClickListener mOnItemClickListener = null;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public MyIllegalQueryAdapter(Context context, List<UserIllegalQueryEntity> followlist) {
        this.context = context;
        this.followlist = followlist;
    }

    @Override
    public MyIllegalQueryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_my_follow, parent, false);
        ViewHolder holder = new MyIllegalQueryAdapter.ViewHolder(v);
        v.setOnClickListener(this);
        return holder;

    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        String coverPath = followlist.get(position).getCause();
        //String userPhotoPath = followlist.get(position).getAvatart();
        if (coverPath != null) {
            //创建将要下载的图片的URI
            Uri imageUri = Uri.parse(coverPath);
            //开始下载
            holder.ivUserPhoto.setImageURI(imageUri);
        }

        holder.rl_illegalquery_cause.setText(followlist.get(position).getCause());
        holder.rl_illegalquery_createdate.setText(followlist.get(position).getCreateDate());


    }

    @Override
    public int getItemCount() {
        return followlist.size();
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v, (int) v.getTag());
        }
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        SimpleDraweeView ivCover;
        SimpleDraweeView ivUserPhoto;
        TextView rl_illegalquery_createdate,rl_illegalquery_cause;

        public ViewHolder(View itemView) {
            super(itemView);
          rl_illegalquery_cause = itemView.findViewById(R.id.rl_illegalquery_cause);
          rl_illegalquery_createdate = itemView.findViewById(R.id.rl_illegalquery_createdate);

        }
    }
}
