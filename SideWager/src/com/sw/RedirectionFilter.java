package com.sw;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class RedirectionFilter
 */
//@WebFilter("/*")
public class RedirectionFilter implements Filter {

    /**
     * Default constructor. 
     */
    public RedirectionFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		// pass the request along the filter chain
		HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse res = (HttpServletResponse) response;

        
        String requestUrl = req.getRequestURL().toString();
        System.out.println("Filter : "+requestUrl);
        if(!requestUrl.contains("/rest/") 
        		&& !requestUrl.contains("/resources/")
        		&& !requestUrl.contains("/publish/")
        		&& !requestUrl.contains("/data/")
        		&& !requestUrl.equalsIgnoreCase("https://www.sidewagerapp.com/")){
        	res.sendRedirect("https://www.sidewagerapp.com/");
        }else {
            chain.doFilter(request, response);
        }

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
