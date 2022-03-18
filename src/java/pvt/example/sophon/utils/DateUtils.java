package pvt.example.sophon.utils;

import org.quartz.TriggerUtils;
import org.quartz.impl.triggers.CronTriggerImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * 类&emsp;&emsp;名：DateUtils <br/>
 * 描&emsp;&emsp;述：日期工具
 */
public class DateUtils {
    private static final Logger LOG = LoggerFactory.getLogger(DateUtils.class);

    /**
     * 计算两个日期相差年数
     * @param startDate 开始日期
     * @param endDate   结束日期
     * @return
     */
    public static int yearDateDiff(String startDate, String endDate) {
        Calendar calBegin = Calendar.getInstance(); //获取日历实例
        Calendar calEnd = Calendar.getInstance();
        calBegin.setTime(stringToDate(startDate, "yyyy")); //字符串按照指定格式转化为日期
        calEnd.setTime(stringToDate(endDate, "yyyy"));
        return calEnd.get(Calendar.YEAR) - calBegin.get(Calendar.YEAR);
    }

    /**
     * 计算今天距离指定日期的年数
     */
    public static int yearToNow(String oldDate) {
        Calendar calNow = Calendar.getInstance();
        Calendar calOld = Calendar.getInstance();
        calOld.setTime(stringToDate(oldDate, "yyyy-MM-dd"));
        return calNow.get(Calendar.YEAR) - calOld.get(Calendar.YEAR);
    }

    //字符串按照指定格式转化为日期
    public static Date stringToDate(String dateStr, String formatStr) {
        // 如果时间为空则默认当前时间
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat(formatStr);
        if (dateStr != null && !dateStr.equals("")) {
            try {
                date = format.parse(dateStr);
            } catch (ParseException e) {
                date = new Date();
                LOG.warn(e.getMessage());
            }
        } else {
            date = new Date();
        }
        return date;
    }

    /**
     * 传入时间戳,格式化时间字符串
     */
    public static String timestampToFormat(long time, String formatStr) {
        if (time == -1L) {
            return "不支持查询";
        }
        Date date = new Date(time);
        String dateStr = null;
        SimpleDateFormat sdf = new SimpleDateFormat(formatStr);
        dateStr = sdf.format(date);
        return dateStr;
    }

    /**
     * 传入时间戳,判断是否属于今天
     */
    public static boolean timestampIsToDay(long timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date toDay = new Date();
        Date testDay = new Date(timestamp);
        String toDayStr = sdf.format(toDay);
        String testDayStr = sdf.format(testDay);
        return toDayStr.equals(testDayStr);
    }

    public static boolean isSameDay(Date date1, Date date2) {
        Calendar calDateA = Calendar.getInstance();
        calDateA.setTime(date1);

        Calendar calDateB = Calendar.getInstance();
        calDateB.setTime(date2);

        return calDateA.get(Calendar.YEAR) == calDateB.get(Calendar.YEAR)
                && calDateA.get(Calendar.MONTH) == calDateB.get(Calendar.MONTH)
                && calDateA.get(Calendar.DAY_OF_MONTH) == calDateB
                .get(Calendar.DAY_OF_MONTH);
    }

    public static List<String> cronExpression2executeDates(String cronExpression) throws ParseException {
        List<String> resultList = new ArrayList<String>() ;
        CronTriggerImpl cronTriggerImpl = new CronTriggerImpl();
        cronTriggerImpl.setCronExpression(cronExpression);//这里写要准备猜测的cron表达式
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.YEAR, 2);//把统计的区间段设置为从现在到2年后的今天（主要是为了方法通用考虑，如那些1个月跑一次的任务，如果时间段设置的较短就不足20条)
        List<Date> dates = TriggerUtils.computeFireTimesBetween(cronTriggerImpl, null, now, calendar.getTime());//这个是重点，一行代码搞定~~
        System.out.println(dates.size());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        for(int i =0;i < dates.size();i ++){
            if(i >19){//这个是提示的日期个数
                break;
            }
            resultList.add(dateFormat.format(dates.get(i)));
        }
        return resultList;
    }
}
