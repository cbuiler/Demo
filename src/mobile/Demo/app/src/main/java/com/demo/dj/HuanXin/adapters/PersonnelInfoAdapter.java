package com.demo.dj.HuanXin.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.demo.dj.HuanXin.beans.PersonnelBean;
import com.demo.dj.demo.R;
import com.demo.dj.HuanXin.views.CircleImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class PersonnelInfoAdapter extends BaseAdapter {
    private Context mContext;
    //	private List<HomeDataCol_shop> mDataList;
    private List<PersonnelBean> mDataList;
    LayoutInflater mInflater;

    public PersonnelInfoAdapter(Context context) {
        this.mContext = context;
        mDataList = new ArrayList<PersonnelBean>();
        mInflater = LayoutInflater.from(context);
    }

    public PersonnelInfoAdapter(Context context, List<PersonnelBean> list) {
        this.mContext = context;
        mDataList = new ArrayList<PersonnelBean>();
        mInflater = LayoutInflater.from(context);
        this.mDataList = list;
    }

    public void setData(List<PersonnelBean> list){
        this.mDataList = list;
    }

    public List<PersonnelBean> getData() {
        return mDataList;
    }

    @Override
    public int getCount() {
        return mDataList.size();
    }

    @Override
    public PersonnelBean getItem(int position) {
        return mDataList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final PersonnelBean item = getItem(position);
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.personnel_list_item, parent, false);
            holder.pic = (CircleImageView) convertView.findViewById(R.id.personnel_list_item_pic);
            holder.name = (TextView) convertView.findViewById(R.id.personnel_list_item_name);
            holder.used = (TextView) convertView.findViewById(R.id.personnel_list_item_used);
            holder.address = (TextView) convertView.findViewById(R.id.personnel_list_item_address);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText(item.getName());
        holder.used.setText(String.valueOf(item.getUsed()));
        holder.address.setText(item.getAddress());
        getPic(holder.pic, item.getPicUrl());

        return convertView;
    }

    public final class ViewHolder {
        private CircleImageView pic;
        private TextView name;
        private TextView used;
        private TextView address;
    }

    /**
     * 下载图片
     * @param view
     * @param url
     */
    private void getPic(CircleImageView view, String url){
        //TODO:是否需要网络下载？
    }
}
