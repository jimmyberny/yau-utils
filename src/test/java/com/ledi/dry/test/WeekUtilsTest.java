package com.ledi.dry.test;

import com.ledi.dry.utils.TzDateUtils;
import com.ledi.dry.utils.WeekUtils;
import com.ledi.util.DateInterval;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Time;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by berny on 12/1/15.
 */
public class WeekUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(WeekUtilsTest.class);

    @Test
    public void testGetWeek() {
        TimeZone tz = TimeZone.getTimeZone("America/Mexico_City"); // CST || CDT (depends on DST)
        //
        TimeZone utc = TimeZone.getTimeZone("UTC");
        Calendar cal = Calendar.getInstance(utc);

        // Sunday Nov 29th
        cal.set(2015, Calendar.NOVEMBER, 29, 0, 0, 0);
        Assert.assertEquals("Calculo de semana es incorrecto",
                new Integer(48), WeekUtils.getWeek(cal.getTime(), tz));

        cal.set(2016, Calendar.JANUARY, 3, 0, 0, 0);
        Assert.assertEquals("Calculo de semana es incorrecto",
                new Integer(1), WeekUtils.getWeek(cal.getTime(), tz));
    }

    @Test
    public void testGetWeekInterval() {
        TimeZone tz = TimeZone.getTimeZone("America/Mexico_City"); // CST || CDT (depends on DST)

        Calendar cal = Calendar.getInstance();
        // Week 51th,
        cal.set(2015, Calendar.DECEMBER, 14, 6, 0, 0); // Starts Dec 21th, 06:00:00
        cal.set(Calendar.MILLISECOND, 0);
        Date start = cal.getTime();
        cal.set(2015, Calendar.DECEMBER, 21, 6, 0, 0); // Ends Dec 28th, 06:00:00
        Date end = cal.getTime();
        cal.set(Calendar.MILLISECOND, 0);
        DateInterval val = new DateInterval(start, end);

        cal.set(2015, Calendar.DECEMBER, 20, 5, 59, 59); // UTC => Saturday 19th, 19:59:59
        Date test = cal.getTime();
        Assert.assertEquals("El intervalo de la semana esperada es incorrecta",
                val, WeekUtils.getUTCWeekInterval(test, tz));


    }
}
