package com.connxun.elinetv.view.Find;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.connxun.elinetv.R;
import com.connxun.elinetv.base.ui.BaseFragment;

/**
 * Created by connxun-16 on 2017/12/28.
 */

/**
 * 发现
 */
public class FindFragment extends BaseFragment {
    View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_home_find,null);
        return view ;
    }
}
