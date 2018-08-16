package com.bridgelabz.zuulserver.filters;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;

import com.bridgelabz.zuulserver.utility.JWTtokenProvider;
import com.netflix.zuul.ZuulFilter;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;

public class Simplefilter extends ZuulFilter {

	@Autowired
	private JWTtokenProvider tokenProvider;

	@Override
	public boolean shouldFilter() {
		return true;
	}

	@Override
	public Object run() throws ZuulException {
		RequestContext ctx = RequestContext.getCurrentContext();
		HttpServletRequest request = ctx.getRequest();

		System.out.println("in zuul server");
		if (request.getRequestURI().startsWith("/note/")) {
			System.out.println("will parse");
			String token = request.getHeader("token");

			System.out.println(token);
			String userId = tokenProvider.parseJWT(token);

			System.out.println("parsed");
			ctx.addZuulRequestHeader("userId", userId);

			System.out.println("successful");
			return "Successfully authorized";
		}
		return "";
	}

	@Override
	public String filterType() {
		return "pre";
	}

	@Override
	public int filterOrder() {
		return 1;
	}

}
