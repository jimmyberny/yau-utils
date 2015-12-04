package com.ledi.dry.test;

import com.ledi.dry.utils.TzDateUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by berny on 10/12/15.
 */
public class TzDateUtilsTest {

    public static final Logger log = LoggerFactory.getLogger(TzDateUtilsTest.class);

    @Test
    public void testToUTC() {
        TimeZone tz = TimeZone.getTimeZone("America/Mexico_City"); // CST || CDT (depends on DST)

        TimeZone utc = TimeZone.getTimeZone("UTC");
        Calendar cal = Calendar.getInstance(utc); // Default timezone, must be UTC, otherwise this test won't work

        // First second of day
        cal.set(2015, Calendar.OCTOBER, 12, 0, 0, 0); // Oct 12th, 2015
        // A date obtained from calendar with getTime have not time zone.
        Date gmt = cal.getTime();
        log.info("GMT: {}\n(Forged to ignore timezone)", DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(gmt));

        // Set expected result:
        // Hours to add to an UTC instant for toTimeZone time zoned instant
        long hourOffset = tz.getOffset(gmt.getTime()) / DateUtils.MILLIS_PER_HOUR;// Oct 12th, 2015 is on Daylight Saving Time
        cal.set(2015, Calendar.OCTOBER, 12, 0, 0, 0); // Supposed final result, instead of add the hours must be subtracted
        cal.add(Calendar.HOUR_OF_DAY, -1 * (int) hourOffset);
        Date _d1 = cal.getTime();
        log.info("Expected UTC: {}\n(Forged to ignore timezone)", DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(_d1));

        // Testing
        Date test = TzDateUtils.getUTC(gmt, tz);
        log.info("Received UTC: {}\n(Forged to ignore timezone)", DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(test));

        Assert.assertEquals("Convert to UTC is wrong",
                DateFormatUtils.ISO_DATETIME_FORMAT.format(_d1), DateFormatUtils.ISO_DATETIME_FORMAT.format(test));
    }

    @Test
    public void testToGMT() {
        TimeZone tz = TimeZone.getTimeZone("America/Mexico_City");
        Calendar c = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        c.set(2015, Calendar.OCTOBER, 12, 23, 06, 0); // Oct 12th, 2015 23:06:00 UTC
        Date utc = c.getTime();

        c.set(2015, Calendar.OCTOBER, 12, 18, 06, 0); // Oct 12th, 2015 18:06:00 CST (GMT+05)
        Date gmt = c.getTime();

        // Testing
        Date result = TzDateUtils.toTimeZone(utc, tz);
        Assert.assertEquals("Convert to time zone is wrong",
                DateFormatUtils.ISO_DATETIME_FORMAT.format(gmt), DateFormatUtils.ISO_DATETIME_FORMAT.format(result));

    }

    @Test
    public void testStartToday() {
        TimeZone tz = TimeZone.getTimeZone("America/Mexico_City");
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        cal.set(2015, Calendar.OCTOBER, 12, 22, 40, 8); // Oct 12th, 2015 22:40:08, stills is 12th in CST(GMT+05)
        Date utc = cal.getTime();

        cal.clear();
        cal.set(2015, Calendar.OCTOBER, 12, 0, 0, 0); // GMT
        Date expected = cal.getTime();

        Date start = TzDateUtils.getStartOfDate(utc, tz);
        Assert.assertEquals("Start of day is wrong",
                DateFormatUtils.ISO_DATETIME_FORMAT.format(expected), DateFormatUtils.ISO_DATETIME_FORMAT.format(start));

        // UTC 2015-10-12 05:00:00+00:00 is the corresponding to 2015-10-12 00:00:00-05:00 (CDT || CST + DST)
        cal.clear();
        cal.set(2015, Calendar.OCTOBER, 12, 5, 0, 0);
        expected = cal.getTime();
        start = TzDateUtils.getUTCStartOfDate(utc, tz);
        Assert.assertEquals("Start of day as UTC is wrong",
                DateFormatUtils.ISO_DATETIME_FORMAT.format(expected), DateFormatUtils.ISO_DATETIME_FORMAT.format(start));
    }

}
