package com.demo.dj.HuanXin.adapters;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.demo.dj.HuanXin.beans.PersonnelBean;
import com.demo.dj.HuanXin.views.PersonnelDetailLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class PersonnelDetailAdapter extends PagerAdapter implements PersonnelDetailLayout.OnClickGoFirstListener{

    private Context mContext;
    private List<View> mListViews;
    private List<PersonnelBean> mPersonnelBeanList;

    public PersonnelDetailAdapter(Context context, List<PersonnelBean> personnelBeanList){
        this.mContext = context;
        this.mPersonnelBeanList = personnelBeanList;
        mListViews = new ArrayList<>();
        addItemView();
    }

    public void addItemView(){
        for (PersonnelBean personnelBean : mPersonnelBeanList) {
            PersonnelDetailLayout view = new PersonnelDetailLayout(mContext, personnelBean,
                    mPersonnelBeanList.indexOf(personnelBean), mPersonnelBeanList.size());
            view.setOnClickGoFirstListener(this);
            mListViews.add(view);
        }

        for(int i = 0; i < mPersonnelBeanList.size(); i++){

        }
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object)   {
        container.removeView(mListViews.get(position));//删除页卡
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {  //这个方法用来实例化页卡
        container.addView(mListViews.get(position), 0);//添加页卡
        return mListViews.get(position);
    }

    @Override
    public int getCount() {
        return mListViews.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void OnClickGoFirst() {
        if(mOnClickGoFirstListenerWithAdapter != null){
            mOnClickGoFirstListenerWithAdapter.OnClickGoFirstWithAdapter();
        }
    }

    private OnClickGoFirstListenerWithAdapter mOnClickGoFirstListenerWithAdapter;

    public void setOnClickGoFirstListenerWithAdapter(OnClickGoFirstListenerWithAdapter onClickGoFirstListenerWithAdapter){
        this.mOnClickGoFirstListenerWithAdapter = onClickGoFirstListenerWithAdapter;
    }

    public interface OnClickGoFirstListenerWithAdapter{
        void OnClickGoFirstWithAdapter();
    }
}
