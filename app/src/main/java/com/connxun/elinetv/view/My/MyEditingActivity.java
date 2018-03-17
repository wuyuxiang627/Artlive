package com.connxun.elinetv.view.My;


import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bigkoo.pickerview.OptionsPickerView;
import com.bigkoo.pickerview.TimePickerView;
import com.bumptech.glide.Glide;
import com.connxun.elinetv.R;
import com.connxun.elinetv.app.BaseApplication;
import com.connxun.elinetv.base.Dialog.ActionSheetDialog;
import com.connxun.elinetv.base.ui.BaseActivity;
import com.connxun.elinetv.base.ui.CustomRoundView;
import com.connxun.elinetv.entity.JsonBean;
import com.connxun.elinetv.entity.JsonEntity;
import com.connxun.elinetv.entity.user.UserInfoEntity;
import com.connxun.elinetv.observer.NotifyObject;
import com.connxun.elinetv.presenter.user.UserinfoPresenter;
import com.connxun.elinetv.util.AddressPickTask;
import com.connxun.elinetv.util.DialogUtil;
import com.connxun.elinetv.util.FileUploadUtil;
import com.connxun.elinetv.util.ToastUtils;
import com.connxun.elinetv.util.UserInfoUtil;
import com.connxun.elinetv.view.user.ITestView;
import com.google.gson.Gson;
import com.lzy.imagepicker.ImagePicker;
import com.lzy.imagepicker.bean.ImageItem;
import com.lzy.imagepicker.ui.ImageGridActivity;

import org.json.JSONArray;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cn.qqtheme.framework.entity.City;
import cn.qqtheme.framework.entity.County;
import cn.qqtheme.framework.entity.Province;

@ContentView(R.layout.activity_my_editing)
public class MyEditingActivity extends BaseActivity implements View.OnClickListener {
    private static final int REUST_CODE_USER_PHOTO_COVER = 1008;
    private ArrayList<JsonBean> options1Items = new ArrayList<>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<>();

    private TimePickerView pvTime;
    @ViewInject(R.id.rl_my_editor_return)
    RelativeLayout rlFinish;

    @ViewInject(R.id.rl_editor_birthday)
    RelativeLayout rlBirthday;
    @ViewInject(R.id.rl_editor_setting)

    RelativeLayout rlUserPhoto;
    @ViewInject(R.id.tv_editing_birthday)
     TextView tvBirthday;
    @ViewInject(R.id.rl_editor_hometown)
    RelativeLayout rlCity;
    @ViewInject(R.id.tv_editing_city)
     TextView tvCity;
    @ViewInject(R.id.iv_item_edit_cover)
    CustomRoundView ivCover;

    @ViewInject(R.id.tv_editing_nick_name)
    EditText tvNickName;
    @ViewInject(R.id.rl_editor_sex)
    RelativeLayout rlSex;
    @ViewInject(R.id.tv_editing_sex)
    TextView tvSex;
    @ViewInject(R.id.tv_personal)
    EditText tvPersonal;
    @ViewInject(R.id.tv_live_number)
    TextView tvLiveNumber;
    @ViewInject(R.id.tv_editing_userNo)
    TextView tvUserNo;


    private UserInfoUtil userInfoUtil;
    private String path;
    private UserInfoEntity userInfoEntity;
    private ActionSheetDialog mDialog;
    private UserinfoPresenter userinfoPresenter = new UserinfoPresenter(this);


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        userInfoEntity = (UserInfoEntity) getIntent().getSerializableExtra("userInfo");

        if(userInfoEntity != null){
            setUserInformation(userInfoEntity);
        }


