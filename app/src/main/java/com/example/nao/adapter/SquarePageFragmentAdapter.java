package com.example.nao.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by 爱冒险的小鸡仔 on 2018/5/16.
 */

public class SquarePageFragmentAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragmentList;
    private FragmentManager fm;
    public SquarePageFragmentAdapter(FragmentManager fm, List<Fragment> fragmentList){
        super(fm);
        this.fragmentList = fragmentList;
        this.fm = fm;
    }

    @Override
    public Fragment getItem(int idx){
        return fragmentList.get(idx%fragmentList.size());
    }

    @Override
    public int getCount(){
        return  fragmentList.size();
    }

    @Override
    public int getItemPosition(Object object){
        return POSITION_NONE;   //没有找到child要求重新加载
    }

}
