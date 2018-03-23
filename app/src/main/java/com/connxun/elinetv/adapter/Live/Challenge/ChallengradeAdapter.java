package com.connxun.elinetv.adapter.Live.Challenge;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.MBaseViewHolder;
import com.connxun.elinetv.entity.Video.ChallengeTypeThreeEntity;

import java.util.List;

/**
 * Created by Administrator on 2018\3\17 0017.
 */

public class ChallengradeAdapter extends BaseQuickAdapter<Integer,MBaseViewHolder> {

    public ChallengradeAdapter(int layoutResId, @Nullable List<Integer> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MBaseViewHolder helper, Integer item) {
        helper.setText(R.id.tv_item_challenge_grade,item+"");
    }
}

