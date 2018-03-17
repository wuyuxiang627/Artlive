package com.netease.nim.uikit.business.chatroom.viewholder;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.netease.nim.uikit.R;
import com.netease.nim.uikit.business.chatroom.helper.ChatRoomHelper;
import com.netease.nim.uikit.business.uinfo.UserInfoHelper;
import com.netease.nim.uikit.impl.NimUIKitImpl;
import com.netease.nimlib.sdk.chatroom.constant.MemberType;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMember;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessage;
import com.netease.nimlib.sdk.chatroom.model.ChatRoomMessageExtension;

import java.util.HashMap;


/**
 * 聊天室成员姓名
 * Created by hzxuwen on 2016/1/20.
 */
public class ChatRoomViewHolderHelper {

    public static String getNameText(ChatRoomMessage message) {
        // 聊天室中显示姓名
        if (message.getChatRoomMessageExtension() != null) {
            return message.getChatRoomMessageExtension().getSenderNick();
        } else {
            ChatRoomMember member = NimUIKitImpl.getChatRoomProvider().getChatRoomMember(message.getSessionId(), message.getFromAccount());
            return member == null ? UserInfoHelper.getUserName(message.getFromAccount()) : member.getNick();
        }
    }

    public static void setStyleOfNameTextView(ChatRoomMessage message, TextView nameTextView, ImageView nameIconView) {
        nameTextView.setTextColor(NimUIKitImpl.getContext().getResources().getColor(R.color.color_split_user_name));
        MemberType type = ChatRoomHelper.getMemberTypeByRemoteExt(message);
        ChatRoomMessageExtension aaa = message.getChatRoomMessageExtension();
        message.getChatRoomMessageExtension();
        HashMap<String,Object> map = (HashMap) message.getRemoteExtension();
        if(map!= null){
            Log.e("nizhdiaolkasdjfls","map: "+map.get("level").toString());
            int level = Integer.parseInt(map.get("level").toString());
            nameIconView.setVisibility(View.VISIBLE);
            switch (level)
            {
                case 0:
                    nameIconView.setImageResource(R.drawable.yanyuan);
                    break;
                case 1:
                    nameIconView.setImageResource(R.drawable.peijue);
                    break;
                case 2:
                    nameIconView.setImageResource(R.drawable.zhujue);
                    break;
                case 3:
                    nameIconView.setImageResource(R.drawable.teyueyanyuan);
                    break;
                case 4:
                    nameIconView.setImageResource(R.drawable.mingxing);
                    break;
                case 5:
                    nameIconView.setImageResource(R.drawable.dawan);
                    break;
                case 6:
                    nameIconView.setImageResource(R.drawable.guojijuxing);
                    break;
                case 7:
                    nameIconView.setImageResource(R.drawable.fudaoyan);
                    break;
                case 8:
                    nameIconView.setImageResource(R.drawable.daoyan);
                    break;
                case 9:
                    nameIconView.setImageResource(R.drawable.daoyan);
                    break;

            }

        }
//        if (type == MemberType.ADMIN) {
//            nameIconView.setImageResource(R.drawable.nim_admin_icon);
//            nameIconView.setVisibility(View.VISIBLE);
//        } else if (type == MemberType.CREATOR) {
//            nameIconView.setImageResource(R.drawable.nim_master_icon);
//            nameIconView.setVisibility(View.VISIBLE);
//        } else {
//            nameIconView.setVisibility(View.GONE);
//        }
    }
}
