package com.aserbao.aserbaosandroid.ui.canvas.shadows;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;

import com.aserbao.aserbaosandroid.AUtils.utils.screen.DisplayUtil;
import com.aserbao.aserbaosandroid.R;
import com.aserbao.aserbaosandroid.base.BaseRecyclerViewActivity;
import com.aserbao.aserbaosandroid.base.beans.BaseRecyclerBean;
import com.aserbao.aserbaosandroid.ui.canvas.shadows.views.ShadowTextView;

public class ShadowsActivity extends BaseRecyclerViewActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void initGetData() {
        mBaseRecyclerBeen.add(new BaseRecyclerBean("带阴影的TextView",1));
        mBaseRecyclerBeen.add(new BaseRecyclerBean("带阴影的Fragment",2));
        mBaseRecyclerEmptyContainer.setVisibility(View.GONE);
    }

    @Override
    public void itemClickBack(View view, int position) {
        if (mBaseRecyclerEmptyContainer.getVisibility() == View.VISIBLE){
            mBaseRecyclerEmptyContainer.setVisibility(View.GONE);
            return;
        }
        mBaseRecyclerEmptyContainer.removeAllViews();
        mBaseRecyclerEmptyContainer.setVisibility(View.VISIBLE);
        mBaseRecyclerEmptyContainer.setBackgroundColor(Color.WHITE);
        switch (position){
            case 1:
                addShadowTextView();
                break;
            case 2:
                addShadowFragment();
                break;
        }
    }

    public void addShadowTextView(){
        ShadowTextView shadowTextView = new ShadowTextView(this);
        shadowTextView.setWidth(DisplayUtil.dip2px(50));
        shadowTextView.setHeight(DisplayUtil.dip2px(50));
        shadowTextView.setText("shadowTextView");
        shadowTextView.setGravity(Gravity.CENTER);
        shadowTextView.setTextColor(Color.BLACK);
        shadowTextView.setTextSize(30);
        mBaseRecyclerEmptyContainer.addView(shadowTextView);
    }

    public void addShadowFragment(){
        View inflate = LayoutInflater.from(this).inflate(R.layout.shadow_fragment_ll, null);
        mBaseRecyclerEmptyContainer.addView(inflate);
    }
}