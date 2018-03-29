package com.connxun.elinetv.view.Live.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.ContentLoadingProgressBar;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Live.HOtRecommendAdapter;
import com.connxun.elinetv.adapter.Live.PopularAnchorAdapter;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.AD;
import com.connxun.elinetv.entity.AdList;
import com.connxun.elinetv.entity.LiveModel;
import com.connxun.elinetv.entity.WatchLive;
import com.connxun.elinetv.presenter.Live.LiveHotPresenter;
import com.connxun.elinetv.presenter.Live.LiveListPresenter;
import com.connxun.elinetv.presenter.Live.LivePresenter;
import com.connxun.elinetv.presenter.other.OtherPresenter;
import com.connxun.elinetv.view.MediaPreview.LIveRoomActivity;
import com.connxun.elinetv.view.MediaPreview.MediaPreviewActivity;
import com.connxun.elinetv.view.SearchActivity;
import com.connxun.elinetv.view.user.ITestView;
import com.donkingliang.banner.CustomBanner;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by connxun-16 on 2017/12/28.
 */

public class HotOptimizationFragment extends BaseFragment {
    View view;

    //控件
    @ViewInject(R.id.swiperefreshlayout)
    SmartRefreshLayout swiperefreshlayout;
    @ViewInject(R.id.layout_rlv_hot_anchor_view)
    RecyclerView rlvAnchor;
    @ViewInject(R.id.layout_rlv_hot_recommened_view)
    RecyclerView rlvRecommended;
    @ViewInject(R.id.rlv_hot_live_all_anchor)
    RecyclerView rlvallAnchor;

    @ViewInject(R.id.layout_rl_live_hot_anchor_title)
    RelativeLayout rlAnchorTitle;
    @ViewInject(R.id.layout_rl_live_hot_recommened_title)
    RelativeLayout rlRecommendedTitlle;

    @ViewInject(R.id.iv_live_title_search)
    ImageView ivSearch;
    @BindView(R.id.tv_live_title_text)
    TextView tvLiveTitleText;
    @BindView(R.id.iv_live_title_search)
    ImageView ivLiveTitleSearch;
    @BindView(R.id.iv_live_title_news)
    ImageView ivLiveTitleNews;
    @BindView(R.id.rl_live_title)
    RelativeLayout rlLiveTitle;
    @BindView(R.id.banner)
    CustomBanner banner;
    @BindView(R.id.layout_rl_live_hot_anchor_title)
    RelativeLayout layoutRlLiveHotAnchorTitle;
    @BindView(R.id.layout_rlv_hot_anchor_view)
    RecyclerView layoutRlvHotAnchorView;
    @BindView(R.id.layout_rl_live_hot_recommened_title)
    RelativeLayout layoutRlLiveHotRecommenedTitle;
    @BindView(R.id.layout_rlv_hot_recommened_view)
    RecyclerView layoutRlvHotRecommenedView;
    @BindView(R.id.rlv_hot_live_all_anchor)
    RecyclerView rlvHotLiveAllAnchor;
    Unbinder unbinder;

    private MyAdapter myAdapter;
    PopularAnchorAdapter anchorAdapter;//人气主播
    HOtRecommendAdapter hotRecommendedAdapter;//推荐主播
    HOtRecommendAdapter ListLiveAdapter;//直播列表


    ArrayList<String> images;
    ArrayList<LiveModel> hotRecommendedList;
    WatchLive watchLives; //人气主播
    WatchLive watchHotLives; //推荐主播
    WatchLive watchListLives; //直播列表
    List<LiveModel> liveModels = new ArrayList<>(); //人气主播
    List<LiveModel> hotLiveModels = new ArrayList<>(); //推荐主播
    List<LiveModel> LiveListModels = new ArrayList<>(); //直播列表
    ArrayList<Integer> allAnchorList;

    private Handler handler;
    private LinearLayoutManager layoutManager;
    private int count = 0;
    private CustomBanner<String> mBanner; //轮播控件

