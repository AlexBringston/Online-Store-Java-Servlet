package com.store.controller.tags;

import javax.servlet.jsp.tagext.*;
import javax.servlet.jsp.*;
import java.io.*;

public class GreetingTag extends SimpleTagSupport{
    StringWriter sw = new StringWriter();
    @Override
    public void doTag() throws JspException, IOException {
        getJspBody().invoke(sw);
        getJspContext().getOut().println(sw.toString());
    }
}
