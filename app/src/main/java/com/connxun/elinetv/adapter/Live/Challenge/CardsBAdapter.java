package com.connxun.elinetv.adapter.Live.Challenge;

import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.MBaseViewHolder;
import com.connxun.elinetv.entity.ChallengeTypeEntity;
import com.connxun.elinetv.entity.Video.ChallengeTypeThreeEntity;

import java.util.List;

/**
 * Created by Administrator on 2018\3\17 0017.
 */

public class CardsBAdapter extends BaseQuickAdapter<ChallengeTypeThreeEntity,MBaseViewHolder> {


    int[] back = {R.color.cards_color_chengse,
            R.color.cards_color_fense,
            R.color.cards_color_honse,
            R.color.cards_color_zise};
    private int colorBack;

    public CardsBAdapter(int layoutResId, @Nullable List<ChallengeTypeThreeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MBaseViewHolder helper, ChallengeTypeThreeEntity item) {
        helper.setCardBackgroundColor(R.id.item_challenge_type_card,item.getColor());
        helper.setText(R.id.item_challenge_type_card_title,item.getName());
    }
}

