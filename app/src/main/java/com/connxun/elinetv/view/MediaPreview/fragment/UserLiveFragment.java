package com.connxun.elinetv.view.MediaPreview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.User.LabelAdapter;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.base.ui.CustomRoundView;
import com.connxun.elinetv.entity.Entity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.LiveModel;
import com.connxun.elinetv.entity.user.LabelEntity;
import com.connxun.elinetv.entity.user.UserInfoEntity;
import com.connxun.elinetv.presenter.user.UserLabelPresenter;
import com.connxun.elinetv.presenter.user.UserinfoPresenter;
import com.connxun.elinetv.view.MediaPreview.LIveRoomActivity;
import com.connxun.elinetv.view.user.ITestView;
import com.netease.nim.uikit.api.NimUIKit;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


/**
 * Created by Administrator on 2018\3\14 0014.
 */

public class UserLiveFragment extends BaseFragment {
    View view;
    UserinfoPresenter userinfoPresenter = new UserinfoPresenter(getActivity());
    UserLabelPresenter userLabelPresenter = new UserLabelPresenter(getActivity());

    private EntityObject<UserInfoEntity> userInfoEntityEntity;


    private RelativeLayout rlUserInfo;
    private RelativeLayout rlAddlabel;


    private LIveRoomActivity liveActivity;
    private LiveModel mLiveModel;

    private CustomRoundView ivCover;
    private TextView tvTitle;
    private TextView tvLiveNumber;
    private TextView tvLiveSinge;
    private TextView tvSend;
    private ImageView ivGrade;
    private ImageButton ibfinish;
    private ImageButton ibLabelfinish;

    private Button btnLabelOne;
    private Button btnLabelTwo;
    private Button btnLabelThree;
    private Button btnLabelAdd;
    private Button btnGender;

    private Button btnIm;
    private Button btnFollow;

    private RecyclerView rlvLabelList;
    private RecyclerView rlvmyLabelList;




    Entity<LabelEntity> labelEntityEntity;
    Entity<LabelEntity> labelLIstEntity;
    EntityObject<List<LabelEntity>> toLiveUserLabelList;
    ArrayList<LabelEntity> labelEntities;

    private LabelAdapter labelAdapter;
    private LabelAdapter labelToUserAdapter;

    private ArrayList<LabelEntity> labelZlist = new ArrayList<>();
    private ArrayList<LabelEntity> labelAList = new ArrayList<>();
    private ArrayList<LabelEntity> labelMyAlist = new ArrayList<>();
    private String TAG = "UserLiveFragment";


    private void initView() {
        btnFollow = view.findViewById(R.id.btn_live_user_info_conment);
        btnIm = view.findViewById(R.id.btn_live_info_im);
        tvSend = view.findViewById(R.id.tv_btn_label_send);
        rlvmyLabelList = view.findViewById(R.id.rlv_label_list_my);
        rlvLabelList = view.findViewById(R.id.rlv_label_list);
        ivCover = view.findViewById(R.id.iv_user_photo_live);
        tvTitle = view.findViewById(R.id.iv_live_info_user_name);
        ivGrade = view.findViewById(R.id.iv_live_info_grade);
        tvLiveNumber = view.findViewById(R.id.tv_live_info_live_numver);
        tvLiveSinge = view.findViewById(R.id.tv_Live_singe);
        ibfinish = view.findViewById(R.id.ib_finish);
        ibLabelfinish = view.findViewById(R.id.ib_label_finish);
        btnLabelOne = view.findViewById(R.id.btn_user_info_label_one);
        btnLabelTwo = view.findViewById(R.id.btn_user_info_label_two);
        btnLabelThree = view.findViewById(R.id.btn_user_info_label_three);
        btnLabelAdd = view.findViewById(R.id.btn_user_info_label_add);
        rlUserInfo = view.findViewById(R.id.il_live_user_info);
        rlAddlabel = view.findViewById(R.id.il_live_add_label);
        btnGender = view.findViewById(R.id.btn_user_info_label_change);

    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        liveActivity = (LIveRoomActivity) getActivity();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        liveActivity = null;
    }



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_live_user,null);
//        mLiveModel = (LiveModel) getActivity().getIntent().getSerializableExtra("mLiveModel");
        initView();
