package com.example.nao.ui.fragment;

import android.app.Activity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by 爱冒险的小鸡仔 on 2018/5/16.
 */

public class SquareChannelFragment extends Fragment {
    private String channelName;
    @Override
    public void setArguments(Bundle args){
        channelName = args.getString("cname");
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
    }

    private TextView view;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        if (view == null)
        {
            view = new TextView((super.getActivity()));
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            view.setGravity(Gravity.CENTER);
            view.setTextSize(30);
            view.setText(channelName);
        }

        ViewGroup parent = (ViewGroup)view.getParent();
        if (parent != null)
        {
            parent.removeView(view);
        }

        return view;
    }

    @Override
    public void onDestroy()
    {
        super.onDestroy();
    }
}
