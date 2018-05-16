package com.example.nao.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.RadioButton;
import android.widget.RadioGroup;


import com.example.nao.R;
import com.example.nao.adapter.SquarePageFragmentAdapter;
import com.example.nao.db.ChannelDb;
import com.example.nao.entity.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 爱冒险的小鸡仔 on 2018/5/9.
 */

public class FragmentSquare extends Fragment implements ViewPager.OnPageChangeListener {

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    private View view =null;
    private RadioGroup rgChannel = null;
    private ViewPager viewPager;
    private HorizontalScrollView hvChannel = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (view == null)
        {
            view = inflater.inflate(R.layout.fragment_square, null);
            rgChannel = (RadioGroup) view.findViewById(R.id.rgChannel);
            viewPager = (ViewPager)view.findViewById(R.id.vpNewsList);
            hvChannel = (HorizontalScrollView)view.findViewById(R.id.hvChannel);
            rgChannel.setOnCheckedChangeListener(
                    new RadioGroup.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(RadioGroup group,
                                                     int checkedId) {
                            viewPager.setCurrentItem(checkedId);
                        }
                    });
            initTab(inflater);
            initViewPager();
        }

        ViewGroup parent = (ViewGroup)view.getParent();
        if (parent != null)
        {
            parent.removeView(view);
        }

        return view;
    }

    private List<Fragment> squareChannelList = new ArrayList<Fragment>();
    private SquarePageFragmentAdapter adapter;
    private void initViewPager()
    {
        List<Channel> channelList = ChannelDb.getSelectedChannel();
        for (int i = 0; i<channelList.size(); i++)
        {
            SquareChannelFragment fragment = new SquareChannelFragment();
            Bundle bundle = new Bundle();
            bundle.putString("cname", channelList.get(i).getName());
            fragment.setArguments(bundle);
            squareChannelList.add(fragment);
        }

        adapter = new SquarePageFragmentAdapter(super.getActivity().getSupportFragmentManager(), squareChannelList);
        viewPager.setAdapter(adapter);
        viewPager.setOffscreenPageLimit(2);
        viewPager.setCurrentItem(0);
        viewPager.setOnPageChangeListener(this);

    }

    private void initTab(LayoutInflater inflater)
    {
        List<Channel> channelList = ChannelDb.getSelectedChannel();
        for (int i=0; i<channelList.size(); i++)
        {
            RadioButton rb = (RadioButton)inflater.inflate(R.layout.main_top_bar, null);
            rb.setId(i);
            rb.setText(channelList.get(i).getName());
            RadioGroup.LayoutParams params = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.WRAP_CONTENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            rgChannel.addView(rb, params);
        }
        rgChannel.check(0);
    }

    @Override
    public void setArguments(Bundle args)
    {
        super.setArguments(args);
    }

    @Override
    public void onPageScrollStateChanged(int arg0)
    {

    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2)
    {

    }

    @Override
    public void onPageSelected(int idx)
    {
        setTab(idx);
    }

    private void setTab(int idx)
    {
        RadioButton rb = (RadioButton)rgChannel.getChildAt(idx);
        rb.setChecked(true);
        int left = rb.getLeft();
        int width = rb.getMeasuredWidth();
        DisplayMetrics metrics = new DisplayMetrics();
        super.getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
        int screenWidth = metrics.widthPixels;
        int len = left+width/2 - screenWidth/2;
        hvChannel.smoothScrollTo(len,0);
    }

}