//        if(mLiveModel != null){
//            setUserInfo(mLiveModel.getUserNo());
//        }



        setRecyclerView();



        setOnClick();



        return view;
    }

    private void setRecyclerView() {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),3);
        rlvLabelList.setLayoutManager(gridLayoutManager);
        labelAdapter = new LabelAdapter(getActivity(),1,labelAList);
        rlvLabelList.setAdapter(labelAdapter);

        GridLayoutManager gridLayoutManagerto = new GridLayoutManager(getActivity(),3);
        rlvmyLabelList.setLayoutManager(gridLayoutManagerto);
        labelToUserAdapter = new LabelAdapter(getActivity(),2,labelMyAlist);
        rlvmyLabelList.setAdapter(labelToUserAdapter);

    }




    public void setOnClick() {
        btnIm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NimUIKit.startP2PSession(getActivity(), TOuserNo);
            }
        });

        ibfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewINVisible();
            }
        });

        ibLabelfinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setViewINVisible();
            }
        });


        btnLabelAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rlAddlabel.setVisibility(View.VISIBLE);
                rlUserInfo.setVisibility(View.GONE);


                //所有标签
                userLabelPresenter.onCreate();
                userLabelPresenter.getMenuLabelList("1","50");
                userLabelPresenter.attachView(LabelsView);

            }
        });
        btnGender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                labelAList.clear();
                ArrayList labelsAList = (ArrayList<LabelEntity>) getRandomList(labelZlist,6);
                labelAList.addAll(labelsAList);
                labelAdapter.notifyDataSetChanged();
            }
        });

        labelAdapter.setOnItemClickListener(new LabelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
               LabelEntity labelEntity = labelAList.get(position);
               if(!labelMyAlist.contains(labelEntity) && labelMyAlist.size() < 3){
                   labelMyAlist.add(labelEntity);
               }
               labelToUserAdapter.notifyDataSetChanged();
            }
        });

        labelToUserAdapter.setOnItemClickListener(new LabelAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                labelMyAlist.remove(position);
                labelToUserAdapter.notifyDataSetChanged();
            }
        });
        tvSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(labelMyAlist.size()!= 0){
                    StringBuilder stringBuilder = new StringBuilder();
                    for(LabelEntity labelEntity : labelMyAlist){
                        stringBuilder =  stringBuilder.append(labelEntity.getMenuNo()+",");
                    }
                    String menuNo = String.valueOf(stringBuilder).substring(0,String.valueOf(stringBuilder).length()-1);
                    Log.e(TAG,"stringBuilder"+menuNo);

                    userLabelPresenter.onCreate();
                    userLabelPresenter.getuserLabelAdd(TOuserNo, menuNo);
                    userLabelPresenter.attachView(AddLabelView);
                }
            }
        });
    }

    private void setData() {
        String cover = userInfoEntityEntity.getData().getAvatar().toString();
        if(cover != null){
            Glide.with(this).load(cover).into(ivCover);
        }
        tvTitle.setText(userInfoEntityEntity.getData().getNickName().toString());
        tvLiveSinge.setText(userInfoEntityEntity.getData().getSignature() != null ?userInfoEntityEntity.getData().getSignature():"这个人很懒,还没有签名");
        String fans = userInfoEntityEntity.getData().getFollowerSize()+"";
        String liveId = userInfoEntityEntity.getData().getLiveId()+"";
        int ex = userInfoEntityEntity.getData().getUserLever();
        tvLiveNumber.setText("粉丝: " +fans +"   直播间号 : "+liveId);
        btnFollow.setText(userInfoEntityEntity.getData().getStatus() == 1?"已关注":"关注");
        switch (ex)
        {
            case 0:
                ivGrade.setBackgroundResource(R.drawable.yanyuan);
                break;
            case 1:
                ivGrade.setBackgroundResource(R.drawable.peijue);
                break;
            case 4:
                ivGrade.setBackgroundResource(R.drawable.zhujue);
                break;
            case 5:
                ivGrade.setBackgroundResource(R.drawable.teyueyanyuan);
                break;
            case 6:
                ivGrade.setBackgroundResource(R.drawable.mingxing);
                break;
            case 7:
                ivGrade.setBackgroundResource(R.drawable.dawan);
                break;
            case 8:
                ivGrade.setBackgroundResource(R.drawable.guojijuxing);
                break;
            case 9:
                ivGrade.setBackgroundResource(R.drawable.guojijuxing);
                break;
            case 10:
                ivGrade.setBackgroundResource(R.drawable.fudaoyan);
                break;
            case 11:
                ivGrade.setBackgroundResource(R.drawable.daoyan);
                break;
        }



    }

    //查看的用户信息
    public  void setUserInfo(String userNo){
        //用户信息
        userinfoPresenter.onCreate();
        userinfoPresenter.getUserUserinfo(userNo);
        userinfoPresenter.attachView(userInfoView);

        TOuserNo = userNo;

    }

    private String TOuserNo;
    //用户信息
    public ITestView userInfoView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            userInfoEntityEntity = (EntityObject<UserInfoEntity>) object;
            //用户的标签标签
            userLabelPresenter.onCreate();
            userLabelPresenter.getUserLabelList(TOuserNo,"1","3");
            userLabelPresenter.attachView(LabelView);
            setData();
        }

        @Override
        public void onError(Object object) {

        }
    };


    //修改标签
    private ITestView AddLabelView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            setViewINVisible();
        }

        @Override
        public void onError(Object object) {

        }
    };


    public void setViewINVisible(){
        rlAddlabel.setVisibility(View.GONE);
        rlUserInfo.setVisibility(View.VISIBLE);
        liveActivity.setInVisible();
        labelMyAlist.clear();
        labelZlist.clear();
        labelAList.clear();

    }



    //主播的标签
    private ITestView LabelView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            labelEntityEntity = (Entity<LabelEntity>) object;
            labelEntities = (ArrayList<LabelEntity>) labelEntityEntity.getData().getList();
            if(labelEntities.size() == 0){
                return;
            }
            if (labelEntities.size() == 0){
                btnLabelThree.setVisibility(View.GONE);
                btnLabelOne.setVisibility(View.GONE);
                btnLabelTwo.setVisibility(View.GONE);
                btnLabelAdd.setVisibility(View.VISIBLE);
                return;
            }
            if(labelEntities.size() == 1){
                btnLabelOne.setVisibility(View.VISIBLE);
                btnLabelTwo.setVisibility(View.GONE);
                btnLabelOne.setText(labelEntities.get(0).getName());
                btnLabelThree.setVisibility(View.GONE);
                btnLabelAdd.setVisibility(View.VISIBLE);
                return;
            }
            if(labelEntities.size() == 2){
                btnLabelOne.setText(labelEntities.get(0).getName());
                btnLabelTwo.setText(labelEntities.get(1).getName());
                btnLabelOne.setVisibility(View.VISIBLE);
                btnLabelTwo.setVisibility(View.VISIBLE);
                btnLabelThree.setVisibility(View.GONE);
                btnLabelAdd.setVisibility(View.VISIBLE);
                return;
            }
            if(labelEntities.size() == 3){
                btnLabelOne.setText(labelEntities.get(0).getName());
                btnLabelTwo.setText(labelEntities.get(1).getName());
                btnLabelThree.setText(labelEntities.get(2).getName());
                btnLabelThree.setVisibility(View.VISIBLE);
                btnLabelOne.setVisibility(View.VISIBLE);
                btnLabelTwo.setVisibility(View.VISIBLE);
                btnLabelAdd.setVisibility(View.VISIBLE);
                return;
            }

        }

        @Override
        public void onError(Object object) {

        }
    };


    //标签列表
    private ITestView LabelsView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            labelLIstEntity = (Entity<LabelEntity>) object;
            labelZlist = (ArrayList<LabelEntity>) labelLIstEntity.getData().getList();
            ArrayList labelALists = (ArrayList<LabelEntity>) getRandomList(labelZlist,6);
            labelAList.addAll(labelALists);
            labelAdapter.notifyDataSetChanged();

            //用户对主播的标签
            userLabelPresenter.onCreate();
            userLabelPresenter.getUserLabelTOuserLabelIst(TOuserNo,"1","3");
            userLabelPresenter.attachView(toLiveUserLabelsView);
        }

        @Override
        public void onError(Object object) {

        }
    };

    //对主播标签列表
    private ITestView toLiveUserLabelsView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            toLiveUserLabelList = (EntityObject<List<LabelEntity>>) object;
            ArrayList labelMyAlists = (ArrayList<LabelEntity>) toLiveUserLabelList.getData();
            labelMyAlist.addAll(labelMyAlists);
            labelToUserAdapter.notifyDataSetChanged();
        }

        @Override
        public void onError(Object object) {

        }
    };


    //获取标签
    public static List getRandomList(List paramList,int count){
        if(paramList.size()<count){
            return paramList;
        }
        Random random=new Random();
        List<Integer> tempList=new ArrayList<Integer>();
        List<Object> newList=new ArrayList<Object>();
        int temp=0;
        for(int i=0;i<count;i++){
            temp=random.nextInt(paramList.size());//将产生的随机数作为被抽list的索引
            if(!tempList.contains(temp)){
                tempList.add(temp);
                newList.add(paramList.get(temp));
            }
            else{
                i--;
            }
        }
        return newList;
    }








}
