package com.connxun.elinetv.adapter.Live;

import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.VideoShortAdapter.SoundTrackAdapter;
import com.connxun.elinetv.entity.LiveModel;
import com.connxun.elinetv.entity.VideoEtivity;
import com.facebook.drawee.view.SimpleDraweeView;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;

import java.util.List;

/**
 * Created by connxun-16 on 2018/1/6.
 */

/**
 * 聊天室成员
 */
public class CheetahStaffAdapter extends RecyclerView.Adapter<CheetahStaffAdapter.ViewHolder>implements View.OnClickListener{
    Context context;
    List<ChatRoomMember> arrayList;
    private SoundTrackAdapter.OnItemClickListener mOnItemClickListener = null;

    public CheetahStaffAdapter(Context context, List<ChatRoomMember> arrayList){
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_personnel_list, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.itemView.setTag(position);
        Log.e("测试: ","头像地址"+arrayList.get(position).getAvatar());
        Uri uri = Uri.parse(arrayList.get(position).getAvatar());
        holder.ivCavar.setImageURI(uri);
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }
    public List<ChatRoomMember> getList() {
        arrayList.clear();
        return arrayList;
    }

    @Override
    public void onClick(View v) {
        if (mOnItemClickListener != null) {
            //注意这里使用getTag方法获取position
            mOnItemClickListener.onItemClick(v,(int)v.getTag());
        }
    }
    public void setOnItemClickListener(SoundTrackAdapter.OnItemClickListener listener) {
        this.mOnItemClickListener = listener;
    }

    public static interface OnItemClickListener {
        void onItemClick(View view, int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        SimpleDraweeView ivCavar;

        public ViewHolder(View itemView) {
            super(itemView);
            ivCavar = itemView.findViewById(R.id.iv_item_personnel_user_photo);

        }
    }


}
