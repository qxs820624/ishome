package com.mcookies.qxy.base.SWorkRule;

import javax.annotation.Resource;

import org.jfpc.framework.support.MyControllerSupport;
import org.springframework.stereotype.Controller;

@Controller
/** 作息时间规则表 */
public class SWorkRuleController extends MyControllerSupport {

	@Resource
	protected SWorkRuleService SWorkRuleService_;

}
