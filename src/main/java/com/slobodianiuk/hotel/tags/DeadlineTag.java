package com.slobodianiuk.hotel.tags;

import com.slobodianiuk.hotel.db.bean.UserOrderBean;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.Calendar;

public class DeadlineTag extends TagSupport {

    private static final long serialVersionUID = -5349897599307910060L;

    private UserOrderBean order;

    @Override
    public int doStartTag() throws JspException {
        JspWriter out = pageContext.getOut();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(order.getTransactionStart());
        calendar.add(Calendar.DATE, 2);
        try {
            out.print(calendar.getTime().getTime());
        } catch (IOException e) {
            throw new JspException(e);
        }

        return SKIP_BODY;
    }

    public UserOrderBean getOrder() {
        return order;
    }

    public void setOrder(UserOrderBean order) {
        this.order = order;
    }
}
