package com.connxun.elinetv.view.user.MyVideo;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.ui.NoScrollViewPager;
import com.connxun.elinetv.view.VideoShort.VideoShortActivity;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;

/**
 * Created by connxun-16 on 2017/12/28.
 */

/**
 * 我的小视频
 */
@ContentView(R.layout.activity_my_video)
public class MyVideoActivity extends BaseActivity {

    View view;


    @ViewInject(R.id.rl_my_video_finish)
    RelativeLayout rlFinish;

    @ViewInject(R.id.tl_video_title)
    TabLayout tlVideoTitle; //标题栏

    @ViewInject(R.id.vp_video_viewpager)
    NoScrollViewPager vpVideoViewPgaer; //容器

    @ViewInject(R.id.rg_my_video_title)
    RadioGroup rgVideoTile;

    @ViewInject(R.id.rb_my_video_send)
    RadioButton rbSend;

    @ViewInject(R.id.rb_my_video_like)
    RadioButton rbLike;

    @ViewInject(R.id.rb_my_video_box)
    RadioButton rbBox;

    MainPagerAdapter pagerAdapter; //viewpager的adapter

    private ArrayList<Fragment> fragments ; //fragment数据


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设置viewpager数据
        setViewPager();
    }


    @Override
    public void setOnClick() {
        super.setOnClick();
        rlFinish.setOnClickListener(this);

        rgVideoTile.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @SuppressLint("ResourceAsColor")
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rb_my_video_send:
                        vpVideoViewPgaer.setCurrentItem(0);
                        break;
                    case R.id.rb_my_video_like:
                        vpVideoViewPgaer.setCurrentItem(1);
                        break;
                    case R.id.rb_my_video_box:
                        vpVideoViewPgaer.setCurrentItem(2);
                        break;
                }
            }
        });

    }




    /**
     * 设置viewpager数据
     */
    private void setViewPager() {
        fragments = new ArrayList<Fragment>();
        fragments.add(new MyVideoReleaseragment());
        fragments.add(new MyVideoLikeFragment());
        fragments.add(new MyDraftsFragment());

        //构建Adapter
        pagerAdapter = new MainPagerAdapter(getSupportFragmentManager(), fragments);
        vpVideoViewPgaer.setAdapter(pagerAdapter);
        vpVideoViewPgaer.setOffscreenPageLimit(1);

    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_my_video_finish:
                this.finish();
                break;
            case R.id.iv_video_shoot:
                isLogin_startActivity(new Intent(getContext(), VideoShortActivity.class));
                break;
        }
    }

    public class MainPagerAdapter extends FragmentPagerAdapter {
        private ArrayList<Fragment> fragments;

        public MainPagerAdapter(FragmentManager fm, ArrayList<Fragment> fragments) {
            super(fm);
            this.fragments = fragments;
        }

        @Override
        public Fragment getItem(int i) {
            return fragments.get(i);
        }

        @Override
        public int getCount() {
            return fragments.size();
        }
    }








}
