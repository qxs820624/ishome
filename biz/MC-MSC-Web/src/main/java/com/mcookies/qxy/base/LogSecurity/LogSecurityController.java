package com.mcookies.qxy.base.LogSecurity;
import javax.annotation.Resource;

import org.jfpc.framework.support.MyControllerSupport;
import org.springframework.stereotype.Controller;

@Controller
/** 安全日志表*/
public class LogSecurityController extends MyControllerSupport {
    @Resource
    protected LogSecurityService LogSecurityService_;

}
