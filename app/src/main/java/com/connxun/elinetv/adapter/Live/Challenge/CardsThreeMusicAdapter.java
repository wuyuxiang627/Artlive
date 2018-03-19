package com.connxun.elinetv.adapter.Live.Challenge;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.MBaseViewHolder;
import com.connxun.elinetv.entity.live.ChallengeTypeThree;

import java.util.List;

/**
 * Created by Administrator on 2018\3\19 0019.
 */

public class CardsThreeMusicAdapter extends BaseQuickAdapter<ChallengeTypeThree,MBaseViewHolder> {
    public CardsThreeMusicAdapter(int layoutResId, @Nullable List<ChallengeTypeThree> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MBaseViewHolder helper, ChallengeTypeThree item) {
        if(item.isBlType()){
            helper.setBackgroundRadioButtonResource(R.id.rb_item_soundtrack_start_stop,R.drawable.icon_soundtrack_start);
        }else {
            helper.setBackgroundRadioButtonResource(R.id.rb_item_soundtrack_start_stop,R.drawable.icon_soundtrack_stop);
        }
        helper.setPicassoImg(R.id.iv_item_soundtrack_cover,item.getCover());
        helper.setText(R.id.tv_item_soundtrack_text,item.getName());
        helper.setText(R.id.tv_item_soundtrack_author_text,item.getAuthor());


    }
}
