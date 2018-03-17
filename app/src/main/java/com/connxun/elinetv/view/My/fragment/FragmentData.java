package com.connxun.elinetv.view.My.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.entity.user.UserInfoEntity;
import com.connxun.elinetv.presenter.user.UserinfoPresenter;

/**
 * Created by connxun-16 on 2018/3/7.
 */

public class FragmentData extends BaseFragment {

    UserinfoPresenter videoPresenter = new UserinfoPresenter(getActivity());
    View view;

    static TextView tvNickName;
    static TextView tvSex;
    static TextView tvBirthday;

    static TextView tvAddress;
    static TextView tvPersonal;
    static TextView tvLiveNumber;
    static TextView tvUserNo;
    static TextView tvGrade;
    static TextView tvExperience;











    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         view = View.inflate(getActivity(), R.layout.fragment_myinformation_datas,null);
        initView();
        return view;
    }

    private void initView() {
        tvNickName = view.findViewById(R.id.tv_editing_nick_name);
        tvSex = view.findViewById(R.id.tv_editing_sex);
        tvBirthday = view.findViewById(R.id.tv_editing_birthday);
        tvAddress = view.findViewById(R.id.tv_editing_city);
        tvPersonal = view.findViewById(R.id.tv_personal);
        tvLiveNumber = view.findViewById(R.id.tv_live_number);
        tvUserNo = view.findViewById(R.id.tv_editing_userNo);
        tvGrade = view.findViewById(R.id.tv_grade);
        tvExperience = view.findViewById(R.id.tv_experience);



    }

    public static void  setUserInformation(UserInfoEntity  userInfoEntity){
        tvNickName.setText(userInfoEntity.getNickName() != null ? userInfoEntity.getNickName():"未知");
        tvBirthday.setText(userInfoEntity.getBirthday() != null ? userInfoEntity.getBirthday():"2018-03-13");
        tvSex.setText(userInfoEntity.getSex()== 1? "男":"女");
        tvAddress.setText(userInfoEntity.getCity() != null ? userInfoEntity.getCity():"北京市");
        tvPersonal.setText(userInfoEntity.getSignature() != null ? userInfoEntity.getSignature():"");
        tvLiveNumber.setText(userInfoEntity.getLiveId()+"" != null ? userInfoEntity.getLiveId()+"":"100000");
        tvUserNo.setText(userInfoEntity.getId()+"" != null ? userInfoEntity.getId()+"":"0000");
        tvGrade.setText(userInfoEntity.getUserLever()+"" != null ? userInfoEntity.getUserLever()+" 级 ":"0 级 ");
        tvExperience.setText(userInfoEntity.getEx()+"" != null ? userInfoEntity.getEx()+"":"0");
//        tvNickName.setText(userInfoEntity.getNickName() != null ? userInfoEntity.getNickName():"");
//        tvNickName.setText(userInfoEntity.getNickName() != null ? userInfoEntity.getNickName():"");
//        tvNickName.setText(userInfoEntity.getNickName() != null ? userInfoEntity.getNickName():"");
    }










}
