package com.hotbitmapgg.rxzhihu.utils;

import java.util.Calendar;

/**
 * Created by hcc on 16/4/3.
 */
public class WeekUtil
{

    public static String getWeek(String date)
    {

        LogUtil.all(date);
        int year = Integer.parseInt(date.substring(0, 4));
        int month = Integer.parseInt(date.substring(4,6));
        int day = Integer.parseInt(date.substring(6,8));
        LogUtil.all(year + "~~~" + month + "~~~" + day);
        Calendar calendar = Calendar.getInstance();//获得一个日历
        calendar.set(year, month-1, day);//设置当前时间,月份是从0月开始计算
        int number = calendar.get(Calendar.DAY_OF_WEEK);//星期表示1-7，是从星期日开始，
        String [] str = {"","星期日","星期一","星期二","星期三","星期四","星期五","星期六",};
        String weekDay = getWeekDay(number);
        return weekDay;
    }

    private static String getWeekDay(int dayForWeek)
    {

        if (dayForWeek == 1)
        {
            return "星期日";
        } else if (dayForWeek == 2)
        {
            return "星期一";
        } else if (dayForWeek == 3)
        {
            return "星期二";
        } else if (dayForWeek == 4)
        {
            return "星期三";
        } else if (dayForWeek == 5)
        {
            return "星期四";
        } else if (dayForWeek == 6)
        {
            return "星期五";
        } else if (dayForWeek == 7)
        {
            return "星期六";
        }
        else
        {
            return "";
        }


    }
}
