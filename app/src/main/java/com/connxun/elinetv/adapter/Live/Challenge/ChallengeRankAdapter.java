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
public class ChallengeRankAdapter extends BaseQuickAdapter<RankEntity.Data.Rank,MBaseViewHolder> {

    public ChallengeRankAdapter(int layoutResId, @Nullable List<RankEntity.Data.Rank> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MBaseViewHolder helper, RankEntity.Data.Rank item) {
        helper.setText(R.id.tv_item_challenge_rank_user_name,item.getNickName());
        if(item.getAvatar() != null){
            helper.setSimpleImg(R.id.iv_item_challenge_rank_user_photo,item.getAvatar());
        }
    }

}
