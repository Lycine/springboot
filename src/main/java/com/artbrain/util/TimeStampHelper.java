package com.artbrain.util;

import java.text.DecimalFormat;

/**
 * Created by hongyu on 16/9/18.
 */
public class TimeStampHelper {

    public TimeStampHelper() {
    }

    public static Double getTimeStamp() {
        Double timeStamp = (double) System.currentTimeMillis()/10;
        DecimalFormat decimalFormat = new DecimalFormat("#");//格式化设置

        String timeStampStr = decimalFormat.format(timeStamp);//时间戳从科学技术法转为字符串
        timeStampStr = timeStampStr.substring(timeStampStr.length()-6,timeStampStr.length());//截取最后6位,所以最大范围是9999
        timeStamp = Double.valueOf(timeStampStr);
        return timeStamp;
    }
}
