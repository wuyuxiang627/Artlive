package com.connxun.elinetv.view.LiveBroadcast;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.TypedValue;
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
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.Live;
import com.connxun.elinetv.entity.LiveModel;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.MediaPreview.LIveRoomActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\3\21 0021.
 */

@SuppressLint("ValidFragment")
public class LiveRoomUserFragment extends BaseFragment {

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
    @BindView(R.id.rl_challenge_top)
    RelativeLayout rlChallengeTop;
    @BindView(R.id.iv_challenge_top_finish)
    ImageButton ivChallengeTopFinish;
    @BindView(R.id.rlv_challenge_top_staff)
    RecyclerView rlvChallengeTopStaff;
    @BindView(R.id.rb_challenge_contribution)
    RadioButton rbChallengeContribution;
    @BindView(R.id.iv_media_preview_user_photo)
    SimpleDraweeView ivMediaPreviewUserPhoto;
    @BindView(R.id.tv_media_preview_user_name)
    TextView tvMediaPreviewUserName;
    @BindView(R.id.tv_media_preview_watch_number)
    TextView tvMediaPreviewWatchNumber;
    @BindView(R.id.rl_media_preview_user)
    RelativeLayout rlMediaPreviewUser;
    @BindView(R.id.tv_media_preview_number)
    TextView tvMediaPreviewNumber;
    @BindView(R.id.tv_media_preview_number_number)
    TextView tvMediaPreviewNumberNumber;
    @BindView(R.id.rlv_cheetah_staff)
    RecyclerView rlvCheetahStaff;
    @BindView(R.id.ib_challenge_bottom_gengduo)
    ImageButton ibChallengeBottomGengduo;
    @BindView(R.id.ib_challenge_bottom_xiaoxi)
    ImageButton ibChallengeBottomXiaoxi;
    @BindView(R.id.ib_challenge_bottom_fenxiang)
    ImageButton ibChallengeBottomFenxiang;
    @BindView(R.id.ib_challenge_bottom_fanzhuan)
    ImageButton ibChallengeBottomFanzhuan;

    @BindView(R.id.tv_challenge_top_live_number)
    TextView tvChallengeTopLiveNumber;
    @BindView(R.id.ib_live_media_preview_follow)
    ImageButton ibLiveMediaPreviewFollow;
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
    @BindView(R.id.rl_challenge_top_layout)
    RelativeLayout rlChallengeTopLayout;
    @BindView(R.id.rl_live_user_info)
    RelativeLayout rlLiveUserInfo;


    Unbinder unbinder;
    PushFlowFragment pushFlowFragment;

    LiveModel PullLiveModel;
    Live PushLiveModel;
    private LIveRoomActivity liveActivity;


    @SuppressLint("ValidFragment")
    public LiveRoomUserFragment(PushFlowFragment pushFlowFragment) {
        this.pushFlowFragment = pushFlowFragment;

    }
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        liveActivity = (LIveRoomActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        liveActivity = null;
    }




    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live_room_user, null);
        unbinder = ButterKnife.bind(this, view);
        PullLiveModel = (LiveModel) getActivity().getIntent().getSerializableExtra("mLiveModel"); //观看直播中的数据实体类
        PushLiveModel = (Live) getActivity().getIntent().getSerializableExtra("liveMOdel"); //自己直播中的数据实体类
        setPushUserDate();
        return view;
    }

    //主播方资料
    public void setPushUserDate() {
        //直播
        if (PushLiveModel != null) {
            tvChallengeTopUserName.setText(BaseApplication.getUserSp().getString("nickName", "0"));
            tvMediaPreviewUserName.setText(BaseApplication.getUserSp().getString("nickName", "0"));
            String avatar = BaseApplication.getUserSp().getString("avatar", null);
            if (avatar != null) {
                //创建将要下载的图片的URI
                Uri imageUri = Uri.parse(avatar);
                //开始下载
                ivMediaPreviewUserPhoto.setImageURI(imageUri);
                ivChallengeTopUserPhoto.setImageURI(imageUri);
            }
            tvMediaPreviewNumberNumber.setText(PushLiveModel.getLiveId());
            tvChallengeTopLiveNumber.setText("直播间号: "+PushLiveModel.getLiveId());
            ibLiveMediaPreviewFollow.setVisibility(View.INVISIBLE);
            //显示布局
            ToastUtils.showLong("LiveOrChallenge: "+ BaseApplication.blLiveTypeLiveOrChallenge);
            if (BaseApplication.blLiveTypeLiveOrChallenge) {
                rlLiveUserInfo.setVisibility(View.VISIBLE);
                rlChallengeTopLayout.setVisibility(View.GONE);
                setLiaotianshiView(220,220);
                liveActivity.setLiaotianshiFragmentWH(220,220);
            } else {
                rlLiveUserInfo.setVisibility(View.GONE);
                rlChallengeTopLayout.setVisibility(View.VISIBLE);
                setLiaotianshiView(160,160);
                liveActivity.setLiaotianshiFragmentWH(160,160);
            }

        }
        //挑战

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.ib_challenge_bottom_gengduo, R.id.ib_challenge_bottom_xiaoxi, R.id.ib_challenge_bottom_fenxiang, R.id.ib_challenge_bottom_fanzhuan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ib_challenge_bottom_gengduo:
                break;
            case R.id.ib_challenge_bottom_xiaoxi:
                pushFlowFragment.showInputPanel();
                break;
            case R.id.ib_challenge_bottom_fenxiang:
                break;
            case R.id.ib_challenge_bottom_fanzhuan:
                break;
        }
    }


    public void setLiaotianshiView(float width,float height){
        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) rlLiveRoomUser.getLayoutParams();
//        params.width = dip2px(getActivity(), width);
        params.height = dip2px(getActivity(), height);
        rlLiveRoomUser.setLayoutParams(params);
    }



    /**
     * dp转为px
     * @param context  上下文
     * @param dipValue dp值
     * @return
     */
    private int dip2px(Context context, float dipValue)
    {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP, dipValue, r.getDisplayMetrics());
    }


}
