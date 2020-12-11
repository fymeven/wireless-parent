package cn._51even.wireless.core.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjuster;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

/**
 * Created by Administrator on 2018/6/9.
 */
public class DateUtils {

    private static final Logger logger=LoggerFactory.getLogger(DateUtils.class);

    public static final String DATE_FORMAT = "yyyy-MM-dd";

    public static final String DATE_TIME_FORMAT = "yyyy-MM-dd HH:mm:ss";

    public static final String DATE_CHINA_FORMAT = "yyyy年M月d日";

    public static final String YEAR = "year";

    public static final String MONTH = "month";

    public static final String WEEK = "week";

    public static final String DAY = "day";

    private static final int ZERO = 0;
    private static final int TWENTY_THREE = 23;
    private static final int FIFTY_NINE = 59;
    private static final TemporalAdjuster MONDAY_ADJUSTER = TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY);
    private static final TemporalAdjuster SUNDAY_ADJUSTER = TemporalAdjusters.nextOrSame(DayOfWeek.SUNDAY);

    /**
     * 获取当期日期（yyyy-MM-dd）
     * @return
     */
    public static String getCurrentDate(){
        SimpleDateFormat sdf=new SimpleDateFormat(DATE_FORMAT);
        return sdf.format(new Date());
    }

    /**
     * 获取当前日期时间（yyyy-MM-dd HH:mm:ss）
     * @return
     */
    public static String getCurrentDateTime(){
        SimpleDateFormat sdf=new SimpleDateFormat(DATE_TIME_FORMAT);
        return sdf.format(new Date());
    }

    /**
     * 获取（yyyy年M月d日）格式日期
     * @param date
     * @return
     */
    public static String getChinaDate(Date date){
        return formatDate(date, DATE_CHINA_FORMAT);
    }

    /**
     * 获取（yyyy年M月d日）格式日期
     * @return
     */
    public static String getChinaDate(){
        return getChinaDate(new Date());
    }

    /**
     * 将Date类型日期转换为String类型（自定义格式）
     * @param date
     * @param dateFormat
     * @return
     */
    public static String formatDate(Date date,String dateFormat){
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * 将Date类型日期转换为String类型（yyyy-MM-dd）
     * @param date
     * @return
     */
    public static String formatDate(Date date){
        return formatDate(date,DATE_FORMAT);
    }

    /**
     * 将Date类型日期时间转换为String类型（自定义格式）
     * @param date
     * @param dateFormat
     * @return
     */
    public static String formatDateTime(Date date,String dateFormat){
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        return sdf.format(date);
    }

    /**
     * 将Date类型日期时间转换为String类型（yyyy-MM-dd HH:mm:ss）
     * @param date
     * @return
     */
    public static String formatDateTime(Date date){
        return formatDateTime(date,DATE_TIME_FORMAT);
    }

    /**
     * 将String类型日期转换为Date类型（自定义格式）
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static Date parseDate(String dateStr,String dateFormat){
        Date date=null;
        if (StringUtils.isBlank(dateStr)){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        try {
            date=sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期转换出错",e);
        }
        return date;
    }

    /**
     * 将String类型日期转换为Date类型（yyyy-MM-dd）
     * @param dateStr
     * @return
     */
    public static Date parseDate(String dateStr){
        return parseDate(dateStr,DATE_FORMAT);
    }

    /**
     * 将String类型日期时间转换为Date类型（自定义格式）
     * @param dateStr
     * @param dateFormat
     * @return
     */
    public static Date parseDateTime(String dateStr,String dateFormat){
        Date date=null;
        if (StringUtils.isBlank(dateStr)){
            return null;
        }
        SimpleDateFormat sdf=new SimpleDateFormat(dateFormat);
        try {
            date=sdf.parse(dateStr);
        } catch (ParseException e) {
            logger.error("日期转换出错",e);
        }
        return date;
    }

    /**
     * 将String类型日期时间转换为Date类型（yyyy-MM-dd HH:mm:ss）
     * @param dateStr
     * @return
     */
    public static Date parseDateTime(String dateStr){
        return parseDate(dateStr,DATE_TIME_FORMAT);
    }

    /**
     * 判断是否闰年
     * @param date
     * @return
     */
    public static boolean isLeapYear(Date date){
        Calendar calendar=Calendar.getInstance();
        calendar.setTime(date);
        int year = calendar.get(Calendar.YEAR);
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 以当前时间为准，明天+1 ，昨天-1
     *
     * @param n
     * @return
     */
    public static Date getNDate(int n) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, n);
        Date nDate = calendar.getTime();
        String dateStr = DateUtils.formatDate(nDate);
        return DateUtils.parseDate(dateStr);
    }

    public static Date getNMonth(int n) {
        Date date = new Date();
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, n);
        Date nDate = calendar.getTime();
        String dateStr = DateUtils.formatDate(nDate);
        return DateUtils.parseDate(dateStr);
    }

    /**
     * 传入开始时间年月日和结束时间年月日，获取时间区间内的每天的年月日list
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<String> timeIntervalDay(String startTime,String endTime){
        List<String> list = new ArrayList<String>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(startTime);
            Date d2 = sdf.parse(endTime);
            Calendar c = Calendar.getInstance();
            c.setTime(d1);
            while((c.getTime()).before(d2)) {
                list.add(sdf.format(c.getTime()));
                c.add(Calendar.DATE, 1);
            }
            list.add(sdf.format(c.getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    /**
     * 获取开始时间和结束时间的年月日中的每一天的日期和周几
     * @param startTime
     * @param endTime
     * @return
     */
    public static List<Map<String,String>> timeIntervalDayAndWeek(String startTime,String endTime){
        List<Map<String,String>> list = new ArrayList<Map<String,String>>();
        try {
            String[] weekDays = {"0","1","2","3","4","5","6"};
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Date d1 = sdf.parse(startTime);
            Date d2 = sdf.parse(endTime);
            Calendar c = Calendar.getInstance();
            c.setTime(d1);
            while((c.getTime()).before(d2)) {
                Map<String,String> map = new LinkedHashMap<String,String>();
                map.put(sdf.format(c.getTime()), weekDays[c.get(Calendar.DAY_OF_WEEK)-1]);
                list.add(map);
                c.add(Calendar.DATE, 1);
            }
            Map<String,String> map = new LinkedHashMap<String,String>();
            map.put(sdf.format(c.getTime()), weekDays[c.get(Calendar.DAY_OF_WEEK)-1]);
            list.add(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 获得指定日期格式的时间戳 eg：2018-09-19 00:00:00
     * @param str
     * @return
     */
    public static Long getCurrentTimeStamp(String str){
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_TIME_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            System.out.println(e);
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.getTimeInMillis();
    }
    public static String getNowTimeStampForUnix(){
        long time = System.currentTimeMillis();
        String nowTimeStamp = String.valueOf(time/1000);
        return nowTimeStamp;
    }
    public static long getTimeStampForUnixByDate(String str){
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long temp = 0L;
        try {
            temp = sdf.parse(str).getTime() / 1000;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return temp;
    }

    /**
     * 将秒转换为人性化时间
     * @param second
     * @return
     */
    public static String secondToTime(long second){
        StringBuilder timeStr = new StringBuilder();
        long days = second / 86400;            //转换天数
        second = second % 86400;            //剩余秒数
        long hours = second / 3600;            //转换小时
        second = second % 3600;                //剩余秒数
        long minutes = second /60;            //转换分钟
        second = second % 60;                //剩余秒数
        if(days>0){
            timeStr.append(days + "天");
        }else{
            if (hours >0){
                timeStr.append(hours + "小时");
            }else {
                if (minutes >0){
                    timeStr.append(minutes + "分钟");
                }else {
                    timeStr.append(second + "秒");
                }
            }
        }
        return timeStr.toString();
    }

    /**
     * 获得某天零时零分零秒
     * @return
     */
    public static Date getMorningTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        return calendar.getTime();
    }

    /**
     * 获得某天23时59分59秒
     * @return
     */
    public static Date getEveningTime(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        return calendar.getTime();
    }

    /**
     * 本周开始时间 - Date
     */
    public static Date getWeekStart() {
        LocalDateTime dateTime = adjust(LocalDateTime.now(), MONDAY_ADJUSTER, ZERO, ZERO, ZERO);
        return toDate(dateTime);
    }

    /**
     * 本周结束时间 - Date
     */
    public static Date getWeekEnd() {
        LocalDateTime dateTime = adjust(LocalDateTime.now(), SUNDAY_ADJUSTER, TWENTY_THREE, FIFTY_NINE, FIFTY_NINE);
        return toDate(dateTime);
    }

    /**
     * 本周开始时间 - Timestamp
     */
    public static Timestamp getWeekStartTimestamp() {
        return Timestamp.valueOf(adjust(LocalDateTime.now(), MONDAY_ADJUSTER, ZERO, ZERO, ZERO));
    }

    /**
     * 本周结束时间 - Timestamp
     */
    public static Timestamp getWeekEndTimestamp() {
        return Timestamp.valueOf(adjust(LocalDateTime.now(), SUNDAY_ADJUSTER, TWENTY_THREE, FIFTY_NINE, FIFTY_NINE));
    }

    /**
     * 当前时间加（n为正）减（n为负）n天后的0点 - Date
     */
    public static Date getDayStart(int n) {
        LocalDateTime dateTime = adjust(LocalDateTime.now().plusDays(n), ZERO, ZERO, ZERO);
        return toDate(dateTime);
    }

    /**
     * 当前时间加（n为正）减（n为负）n天后的 23:59:59 - Date
     */
    public static Date getDayEnd(int n) {
        LocalDateTime dateTime = adjust(LocalDateTime.now().plusDays(n), TWENTY_THREE, FIFTY_NINE, FIFTY_NINE);
        return toDate(dateTime);
    }

    /**
     * 当前时间加（n为正）减（n为负）n天后的0点 - Timestamp
     */
    public static Timestamp getDayStartTimestamp(int n) {
        return Timestamp.valueOf(adjust(LocalDateTime.now().plusDays(n), ZERO, ZERO, ZERO));
    }

    /**
     * 当前时间加（n为正）减（n为负）n天后的 23:59:59 - Timestamp
     */
    public static Timestamp getDayEndTimestamp(int n) {
        return Timestamp.valueOf(adjust(LocalDateTime.now().plusDays(n), TWENTY_THREE, FIFTY_NINE, FIFTY_NINE));
    }

    /**
     * 当前时间加（n为正）减（n为负）n个月后的月初 - Date
     */
    public static Date getMonthStart(int n) {
        LocalDateTime dateTime = adjust(LocalDateTime.now().plusMonths(n), TemporalAdjusters.firstDayOfMonth(), ZERO, ZERO, ZERO);
        return toDate(dateTime);
    }

    /**
     * 当前时间加（n为正）减（n为负）n个月后的月末 - Date
     */
    public static Date getMonthEnd(int n) {
        LocalDateTime dateTime = adjust(LocalDateTime.now().plusMonths(n), TemporalAdjusters.lastDayOfMonth(), TWENTY_THREE, FIFTY_NINE, FIFTY_NINE);
        return toDate(dateTime);
    }

    /**
     * 当前时间加（n为正）减（n为负）n个月后的月初 - Timestamp
     */
    public static Timestamp getMonthStartTimestamp(int n) {
        return Timestamp.valueOf(adjust(LocalDateTime.now().plusMonths(n), TemporalAdjusters.firstDayOfMonth(), ZERO, ZERO, ZERO));
    }

    /**
     * 当前时间加（n为正）减（n为负）n个月后的月末 - Timestamp
     */
    public static Timestamp getMonthEndTimestamp(int n) {
        return Timestamp.valueOf(adjust(LocalDateTime.now().plusMonths(n), TemporalAdjusters.lastDayOfMonth(), TWENTY_THREE, FIFTY_NINE, FIFTY_NINE));
    }

    /**
     * 调整时间
     *
     * @param dateTime 时间
     * @param adjuster 调整方式
     * @param hour 小时
     * @param minute 分钟
     * @param second 秒
     * @return LocalDateTime
     */
    private static LocalDateTime adjust(LocalDateTime dateTime, TemporalAdjuster adjuster, int hour, int minute, int second) {
        return dateTime.with(adjuster).withHour(hour).withMinute(minute).withSecond(second);
    }

    private static LocalDateTime adjust(LocalDateTime time, int hour, int minute, int second) {
        return time.withHour(hour).withMinute(minute).withSecond(second);
    }

    private static Date toDate(LocalDateTime time) {
        return Date.from(time.atZone(ZoneId.systemDefault()).toInstant());
    }

    /**
     * 两个日期之间的月份，包含起始月份
     *
     * @param start 日期1
     * @param end 日期2
     * @return 月份列表
     */
    public static List<String> monthsBetween(LocalDate start, LocalDate end) {
        // 日期没按大小顺序传，交换
        if (start.isAfter(end)) {
            LocalDate tmp = end;
            end = start;
            start = tmp;
        }
        List<String> list = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        // 年不是同一年，调整年
        while (start.getYear() != end.getYear()) {
            list.add(formatter.format(start));
            start = start.plusMonths(1);
        }
        // 月不是同一月，调整月
        while (start.getMonthValue() != end.getMonthValue()) {
            list.add(formatter.format(start));
            start = start.plusMonths(1);
        }
        // 把最后一个月包含进去
        list.add(formatter.format(start));
        return list;
    }

    public static void main(String[] args) {
        Date nDate = getNDate(-90);
        System.out.println("date="+DateUtils.formatDate(nDate));
    }
}
