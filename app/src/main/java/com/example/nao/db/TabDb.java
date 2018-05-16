package com.example.nao.db;

import com.example.nao.R;
import com.example.nao.ui.fragment.FragmentMe;
import com.example.nao.ui.fragment.FragmentNews;
import com.example.nao.ui.fragment.FragmentSquare;

/**
 * Created by 爱冒险的小鸡仔 on 2018/5/16.
 */

public class TabDb {

    public static String[] getTabsTxt(){
        String[] tabs = {"动态","消息","我"};
        return tabs;
    }

    public static int[] getTabsImgLight(){
        int[] ids = {R.drawable.foot_dynamic_light,R.drawable.foot_news_light,R.drawable.foot_me_light};
        return ids;
    }

    public static int[] getTabsImg(){
        int[] ids = {R.drawable.foot_dynamic_normal,R.drawable.foot_news_normal,R.drawable.foot_me_normal};
        return ids;
    }

    public static Class[] getFragments(){
        Class[] clz = {FragmentSquare.class, FragmentNews.class, FragmentMe.class};
        return clz;
    }
}
