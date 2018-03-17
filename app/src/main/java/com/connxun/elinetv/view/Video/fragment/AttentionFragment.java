package com.connxun.elinetv.view.Video.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseFragment;

/**
 * Created by connxun-16 on 2018/1/10.
 */

public class AttentionFragment extends BaseFragment {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BaseFragment.setView(R.layout.fragment_video_attention);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

}
