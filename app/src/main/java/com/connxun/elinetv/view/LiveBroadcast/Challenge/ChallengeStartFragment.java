package com.connxun.elinetv.view.LiveBroadcast.Challenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\3\20 0020.
 */

public class ChallengeStartFragment extends BaseFragment {
    View view;
    Unbinder unbinder;
    @BindView(R.id.relativeLayout4)
    RelativeLayout relativeLayout4;
    @BindView(R.id.btn_challenge_meddle_start)
    Button btnChallengeMeddleStart;
    @BindView(R.id.iv_layout_challenge_meddle_ranking_img)
    ImageView ivLayoutChallengeMeddleRankingImg;
    @BindView(R.id.rlv_challenge_meddle_ranking_list_left)
    RecyclerView rlvChallengeMeddleRankingListLeft;
    @BindView(R.id.rlv_challenge_meddle_ranking_list_reight)
    RecyclerView rlvChallengeMeddleRankingListReight;
    @BindView(R.id.tv_layout_challenge_meddle_grading_result_title)
    TextView tvLayoutChallengeMeddleGradingResultTitle;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_challenge_start, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    @OnClick({R.id.relativeLayout4, R.id.btn_challenge_meddle_start, R.id.iv_layout_challenge_meddle_ranking_img, R.id.rlv_challenge_meddle_ranking_list_left, R.id.rlv_challenge_meddle_ranking_list_reight, R.id.tv_layout_challenge_meddle_grading_result_title})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.relativeLayout4:
                break;
            case R.id.btn_challenge_meddle_start:
                break;
            case R.id.iv_layout_challenge_meddle_ranking_img:
                break;
            case R.id.rlv_challenge_meddle_ranking_list_left:
                break;
            case R.id.rlv_challenge_meddle_ranking_list_reight:
                break;
            case R.id.tv_layout_challenge_meddle_grading_result_title:
                break;
        }
    }
}
