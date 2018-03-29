package com.connxun.elinetv.adapter.Live.Challenge;

import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.MBaseViewHolder;
import com.connxun.elinetv.entity.ChallengeTypeEntity;

import java.util.List;

/**
 * Created by Administrator on 2018\3\17 0017.
 */

public class ChallengeBAdapter extends BaseQuickAdapter<ChallengeTypeEntity,MBaseViewHolder> {

    int [] back = {R.drawable.gradient_color_chengse,
            R.drawable.gradient_color_fense,
            R.drawable.gradient_color_hongse,
            R.drawable.gradient_color_zise};

    public ChallengeBAdapter(int layoutResId, @Nullable List<ChallengeTypeEntity> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(MBaseViewHolder helper, ChallengeTypeEntity item) {
//        helper.setCardBackgroundColor(R.id.rl_challenge_type,"#F8ABFF");
        helper.setText(R.id.tv_item_challenge_type_name,item.getName());
        helper.setText(R.id.tv_item_challenge_type_text,item.getContent());
        switch (item.getSort())
        {
            case 8:
//                helper.setCardBackgroundColor(R.id.rl_challenge_type,"#F8ABFF");
                helper.setBackgroundResource(R.id.iv_item_challenge_type_img,R.drawable.icon_challenge_type_singe);
                break;
            case 6:
//                helper.setCardBackgroundColor(R.id.rl_challenge_type,"#6DCEFF");
                helper.setBackgroundResource(R.id.iv_item_challenge_type_img,R.drawable.icon_challenge_type_rap);

                break;
            case 5:
//                helper.setCardBackgroundColor(R.id.rl_challenge_type,"#FDAA6F");
                helper.setBackgroundResource(R.id.iv_item_challenge_type_img,R.drawable.icon_challenge_type_dance);

                break;
            case 4:
//                helper.setCardBackgroundColor(R.id.rl_challenge_type,"#63E52A");
                helper.setBackgroundResource(R.id.iv_item_challenge_type_img,R.drawable.icon_challenge_type_music);

                break;
            case 3:
//                helper.setCardBackgroundColor(R.id.rl_challenge_type,"#A78FFF");
                helper.setBackgroundResource(R.id.iv_item_challenge_type_img,R.drawable.icon_challenge_type_perform);

                break;
            case 2:
//                helper.setCardBackgroundColor(R.id.rl_challenge_type,"#7FE2FF");
                helper.setBackgroundResource(R.id.iv_item_challenge_type_img,R.drawable.icon_challenge_type_eloquence);

                break;
        }
    }
}
