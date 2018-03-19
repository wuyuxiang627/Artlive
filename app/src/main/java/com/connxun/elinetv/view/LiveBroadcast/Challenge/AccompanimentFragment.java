package com.connxun.elinetv.view.LiveBroadcast.Challenge;

/**
 * Created by Administrator on 2018\3\19 0019.
 */

import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Live.Challenge.CardsThreeMusicAdapter;
import com.connxun.elinetv.adapter.Live.Challenge.CardsThreeTextAdapter;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.Video.ChallengeTypeThreeEntity;
import com.connxun.elinetv.entity.live.ChallengeTypeThree;
import com.connxun.elinetv.presenter.Live.ChallengeTypePresenter;
import com.connxun.elinetv.service.PlayMusicService;
import com.connxun.elinetv.view.user.ITestView;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 伴奏的音乐
 */
public class AccompanimentFragment extends BaseFragment {
    ChallengeTypePresenter challengeTypePresenter = new ChallengeTypePresenter(getActivity());
    View view;
    @BindView(R.id.rl_finish)
    RelativeLayout rlFinish;
    @BindView(R.id.tv_title)
    TextView tvTitle;
    @BindView(R.id.tv_reight)
    TextView tvReight;
    @BindView(R.id.rl_title)
    RelativeLayout rlTitle;
    @BindView(R.id.rlv_accompanimen_list)
    RecyclerView rlvAccompanimenList;
    @BindView(R.id.refreshLayout)
    SmartRefreshLayout refreshLayout;

    Unbinder unbinder;

    CardsThreeMusicAdapter cardsThreeMusicAdapter;

    ChallengeTypeThreeEntity challengeTypeThreeEntity;
    ArrayList<ChallengeTypeThree> challengeTypeThreeList = new ArrayList<>();

    private int positions = -1; //点击 的音乐
    private PlayMusicService.MusicBinder musicBinder; //音乐播放的binnder
    private ServiceConnection conn;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_accompaniment_fragment, null);
        unbinder = ButterKnife.bind(this, view);
        //绑定播放音乐的service
        bindMusicService();

        //设置音乐
//        setSongsData();

        //监听
//        setListener();

        return view;
    }


    /**
     * 绑定service
     */
    private void bindMusicService() {
        Intent intent = new Intent(getActivity(), PlayMusicService.class);
        conn = new ServiceConnection() {
            //异常断开时 执行
            public void onServiceDisconnected(ComponentName name) {
            }
            //当与service绑定成功后 执行
            public void onServiceConnected(ComponentName name, IBinder service) {
                musicBinder = (PlayMusicService.MusicBinder) service;

            }
        };
        getActivity().bindService(intent, conn, Service.BIND_AUTO_CREATE);
    }

    public void setMusicLayout() {
        GridLayoutManager xLinearLayoutManager = new GridLayoutManager(getActivity(),1);
        rlvAccompanimenList.setLayoutManager(xLinearLayoutManager);
        cardsThreeMusicAdapter = new CardsThreeMusicAdapter(R.layout.item_soundtrack,challengeTypeThreeList);
        rlvAccompanimenList.setAdapter(cardsThreeMusicAdapter);
        cardsThreeMusicAdapter.openLoadAnimation();

        cardsThreeMusicAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                if(positions != -1 && positions == position){
                    boolean blTYpe= musicBinder.playOrPause();
                    challengeTypeThreeList.get(position).setBlType(blTYpe);
                }else {
                    musicBinder.playMusic(challengeTypeThreeList.get(position).getUrl());
                }
                positions = position;
//                cardsThreeMusicAdapter.setSelectedPosition(position);
                cardsThreeMusicAdapter.notifyDataSetChanged();
            }
        });
    }

    public void setType(ChallengeTypeThreeEntity threeEntity, String typeName) {
        challengeTypeThreeEntity = threeEntity;
        tvTitle.setText(typeName);
        setMusicLayout();
        challengeTypePresenter.onCreate();
        challengeTypePresenter.getChallengeResourceList(String.valueOf(threeEntity.getMenuNo()));
        challengeTypePresenter.attachView(ChallengeResourceListView);
    }


    //三级类别详情
    public ITestView ChallengeResourceListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            ArrayList<ChallengeTypeThree> challengeTypeThreeLists = (ArrayList<ChallengeTypeThree>) object;
            challengeTypeThreeList.clear();
            challengeTypeThreeList.addAll(challengeTypeThreeLists);
            cardsThreeMusicAdapter.notifyDataSetChanged();
        }

        @Override
        public void onError(Object object) {

        }
    };


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick(R.id.rl_finish)
    public void onViewClicked() {
        ChallengeFragment.setTwoVIsibility();

    }
}
