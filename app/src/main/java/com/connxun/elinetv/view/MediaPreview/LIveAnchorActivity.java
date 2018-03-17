package com.connxun.elinetv.view.MediaPreview;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.connxun.elinetv.IM.NimContract;
import com.connxun.elinetv.IM.NimController;
import com.connxun.elinetv.IM.activity.InputActivity;
import com.connxun.elinetv.IM.config.InputConfig;
import com.connxun.elinetv.IM.fragment.ChatRoomMessageFragment;
import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Live.CheetahStaffAdapter;
import com.connxun.elinetv.base.Dialog.ActionSheetDialog;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.LiveModel;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomInfo;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by connxun-16 on 2018/2/25.
 */

@ContentView(R.layout.activity_live_room)
public class LIveAnchorActivity extends BaseActivity implements View.OnClickListener, NimContract.Ui{
    public static LIveAnchorActivity lIveRoomActivity= null;

    //各大板块容器

//    private LiveFragment liveFragment; //主播


    //聊天室相关
    private FrameLayout chatLayout;
    private ChatRoomMessageFragment chatRoomFragment;
    private NimController nimController;
    @ViewInject(R.id.rlv_cheetah_staff)
    RecyclerView rlvCheetahStaff;
    List<ChatRoomMember> result = new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    private CheetahStaffAdapter cheetahStaffAdapter;
    @ViewInject(R.id.tv_media_preview_watch_number)
    TextView tvWatchNumber;

    String roomId;
    private LiveModel mLiveModel;
    private ActionSheetDialog mDialog;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFragment();
        lIveRoomActivity = this;
        //应用运行时，保持屏幕高亮，不锁屏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //首先加入聊天室
        nimController = new NimController(this, this);
        nimController.onHandleIntent(getIntent());

    }





    /**A
     * 根据是否为观众,加载不同的Fragment
     */
    private void loadFragment() {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
//        liveFragment = new LiveFragment();
//        transaction.replace(R.id.layout_main_content, liveFragment);
        transaction.commit();
    }


    /**
     * 显示聊天输入布局 展开键盘
     */
    public void showInputPanel(){
        startInputActivity();
    }

    /**
     * ***************************** 部分机型键盘弹出会造成布局挤压的解决方案 ***********************************
     */
    private InputConfig inputConfig = new InputConfig(false, false, false);
    private String cacheInputString = "";
    public final static String EXTRA_TEXT = "EXTRA_TEXT";
    public final static String EXTRA_MODE = "EXTRA_MODE";
    private final static String EXTRA_INPUT_CONFIG = "EXTRA_INPUT_CONFIG";
    public final static int REQ_CODE = 20;
    private void startInputActivity() {
        InputActivity.startActivityForResult(LIveAnchorActivity.this, cacheInputString,
                inputConfig, new InputActivity.InputActivityProxy() {
                    @Override
                    public void onSendMessage(String text) {
                        if(chatRoomFragment != null){
                            chatRoomFragment.onTextMessageSendButtonPressed(text);
                        }
                    }
                });
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK && requestCode == InputActivity.REQ_CODE) {
            // 设置EditText显示的内容
            cacheInputString = data.getStringExtra(InputActivity.EXTRA_TEXT);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
//        mDialog = new ActionSheetDialog(this).builder();
//        mDialog.setCancelable(false);
//        mDialog.setCanceledOnTouchOutside(true);
//        mDialog.setTitle("是否退出观看直播");
//        mDialog.addSheetItem("是", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
//
//            @Override
//            public void onClick(int which) {
//                finish();
//            }
//        });
//        mDialog.addSheetItem("否", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {
//            @Override
//            public void onClick(int which) {
//            }
//        }).show();
        return false;
    }



    @Override
    public void onEnterChatRoomSuc(final String roomId) {
        this.roomId = roomId;
        chatRoomFragment = (ChatRoomMessageFragment) getSupportFragmentManager().findFragmentById(R.id.chat_room_fragment);
        if (chatRoomFragment != null) {
            initChatRoomFragment();
        } else {
            // 如果Fragment还未Create完成，延迟初始化
            getHandler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    onEnterChatRoomSuc(roomId);
                }
            }, 50);
        }
    }

    /**
     * 刷新房间信息
     * @param roomInfo
     */
    @Override
    public void refreshRoomInfo(ChatRoomInfo roomInfo) {

    }

    /**
     * 刷新人员列表
     * @param result
     */
    @Override
    public void refreshRoomMember(List<ChatRoomMember> result) {
        MediaPreviewFragment.setUSerPhotoNumber(result);
    }

    /**
     * 隐藏人员操作布局
     */
    @Override
    public void dismissMemberOperateLayout() {

    }

    /**
     * 聊天室结束回调
     * @param reason  结束原因
     */
    @Override
    public void onChatRoomFinished(String reason) {

    }

    @Override
    public void showTextToast(String text) {

    }

    /**
     * 初始化聊天室Fragment
     */
    private void initChatRoomFragment() {
        chatRoomFragment.init(roomId);

    }

}
