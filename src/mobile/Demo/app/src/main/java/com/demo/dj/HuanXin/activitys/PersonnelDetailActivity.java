package com.demo.dj.HuanXin.activitys;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.demo.dj.demo.R;
import com.demo.dj.HuanXin.adapters.PersonnelDetailAdapter;
import com.demo.dj.HuanXin.beans.PersonnelBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/8/3.
 */
public class PersonnelDetailActivity extends FragmentActivity implements PersonnelDetailAdapter.OnClickGoFirstListenerWithAdapter,
        View.OnClickListener{

    private ViewPager mViewPager;
    private PersonnelDetailAdapter mAdapter;
    private List<PersonnelBean> mPersonnelBeanList;
    private int mPosition;
//    private TextView mBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personnel_detail);

        initActionBar();
        getData();
        initView();
        showData();
    }

    private void initActionBar(){
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.layout_actionbar_detail);//自定义ActionBar布局
        actionBar.getCustomView().findViewById(R.id.personnel_detail_title_back).setOnClickListener(this);
    }

    private void getData(){
        if(mPersonnelBeanList == null) {
            mPersonnelBeanList = new ArrayList<>();
        }

        mPersonnelBeanList.clear();

        Intent intent = getIntent();
        mPersonnelBeanList = (List<PersonnelBean>) intent.getSerializableExtra("data");
        mPosition = intent.getIntExtra("position", -1);
    }

    private void initView(){
        mViewPager = (ViewPager)findViewById(R.id.personnel_detail_viewPager);
        mAdapter = new PersonnelDetailAdapter(this, mPersonnelBeanList);
        mAdapter.setOnClickGoFirstListenerWithAdapter(this);
    }

    private void showData(){
        mViewPager.setAdapter(mAdapter);
        if(mPosition >= 0){
            mViewPager.setCurrentItem(mPosition);
        }
    }

    @Override
    public void OnClickGoFirstWithAdapter() {
        mViewPager.setCurrentItem(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personnel_detail_title_back:
                finish();
                break;
            default:
                break;
        }
    }
}
