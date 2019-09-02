package com.ylp.ccut.util;

import org.apache.ibatis.annotations.Mapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
@Component
public class DateUtil{
    private static SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
    private static Calendar calendar = Calendar.getInstance(Locale.CHINA);
    private static final Logger logger = LogManager.getLogger(DateUtil.class);

    /**
     * 对日期进行计算（加为正，减为负）年月日
     * @param type 操作的单位（年y，月m，日d）
     * @param date 基础日期
     * @param number 操作数
     * @return 操作后的日期
     */
    public Date dateAddNumber(char type, Date date, Integer number) {
        calendar.setTime(date);
        switch (type){
            case 'y':
                calendar.add(Calendar.YEAR,number);
                break;
            case 'm':
                calendar.add(Calendar.MONTH,number);
                break;
            case 'd':
                calendar.add(Calendar.DATE,number);
                break;
            default:
                calendar.add(Calendar.DATE,number);
                break;
        }
        return calendar.getTime();
    }

    /**
     * 形状如yyyy-MM-dd的日期字符串转Java Date类
     * @param dateStr
     * @return 转换后的日期
     */
    public Date dateFromString(String dateStr){
        Date date = new Date();
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (Exception e){
            logger.error(e);
        }
        return date;
    }
    /**
     * Java Date类转形状如yyyy-MM-dd的日期字符串转
     * @param date
     * @return 转换后的字符串
     */
    public String fromDate(Date date){
        SimpleDateFormat printFormat = new SimpleDateFormat("yyyy年MM月dd日");
        return printFormat.format(date);
    }

    /**
     * 计算两个日期之间的天数
     * @param start 开始日期
     * @param end 结束日期
     * @return 两个日期之间的间隔天数，如果开始日期在后，转换后的日期为负数。
     */
    public Integer intervalDate(Date start, Date end) {
        calendar.setTime(start);
        long time1 = calendar.getTimeInMillis();
        calendar.setTime(end);
        long time2 = calendar.getTimeInMillis();
        long between_days=(time2-time1)/(1000*3600*24);
        return Integer.parseInt(String.valueOf(between_days));
    }
    public Integer getDay(Date date){
        calendar.setTime(date);
        return calendar.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 设置日期的年月日
     * @param type 设置日期的单位，年月日。
     * @param date 操作的基础日期
     * @param number 操作数
     * @return 返回设置后的日期
     */
    public Date setDateWithNumber(char type,Date date,Integer number){
        calendar.setTime(date);
        switch (type){
            case 'y':
                calendar.set(Calendar.YEAR,number);
                break;
            case 'm':
                calendar.set(Calendar.MONTH,number);
                break;
            case 'd':
                calendar.set(Calendar.DAY_OF_MONTH,number);
                break;
            default:
                calendar.set(Calendar.DAY_OF_MONTH,number);

        }
        return calendar.getTime();
    }
    public Date getSystemDate(){
        return  new Date();
    }

}