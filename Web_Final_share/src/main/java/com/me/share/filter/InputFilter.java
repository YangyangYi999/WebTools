package com.me.share.filter;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;


/**
 * Servlet Filter implementation class InputFilter
 */
@WebFilter(filterName="inputFilter", urlPatterns = {"/*"})
public class InputFilter implements Filter {

	private final String sqlReg = "\\b(ALTER|CREATE|DELETE|DROP|EXEC(UTE){0,1}|INSERT( +INTO){0,1}|MERGE|SELECT|UPDATE|UNION( +ALL){0,1})\\b";
	private final String scriptReg = "<[^>]*>";
	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		// TODO Auto-generated method stub
		Enumeration<String> paramterNames = request.getParameterNames();
		while(paramterNames.hasMoreElements()) {
			String paraName = paramterNames.nextElement();
			String paraValue = request.getParameter(paraName);
			if(isRegexMached(paraValue, sqlReg, scriptReg)) {
				PrintWriter out = response.getWriter();
				out.print("<script language='javascript'>alert(\"ERROR!!\");" + "window.history.go(-1);</script>");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean isRegexMached(String paraValue, String regex, String scriptReg) {
		// TODO Auto-generated method stub
		Pattern checkSQLRegex = Pattern.compile(regex);
		Pattern checkScriptRegex = Pattern.compile(scriptReg);
		Matcher regexSQLMatcher = checkSQLRegex.matcher(paraValue.toUpperCase());
		Matcher regexScriptMatcher = checkScriptRegex.matcher(paraValue.toUpperCase());
		return regexSQLMatcher.find()||regexScriptMatcher.find();
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		// TODO Auto-generated method stub
		
	}

    

}
