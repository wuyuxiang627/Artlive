package com.connxun.elinetv.adapter.Live.Challenge;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.MBaseViewHolder;
import com.connxun.elinetv.entity.Video.ChallengeTypeThreeEntity;
import com.connxun.elinetv.entity.live.ChallengeTypeThree;

import java.util.List;

/**
 * Created by Administrator on 2018\3\19 0019.
 */

public class CardsThreeTextAdapter extends BaseQuickAdapter<ChallengeTypeThree,MBaseViewHolder> {
    public CardsThreeTextAdapter(int layoutResId, @Nullable List<ChallengeTypeThree> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MBaseViewHolder helper, ChallengeTypeThree item) {
        helper.setText(R.id.item_challenge_type_three_text_card_title,item.getName());
        helper.setText(R.id.tv_challenge_type_three_text_text,item.getContent());

    }
}
