package com.connxun.elinetv.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Search.EveryRecommendedAdapter;
import com.connxun.elinetv.adapter.Search.TagAdapter;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.util.ui.FlowTagLayout;
import com.connxun.elinetv.util.ui.OnTagClickListener;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/1/11.
 */

/**
 * 搜索
 */
@ContentView(R.layout.activity_search)
public class SearchActivity extends BaseActivity{

    @ViewInject(R.id.search_tag_flow_layout)
    FlowTagLayout mColorFlowTagLayout;
    @ViewInject(R.id.layout_rlv_search_recommend_view)
    RecyclerView rlvSearchRecommendList;

    private TagAdapter<String> mColorTagAdapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //颜色
        mColorTagAdapter = new TagAdapter<>(this);
        mColorFlowTagLayout.setAdapter(mColorTagAdapter);
        mColorFlowTagLayout.setOnTagClickListener(new OnTagClickListener() {
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
        rlvSearchRecommendList.setLayoutManager(new LinearLayoutManager(this));
        rlvSearchRecommendList.setAdapter(recommendedAdapter);
    }
}
