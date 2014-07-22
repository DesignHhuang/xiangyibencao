package com.blisscloud.util;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

public class SetCharacterEncodingFilter implements Filter {
    protected String encoding = "GBK";
    protected FilterConfig filterConfig = null;
    protected boolean ignore = true;

    public void destroy() {
        this.encoding = "GBK";
        this.filterConfig = null;
    }

    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain) throws IOException,
            ServletException {

        request.setCharacterEncoding(encoding);
        chain.doFilter(request, response);
    }

    public void init(FilterConfig filterConfig) throws ServletException {
        this.encoding = "GBK";
    }

    protected String selectEncoding(ServletRequest request) {
        return (this.encoding);
    }

}
