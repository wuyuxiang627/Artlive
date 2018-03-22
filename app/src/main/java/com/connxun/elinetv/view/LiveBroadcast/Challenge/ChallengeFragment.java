package com.connxun.elinetv.view.LiveBroadcast.Challenge;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Live.Challenge.ChallengeAdapter;
import com.connxun.elinetv.adapter.Live.Challenge.ChallengeBAdapter;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.base.ui.GridRecyclerView;
import com.connxun.elinetv.entity.ChallengeTypeEntity;
import com.connxun.elinetv.entity.Video.ChallengeTypeThreeEntity;
import com.connxun.elinetv.presenter.Live.ChallengeTypePresenter;
import com.connxun.elinetv.util.AnimationUtil;
import com.connxun.elinetv.util.RecyclerViewAnimation;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.LiveBroadcast.Challenge.CardsFragment;
import com.connxun.elinetv.view.LiveBroadcast.PushFlowFragment;
import com.connxun.elinetv.view.user.ITestView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\10 0010.
 */

/**
 *挑战
 */
@SuppressLint("ValidFragment")
public class ChallengeFragment extends BaseFragment {
    private final PushFlowFragment pushFlowFragment;
    ChallengeTypePresenter challengeTypePresenter = new ChallengeTypePresenter(getActivity());
    View view;
    ArrayList<ChallengeTypeEntity> challengeTypeList = new ArrayList<>(); //二级菜单
    ArrayList<ChallengeTypeThreeEntity> challengeTypeThreeList = new ArrayList<>(); //三级菜单

    RecyclerView glChallengeType;

    CardsFragment cardsFragment;
    static CardsThreeFragment cardsThreeFragment;
    static AccompanimentFragment accompanimentFragment;

    ChallengeBAdapter challengeAdapter;
    static FrameLayout frameCards;
    static FrameLayout frameCardsThree;
    static FrameLayout frameCardsThreeMusic;


    @SuppressLint("ValidFragment")
    public ChallengeFragment(BaseFragment baseFragment){
        pushFlowFragment = (PushFlowFragment) baseFragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.layout_challenge_type,null);

        intentView();

        loadFragment();

        setType();

        challengeTypePresenter.onCreate();
        challengeTypePresenter.getChallengeList("20","1");
        challengeTypePresenter.attachView(ChallengeListView);

        return view;
    }

    private void setType() {

        GridLayoutManager xLinearLayoutManager = new GridLayoutManager(getActivity(),2);
        glChallengeType.setLayoutManager(xLinearLayoutManager);
        challengeAdapter = new ChallengeBAdapter(R.layout.item_challenge,challengeTypeList);
        glChallengeType.setAdapter(challengeAdapter);
        challengeAdapter.openLoadAnimation();
        challengeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                frameCards.setVisibility(View.VISIBLE);
                glChallengeType.setVisibility(View.GONE);
                frameCards.startAnimation(AnimationUtil.startAnimation(1.0f,0.0f,0.0f,0.0f));
                glChallengeType.startAnimation(AnimationUtil.startAnimation(0.0f,-1.0f,0.0f,0.0f));
                challengeTypeThreeList = (ArrayList<ChallengeTypeThreeEntity>) challengeTypeList.get(position).getTwo();
                String typeName = challengeTypeList.get(position).getName();
                cardsFragment.setChallengeType(challengeTypeThreeList,typeName);
            }

        });
    }

    int[] back = {R.drawable.gradient_color_chengse,
            R.drawable.gradient_color_fense,
            R.drawable.gradient_color_hongse,
            R.drawable.gradient_color_zise};
    private int colorBack;
    /**
     * 加载布局
     */
    private void loadFragment() {
        FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        cardsFragment = new CardsFragment();
        cardsThreeFragment = new CardsThreeFragment();
        accompanimentFragment = new AccompanimentFragment(this);
        transaction.replace(R.id.layout_challenge_type_two_layout, cardsFragment);
        transaction.replace(R.id.layout_challenge_type_three_layout, cardsThreeFragment);
        transaction.replace(R.id.layout_challenge_type_three_music_layout, accompanimentFragment);
        transaction.commit();
    }

    private void intentView() {
        glChallengeType = view.findViewById(R.id.layout_challenge_type_recy);
        frameCards = view.findViewById(R.id.layout_challenge_type_two_layout);
        frameCardsThree = view.findViewById(R.id.layout_challenge_type_three_layout);
        frameCardsThreeMusic = view.findViewById(R.id.layout_challenge_type_three_music_layout);
    }


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(hidden){
            ToastUtils.showLong("onHiddenChanged 不可见");
        }else {
            ToastUtils.showLong("onHiddenChanged 可见");
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if(getUserVisibleHint()) {
            challengeTypePresenter.onCreate();
            challengeTypePresenter.getChallengeList("20","1");
            challengeTypePresenter.attachView(ChallengeListView);
//            isVisible = true;
        } else {
//            isVisible = false;
        }
    }


    @Override
    public boolean getUserVisibleHint() {
        return super.getUserVisibleHint();
    }

    //挑战一二级菜单列表
    public ITestView ChallengeListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            ArrayList<ChallengeTypeEntity> challengeTypeLists = (ArrayList<ChallengeTypeEntity>) object;
            challengeTypeList.addAll(challengeTypeLists);
            challengeAdapter.notifyDataSetChanged();

        }

        @Override
        public void onError(Object object) {

        }
    };


    //二级菜单点击事件
    public static void setLayoutViTwo(){
        frameCardsThree.setVisibility(View.VISIBLE);
        frameCards.setVisibility(View.GONE);
    }

    //点击自由播
    public void setvisibility(){
        glChallengeType.setVisibility(View.VISIBLE);
        frameCardsThreeMusic.setVisibility(View.GONE);
        frameCardsThree.setVisibility(View.GONE);
        frameCards.setVisibility(View.GONE);
    }

    //三级页面点击返回键
    public static  void setTwoVIsibility(){
        frameCardsThreeMusic.setVisibility(View.GONE);
        frameCardsThree.setVisibility(View.GONE);
        frameCards.setVisibility(View.VISIBLE);
    }


    //设置三级页面数据
    public static void setFragmentThree(ChallengeTypeThreeEntity typeThreeEntity,String typeName){
        if(typeThreeEntity.getContent().equals("音频")){
            frameCardsThreeMusic.setVisibility(View.VISIBLE);
            frameCardsThree.setVisibility(View.GONE);
            frameCards.setVisibility(View.GONE);
            accompanimentFragment.setType(typeThreeEntity,typeName);

        }else{
            //文本
            frameCardsThreeMusic.setVisibility(View.GONE);
            frameCardsThree.setVisibility(View.VISIBLE);
            frameCards.setVisibility(View.GONE);
            cardsThreeFragment.setType(typeThreeEntity,typeName);
        }

    }


    /**
     * 进入挑战界面
     */
    public void setGoOutChallenge(){
        pushFlowFragment.contrellerLiveStartStop();
    }





}