    private OtherPresenter otherPresenter = new OtherPresenter(getActivity());
    private LivePresenter livePresenter = new LivePresenter(getActivity());
    private LiveHotPresenter liveHotPresenter = new LiveHotPresenter(getActivity());
    private LiveListPresenter liveListPresenter = new LiveListPresenter(getActivity());


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live_hot_optimization, null);
        x.view().inject(this, view);
        ivSearch.setOnClickListener(v -> {
            startActivity(new Intent(getActivity(), SearchActivity.class));

        });

        //设置banner
        setBanner();

        LinearLayoutManager ms = new LinearLayoutManager(getActivity());
        ms.setOrientation(LinearLayoutManager.HORIZONTAL);
        rlvAnchor.setLayoutManager(ms);
        anchorAdapter = new PopularAnchorAdapter(getActivity(), liveModels);
        rlvAnchor.setAdapter(anchorAdapter);
        rlvAnchor.setNestedScrollingEnabled(false);

        //设置热门推荐

        setHotRecommended();

        //所有直播
        setAllLlist();

        //人气主播
        setAnchor();

        //推荐主播
        setHotLive();

        //直播列表
        setLiveList();

//        //下拉刷新
//        setAllAnchor();

        setListener();

        unbinder = ButterKnife.bind(this, view);
        return view;
    }

    //所有直播
    private void setLiveList() {
        liveListPresenter.onCreate();
        try {
            liveListPresenter.getliveList("20", "1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        liveListPresenter.attachView(mlivLiveListView);
    }

    /**
     * 推荐主播
     */
    private void setHotLive() {
        liveHotPresenter.onCreate();
        try {
            liveHotPresenter.getliveHotLiveList("20", "1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        liveHotPresenter.attachView(mliveHotLiveListView);
    }

    /**
     * 人气主播
     */
    private void setAnchor() {
        livePresenter.onCreate();
        try {
            livePresenter.getliveRanKingList("20", "1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        livePresenter.attachView(mliveRanKingListView);
    }


    private void setListener() {
        swiperefreshlayout.setOnRefreshListener((refreshlayout) -> {
            //人气主播
            setAnchor();
//                //推荐主播
            setHotLive();
            setLiveList();
            refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
        });

        swiperefreshlayout.setOnLoadMoreListener((refreshlayout) -> {

            refreshlayout.finishLoadMore(2000);
        });


    }

    @Override
    public void setOnClick() {
        super.setOnClick();
        ivSearch.setOnClickListener(this);

    }

    @Override

    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId()) {
            case R.id.iv_live_title_search:
//                startActivity(new Intent(getActivity(), SearchActivity.class));
                Intent intent = new Intent(getActivity(), MediaPreviewActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        images = new ArrayList<>();

        images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4026968818,2898285081&fm=27&gp=0.jpg");
        images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4026968818,2898285081&fm=27&gp=0.jpg");
        images.add("https://ss3.bdstatic.com/70cFv8Sh_Q1YnxGkpoWK1HF6hhy/it/u=4026968818,2898285081&fm=27&gp=0.jpg");

        try {
            //设置banner
//            setBanner();
        } catch (Exception e) {

        }


    }

    private void setBanner() {
        otherPresenter.onCreate();
        otherPresenter.getAdList();
        otherPresenter.attachView(ohterBannerView);
    }

    /**
     * 设置banner
     */
    private ITestView ohterBannerView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            mBanner = (CustomBanner) view.findViewById(R.id.banner);
            AD adentity = (AD) object;
            List<AdList> adLists = adentity.getData().getData().getList();
            ArrayList<String> images = new ArrayList<>();
            for (int i = 0; i < adLists.size(); i++) {
                images.add(adLists.get(i).getAdPic());
            }
            mBanner.setPages(new CustomBanner.ViewCreator<String>() {
                @Override
                public View createView(Context context, int position) {
                    ImageView imageView = new ImageView(context);
                    imageView.setScaleType(ImageView.ScaleType.FIT_XY);
                    return imageView;
                }

                @Override
                public void updateUI(Context context, View view, int position, String entity) {
                    Glide.with(context).load(entity).into((ImageView) view);
                }
            }, images).startTurning(5000);

        }

        @Override
        public void onError(Object object) {

        }
    };


    /**
     * 设置热门推荐
     */
    private void setHotRecommended() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rlvRecommended.setLayoutManager(gridLayoutManager);
        hotRecommendedAdapter = new HOtRecommendAdapter(getActivity(), hotLiveModels);
        rlvRecommended.setAdapter(hotRecommendedAdapter);
        rlvRecommended.setNestedScrollingEnabled(false);
    }

    /**
     * 设置热门推荐
     */
    private void setAllLlist() {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
        rlvallAnchor.setLayoutManager(gridLayoutManager);
        ListLiveAdapter = new HOtRecommendAdapter(getActivity(), LiveListModels);
        rlvallAnchor.setAdapter(ListLiveAdapter);
        rlvallAnchor.setNestedScrollingEnabled(false);
    }


    private void getData(final String type) {

        for (int i = 0; i < 20; i++) {
            count += 1;
            allAnchorList.add(count);
        }

        myAdapter.notifyDataSetChanged();
//        if (swipeRefreshLayout.isRefreshing()) {
//            swipeRefreshLayout.setRefreshing(false);
//        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }


    private class MyAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
        private final static int TYPE_CONTENT = 0;//正常内容
        private final static int TYPE_FOOTER = 1;//加载View

        @Override
        public int getItemViewType(int position) {
            return TYPE_CONTENT;
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getActivity()).inflate(R.layout.item_live_hot_recommendedor, parent, false);
            MyViewHolder myViewHolder = new MyViewHolder(view);
            return myViewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
            MyViewHolder viewHolder = (MyViewHolder) holder;
            viewHolder.textView.setText("第" + position + "行");
            layoutManager.getChildCount();
            layoutManager.getItemCount();
            layoutManager.findLastVisibleItemPosition();
        }


        @Override
        public int getItemCount() {
            return allAnchorList.size();
        }
    }

    private class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.tv_item_hot_user_name);
        }
    }

    private class FootViewHolder extends RecyclerView.ViewHolder {
        ContentLoadingProgressBar contentLoadingProgressBar;

        public FootViewHolder(View itemView) {
            super(itemView);
            contentLoadingProgressBar = itemView.findViewById(R.id.pb_progress);
        }
    }

    /**
     * 人气主播
     */
    public ITestView mliveRanKingListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
