package com.connxun.elinetv.view.user.MyVideo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseFragment;

/**
 * Created by Administrator on 2018\3\7 0007.
 */

public class MyDraftsFragment extends BaseFragment {
    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_my_drafts,null);
        return view;
    }
}
