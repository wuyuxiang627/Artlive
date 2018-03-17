package com.connxun.elinetv.view.user.MyOpinion;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.adapter.Beauty.BeautyTypeAdaper;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.entity.RegisterEntity;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.presenter.user.LoginPresenter;
import com.connxun.elinetv.presenter.user.UserOpinionPresenter;
import com.connxun.elinetv.util.FileUploadUtil;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.view.user.ITestView;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;
import com.squareup.picasso.Picasso;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.File;
import java.util.ArrayList;
import java.util.List;


/**
 * 帮助和反馈
 */
@ContentView(R.layout.activity_my_opinion)
public class MyOpinionActivity extends BaseActivity {
    private String TAG = "MyOpinionActivity";
    public List<String> imgPaths=new ArrayList<>();
    public List<String> imgUrls=new ArrayList<>();
    PicAdapter adapter;
    @ViewInject(R.id.picList)
    RecyclerView picList;
    @ViewInject(R.id.rl_opinion_finish)
    RelativeLayout rlFinish;
    @ViewInject(R.id.submit_opinion)
    Button submitBtn;
    @ViewInject(R.id.opinion_title)
    EditText opinionTitle;
    @ViewInject(R.id.opinion_phone)
    EditText opinionPhone;
    private static int RESULT_CODE_OPINION = 1003; //意见反馈图片上传

    private UserOpinionPresenter userOpinionPresenter = new UserOpinionPresenter(this); //登录
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        picList.setHasFixedSize(true);
        int spanCount = 1; // 只显示一行
        StaggeredGridLayoutManager layoutManager=new StaggeredGridLayoutManager(spanCount, StaggeredGridLayoutManager.HORIZONTAL);
        picList.setLayoutManager(layoutManager);
//        imgPaths.add(R.drawable.upimg_default);
        adapter = new PicAdapter(imgPaths);
        picList.setAdapter(adapter);
    }

    @Override
    public void setOnClick() {
        super.setOnClick();
        rlFinish.setOnClickListener(this);
        submitBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);
        switch (view.getId())
        {
            case R.id.rl_opinion_finish:
                finish();
                break;
            case R.id.submit_opinion:
                uploadImg();
                break;
        }
    }


    public ITestView mOpinionView  = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            ToastUtils.showLong("提交成功");
            finish();
        }

        @Override
        public void onError(Object object) {
            ToastUtils.showLong(object.toString()+"1834");
        }
    };
    public void uploadImg(){
        if(imgPaths.size()>0&&(imgPaths.get(0) != null && ! imgPaths.get(0).equals(""))){
            FileUploadUtil.getUploadImageToken(this,imgPaths.get(0),1003);
        }
    }
    public void submitInfo(){
        String opinionTitleTxt=opinionTitle.getText()+"";
        String opinionPhoneTxt=opinionPhone.getText()+"";
        String opinionImgUrls="";
        for(int i=0;i<imgUrls.size();i++){
            opinionImgUrls+=imgUrls.get(i);
        }
        userOpinionPresenter.onCreate();
        Log.e("UserOpinionPresenter",opinionTitleTxt+"");
        Log.e("UserOpinionPresenter",opinionPhoneTxt+"");
        Log.e("UserOpinionPresenter",opinionImgUrls+"");
        userOpinionPresenter.submitOpinion(opinionTitleTxt,opinionPhoneTxt,opinionImgUrls);
        userOpinionPresenter.attachView(mOpinionView);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == RESULT_CODE_OPINION) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                String path = images.get(0).path;
                if(imgPaths.size()>0){
                    imgPaths.add(imgPaths.get(imgPaths.size()-1));
                }
                for(int i = imgPaths.size()-1; i>0; i--) {
                    imgPaths.set(i,imgPaths.get(i-1));
                }
                if(imgPaths.size()>0){
                    imgPaths.set(0,path);
                }else{
                    imgPaths.add(path);
                }
                adapter.setData(imgPaths);
                SharedPreferences.Editor editor= BaseApplication.getUserSp().edit();
                editor.putString("opinionImg",path);
                editor.commit();
            }
        }
    }

    @Override
    public void notifyUpdate(NotifyObject obj) {
        imgUrls.add(obj.str);
        if(imgUrls.size()<imgPaths.size()){
            FileUploadUtil.getUploadImageToken(this,imgPaths.get(imgUrls.size()),1003);
        }else{
            submitInfo();
        }
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivImg;
        public ViewHolder(View view){
            super(view);
            ivImg = (ImageView)view;
        }
    }

    private class PicAdapter extends RecyclerView.Adapter<MyOpinionActivity.ViewHolder>{
        public List<String> imgPaths;
        public PicAdapter(List<String> imgPaths) {
            this.imgPaths = imgPaths;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
            View view = new ImageView(viewGroup.getContext());
            ViewHolder vh = new ViewHolder(view);
            return vh;
        }

        @Override
        public void onBindViewHolder(ViewHolder viewHolder, final int position) {
            viewHolder.itemView.setTag(position-1);
            viewHolder.ivImg.setPadding(10,10,10,10);
            if(position<=0){
                viewHolder.ivImg.setImageResource(R.drawable.upimg_default);
                viewHolder.ivImg.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(getContext(), ImageGridActivity.class);
                        startActivityForResult(intent, RESULT_CODE_OPINION);
                    }
                });
            }else{
                Picasso.with(context).load(new File(imgPaths.get(position-1))).into(viewHolder.ivImg);
                viewHolder.ivImg.setAdjustViewBounds(true);
                viewHolder.ivImg.setOnClickListener(new View.OnClickListener(){
                    @Override
                    public void onClick(View view) {
                        imgPaths.remove(position-1);
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            return imgPaths.size()+1;
        }
        public void setData(List<String> imgPaths){
            this.imgPaths = imgPaths;
            this.notifyDataSetChanged();
        }
    }

}
