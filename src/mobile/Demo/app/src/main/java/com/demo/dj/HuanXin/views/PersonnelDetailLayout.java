package com.demo.dj.HuanXin.views;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.demo.dj.HuanXin.beans.PersonnelBean;
import com.demo.dj.demo.R;

/**
 * Created by Administrator on 2016/8/4.
 */
public class PersonnelDetailLayout extends FrameLayout implements View.OnClickListener{
    private PersonnelBean mPersonnelBean;
    private Context mContext;
    private CircleImageView mPic;
    private TextView mName, mAddress, mUsed, mRemain, mPrice;
    private Button mGoFirst;
    private ImageView mArrow_left, mArrow_right;

    private int mPosition;//当前view所在viewpager的位置
    private int mListCount;//viewpager的总大小

    public PersonnelDetailLayout(Context context, PersonnelBean personnelBean, int position, int listCount) {
        super(context);
        this.mContext = context;
        this.mPersonnelBean = personnelBean;
        this.mPosition = position;
        this.mListCount = listCount;
        initView();
        showData();
    }

    private void initView(){
        View view =  LayoutInflater.from(mContext).inflate(R.layout.personnel_detail_item, null);
        mPic = (CircleImageView) view.findViewById(R.id.personnel_detail_pic) ;
        mName = (TextView) view.findViewById(R.id.personnel_detail_name);
        mAddress = (TextView) view.findViewById(R.id.personnel_detail_address);
        mUsed = (TextView) view.findViewById(R.id.personnel_detail_used);
        mRemain = (TextView) view.findViewById(R.id.personnel_detail_remain);
        mPrice = (TextView) view.findViewById(R.id.personnel_detail_price);
        mArrow_left = (ImageView) view.findViewById(R.id.personnel_detail_arrow_left);
        mArrow_right = (ImageView) view.findViewById(R.id.personnel_detail_arrow_right);

        mGoFirst = (Button) view.findViewById(R.id.personnel_detail_go_first);
        mGoFirst.setOnClickListener(this);

        addView(view);
    }

    private void showData(){
        getPic(mPic, mPersonnelBean.getPicUrl());
        mName.setText(mPersonnelBean.getName());
        mAddress.setText(mPersonnelBean.getAddress());
        mUsed.setText(String.valueOf(mPersonnelBean.getUsed()));
        mRemain.setText(String.valueOf(mPersonnelBean.getRemain()));
        mPrice.setText(String.valueOf(mPersonnelBean.getPrice()));

        if(mPosition == 0){
            mArrow_left.setVisibility(View.INVISIBLE);
            mGoFirst.setVisibility(View.INVISIBLE);
        }else if(mPosition == mListCount - 1){
            mArrow_right.setVisibility(View.INVISIBLE);
        }
    }

    private void getPic(CircleImageView view, String url){
        //TODO:是否需要网络下载？
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
    }

    @Override
    public void onClick(View v) {
        if(mOnClickGoFirstListener != null){
            mOnClickGoFirstListener.OnClickGoFirst();
        }
    }

    private OnClickGoFirstListener mOnClickGoFirstListener;

    public void setOnClickGoFirstListener(OnClickGoFirstListener onClickGoFirstListener){
        this.mOnClickGoFirstListener = onClickGoFirstListener;
    }

    public interface OnClickGoFirstListener{
        void OnClickGoFirst();
    }
}
