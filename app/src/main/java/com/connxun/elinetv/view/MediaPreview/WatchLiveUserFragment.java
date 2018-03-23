package com.connxun.elinetv.view.MediaPreview;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\3\23 0023.
 */

public class WatchLiveUserFragment extends BaseFragment {

    View view;
    @BindView(R.id.iv_challenge_top_user_photo)
    SimpleDraweeView ivChallengeTopUserPhoto;
    @BindView(R.id.tv_challenge_top_user_name)
    TextView tvChallengeTopUserName;
    @BindView(R.id.tv_challenge_top_watch_number)
    TextView tvChallengeTopWatchNumber;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.ib_challenge_top_watch_comment)
    ImageButton ibChallengeTopWatchComment;
    @BindView(R.id.rl_challenge_top_user)
    RelativeLayout rlChallengeTopUser;
    @BindView(R.id.tv_challenge_top_live_number)
    TextView tvChallengeTopLiveNumber;
    @BindView(R.id.rl_challenge_top)
    RelativeLayout rlChallengeTop;
    @BindView(R.id.iv_challenge_top_finish)
    ImageButton ivChallengeTopFinish;
    @BindView(R.id.rlv_challenge_top_staff)
    RecyclerView rlvChallengeTopStaff;
    @BindView(R.id.rb_challenge_contribution)
    RadioButton rbChallengeContribution;
    @BindView(R.id.tv_challenge_top_challenge_timer_start)
    TextView tvChallengeTopChallengeTimerStart;
    @BindView(R.id.iv_challenge_top_challenge_timer)
    ImageView ivChallengeTopChallengeTimer;
    @BindView(R.id.tv_challenge_top_challenge_timer)
    TextView tvChallengeTopChallengeTimer;
    @BindView(R.id.btn_challenge_top_start)
    Button btnChallengeTopStart;
    @BindView(R.id.ll_challenge_top_time)
    LinearLayout llChallengeTopTime;
    @BindView(R.id.rl_challenge_top_layout)
    RelativeLayout rlChallengeTopLayout;
    @BindView(R.id.iv_media_preview_user_photo)
    SimpleDraweeView ivMediaPreviewUserPhoto;
    @BindView(R.id.tv_media_preview_user_name)
    TextView tvMediaPreviewUserName;
    @BindView(R.id.tv_media_preview_watch_number)
    TextView tvMediaPreviewWatchNumber;
    @BindView(R.id.ib_live_media_preview_follow)
    ImageButton ibLiveMediaPreviewFollow;
    @BindView(R.id.rl_media_preview_user)
    RelativeLayout rlMediaPreviewUser;
    @BindView(R.id.tv_media_preview_number)
    TextView tvMediaPreviewNumber;
    @BindView(R.id.tv_media_preview_number_number)
    TextView tvMediaPreviewNumberNumber;
    @BindView(R.id.rlv_cheetah_staff)
    RecyclerView rlvCheetahStaff;
    @BindView(R.id.rl_live_user_info)
    RelativeLayout rlLiveUserInfo;
    @BindView(R.id.llgiftcontent)
    LinearLayout llgiftcontent;
    @BindView(R.id.btn_challenge_meddle_start)
    Button btnChallengeMeddleStart;
    @BindView(R.id.iv_layout_challenge_meddle_ranking_img)
    ImageView ivLayoutChallengeMeddleRankingImg;
    @BindView(R.id.rlv_challenge_meddle_ranking_list_left)
    RecyclerView rlvChallengeMeddleRankingListLeft;
    @BindView(R.id.rlv_challenge_meddle_ranking_list_reight)
    RecyclerView rlvChallengeMeddleRankingListReight;
    @BindView(R.id.textView6)
    TextView textView6;
    @BindView(R.id.rcy_challenge_grade_list)
    RecyclerView rcyChallengeGradeList;
    @BindView(R.id.rl_challenge_grade_time)
    RelativeLayout rlChallengeGradeTime;
    @BindView(R.id.tv_layout_challenge_meddle_grading_result_title)
    TextView tvLayoutChallengeMeddleGradingResultTitle;
    @BindView(R.id.textView5)
    TextView textView5;
    @BindView(R.id.rb_challenge_resue_results_title)
    RadioButton rbChallengeResueResultsTitle;
    @BindView(R.id.iv_challenge_resue_results_number_one)
    ImageView ivChallengeResueResultsNumberOne;
    @BindView(R.id.iv_challenge_resue_results_number_two)
    ImageView ivChallengeResueResultsNumberTwo;
    @BindView(R.id.iv_challenge_resue_results_number_three)
    ImageView ivChallengeResueResultsNumberThree;
    @BindView(R.id.ll_challenge_resue_results_number)
    LinearLayout llChallengeResueResultsNumber;
    @BindView(R.id.tv_challenge_jiuyuan_img)
    TextView tvChallengeJiuyuanImg;
    @BindView(R.id.tv_challenge_jiuyuan_text)
    TextView tvChallengeJiuyuanText;
    @BindView(R.id.rl_live_room_user)
    RelativeLayout rlLiveRoomUser;
    @BindView(R.id.ib_challenge_bottom_gengduo)
    ImageButton ibChallengeBottomGengduo;
    @BindView(R.id.ib_challenge_bottom_xiaoxi)
    ImageButton ibChallengeBottomXiaoxi;
    @BindView(R.id.ib_challenge_bottom_fenxiang)
    ImageButton ibChallengeBottomFenxiang;
    @BindView(R.id.ib_challenge_bottom_fanzhuan)
    ImageButton ibChallengeBottomFanzhuan;
    Unbinder unbinder;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live_room_user, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
