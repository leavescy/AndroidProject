package com.example.chenye.globalvariables;

import java.io.Serializable;

/**
 * Created by chenye on 2018/1/18.
 */

public class MyData implements Serializable{

    private String dataName;
    private int dataTime;

    public MyData(String dataName, int dataTime) {
        this.dataName = dataName;
        this.dataTime = dataTime;
    }


    @Override
    public String toString() {
        return "MyData{" +
                "dataName='" + dataName + '\'' +
                ", dataTime=" + dataTime +
                '}';
    }
}
