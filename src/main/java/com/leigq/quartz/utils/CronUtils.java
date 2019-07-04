package com.leigq.quartz.utils;

import org.quartz.CronExpression;
import org.springframework.util.Assert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 该类提供Quartz的cron表达式与Date之间的转换
 * <br/>
 *
 * @author ：leigq
 * @date ：2019/7/4 19:28
 */
public class CronUtils {
    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    /**
     * 返回一个布尔值代表一个给定的Cron表达式的有效性
     *
     * @param cronExpression Cron表达式
     * @return boolean 表达式是否有效
     */
    public static boolean isValid(String cronExpression) {
        return CronExpression.isValidExpression(cronExpression);
    }

    /**
     * Date 转换为 cron表达式
     * <br/>
     * create by: leigq
     * <br/>
     * create time: 2019/7/3 20:54
     *
     * @param date Cron表达式
     * @return cron
     */
    public static String toCron(Date date) {
        Assert.notNull(date, "date Can not be empty!");
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        return sdf.format(date);
    }

    /**
     * cron表达式 转换为 Date
     * <br/>
     * create by: leigq
     * <br/>
     * create time: 2019/7/3 20:54
     *
     * @param cron Cron表达式
     * @return Date
     */
    public static Date toDate(String cron) {
        Assert.notNull(cron, "cron Can not be empty!");
        if (!isValid(cron)) {
            throw new IllegalArgumentException("cron expression is not legitimate!");
        }
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date date;
        try {
            date = sdf.parse(cron);
        } catch (ParseException e) {
            throw new IllegalArgumentException(e);
        }
        return date;
    }
}
