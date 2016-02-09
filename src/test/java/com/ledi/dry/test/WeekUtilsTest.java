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
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by berny on 12/1/15.
 */
public class WeekUtilsTest {

    private static final Logger log = LoggerFactory.getLogger(WeekUtilsTest.class);

    @Test
    public void testGetUTCWeekNumber() {
        Date utc = getDate("2015-12-31T00:00:00-06:00"); // Dec 31th, 2015. Week 53th
        TimeZone mx = TimeZone.getTimeZone("America/Mexico_City");

        int week = WeekUtils.getUTCWeekNumber(utc, mx, Calendar.MONDAY);
        Assert.assertEquals("Numero de semana incorrecto",
                53, week);

        utc = getDate("2016-01-04T00:00:00-06:00"); // Jan 1st, 2016, Week 1st
        week = WeekUtils.getUTCWeekNumber(utc, mx, Calendar.MONDAY);
        Assert.assertEquals("Número de semana incorrecto",
                1, week);
    }

    @Test
    public void testGetUTCWeek() {
        // "YYYY-MM-ddTHH:mm:ss[+-]hh:mm"
        Date utc = getDate("2015-12-28T00:00:00-06:00"); // An Local time converted to UTC
        TimeZone mx = TimeZone.getTimeZone("America/Mexico_City");
        DateInterval utcWeek = WeekUtils.getUTCWeek(utc, mx);

        Assert.assertEquals("Inicio de semana incorrecto",
                "2015-12-28T06:00:00+00:00", WeekUtils.toString(utcWeek.getStart()));
        Assert.assertEquals("Final de semana incorrecto",
                "2016-01-04T06:00:00+00:00", WeekUtils.toString(utcWeek.getEnd()));
    }

    private Date getDate(String input) {
        try {
            return DateUtils.parseDate(input, DateFormatUtils.ISO_DATETIME_FORMAT.getPattern(),
                    DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.getPattern());
        } catch (ParseException e) {
            throw new IllegalArgumentException("Entrada no pertenece a una fecha");
        }
    }
}
