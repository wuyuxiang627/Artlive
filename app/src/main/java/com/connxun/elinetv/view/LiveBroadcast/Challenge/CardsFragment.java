package com.connxun.elinetv.view.LiveBroadcast.Challenge;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Live.Challenge.CardsAdapter;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.ChallengeTypeEntity;

import java.util.ArrayList;

/**
 * Created by Administrator on 2018\3\12 0012.
 */

public class CardsFragment extends BaseFragment {

    View view;

    RecyclerView rlyCrds;

    TextView tvTitle;



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_challenge_cards,null);

        initView();

        initRecyclerView();




        return view;

    }

    private void initRecyclerView() {
        ArrayList<ChallengeTypeEntity> challengeTypeEntities = new ArrayList<>();
        ChallengeTypeEntity musics = new ChallengeTypeEntity();
        musics.setText("#FF71A4");
        musics.setName("翻唱");
        challengeTypeEntities.add(musics);

        ChallengeTypeEntity musics2 = new ChallengeTypeEntity();
        musics2.setText("#817BE8");
        musics2.setName("原唱");
        challengeTypeEntities.add(musics2);

        ChallengeTypeEntity musics3 = new ChallengeTypeEntity();
        musics3.setText("#F18C1A");
        musics3.setName("清唱");
        challengeTypeEntities.add(musics3);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rlyCrds.setLayoutManager(linearLayoutManager);
        CardsAdapter cardsAdapter = new CardsAdapter(getActivity(),challengeTypeEntities);
        rlyCrds.setAdapter(cardsAdapter);

    }

    private void initView() {
        rlyCrds = view.findViewById(R.id.layout_challenge_type_recy);
        tvTitle = view.findViewById(R.id.tv_challenge_type_title);
    }
}
