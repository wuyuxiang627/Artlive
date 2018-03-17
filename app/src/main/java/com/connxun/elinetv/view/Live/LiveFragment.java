package com.connxun.elinetv.view.Live;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.view.Live.fragment.AgentFragment;
import com.connxun.elinetv.view.Live.fragment.CityFragment;
import com.connxun.elinetv.view.Live.fragment.HotFragment;
import com.connxun.elinetv.view.Live.fragment.SongDancesFragment;
import com.connxun.elinetv.view.Live.fragment.TalkShowFragment;
import com.connxun.elinetv.view.Live.fragment.WorksFragment;
import com.connxun.elinetv.view.Live.fragment.GameFragment;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2017/12/28.
 */

/**
 * 直播
 */
public class LiveFragment extends BaseFragment {
    View view;
    @ViewInject(R.id.tl_live_classification_title)
    TabLayout tlClassificationTitle; //标题栏

    @ViewInject(R.id.vp_live_viewpager)
    ViewPager vpLiveViewPgaer; //容器

//    @ViewInject(R.id.hsv_live_horizontalscro)
//    HorizontalScrollView hsvScrollView;

    private List<String> tablist = new ArrayList<>(); //标题数据
    private List<Fragment> fragments = new ArrayList<>(); //fragment数据

    FragmentVpAdapter fragmentVpAdapter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_live,null);
        x.view().inject(this, view);
        //设置标题数据
        setTableListTitle();
        //设置viewpager数据
        setViewPager();
        //设置监听
        setListener();
        return view ;
    }

    /**
     * 设置viewpager数据
     */
    private void setViewPager() {
        fragments = new ArrayList<>();
        fragments.add(new CityFragment());
        fragments.add(new HotFragment());
        fragments.add(new SongDancesFragment());
        fragments.add(new TalkShowFragment());
        fragments.add(new AgentFragment());
        fragments.add(new WorksFragment());
        fragments.add(new GameFragment());
        fragmentVpAdapter = new FragmentVpAdapter(getActivity().getSupportFragmentManager());
        fragmentVpAdapter.setFragments((ArrayList<Fragment>) fragments);
        vpLiveViewPgaer.setAdapter(fragmentVpAdapter);
        tlClassificationTitle.setupWithViewPager(vpLiveViewPgaer);
        tlClassificationTitle.setTabMode(TabLayout.MODE_SCROLLABLE);
        tlClassificationTitle.setTabsFromPagerAdapter(fragmentVpAdapter);
        vpLiveViewPgaer.setCurrentItem(1);
        vpLiveViewPgaer.setOffscreenPageLimit(5);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
//        //设置标题数据
//        setTableListTitle();
//        //设置viewpager数据
//        setViewPager();
    }

    /**
     * 设置监听
     */
    private void setListener() {
    }

    /**
     * 设置标题数据
     */
    private void setTableListTitle() {
        tablist.clear();
        tablist.add("全国");
        tablist.add("热门");
        tablist.add("歌舞");
        tablist.add("脱口秀");
        tablist.add("经纪人");
        tablist.add("作品");
        tablist.add("游戏");

        //设置颜色
        tlClassificationTitle.setTabTextColors(LiveFragment.this.getResources().getColor(R.color.find_msg)
                ,LiveFragment.this.getResources().getColor(R.color.check_true));
        for (int i = 0; i < tablist.size(); i++) {
            tlClassificationTitle.addTab(tlClassificationTitle.newTab().setText(tablist.get(i)));
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

}
