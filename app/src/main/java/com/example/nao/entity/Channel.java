package com.example.nao.entity;

/**
 * Created by 爱冒险的小鸡仔 on 2018/5/16.
 */

public class Channel
{
    private String id;
    private String name;
    private int order;

    public Channel(){

    }
    public Channel(String id,String name, int order) {
        super();
        this.id=id;
        this.name = name;
//        this.order = order;

    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public int getOrder() {
        return order;
    }
    public void setOrder(int order) {
        this.order = order;
    }

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

}
