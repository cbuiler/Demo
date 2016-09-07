package com.demo.dj.HuanXin.activitys;

import android.app.ActionBar;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.demo.dj.demo.R;
import com.demo.dj.HuanXin.adapters.PersonnelInfoAdapter;
import com.demo.dj.HuanXin.beans.PersonnelBean;
import com.demo.dj.HuanXin.views.PullRefreshListView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class MainActivity extends FragmentActivity implements PullRefreshListView.PullRefreshListener,
        AdapterView.OnItemClickListener, View.OnClickListener, View.OnLayoutChangeListener {
    private PullRefreshListView mList;
    private EditText mSearchEdit;
    private RelativeLayout mSearchLayout;
    private Button mSearchButton;

    private List<PersonnelBean> mPersonnelBeanList;
    private PersonnelInfoAdapter mAdapter;
    private DrawerLayout mDrawerLayout;

    //屏幕高度
    private int screenHeight = 0;
    //软件盘弹起后所占高度阀值
    private int keyHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        initView();
        initActionBar();
        getData();
        showData();
    }

    private void initActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.layout_actionbar_main);//自定义ActionBar布局
        actionBar.getCustomView().findViewById(R.id.title_main_menu).setOnClickListener(this);
        actionBar.getCustomView().findViewById(R.id.title_main_search).setOnClickListener(this);
    }

    private void changeLeftMenu() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        } else {
            mDrawerLayout.openDrawer(Gravity.LEFT);
        }
    }

    private void closeLeftMenu() {
        if (mDrawerLayout.isDrawerOpen(Gravity.LEFT)) {
            mDrawerLayout.closeDrawer(Gravity.LEFT);
        }
    }

    private void initView() {
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED, Gravity.LEFT);
        mDrawerLayout.addOnLayoutChangeListener(this);

        mSearchLayout = (RelativeLayout) findViewById(R.id.main_search_layout);
        mSearchButton = (Button) findViewById(R.id.main_search_button);
        mSearchButton.setOnClickListener(this);
        mSearchEdit = (EditText) findViewById(R.id.main_search_edit);
        mSearchEdit.setFocusable(true);
        mSearchEdit.setFocusableInTouchMode(true);

        mList = (PullRefreshListView) findViewById(R.id.pull_to_refresh_list);
        mList.setPullRefreshListener(this);
        mList.setCanRefresh(true);
        mList.setCanLoadMore(false);

        mAdapter = new PersonnelInfoAdapter(this);
        mList.setAdapter(mAdapter);
        mList.setOnItemClickListener(this);

        //获取屏幕高度
        screenHeight = this.getWindowManager().getDefaultDisplay().getHeight();
        //阀值设置为屏幕高度的1/3
        keyHeight = screenHeight / 4;
    }

    /**
     * 展示数据
     */
    private void showData() {
        if (mAdapter == null) {
            return;
        }

        if(mPersonnelBeanListSearch != null && mPersonnelBeanListSearch.size() > 0){
            mAdapter.setData(mPersonnelBeanListSearch);
        }else{
            mAdapter.setData(mPersonnelBeanList);
        }

        mAdapter.notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.title_main_menu:
                changeLeftMenu();
                break;
            case R.id.main_search_button:
                searchTextWithData();
                changeKeyBoard(false);
                break;
            case R.id.title_main_search:
                closeLeftMenu();
                mSearchEdit.requestFocus();
                changeKeyBoard(true);
                break;
        }
    }

    private List<PersonnelBean> mPersonnelBeanListSearch;
    private void searchTextWithData(){
        if(mPersonnelBeanListSearch == null){
            mPersonnelBeanListSearch = new ArrayList<>();
        }
        mPersonnelBeanListSearch.clear();

        String str = mSearchEdit.getText().toString();
        if(!TextUtils.isEmpty(str)){
            for(PersonnelBean personnelBean : mPersonnelBeanList){
                String name = personnelBean.getName();
                if(name.indexOf(str) >= 0){
                    mPersonnelBeanListSearch.add(personnelBean);
                }
            }
        }

        showData();
    }


    private void changeKeyBoard(boolean isNeedShow) {
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);

        if (isNeedShow) {
            imm.showSoftInput(mSearchEdit, InputMethodManager.SHOW_FORCED);
        } else {
            imm.hideSoftInputFromWindow(mSearchEdit.getWindowToken(), 0);
        }
    }

    /**
     * TODO:这里点击有个问题，如果是搜索结果列表点击，那么进入详情是只显示搜索出的几个还是都可以滑动出来？？
     * @param parent
     * @param view
     * @param position
     * @param id
     */
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        PersonnelBean personnelBean = mAdapter.getData().get(position - 1);

        Intent intent = new Intent(this, PersonnelDetailActivity.class);
        intent.putExtra("data", (Serializable) mPersonnelBeanList);
        intent.putExtra("position", mPersonnelBeanList.indexOf(personnelBean));
        startActivity(intent);
    }

    /**
     * 获取数据，暂时使用假数据
     */
    private void getData() {
        if (mPersonnelBeanList == null) {
            mPersonnelBeanList = new ArrayList<>();
        }

        mPersonnelBeanList.clear();

        for (int i = 0; i < 20; i++) {
            PersonnelBean personnelBean = new PersonnelBean();
            personnelBean.setName("name " + i);
            personnelBean.setPrice(i * 200);
            personnelBean.setUsed(i * 100);
            personnelBean.setRemain(i * 10);
            personnelBean.setAddress("address " + i);
            personnelBean.setId(i);
            mPersonnelBeanList.add(personnelBean);
        }

        mList.onRefreshComplete(new Date());
    }

    @Override
    public void onRefresh() {
        getData();
    }

    @Override
    public void onLoadMore() {

    }

    @Override
    public void onCannotLoadMore() {

    }

    @Override
    public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
        if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
            Toast.makeText(MainActivity.this, "监听到软键盘弹起...", Toast.LENGTH_SHORT).show();
            mSearchEdit.setText("");
            mSearchLayout.setVisibility(View.VISIBLE);
        } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {

            Toast.makeText(MainActivity.this, "监听到软件盘关闭...", Toast.LENGTH_SHORT).show();
            mSearchEdit.setText("");
            mSearchLayout.setVisibility(View.GONE);
        }
    }
}
