package com.example.nao.db;

import com.example.nao.entity.Channel;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 爱冒险的小鸡仔 on 2018/5/16.
 */

public class ChannelDb
{
    private static List<Channel> selectedChannel = new ArrayList<Channel>();

    static {
        selectedChannel.add(new Channel("", "广场",0));
        selectedChannel.add(new Channel("", "动态",0));
    }

    public static List<Channel> getSelectedChannel(){
        return selectedChannel;
    }
}