        initJsonData();
    }



    public  void  setUserInformation(UserInfoEntity  userInfoEntity){
        tvNickName.setText(userInfoEntity.getNickName() != null ? userInfoEntity.getNickName():"未知");
        tvBirthday.setText(userInfoEntity.getBirthday() != null ? userInfoEntity.getBirthday():"2018-03-13");
        tvSex.setText(userInfoEntity.getSex()== 1? "男":"女");
        tvCity.setText(userInfoEntity.getCity() != null ? userInfoEntity.getCity():"北京市");
        tvPersonal.setText(userInfoEntity.getSignature() != null ? userInfoEntity.getSignature():"");
        tvLiveNumber.setText(userInfoEntity.getLiveId()+"" != null ? userInfoEntity.getLiveId()+"":"100000");
        tvUserNo.setText(userInfoEntity.getId()+"" != null ? userInfoEntity.getId()+"":"0000");

        String cover = userInfoEntity.getAvatar();
        if(cover != null){
            Glide.with(this).load(cover).into(ivCover);
        }

    }


    @Override
    public void setOnClick() {
        super.setOnClick();
        rlBirthday.setOnClickListener(this);
        rlCity.setOnClickListener(this);
        rlUserPhoto.setOnClickListener(this);
        rlSex.setOnClickListener(this);
        rlFinish.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        super.onClick(view);

        switch (view.getId()) {
            case R.id.rl_my_editor_return: //返回

                if(GetData()){
                    setDialog(view);
                    return;
                }
                finish();
                break;
            case R.id.rl_editor_birthday: //生日
                SelectDate();
                break;
            case R.id.rl_editor_hometown:
//                getAddress();

                AddressPickTask task = new AddressPickTask(this);
                task.setHideProvince(false);
                task.setHideCounty(false);
                task.setCallback(new AddressPickTask.Callback() {
                    @Override
                    public void onAddressInitFailed() {
                        ToastUtils.showShort("数据初始化失败");
                    }

                    @Override
                    public void onAddressPicked(Province province, City city, County county) {
                        if (county == null) {
                            ToastUtils.showShort(province.getAreaName() + city.getAreaName());
                            tvCity.setText(province.getAreaName() + city.getAreaName());
                        } else {
                            ToastUtils.showShort(province.getAreaName() + city.getAreaName() + county.getAreaName());
                            tvCity.setText(province.getAreaName() + city.getAreaName() + county.getAreaName());

//                            pName = province.getAreaName();
//                            cName = city.getAreaName();
//                            couName = county.getAreaName();
                        }
                    }
                });
//                task.execute("贵州", "毕节", "纳雍");
                task.execute("北京市", "北京市", "东城区");
                break;
            case R.id.rl_editor_setting:
                Intent intent = new Intent(this, ImageGridActivity.class);
                startActivityForResult(intent, REUST_CODE_USER_PHOTO_COVER);
                break;
            case R.id.rl_editor_sex:
                setSex();
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        List<Uri> mSelected = new ArrayList<>();
        if (resultCode == ImagePicker.RESULT_CODE_ITEMS) {
            if (data != null && requestCode == REUST_CODE_USER_PHOTO_COVER) {
                ArrayList<ImageItem> images = (ArrayList<ImageItem>) data.getSerializableExtra(ImagePicker.EXTRA_RESULT_ITEMS);
                path = images.get(0).path;

                Glide.with(this).load(path).into(ivCover);
            }
        }

    }


    int type = 0;

    public boolean GetData(){

        //头像
        if(path != null){
            type =1;
        }
        if(!tvNickName.getText().toString().equals(userInfoEntity.getNickName())) {
            type = 1;
        }
        if(!tvBirthday.getText().toString().equals(userInfoEntity.getBirthday())){
            type =1;
        }
        if(!tvSex.getText().toString().equals(userInfoEntity.getSex()== 1? "男":"女")){
            type =1;
        }
        if(!tvCity.getText().toString().equals(userInfoEntity.getCity())){
            type =1;
        }
        if(userInfoEntity.getSignature() == null){
           if(!tvPersonal.getText().toString().equals("")){
               type =1;
           }
        }else {
            if(!tvPersonal.getText().toString().equals(userInfoEntity.getSignature())){
                type =1;
            }
        }



        if(type == 0){
            return false;
        }
        return true;

    }

    /**
     * 返回提示框
     * @param view
     */
    private void setDialog(View view) {

        final View contentView = LayoutInflater.from(getContext()).inflate(
                R.layout.item_go_set, null);

        final PopupWindow popupWindow = new PopupWindow(contentView,
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT, true);

        Button go_set = (Button) contentView.findViewById(R.id.go_set);
        Button out_close = (Button) contentView.findViewById(R.id.out_close);
        TextView tv_out_text = (TextView) contentView.findViewById(R.id.tv_out_text);

        tv_out_text.setText("是否保存资料的修改");
        go_set.setText("保存");

        //保存资料
        go_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();

                if(path != null){
                    DialogUtil.startWaitingDialog(getContext(),"上传资料中");
                    FileUploadUtil.getUploadImageToken(getContext(),path,REUST_CODE_USER_PHOTO_COVER);
                }else {
                    setUserUpdateInfo();
                }




            }
        });

        //关闭
        out_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    }

    @Override
    public void notifyUpdate(NotifyObject obj) {
        super.notifyUpdate(obj);
        switch (obj.what)
        {
            case 1:
                String nickName = tvNickName.getText().toString();
                String avatar = obj.str;
                String city = tvCity.getText().toString();
                String birthday = tvBirthday.getText().toString();
                String sex  = tvSex.getText().toString();

                userinfoPresenter.onCreate();
            userinfoPresenter.getAttentionDataList(
                    BaseApplication.getUserNo(),tvPersonal.getText().toString()
                    ,nickName,avatar,city,birthday,sex.equals("男")?1:2);
            userinfoPresenter.attachView(UpDateInfo);
            break;
        }

    }


    public void setUserUpdateInfo(){
        String nickName = tvNickName.getText().toString();
        String avatar = "0";
        String city = tvCity.getText().toString();
        String birthday = tvBirthday.getText().toString();
        String sex  = tvSex.getText().toString();

        userinfoPresenter.onCreate();
        userinfoPresenter.getAttentionDataList(
                BaseApplication.getUserNo(),tvPersonal.getText().toString()
                ,nickName,avatar,city,birthday,sex.equals("男")?1:2);
        userinfoPresenter.attachView(UpDateInfo);
    }


    private ITestView UpDateInfo = new ITestView() {
        @Override
        public void onSuccess(Object object) {
            DialogUtil.dismissWaitingDialog();
            JsonEntity jsonEntity = (JsonEntity) object;
            ToastUtils.showLong(jsonEntity.getMsg());
            finish();
        }

        @Override
        public void onError(Object object) {

        }
    };


    //设置性别
    public void setSex(){
        mDialog = new ActionSheetDialog(this).builder();
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(true);
//        mDialog.setTitle("是否退出并更换账号");
        mDialog.addSheetItem("男", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {

            @Override
            public void onClick(int which) {
                tvSex.setText("男");
            }
        });
        mDialog.addSheetItem("女", ActionSheetDialog.SheetItemColor.Red, new ActionSheetDialog.OnSheetItemClickListener() {

            @Override
            public void onClick(int which) {
                tvSex.setText("女");
            }
        }).show();
    }


    public static void setBirthday(String birthday){
    }

    public static void setAddress(String address){
    }


    //生日
    public  void SelectDate(){

        Calendar now = Calendar.getInstance();
        int year = now.get(Calendar.YEAR);
        int month = now.get(Calendar.MONTH);
        int day = now.get(Calendar.DAY_OF_MONTH);
        //控制时间范围(如果不设置范围，则使用默认时间1900-2100年，此段代码可注释)
        //因为系统Calendar的月份是从0-11的,所以如果是调用Calendar的set方法来设置时间,月份的范围也要是从0-11
        Calendar selectedDate = Calendar.getInstance();
        Calendar startDate = Calendar.getInstance();
        startDate.set(1951, 0, 1);
        Calendar endDate = Calendar.getInstance();
        endDate.set(year, month, day);
        //时间选择器--暂时注释保证gradle正常运行
        pvTime = new TimePickerView.Builder(this, new TimePickerView.OnTimeSelectListener() {
            @Override
            public void onTimeSelect(Date date, View v) {//选中事件回调
                // 这里回调过来的v,就是show()方法里面所添加的 View 参数，如果show的时候没有添加参数，v则为null
                /*btn_Time.setText(getTime(date));*/
                tvBirthday.setText(getTime(date));
//                MyEditingActivity.setBirthday(getTime(date));

            }
        })
                //年月日时分秒 的显示与否，不设置则默认全部显示
                .setType(new boolean[]{true, true, true, false, false, false})
                .setLabel("", "", "", "", "", "")
                .isCenterLabel(false)
                .setDividerColor(Color.BLACK)
                .setContentSize(18)
                .setDate(selectedDate)
                .setRangDate(startDate, endDate)
                .setBackgroundId(0x00FFFFFF) //设置外部遮罩颜色
                .setDecorView(null)
                .build();
        pvTime.show();
    }
    private String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取地址
     */
    public void getAddress( ) {
        OptionsPickerView pvOptions = new OptionsPickerView.Builder(this, new OptionsPickerView.OnOptionsSelectListener() {
            @Override
            public void onOptionsSelect(int options1, int options2, int options3, View v) {
                //返回的分别是三个级别的选中位置
                String tx = options1Items.get(options1).getPickerViewText()+
                        options2Items.get(options1).get(options2)+
                        options3Items.get(options1).get(options2).get(options3);
                if(options1Items.get(options1).getPickerViewText().equals(options2Items.get(options1).get(options2))){
                    tx = options1Items.get(options1).getPickerViewText()+ " - " +
                            options3Items.get(options1).get(options2).get(options3);
                }else {
                    tx = options1Items.get(options1).getPickerViewText()+ " - " +
                            options2Items.get(options1).get(options2);
                }
                tvCity.setText(tx);
            }
        })
                .setDividerColor(Color.BLACK)
                .setTextColorCenter(Color.BLACK) //设置选中项文字颜色
                .setContentTextSize(20)
                .build();

//        /*pvOptions.setPicker(options1Items);//一级选择器
//        pvOptions.setPicker(options1Items, options2Items);//二级选择器*/
        pvOptions.setPicker(options1Items, options2Items,options3Items);//三级选择器
        pvOptions.show();
    }

    private void initJsonData() {//解析数据

        /**
         * 注意：assets 目录下的Json文件仅供参考，实际使用可自行替换文件
         * 关键逻辑在于循环体
         *
         * */
        String JsonData = new GetJsonDataUtil().getJson(this,"province.json");//获取assets目录下的json文件数据

        ArrayList<JsonBean> jsonBean = parseData(JsonData);//用Gson 转成实体

        /**
         * 添加省份数据
         *
         * 注意：如果是添加的JavaBean实体，则实体类需要实现 IPickerViewData 接口，
         * PickerView会通过getPickerViewText方法获取字符串显示出来。
         */
        options1Items = jsonBean;

        for (int i=0;i<jsonBean.size();i++){//遍历省份
            ArrayList<String> CityList = new ArrayList<>();//该省的城市列表（第二级）
            ArrayList<ArrayList<String>> Province_AreaList = new ArrayList<>();//该省的所有地区列表（第三极）

            for (int c=0; c<jsonBean.get(i).getCityList().size(); c++){//遍历该省份的所有城市
                String CityName = jsonBean.get(i).getCityList().get(c).getName();
                CityList.add(CityName);//添加城市

                ArrayList<String> City_AreaList = new ArrayList<>();//该城市的所有地区列表

                //如果无地区数据，建议添加空字符串，防止数据为null 导致三个选项长度不匹配造成崩溃
                if (jsonBean.get(i).getCityList().get(c).getArea() == null
                        ||jsonBean.get(i).getCityList().get(c).getArea().size()==0) {
                    City_AreaList.add("");
                }else {

                    for (int d=0; d < jsonBean.get(i).getCityList().get(c).getArea().size(); d++) {//该城市对应地区所有数据
                        String AreaName = jsonBean.get(i).getCityList().get(c).getArea().get(d);

                        City_AreaList.add(AreaName);//添加该城市所有地区数据
                    }
                }
                Province_AreaList.add(City_AreaList);//添加该省所有地区数据
            }

            /**
             * 添加城市数据
             */
            options2Items.add(CityList);

            /**
             * 添加地区数据
             */
            options3Items.add(Province_AreaList);
        }

//        mHandler.sendEmptyMessage(MSG_LOAD_SUCCESS);

    }

    public ArrayList<JsonBean> parseData(String result) {//Gson 解析
        ArrayList<JsonBean> detail = new ArrayList<>();
        try {
            JSONArray data = new JSONArray(result);
            Gson gson = new Gson();
            for (int i = 0; i < data.length(); i++) {
                JsonBean entity = gson.fromJson(data.optJSONObject(i).toString(), JsonBean.class);
                detail.add(entity);
            }
        } catch (Exception e) {
            e.printStackTrace();
//            mHandler.sendEmptyMessage(MSG_LOAD_FAILED);
        }
        return detail;
    }


    private class GetJsonDataUtil {
        public String getJson(Context context, String fileName) {

            StringBuilder stringBuilder = new StringBuilder();
            try {
                AssetManager assetManager = context.getAssets();
                BufferedReader bf = new BufferedReader(new InputStreamReader(
                        assetManager.open(fileName)));
                String line;
                while ((line = bf.readLine()) != null) {
                    stringBuilder.append(line);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            return stringBuilder.toString();
        }
    }


}
