package com.connxun.elinetv.view.user;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseFragment;
import com.connxun.elinetv.presenter.user.UserinfoPresenter;
import com.connxun.elinetv.view.My.MyFancesActivity;
import com.connxun.elinetv.view.My.MyFollowActivity;
import com.connxun.elinetv.view.My.MyInformationActivity;
import com.connxun.elinetv.view.Setting.SettingActivity;
import com.connxun.elinetv.view.user.MyChat.MyChatActivity;
import com.connxun.elinetv.view.user.MyOpinion.MyOpinionActivity;
import com.connxun.elinetv.view.user.MyPurse.MyPurseActivity;
import com.connxun.elinetv.view.user.MyVideo.MyVideoActivity;
import com.connxun.elinetv.view.user.Privilege.PriVilegeCenterActivity;
import com.facebook.drawee.view.SimpleDraweeView;

import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by connxun-16 on 2017/12/28.
 */

/**
 * 我的
 */
public class MindFragment extends BaseFragment {
    View view;
    @ViewInject(R.id.iv_mind_setting)
    ImageView ivSetting;
    @ViewInject(R.id.iv_mind_user_photo)
    SimpleDraweeView ivUserPhoto;

    @ViewInject(R.id.rl_mind_user_photo)
    RelativeLayout rlUser;

    @ViewInject(R.id.tv_mind_user_name)
    TextView  tvUserName;

    @ViewInject(R.id.rl_mind__wallet)
    RelativeLayout rlMindWallet;

    @ViewInject(R.id.rl_mind_my_chat)
    RelativeLayout rlMindMyChat;

    @ViewInject(R.id.rl_mind_user_follow)
    RelativeLayout rlMindFollow;

    @ViewInject(R.id.rl_mind_user_fans)
    RelativeLayout rlMindFans;

    @ViewInject(R.id.rl_mind_user_video)
    RelativeLayout rlMindVideo;

    @ViewInject(R.id.rl_mind_user_privilege)
    RelativeLayout rlMindPrivilege;

    @ViewInject(R.id.rl_mind_user_help)
    RelativeLayout rlMindHelp;

    @ViewInject(R.id.tv_mind_user_follow_num)
    TextView tvFollowNum;

    @ViewInject(R.id.tv_mind_user_fans_num)
    TextView tvFansNum;


    private UserinfoPresenter userinfoPresenter = new UserinfoPresenter(getActivity());//获取用户的个人信息

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_mind,null);
        x.view().inject(this, view);
        initView();
        setOnClick();
        return view ;
    }




    private void initView() {
        rlMindWallet = view.findViewById(R.id.rl_mind__wallet);
    }

    @Override
    public void onResume() {
        super.onResume();
        tvUserName.setText(BaseApplication.getUserSp().getString("nickName","未登录").toString());
        tvFollowNum.setText(BaseApplication.getUserSp().getString("followNum","0"));
        tvFansNum.setText(BaseApplication.getUserSp().getString("followerSize","0"));
        String userPhotoPath = BaseApplication.getUserSp().getString("avatar",null);
        if(userPhotoPath != null){
            //创建将要下载的图片的URI
            Uri imageUri = Uri.parse(userPhotoPath);
            //开始下载
            ivUserPhoto.setImageURI(imageUri);

//            Picasso.with(BaseApplication.baseContext)
//                    .load(userPhotoPath).transform(new ImageMatisee(20)).into(ivUserPhoto);
        }
    }

    public void setOnClick() {
        ivSetting.setOnClickListener(this);
        rlUser.setOnClickListener(this);
        rlMindWallet.setOnClickListener(this);
        rlMindMyChat.setOnClickListener(this);
        rlMindFollow.setOnClickListener(this);
        rlMindFans.setOnClickListener(this);
        rlMindVideo.setOnClickListener(this);
        rlMindPrivilege.setOnClickListener(this);
        rlMindHelp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        Intent intent = null;
        switch (view.getId())
        {
            case R.id.iv_mind_setting: //设置
                intent = new Intent(BaseApplication.getContext(),SettingActivity.class);
                intent.putExtra("type",2);
                StartActivity(intent);

                break;
            case R.id.rl_mind_user_photo: //用户资料
                isLogin_startActivity(new Intent(getActivity(), MyInformationActivity.class));
                break;
            case R.id.rl_mind__wallet: //我的钱包
                isLogin_startActivity(new Intent(getActivity(), MyPurseActivity.class));
                break;
            case R.id.rl_mind_my_chat: //我的聊天
                isLogin_startActivity(new Intent(getActivity(), MyChatActivity.class));
                break;
            case R.id.rl_mind_user_follow: //我的关注
                isLogin_startActivity(new Intent(getActivity(), MyFollowActivity.class));
                break;
           case R.id.rl_mind_user_fans: //我的粉丝
               isLogin_startActivity(new Intent(getActivity(), MyFancesActivity.class));
                break;
            case R.id.rl_mind_user_video: //我的小视频
                isLogin_startActivity(new Intent(getActivity(), MyVideoActivity.class));
                break;
            case R.id.rl_mind_user_privilege: //特权中心
                isLogin_startActivity(new Intent(getActivity(), PriVilegeCenterActivity.class));
                break;
            case R.id.rl_mind_user_help: //帮助和反馈
                isLogin_startActivity(new Intent(getActivity(), MyOpinionActivity.class));
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


    private ITestView mLoginView = new ITestView() {
        @Override
        public void onSuccess(Object object) {
//            registerEntity = (RegisterEntity) object;
//            textLogin.setText(registerEntity.getData().toString());
//            BaseApplication.setToken(registerEntity.getData().getToken());
//            BaseApplication.setUserNo(registerEntity.getData().getUserNo());
//            SharedPreferences.Editor  editor = BaseApplication.getEditor();
//            editor.putString("userNo",registerEntity.getData().getUserNo());
//            editor.putString("token",registerEntity.getData().getToken());
//            editor.putInt("state",registerEntity.getData().getStatus());
//            editor.putString("nickName",registerEntity.getData().getNickName());
//            editor.commit();
        }

        @Override
        public void onError(Object object) {
//            textLogin.setText(object.toString());
        }
    };

}
