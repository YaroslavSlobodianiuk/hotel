package com.slobodianiuk.hotel.tags;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.Clock;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Tag class that returns future date depends on two parameters:
 * type: day, year, etc
 * period: 0 - Long.MAX_VALUE
 *
 * @author Yaroslav Slobodianiuk
 */
public class TimeTag extends TagSupport {

    private static final long serialVersionUID = -5246409001497897805L;
    private String type;
    private Integer period;

    @Override
    public int doStartTag() throws JspException {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        JspWriter out = pageContext.getOut();

        if ("years".equals(type)) {
            LocalDateTime currentTime = LocalDateTime.now(Clock.systemDefaultZone());
            LocalDateTime newCurrentTime = currentTime.plusYears(period);

            try {
                out.print(newCurrentTime.format(formatter));
            } catch (IOException e) {
                throw new JspException(e);
            }
        }

        if ("days".equals(type)) {
            LocalDateTime currentTime = LocalDateTime.now(Clock.systemDefaultZone());
            LocalDateTime newCurrentTime = currentTime.plusDays(period);

            try {
                out.print(newCurrentTime.format(formatter));
            } catch (IOException e) {
                throw new JspException(e);
            }
        }

        if ("yearsAndDays".equals(type)) {
            LocalDateTime currentTime = LocalDateTime.now(Clock.systemDefaultZone());
            LocalDateTime newCurrentTime = currentTime.plusYears(period).plusDays(period);

            try {
                out.print(newCurrentTime.format(formatter));
            } catch (IOException e) {
                throw new JspException(e);
            }
        }
        return SKIP_BODY;
    }


    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getPeriod() {
        return period;
    }

    public void setPeriod(Integer period) {
        this.period = period;
    }
}
