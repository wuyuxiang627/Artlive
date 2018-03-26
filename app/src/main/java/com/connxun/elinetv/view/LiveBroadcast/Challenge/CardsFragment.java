package com.connxun.elinetv.view.LiveBroadcast.Challenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Live.Challenge.CardsAdapter;
import com.connxun.elinetv.adapter.Live.Challenge.CardsBAdapter;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.ChallengeTypeEntity;
import com.connxun.elinetv.entity.Video.ChallengeTypeThreeEntity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Administrator on 2018\3\12 0012.
 */

public class CardsFragment extends BaseFragment {

    View view;
    @BindView(R.id.tv_challenge_type_title)
    TextView tvChallengeTypeTitle;
    @BindView(R.id.layout_challenge_type_recy)
    RecyclerView layoutChallengeTypeRecy;
    Unbinder unbinder;
    List<ChallengeTypeThreeEntity> challengeTypeThreeLists = new ArrayList<>();
    private CardsBAdapter cardsAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_challenge_cards, null);
        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    String[] back = {"#F18C1A",
            "#FF71A4",
            "#DA3748",
            "#6EABF1"};
    private String colorBack;



    public void setChallengeType(List<ChallengeTypeThreeEntity> challengeTypeThreeList, final String typeName) {
        tvChallengeTypeTitle.setText(typeName);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        layoutChallengeTypeRecy.setLayoutManager(linearLayoutManager);
        challengeTypeThreeLists  = challengeTypeThreeList;
        for(ChallengeTypeThreeEntity entity : challengeTypeThreeList){
            int index = (int) (Math.random() * back.length);
            colorBack = back[index];
            entity.setColor(colorBack);
        }
        cardsAdapter = new CardsBAdapter(R.layout.item_challenge_type_cards, challengeTypeThreeList);
        layoutChallengeTypeRecy.setAdapter(cardsAdapter);

        cardsAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//                ChallengeFragment.setLayoutViTwo();
                ChallengeFragment.setFragmentThree(challengeTypeThreeLists.get(position),typeName);
            }
        });

    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
