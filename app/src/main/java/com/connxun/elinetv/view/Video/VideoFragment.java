package com.connxun.elinetv.view.Video;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.view.Video.fragment.AttentionFFragment;
import com.connxun.elinetv.view.Video.fragment.SameCityFFragment;
import com.connxun.elinetv.view.Video.fragment.SquareFragment;
import com.connxun.elinetv.view.Video.fragment.TopicFragment;
import com.connxun.elinetv.view.VideoShort.VideoShortActivity;
import com.connxun.elinetv.view.user.login.LoginActivity;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2017/12/28.
 */

/**
 * 视频
 */
public class VideoFragment extends BaseFragment {


    View view;


    @ViewInject(R.id.tl_video_title)
    TabLayout tlVideoTitle; //标题栏

    @ViewInject(R.id.vp_video_viewpager)
    ViewPager vpVideoViewPgaer; //容器

    @ViewInject(R.id.iv_video_shoot)
    ImageView ivVideoShort;


    private List<String> tablist = new ArrayList<>(); //标题数据
    private List<Fragment> fragments = new ArrayList<>(); //fragment数据

    FragmentVpAdapter fragmentVpAdapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseFragment.setView(R.layout.fragment_home_video);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//        this.view =super.onCreateView(inflater, container, savedInstanceState);
        view = inflater.inflate(R.layout.fragment_home_video,null);
        x.view().inject(this, view);
        //设置标题数据
        setTableListTitle();
        //设置viewpager数据
        setViewPager();

        setListener();

        return view;
    }


    /**
     * 设置viewpager数据
     */
    private void setViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new AttentionFFragment());
        fragments.add(new SquareFragment());
        fragments.add(new TopicFragment());
        fragments.add(new SameCityFFragment());

        fragmentVpAdapter = new FragmentVpAdapter(getActivity().getSupportFragmentManager());
        fragmentVpAdapter.setFragments((ArrayList<Fragment>) fragments);
        vpVideoViewPgaer.setAdapter(fragmentVpAdapter);
        tlVideoTitle.setupWithViewPager(vpVideoViewPgaer);
        tlVideoTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        tlVideoTitle.setTabsFromPagerAdapter(fragmentVpAdapter);
        vpVideoViewPgaer.setCurrentItem(1);
        vpVideoViewPgaer.setOffscreenPageLimit(5);
    }

    /**
     * 设置监听
     */
    private void setListener() {
        ivVideoShort.setOnClickListener(this);
    }

    /**
     * 设置标题数据
     */
    private void setTableListTitle() {
        tablist.clear();
        tablist.add("关注");
        tablist.add("广场");
        tablist.add("话题");
        tablist.add("同城");


        //设置颜色
        tlVideoTitle.setTabTextColors(VideoFragment.this.getResources().getColor(R.color.find_msg)
                ,VideoFragment.this.getResources().getColor(R.color.check_true));
        for (int i = 0; i < tablist.size(); i++) {
            tlVideoTitle.addTab(tlVideoTitle.newTab().setText(tablist.get(i)));
        }

    }


    class FragmentVpAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragmentList;

        public void setFragments(ArrayList<Fragment> fragments) {
            mFragmentList = fragments;
        }

        public FragmentVpAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {

            Fragment fragment = mFragmentList.get(position);

            return fragment;
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }
        //ViewPager与TabLayout绑定后，这里获取到PageTitle就是Tab的Text
        @Override
        public CharSequence getPageTitle(int position) {
            return tablist.get(position);
        }
    }


    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.iv_video_shoot:
                if(isLogin()){
                    startActivity(new Intent(getActivity(), LoginActivity.class));
                    return;
                }
                startActivity(new Intent(getContext(), VideoShortActivity.class));
                break;
        }
    }

    /**
     * 是否已经登录
     *
     * @return
     */
    private boolean isLogin() {
        String userNo =  BaseApplication.getUserSp().getString("userNo","");
        String token = BaseApplication.getUserSp().getString("token","");
        if(TextUtils.isEmpty(userNo) && TextUtils.isEmpty(token)){
            return  true;
        }else {
            return false;

        }

    }

}
