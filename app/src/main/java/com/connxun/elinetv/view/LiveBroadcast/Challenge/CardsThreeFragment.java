package com.connxun.elinetv.view.LiveBroadcast.Challenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Live.Challenge.CardsThreeTextAdapter;
import com.connxun.elinetv.adapter.Live.Challenge.ChallengeBAdapter;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.Video.ChallengeTypeThreeEntity;
import com.connxun.elinetv.entity.live.ChallengeTypeThree;
import com.connxun.elinetv.presenter.Live.ChallengeTypePresenter;
import com.connxun.elinetv.view.user.ITestView;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\3\19 0019.
 */

public class CardsThreeFragment extends BaseFragment {
    ChallengeTypePresenter challengeTypePresenter = new ChallengeTypePresenter(getActivity());
    View view;
    @BindView(R.id.tv_challenge_three_type_text_title)
    TextView tvChallengeThreeTypeTitle;
    @BindView(R.id.layout_challenge_three_type_text_recy)
    RecyclerView layoutChallengeThreeTypeRecy;
    Unbinder unbinder;

    CardsThreeTextAdapter cardsThreeTextAdapter;

    ChallengeTypeThreeEntity challengeTypeThreeEntity;
    ArrayList<ChallengeTypeThree> challengeTypeThreeList = new ArrayList<>();

    String type = "音频";

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_cards_three, null);

        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    public void setTextLayout(){
        GridLayoutManager xLinearLayoutManager = new GridLayoutManager(getActivity(),1);
        layoutChallengeThreeTypeRecy.setLayoutManager(xLinearLayoutManager);
        cardsThreeTextAdapter = new CardsThreeTextAdapter(R.layout.item_challenge_type_three_text_cards,challengeTypeThreeList);
        layoutChallengeThreeTypeRecy.setAdapter(cardsThreeTextAdapter);
        cardsThreeTextAdapter.openLoadAnimation();

    }

    public void setMusicLayout(){

    }







    public void setType(ChallengeTypeThreeEntity threeEntity,String typeName) {
        challengeTypeThreeEntity = threeEntity;
        tvChallengeThreeTypeTitle.setText(typeName);
        setTextLayout();
        challengeTypePresenter.onCreate();
        challengeTypePresenter.getChallengeResourceList(String.valueOf(threeEntity.getMenuNo()));
        challengeTypePresenter.attachView(ChallengeResourceListView);

        if(threeEntity.getContent() .equals(type)){

        }
    }


    //三级类别详情
    public ITestView ChallengeResourceListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            ArrayList<ChallengeTypeThree> challengeTypeThreeLists = (ArrayList<ChallengeTypeThree>) object;
            if(challengeTypeThreeEntity.getContent().equals(type)){

                challengeTypeThreeList.addAll(challengeTypeThreeLists);
                cardsThreeTextAdapter.notifyDataSetChanged();
            }
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
}
