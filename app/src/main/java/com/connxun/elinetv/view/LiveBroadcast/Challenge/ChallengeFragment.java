package com.connxun.elinetv.view.LiveBroadcast.Challenge;

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
import com.connxun.elinetv.view.user.ITestView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018\3\10 0010.
 */

/**
 *挑战
 */
public class ChallengeFragment extends BaseFragment {
    ChallengeTypePresenter challengeTypePresenter = new ChallengeTypePresenter(getActivity());
    View view;
    ArrayList<ChallengeTypeEntity> challengeTypeList = new ArrayList<>(); //二级菜单
    ArrayList<ChallengeTypeThreeEntity> challengeTypeThreeList = new ArrayList<>(); //三级菜单

    RecyclerView glChallengeType;

    CardsFragment cardsFragment;

    ChallengeBAdapter challengeAdapter;
    FrameLayout frameCards;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view  = inflater.inflate(R.layout.layout_challenge_type,null);

//        initData();

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
//                challengeTypeThreeList = new ArrayList<>();
                String typeName = challengeTypeList.get(position).getName();
//                for(ChallengeTypeThreeEntity entity: challengeTypeThreeList){
//                    int index = (int) (Math.random() * back.length);
//                    colorBack = back[index];
//                    entity.setSort(colorBack);
//                }
//                ToastUtils.showLong(challengeTypeThreeList.size());
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
        transaction.replace(R.id.layout_challenge_type_two_layout, cardsFragment);
        transaction.commit();
    }



    private void intentView() {
        glChallengeType = view.findViewById(R.id.layout_challenge_type_recy);
//        vpSpecific = view.findViewById(R.id.layout_challenge_type_specific);
        frameCards = view.findViewById(R.id.layout_challenge_type_two_layout);
    }


//    public void initData(){
//        challengeTypeList = new ArrayList<>();
//        ChallengeTypeEntity sing = new ChallengeTypeEntity();
//        sing.setId(1);
//        sing.setName("唱歌");
//        sing.setText("展示天籁之音，做认证主播");
//        challengeTypeList.add(sing);
//
//        ChallengeTypeEntity rap = new ChallengeTypeEntity();
//        rap.setId(2);
//        rap.setName("Rap");
//        rap.setText("用嘻哈诉说你的故事");
//        challengeTypeList.add(rap);
//
//        ChallengeTypeEntity dance = new ChallengeTypeEntity();
//        dance.setId(3);
//        dance.setName("跳舞");
//        dance.setText("跳一跳，十年少");
//        challengeTypeList.add(dance);
//
//        ChallengeTypeEntity musics = new ChallengeTypeEntity();
//        musics.setId(4);
//        musics.setName("乐器");
//        musics.setText("多才多艺的你还在等什么");
//        challengeTypeList.add(musics);
//
//        ChallengeTypeEntity perform = new ChallengeTypeEntity();
//        perform.setId(5);
//        perform.setName("表演");
//        perform.setText("演绎你的人生");
//        challengeTypeList.add(perform);
//
//        ChallengeTypeEntity eloquence = new ChallengeTypeEntity();
//        eloquence.setId(6);
//        eloquence.setName("口才");
//        eloquence.setText("说话也是一门艺术");
//        challengeTypeList.add(eloquence);
//
//
//
//
//
//    }
//
////
//    @Override
//    public void onHiddenChanged(boolean hidden) {
//        super.onHiddenChanged(hidden);
//        if (hidden) {
////            System.out.println("不可见");
//
//        } else {
////            System.out.println("当前可见");
////            ToastUtils.showLong("可见");
////            challengeTypePresenter.onCreate();
////            challengeTypePresenter.getChallengeList("20","1");
////            challengeTypePresenter.attachView(ChallengeListView);
//
//        }
//
//    }


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






}
