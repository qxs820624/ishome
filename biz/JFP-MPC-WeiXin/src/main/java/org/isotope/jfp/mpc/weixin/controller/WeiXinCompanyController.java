package org.isotope.jfp.mpc.weixin.controller;

import javax.annotation.Resource;

import org.isotope.jfp.framework.beans.common.RESTResultBean;
import org.isotope.jfp.mpc.weixin.service.MyWeixinCompanyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 微信企业号企业管理
 * 
 * @author spookfcy
 * @since 3.3.1
 * @version 3.3.1.20160825
 */
@Controller
@RequestMapping("/weixin/company")
public class WeiXinCompanyController {
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	@Resource
	MyWeixinCompanyService MyWeixinCompanyBusiness_;
	
	/**
	 * 同步一个企业（数据来源于DB监控）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sync", method = RequestMethod.POST)
	@ResponseBody
	public RESTResultBean companyIdSyncPOST() {
		RESTResultBean result = new RESTResultBean();
		result.setResult(MyWeixinCompanyBusiness_.companyTokenSync());
		return result;
	}
	
	/**
	 * 同步一个企业（数据来源于DB监控）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/sync/{companyId}", method = RequestMethod.POST)
	@ResponseBody
	public RESTResultBean companyIdSyncPOST(@PathVariable String companyId) {
		RESTResultBean result = new RESTResultBean();
		result.setResult(MyWeixinCompanyBusiness_.companyTokenSync(companyId));
		return result;
	}
}
