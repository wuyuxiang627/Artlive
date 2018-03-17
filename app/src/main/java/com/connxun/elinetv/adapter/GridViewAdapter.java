package com.connxun.elinetv.adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.connxun.elinetv.R;
import com.connxun.elinetv.entity.Gift;
import com.facebook.drawee.view.SimpleDraweeView;

import java.util.List;

/**
 * Created by ZXM on 2016/9/12.
 */
public class GridViewAdapter extends BaseAdapter {
    private List<Model> mDatas;
    private List<Gift> mGiftDatas;
    private LayoutInflater inflater;
    private Context mContext;
    /**
     * 页数下标,从0开始(当前是第几页)
     */
    private int curIndex;
    /**
     * 每一页显示的个数
     */
    private int pageSize = 8;

    public GridViewAdapter(Context context, List<Gift> mGiftDatas, int curIndex) {
        inflater = LayoutInflater.from(context);
        this.mGiftDatas = mGiftDatas;
        this.curIndex = curIndex;
        this.mContext = context;
    }

    /**
     * 先判断数据集的大小是否足够显示满本页？mGiftDatas.size() > (curIndex+1)*pageSize,
     * 如果够，则直接返回每一页显示的最大条目个数pageSize,
     * 如果不够，则有几项返回几,(mGiftDatas.size() - curIndex * pageSize);(也就是最后一页的时候就显示剩余item)
     */
    @Override
    public int getCount() {
        return mGiftDatas.size() > (curIndex + 1) * pageSize ? pageSize : (mGiftDatas.size() - curIndex * pageSize);
    }

    @Override
    public Gift getItem(int position) {
        return mGiftDatas.get(position + curIndex * pageSize);
    }

    @Override
    public long getItemId(int position) {
        return position + curIndex * pageSize;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_gridview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tvGiftMoney = (TextView) convertView.findViewById(R.id.tv_gift_money);
            viewHolder.tvGiftName = (TextView) convertView.findViewById(R.id.tv_gift_name);
            viewHolder.ivGiftImg = (SimpleDraweeView) convertView.findViewById(R.id.iv_gift_img);
            viewHolder.item_layout = (RelativeLayout) convertView.findViewById(R.id.item_layout);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        /**
         * 在给View绑定显示的数据时，计算正确的position = position + curIndex * pageSize，
         */
        Gift model = getItem(position);
        viewHolder.tvGiftMoney.setText(model.getGiftPrice()+"金币");
        //创建将要下载的图片的URI
        Uri imageUri = Uri.parse(model.getGiftPic());
        //开始下载
        viewHolder.ivGiftImg.setImageURI(imageUri);
        viewHolder.tvGiftName.setText(model.getGiftName());
//        viewHolder.ivGiftImg.setImageResource(model.getGiftPic());
        if (model.isSelected()){//被选中
            viewHolder.item_layout.setBackgroundDrawable(mContext.getResources().getDrawable(R.drawable.item_selector));
        }else {
            viewHolder.item_layout.setBackgroundDrawable(null);
        }
        return convertView;
    }


    class ViewHolder {
        public RelativeLayout item_layout;
        public TextView tvGiftName;
        public TextView tvGiftMoney;
        public SimpleDraweeView ivGiftImg;
    }

}