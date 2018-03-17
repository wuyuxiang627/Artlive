package com.connxun.elinetv.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.*;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Search.EveryRecommendedAdapter;
import com.connxun.elinetv.adapter.Search.TagAdapter;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.util.ui.FlowTagLayout;
import com.connxun.elinetv.util.ui.OnTagClickListener;

import org.xutils.view.annotation.ContentView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by connxun-16 on 2018/1/11.
 */

/**
 * 搜索
 */
public class SearchActivity extends BaseActivity {
    @BindView(R.id.iv_sousuo)
    ImageView ivSousuo;
    @BindView(R.id.rl_setting_title)
    RelativeLayout rlSettingTitle;
    @BindView(R.id.search_tag_flow_layout)
    FlowTagLayout searchTagFlowLayout;
    @BindView(R.id.iv_search_everyy)
    ImageView ivSearchEveryy;
    @BindView(R.id.layout_rl_live_hot_recommened_title)
    RelativeLayout layoutRlLiveHotRecommenedTitle;
    @BindView(R.id.layout_rlv_search_recommend_view)
    RecyclerView layoutRlvSearchRecommendView;

//    @ViewInject(R.id.search_tag_flow_layout)
//    FlowTagLayout mColorFlowTagLayout;
//    @ViewInject(R.id.layout_rlv_search_recommend_view)
//    RecyclerView rlvSearchRecommendList;

    private TagAdapter<String> mColorTagAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);


        //颜色
        mColorTagAdapter = new TagAdapter<>(this);
        searchTagFlowLayout.setAdapter(mColorTagAdapter);
        searchTagFlowLayout.setOnTagClickListener(new OnTagClickListener() {
            @Override
            public void onItemClick(FlowTagLayout parent, android.view.View view, int position) {
                Snackbar.make(view, "颜色:" + parent.getAdapter().getItem(position), Snackbar.LENGTH_SHORT)
                        .setAction("Action", null).show();
            }
        });
        initColorData();

    }

    private void initColorData() {
        List<String> dataSource = new ArrayList<>();
        dataSource.add("红色");
        dataSource.add("黑色");
        dataSource.add("花边色");
        dataSource.add("深蓝色");
        dataSource.add("白色");
        dataSource.add("萌宠小大人直播");
        dataSource.add("紫黑紫兰色");
        dataSource.add("葡萄红色");
        dataSource.add("屎黄色");
        dataSource.add("绿色");
        dataSource.add("彩虹色");
        dataSource.add("牡丹色");
        mColorTagAdapter.onlyAddAll(dataSource);

        EveryRecommendedAdapter recommendedAdapter = new EveryRecommendedAdapter(this, (ArrayList<String>) dataSource);
        layoutRlvSearchRecommendView.setLayoutManager(new LinearLayoutManager(this));
        layoutRlvSearchRecommendView.setAdapter(recommendedAdapter);
    }

    @OnClick({R.id.iv_sousuo, R.id.rl_setting_title, R.id.search_tag_flow_layout, R.id.iv_search_everyy, R.id.layout_rl_live_hot_recommened_title, R.id.layout_rlv_search_recommend_view})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_sousuo:
                break;
            case R.id.rl_setting_title:
                break;
            case R.id.search_tag_flow_layout:
                break;
            case R.id.iv_search_everyy:
                break;
            case R.id.layout_rl_live_hot_recommened_title:
                break;
            case R.id.layout_rlv_search_recommend_view:
                break;
        }
    }
}
