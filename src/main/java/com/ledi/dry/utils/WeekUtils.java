package com.ledi.dry.utils;

import com.ledi.util.DateInterval;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by berny on 12/1/15.
 */
public class WeekUtils {

    private static final Logger log = LoggerFactory.getLogger(WeekUtils.class);

    /**
     * Retorna el numero de semana al que pertenece una fecha.
     * <p>
     * Se emplea la configuración por defecto de la maquina virtual.
     * </p>
     *
     * @param date Fecha, se ignora la zona horaria.
     * @return El número de la semana que contiene la fecha pasada como parámetro.
     */
    public static Integer getWeek(Date date) {
        Calendar aux = Calendar.getInstance();
        aux.setTime(date);
        return aux.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * Retorna la semana a la que pertenece la fecha UTC pasada como parámetro.
     *
     * @param utc Fecha en UTC.
     * @param tz  Zona horaria en la que se desea obtener la semana.
     * @return El número de la semana a la que pertenece a la fecha.
     */
    public static Integer getWeek(Date utc, TimeZone tz) {
        Date zoned = TzDateUtils.toTimeZone(utc, tz);

        Calendar aux = Calendar.getInstance(tz);
        aux.setTime(zoned);

        return aux.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * @param date
     * @param tz
     * @return
     */
    public static DateInterval getUTCWeekInterval(Date date, TimeZone tz) {
        Date zoned = DateUtils.truncate(TzDateUtils.toTimeZone(date, tz), Calendar.DATE);

        Calendar aux = Calendar.getInstance();
        aux.setTime(zoned);
        zoned = DateUtils.addDays(zoned, Calendar.SUNDAY == aux.get(Calendar.DAY_OF_WEEK) ?
                -6 : Calendar.MONDAY - aux.get(Calendar.DAY_OF_WEEK));

        DateInterval res = new DateInterval();
        res.setStart(TzDateUtils.getUTC(zoned, tz));
        res.setEnd(DateUtils.addDays(res.getStart(), 7));
        return res;
    }


    public static String toString(Date date) {
        return DateFormatUtils.ISO_DATETIME_FORMAT.format(date);
    }
}
