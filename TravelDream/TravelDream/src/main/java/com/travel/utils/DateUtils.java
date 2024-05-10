package com.travel.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @BelongsProject: TravelDream
 * @BelongsPackage: com.travel.utils
 * @CreateTime: 2021-05-18 14:11
 * @Description: 日期工具类
 */
public class DateUtils {

    /**
     * 获取当前系统时间
     * @return
     */
    public static String getNowTime() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 获取订单号
     * @return
     */
    public static String getOrderId() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        return simpleDateFormat.format(new Date());
    }

    /**
     * 计算两个时间相差的天数 2021-9-12  2021-10-10
     * @param startDate
     * @param endDate
     * @return
     */
    public static long diffDays(String startDate,String endDate){
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date1=simpleDateFormat.parse(startDate);
            Date date2=simpleDateFormat.parse(endDate);
            long seconds=date2.getTime()-date1.getTime();
            return (seconds/(3600*24*1000));
        } catch (ParseException e) {
            e.printStackTrace();
        } finally {
        }
        return 0;
    }

    public static void main(String[] args) {
        long days = DateUtils.diffDays("2021-9-12", "2021-9-19");
        System.out.println(days);
    }

}
