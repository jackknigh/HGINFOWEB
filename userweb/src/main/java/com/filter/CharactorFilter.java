package com.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @version version 1.0
 * @ClassName ResponseFilter
 * @Description: 〈编码过滤器〉
 */
@Component
@WebFilter(urlPatterns = "/*", filterName = "charactorFilter")
public class CharactorFilter implements Filter
{
	private String encoding = "UTF-8";

	@Override
	public void init(FilterConfig filterConfig) throws ServletException
	{

	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException
	{
//		HttpServletRequest request = (HttpServletRequest) req;
//		HttpServletResponse response = (HttpServletResponse) resp;
//		response.setContentType("text/html;charset=utf-8");
//		request.setCharacterEncoding(encoding);
		chain.doFilter(req, resp);
	}

	@Override
	public void destroy()
	{

	}
}
