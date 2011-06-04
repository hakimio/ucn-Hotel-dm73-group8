package hotel.utils;

import java.util.Calendar;
import java.util.Date;

public class DateUtils
{
    public static Date setTimeToMidnight(Date date)
    {
        Calendar calendar = Calendar.getInstance();

        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        
        return calendar.getTime();
    }
    
    public static Date getToday()
    {
        return setTimeToMidnight(new Date());
    }
}