//            swipeRefreshLayout.setRefreshing(false);
            watchLives = (WatchLive) object;
            liveModels.clear();

            liveModels = watchLives.getData().getList();
            anchorAdapter.getList().addAll(liveModels);
            anchorAdapter.notifyDataSetChanged();
            anchorAdapter.setOnItemClickListener((view, position) -> {
                BaseApplication.blLiveTypeLiveOrChallenge = false;
                Intent intent = new Intent(getActivity(), LIveRoomActivity.class);
                intent.putExtra("mVideoPath", watchLives.getData().getList().get(position).getRtmpPullUrl());
                intent.putExtra("roomId", watchLives.getData().getList().get(position).getRoomid() + "");
                intent.putExtra("mLiveModel", watchLives.getData().getList().get(position));
                startActivity(intent);
            });
        }

        @Override
        public void onError(Object object) {

        }
    };
    /**
     * 推荐主播
     */
    public ITestView mliveHotLiveListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
//            swipeRefreshLayout.setRefreshing(false);
            watchHotLives = (WatchLive) object;
            hotLiveModels.clear();

            hotLiveModels = watchHotLives.getData().getList();
            hotRecommendedAdapter.getList().addAll(hotLiveModels);
            hotRecommendedAdapter.notifyDataSetChanged();
            hotRecommendedAdapter.setOnItemClickListener((view, position) -> {
                BaseApplication.blLiveTypeLiveOrChallenge = false;
                Intent intent = new Intent(getActivity(), LIveRoomActivity.class);
                intent.putExtra("mVideoPath", watchHotLives.getData().getList().get(position).getRtmpPullUrl());
                intent.putExtra("roomId", watchHotLives.getData().getList().get(position).getRoomid() + "");
                intent.putExtra("mLiveModel", watchHotLives.getData().getList().get(position));
                startActivity(intent);
            });
        }

        @Override
        public void onError(Object object) {

        }
    };
    /**
     * 所有直播
     */
    public ITestView mlivLiveListView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
//            swipeRefreshLayout.setRefreshing(false);
            watchListLives = (WatchLive) object;
            LiveListModels.clear();

            LiveListModels = watchListLives.getData().getList();
            ListLiveAdapter.getList().addAll(LiveListModels);
            ListLiveAdapter.notifyDataSetChanged();
            ListLiveAdapter.setOnItemClickListener((view, position) -> {
//                String name = watchListLives.getData().getList().get(position).getName();
//                if (name.equals("none")) {
//                    BaseApplication.blLiveTypeLiveOrChallenge = false;
//                }
                BaseApplication.blLiveTypeLiveOrChallenge = true;
                Intent intent = new Intent(getActivity(), LIveRoomActivity.class);
                intent.putExtra("mVideoPath", watchListLives.getData().getList().get(position).getRtmpPullUrl());
                intent.putExtra("roomId", watchListLives.getData().getList().get(position).getRoomid() + "");
                intent.putExtra("mLiveModel", watchListLives.getData().getList().get(position));
                startActivity(intent);
            });
        }

        @Override
        public void onError(Object object) {

        }
    };


}
