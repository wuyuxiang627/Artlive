package com.connxun.elinetv.view.My;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.EntityObject;
import com.connxun.elinetv.entity.user.UserInfoEntity;
import com.connxun.elinetv.presenter.user.UserinfoPresenter;
import com.connxun.elinetv.view.My.fragment.FragmentData;
import com.connxun.elinetv.view.user.ITestView;
import com.connxun.elinetv.view.user.MyVideo.MyVideoReleaseragment;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.ArrayList;
import java.util.List;

//用户资料
public class MyInformationActivity extends BaseActivity implements View.OnClickListener {

    private TabLayout tabLayout;
    private ViewPager pager;
    private RelativeLayout rl_my_information_return;
    private ImageView iv_information_editor;
    private TextView tv_my_information_attention,my_information_fans;
    private SimpleDraweeView ivUserPhoto;
    private TextView tvUserName,tv_my_information_sex;
    private ImageView iv_my_information_avatar;
    private TextView tv_autograph;
    UserinfoPresenter userinfoPresenter = new UserinfoPresenter(this);


    private EntityObject<UserInfoEntity> userInfoEntity;



    List<String> list;
    List<Fragment> flist;
    String[] array = {"作品", "资料"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_information);

        initview();
        initData();
        Userdata();

        tabLayout.setupWithViewPager(pager);
        MyViewPager adapter = new MyViewPager(getSupportFragmentManager());
        pager.setAdapter(adapter);
    }

    private void initData() {
        flist = new ArrayList<>();
        flist.add(new MyVideoReleaseragment());
        flist.add(new FragmentData());
    }

    @SuppressLint("WrongViewCast")
    private void initview() {
        my_information_fans = findViewById(R.id.my_information_fans);//粉丝
        rl_my_information_return = findViewById(R.id.rl_my_information_return);//返回
        iv_information_editor = findViewById(R.id.iv_information_editor);//编辑
        tabLayout = findViewById(R.id.tablayout);
        pager = findViewById(R.id.pager_Myinformation);
        tv_my_information_attention = findViewById(R.id.tv_my_information_attention);//关注
        tvUserName = findViewById(R.id.tv_my_information_name); //用户名
        tv_my_information_sex = findViewById(R.id.tv_my_information_sex);//年龄
//        iv_my_information_avatar = findViewById(R.id.iv_my_information_avatar);//头像
        ivUserPhoto = findViewById(R.id.iv_information);
        tv_autograph = findViewById(R.id.tv_autograph);
        my_information_fans.setOnClickListener(this);
        tv_my_information_attention.setOnClickListener(this);
        rl_my_information_return.setOnClickListener(this);
        iv_information_editor.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;
        switch (view.getId()) {
            case R.id.rl_my_information_return: //返回
                finish();
                break;
           case R.id.iv_information_editor: //编辑
               intent = new Intent(MyInformationActivity.this, MyEditingActivity.class);
               if(userInfoEntity != null){
                   intent.putExtra("userInfo",userInfoEntity.getData());
               }
                startActivity(intent);
                break;
            case R.id.tv_my_information_attention: //关注
                startActivity(new Intent(MyInformationActivity.this, MyFollowActivity.class));
                break;
           case R.id.my_information_fans: //粉丝
               startActivity(new Intent(MyInformationActivity.this, MyFancesActivity.class));
                break;
        }
    }

    //viewpager适配器
    public class MyViewPager extends FragmentPagerAdapter {


        public MyViewPager(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return flist.get(position);
        }

        @Override
        public int getCount() {
            return flist.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return array[position];
        }
    }


    private void Userdata(){

        userinfoPresenter.onCreate();
        userinfoPresenter.getUserUserinfo();
        userinfoPresenter.attachView(UserInfoView);

    }


    public ITestView UserInfoView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            userInfoEntity = (EntityObject<UserInfoEntity>) object;
            setUserDate(userInfoEntity);
            FragmentData.setUserInformation(userInfoEntity.getData());
        }

        @Override
        public void onError(Object object) {

        }
    };

    //设置用户数据
    public void setUserDate(EntityObject<UserInfoEntity> userInfoEntity){
        String userName = userInfoEntity.getData().getNickName();
        if(userName != null){
            tvUserName.setText(userName);
        }
        String avatar = userInfoEntity.getData().getAvatar();
        if(avatar != null){
            Uri imageUri = Uri.parse(avatar);
            ivUserPhoto.setImageURI(imageUri);
        }

        String signature = userInfoEntity.getData().getSignature();
        if(signature != null){
            tv_autograph.setText(signature);
        }

        int attention = userInfoEntity.getData().getFollowNum();
        tv_my_information_attention.setText(attention+" 关注");

        int fans = userInfoEntity.getData().getFollowerSize();
        my_information_fans.setText(fans+" 粉丝");


    }




}
