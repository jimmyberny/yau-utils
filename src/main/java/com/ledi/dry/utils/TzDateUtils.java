package com.ledi.dry.utils;

import org.apache.commons.lang3.time.DateUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by berny on 10/12/15.
 */
public class TzDateUtils {

    /**
     * Retorna la fecha con un instante UTC a partir de un instante con base de
     * referencia en la zona horaria dada.
     * <p>
     * Return date as an UTC instant from given instant in given time zone.
     * </p>
     *
     * @param gmt Timezoned instant.
     * @param tz  Time zone.
     * @return UTC Instant which matches to the original instant in the
     * original time zone.
     */
    public static Date getUTC(Date gmt, TimeZone tz) {
        return DateUtils.addMilliseconds(gmt, -1 * tz.getOffset(gmt.getTime()));
    }

    public static Date toTimeZone(Date utc, TimeZone tz) {
        return DateUtils.addMilliseconds(utc, tz.getOffset(utc.getTime()));
    }

    /**
     * Retorna la fecha sin importar su zona horaria con los valores despues del
     * día truncados.
     * <p>
     * P. Ej. Si la fecha es 2015-10-12 12:56:34, el valor de retorno será
     * 2015-10-12 00:00:00.
     * </p>
     *
     * @param date Fecha
     * @return El inicio del día, o dicho de otro modo la fecha sin horas, ni
     * minutos, ni segundos, ni milisegundos.
     */
    public static Date getStartOfDate(Date date) {
        return DateUtils.truncate(date, Calendar.DATE);
    }

    /**
     * Retorna el primer instante del día que inicia en la zona horaria dada que
     * contiene el instante UTC dado.
     * <p>
     * La diferencia con respecto al método {@code getUTCStartOfDate} reside
     * en que este último convierte el inicio del día en UTC, por lo que la
     * entrada es UTC y la salida es UTC. Para este método la entrada es UTC,
     * más la salida usa la zona horaria como referencia (o dicho de algún
     * modo es GMT o tiene desplazamiento).
     * </p>
     *
     * @param utc
     * @param tz
     * @return
     */
    public static Date getStartOfDate(Date utc, TimeZone tz) {
        return DateUtils.truncate(toTimeZone(utc, tz), Calendar.DATE);
    }

    /**
     * Retorna el primer instante en UTC que marca el inicio del día que en la
     * zona horaria dada contiene el instante UTC dado.
     *
     * @param utc Instante UTC.
     * @param tz  Time zone. Zona horaria en la que se desea encontrar la
     *            correspondencia del instante UTC y obtener el
     *            inicio del día.
     * @return Instante UTC que corresponde al inicio del día en la zona
     * horaria, día que contiene al instante UTC original.
     */
    public static Date getUTCStartOfDate(Date utc, TimeZone tz) {
        Date zoned = DateUtils.truncate(toTimeZone(utc, tz), Calendar.DATE);
        return getUTC(zoned, tz);
    }

    public static Date getEndOfDate(Date utc, TimeZone tz) {
        return DateUtils.ceiling(toTimeZone(utc, tz), Calendar.DATE);
    }

    /**
     *
     * @param utc
     * @param tz
     * @return
     */
    public static Date getUTCEndOfDate(Date utc, TimeZone tz) {
        Date zoned = DateUtils.ceiling(toTimeZone(utc, tz), Calendar.DATE);
        return getUTC(zoned, tz);
    }


}
