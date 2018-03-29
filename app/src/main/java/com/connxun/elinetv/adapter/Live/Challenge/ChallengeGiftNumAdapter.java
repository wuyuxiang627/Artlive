package com.connxun.elinetv.adapter.Live.Challenge;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.MBaseViewHolder;
import com.connxun.elinetv.entity.live.challenge.RankEntity;

import java.util.List;

/**
 * Created by Administrator on 2018\3\26 0026.
 */
//富豪榜
public class ChallengeGiftNumAdapter extends BaseQuickAdapter<String,MBaseViewHolder> {

    public ChallengeGiftNumAdapter(int layoutResId, @Nullable List<String> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MBaseViewHolder helper, String item) {
        helper.setText(R.id.tv_item_challenge_gift_num,item);
    }

}
