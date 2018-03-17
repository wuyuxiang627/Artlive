package com.connxun.elinetv.view.MediaPreview.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseFragment;

/**
 * Created by Administrator on 2018\3\9 0009.
 */


/**
 * 自由播
 */
public class FreeLiveFragment extends BaseFragment {
    View view;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_free_live,null);
        return view;
    }
}
