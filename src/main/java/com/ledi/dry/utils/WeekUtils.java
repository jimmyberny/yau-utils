package com.ledi.dry.utils;

import com.ledi.util.DateInterval;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by berny on 12/1/15.
 */
public class WeekUtils {

    private static final Logger log = LoggerFactory.getLogger(WeekUtils.class);

    private static final int _DEFAULTS = -1;

    /**
     * Retorna el numero de semana al que pertenece una fecha.
     * <p>
     * Se emplea la configuración por defecto de la maquina virtual.
     * </p>
     *
     * @param date Fecha, se ignora la zona horaria.
     * @return El número de la semana que contiene la fecha pasada como parámetro.
     */
    public static int getWeekNumber(Date date) {
        return getWeekNumber(date, _DEFAULTS);
    }

    /**
     * Retorna el número de semana en el año al que pertenece una fecha dada considerando
     * el inicio del día indicado.
     *
     * @param date        Fecha de la que se desea obtener la semana.
     * @param startOfWeek Número del día en el que comienza la semana.
     * @return El número de la semana en el año.
     */
    public static int getWeekNumber(Date date, int startOfWeek) {
        Calendar cal = Calendar.getInstance();
        if (startOfWeek != _DEFAULTS) {
            cal.setFirstDayOfWeek(startOfWeek);
        }
        cal.setTime(date);

        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Retorna la semana a la que pertenece la fecha UTC dada.
     *
     * @param utc Fecha en UTC.
     * @param tz  Zona horaria en la que se desea obtener la semana.
     * @return El número de la semana a la que pertenece a la fecha.
     */
    public static int getWeekNumber(Date utc, TimeZone tz) {
        return getWeekNumber(utc, tz, _DEFAULTS);
    }

    public static int getWeekNumber(Date utc, TimeZone tz, int firstDay) {
        Calendar aux = Calendar.getInstance();
        if (firstDay != _DEFAULTS) {
            aux.setFirstDayOfWeek(firstDay);
            aux.setMinimalDaysInFirstWeek(4);
        }

        aux.setTime(TzDateUtils.toTimeZone(utc, tz));
        return aux.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     *
     * @param date
     * @param tz
     * @return
     */
    public static DateInterval getUTCWeek(Date date, TimeZone tz) {
        return getUTCWeek(date, tz, Calendar.MONDAY);
    }

    public static DateInterval getUTCWeek(Date utc, TimeZone tz, int firstDay) {
        Calendar aux = Calendar.getInstance();
            aux.setFirstDayOfWeek(firstDay);

        Date local = TzDateUtils.toTimeZone(utc, tz);
        aux.setTime(local);
        aux.set(Calendar.DAY_OF_WEEK, firstDay);

        DateInterval res = new DateInterval();
        Date utcStart = TzDateUtils.getUTC(DateUtils.truncate(aux.getTime(), Calendar.DATE), tz);
        res.setStart(utcStart);
        res.setEnd(DateUtils.addDays(utcStart, 7));
        return res;
    }


    public static String toString(Date date) {
        return DateFormatUtils.ISO_DATETIME_TIME_ZONE_FORMAT.format(date);
    }
}
