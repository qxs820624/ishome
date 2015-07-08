package com.jfpc.platform.token.controller;

import javax.annotation.Resource;

import org.jfpc.base.helper.TokenHelper;
import org.jfpc.base.login.bean.LoginerBean;
import org.jfpc.base.login.service.LoginService;
import org.jfpc.base.support.MyFrameworkSupport;
import org.jfpc.base.support.MyModelAndViewSupport;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 多包扫描示例
 * 域名访问默认登陆页面
 * 
 * @author Spook
 * @version 0.1
 * @since 0.1.0 2014/2/8
 */
@Controller
public class TokenCheckController extends MyFrameworkSupport{
	@Resource
	protected LoginService LoginService_;
	
	/**
	 * 默认登录界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/001100110011", method = RequestMethod.GET)
	public MyModelAndViewSupport m00111100GET() {
		MyModelAndViewSupport myPv = new MyModelAndViewSupport("home");
		LoginerBean loginer = new LoginerBean();
		loginer.setToken(super.getToken());
		boolean result = LoginService_.doCheckToken(loginer);
		if(result){
			myPv.addObject("loginer", loginer);
		}else{
			myPv.setViewName("index");
		}		
		return myPv;
	}
	
	/**
	 * 默认登录界面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/001100110011", method = RequestMethod.POST)
	@ResponseBody
	public MyModelAndViewSupport m00111100POST() {
		MyModelAndViewSupport myPv = new MyModelAndViewSupport("home");
		LoginerBean loginer = new LoginerBean();
		loginer.setToken(super.getToken());
		boolean result = LoginService_.doCheckToken(loginer);
		if(result){
			myPv.addObject("loginer", loginer);
		}else{
			myPv.setViewName("index");
		}		
		return myPv;
	}
}
