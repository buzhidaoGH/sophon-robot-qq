package pvt.example.sophon.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

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
}
