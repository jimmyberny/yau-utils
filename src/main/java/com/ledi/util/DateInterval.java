package com.ledi.util;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by berny on 12/2/15.
 */
public class DateInterval implements Serializable {

    private Date start;
    private Date end;

    public DateInterval() {
    }

    public DateInterval(Date start, Date end) {
        this.start = start;
        this.end = end;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        DateInterval that = (DateInterval) o;

        return new EqualsBuilder()
                .append(start, that.start)
                .append(end, that.end)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(start)
                .append(end)
                .toHashCode();
    }

    @Override
    public String toString() {
        return String.format("%s[%d] - %s[%d]",
                DateFormatUtils.ISO_DATETIME_FORMAT.format(start),
                start.getTime(),
                DateFormatUtils.ISO_DATETIME_FORMAT.format(end),
                end.getTime());
    }
}
