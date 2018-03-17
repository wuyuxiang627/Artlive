package com.connxun.elinetv.adapter.VideoShortAdapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.entity.Song;
import com.connxun.elinetv.observer.NotifyListenerManager;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.service.PlayMusicService;
import com.connxun.elinetv.view.VideoShort.SoundtrackActivity;

import java.util.ArrayList;

/**
 * Created by connxun-16 on 2018/1/6.
 * 配乐
 */

public class SoundTrackAdapter extends RecyclerView.Adapter<SoundTrackAdapter.ViewHolder> implements View.OnClickListener{
    Context context;
    ArrayList<Song> arrayList;
    private int selectedPosition = -1;// 选中的位置
    private OnItemClickListener mOnItemClickListener = null;
    PlayMusicService.MusicBinder musicBinder;
    Animation operatingAnim;

    public void setSelectedPosition(int position) {
        this.selectedPosition = position;
    }

    public SoundTrackAdapter(Context context, PlayMusicService.MusicBinder musicBinder, ArrayList<Song> arrayList) {
        this.musicBinder = musicBinder;
        this.context = context;
        this.arrayList = arrayList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.item_soundtrack, parent, false);
        ViewHolder holder = new ViewHolder(v);
        v.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.itemView.setTag(position);
        if(position == selectedPosition){
            holder.ivCd.setVisibility(View.VISIBLE);
            holder.btnOk.setVisibility(View.VISIBLE);
            holder.ivCd.setBackgroundResource(R.drawable.icon_cd);
            if(arrayList.get(position).isBlType()){
                holder.rbStartStop.setBackgroundResource(R.drawable.icon_soundtrack_start);
                holder.ivCd.clearAnimation();
            }else {
                holder.rbStartStop.setBackgroundResource(R.drawable.icon_soundtrack_stop);
                operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotate_anim);
                operatingAnim.setRepeatCount(Animation.INFINITE); //一直转
                LinearInterpolator lin = new LinearInterpolator();
                operatingAnim.setInterpolator(lin);
                holder.ivCd.startAnimation(operatingAnim);
            }

            holder.btnOk.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NotifyListenerManager.getInstance().notifyListener(new NotifyObject(2,arrayList.get(position).getFileUrl()), SoundtrackActivity.class);//传参数通知
                }
            });
        }else {
            holder.rbStartStop.setBackgroundResource(R.drawable.icon_soundtrack_start);
            holder.ivCd.setVisibility(View.GONE);
            holder.btnOk.setVisibility(View.GONE);
        }

        holder.tvMusicName.setText(arrayList.get(position).getTitle());
        holder.tvMusicAuthor.setText(arrayList.get(position).getSinger());
        holder.rbStartStop.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                NotifyListenerManager.getInstance().notifyListener(new NotifyObject(1), SoundtrackActivity.class);//传参数通知
//                musicBinder.playOrPause();
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
        void onItemClick(View view , int position);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvMusicName;
        TextView tvMusicAuthor;
        RadioButton rbStartStop;
        Button btnOk;
        ImageView ivCd;

        public ViewHolder(View itemView) {
            super(itemView);
            rbStartStop = itemView.findViewById(R.id.rb_item_soundtrack_start_stop);
            ivCd = itemView.findViewById(R.id.iv_cd);
            btnOk = itemView.findViewById(R.id.btn_item_soundtrack_author_ok);
            tvMusicName = itemView.findViewById(R.id.tv_item_soundtrack_text);
            tvMusicAuthor = itemView.findViewById(R.id.tv_item_soundtrack_author_text);
        }
    }
}
