package com.luoy.library.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.luoy.library.common.util.ConstantsUtils;
import com.sun.java.swing.plaf.windows.resources.windows;

/**
 * 登录拦截器
 * @author ying luo
 * @createDate 2018年4月6日
 */
public class LoginInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {

	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {

	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object object) throws Exception {
		HttpSession session = request.getSession();
		if(null == session.getAttribute(ConstantsUtils.CURRENT_USER)){
			System.out.println(request.getRequestURI());
			
			response.sendRedirect(request.getContextPath());
			return false;
		}
		
		return true;
	}

}
