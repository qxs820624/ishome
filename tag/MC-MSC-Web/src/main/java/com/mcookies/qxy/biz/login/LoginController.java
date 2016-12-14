package com.mcookies.qxy.biz.login;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.isotope.jfp.common.login.LoginBusiness;
import org.isotope.jfp.framework.beans.common.RESTResultBean;
import org.isotope.jfp.framework.beans.user.LoginerBean;
import org.isotope.jfp.framework.beans.user.UserBean;
import org.isotope.jfp.framework.common.sms.SMSTemplateConfig;
import org.isotope.jfp.framework.common.sms.UserSMSSendServiceImpl;
import org.isotope.jfp.framework.security.code.SecurityCodeHelper;
import org.isotope.jfp.framework.support.MyControllerSupport;
import org.isotope.jfp.framework.utils.DateHelper;
import org.isotope.jfp.framework.utils.EmptyHelper;
import org.isotope.jfp.framework.utils.FTPUtil;
import org.isotope.jfp.framework.utils.HttpRequestHelper;
import org.isotope.jfp.framework.utils.token.UserCacheHelper;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.mcookies.qxy.common.School.SchoolDBO;
import com.mcookies.qxy.common.School.SchoolService;
import com.mcookies.qxy.common.UParent.UParentDBO;
import com.mcookies.qxy.common.UParent.UParentService;
import com.mcookies.qxy.common.UStudentParent.UStudentParentDBO;
import com.mcookies.qxy.common.UStudentParent.UStudentParentPVO;
import com.mcookies.qxy.common.UStudentParent.UStudentParentService;
import com.mcookies.qxy.common.UTeacher.UTeacherDBO;
import com.mcookies.qxy.common.UTeacher.UTeacherService;
import com.mcookies.qxy.common.User.UserDBO;
import com.mcookies.qxy.common.User.UserService;

/**
 * 登陆操作
 * 
 * @author Spook
 * @version 0.1
 * @since 0.1.0 2014/2/8
 */
@Controller
public class LoginController extends MyControllerSupport {
	@Resource
	protected LoginBusiness LoginService_;
	@Resource
	protected UserSMSSendServiceImpl UserSMSSendServiceImpl_;
	@Resource
	protected UserService UserService_;
	@Resource
	protected SchoolService SchoolService_;
	@Resource
	protected SMSTemplateConfig SMSTemplateConfig_;
	@Resource
	protected UTeacherService UTeacherService_;
	@Resource
	protected UParentService UParentService_;
	@Resource
	protected UStudentParentService UStudentParentService_ ;
	
	private Long defualtSid = 999999999l;

	// public MyModelAndViewSupport getModelAndView() {
	// return new MyModelAndViewSupport("redirect:/");
	// }

	/**
	 * 用户名/手机号/邮箱密码登录接口
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional
	public RESTResultBean doLoginPOST(@RequestBody LoginPVO loginpvo, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		// 设定返回
		RESTResultBean rs = new RESTResultBean();
		if (logger.isDebugEnabled())
			logger.debug("loginpvo====///loginpvo////loginpvo=======>>>>>=========>>>" + loginpvo);
		/////////////////////// 登录系统/////////////////////////////////////
		LoginerBean loginer = new LoginerBean();
		loginer.setIpAdress(HttpRequestHelper.getClientRemoteIPAddr(request));
		loginer.setClientType(loginpvo.getClientType());
		loginer.setUserType(loginpvo.getUserType());
		loginer.setSchoolId(defualtSid);
		// 用户名
		if (StringUtils.isNotEmpty(loginpvo.getEmail())) {
			loginer.setAccount(loginpvo.getEmail());
		} else if (StringUtils.isNotEmpty(loginpvo.getPhone())) {
			loginer.setAccount(loginpvo.getPhone());
		} else if (StringUtils.isNotEmpty(loginpvo.getAccount())) {
			loginer.setAccount(loginpvo.getAccount());
		}
		// 判断是否为短信登录
		if (loginpvo.getClientType()==2) {
			if (StringUtils.isNotEmpty(loginpvo.getCaptcha())) {
				// 短信验证码校验
				int flag = SecurityCodeHelper.checkRandomCode(1, loginpvo.getCaptcha(), loginpvo.getPhone());
				if (flag == 1) {
					rs.setStatus(1);
					rs.setInfo("登陆失败，验证码错误");
					return rs;
				} else if (flag == 2) {
					rs.setStatus(1);
					rs.setInfo("登录失败，验证码已过期，请重新获取验证码");
					return rs;
				} else {
					// 登录系统
					HashMap<String, String> login = new HashMap<String, String>();
					login.put("phone", loginpvo.getPhone());
					List<UserBean> loginers = LoginService_.readLoginer(login);
					if (loginers == null) {
						rs.setStatus(2);
						rs.setInfo("用户不存在");
						return rs;
					} else if (loginers.size() == 0) {
						rs.setStatus(2);
						rs.setInfo("用户不存在");
						return rs;
					} else if (loginers.size() > 1) {
						rs.setStatus(3);// 多个用户存在
						rs.setInfo("用户资料异常，请联系管理员！");
						return rs;
					} else {
						UserBean user = loginers.get(0);
						user.setSchoolId(defualtSid);
						user.setClientType(loginpvo.getClientType());
						// 保存本次登录信息（缓存）
						LoginService_.doLoginToken(user, false);
						// 获取用户相关的 学校列表
						Map<String, Object> param = new HashMap<String, Object>();
						param.put("userType", loginpvo.getUserType());
						param.put("uid", user.getUserId());
						List<SchoolDBO> schools = SchoolService_.doSelectSchoolByTypeAndUid(param);
						rs.setStatus(0);
						JSONObject data = new JSONObject();
						data.put("info", "登陆成功");
						data.put("uid", user.getUserId());
						data.put("userType", loginpvo.getUserType());
						data.put("school", schools);
						data.put("token", user.getToken());
						rs.setData(data);
						return rs;
					}
				}
			} else {
				rs.setStatus(1);
				rs.setInfo("非法操作！");
				return rs;
			}
		} else {
			// 密码
			if (StringUtils.isNotEmpty(loginpvo.getPassword())) {
				loginer.setPassWord(loginpvo.getPassword());
				loginer.setSchoolId(defualtSid);
				// 登录系统
				UserBean user = LoginService_.doLogIn(loginer);
				// 登陆成功
				if ("0".equals(user.getLoginStatus())) {
					// 获取用户相关的 学校列表
					Map<String, Object> param = new HashMap<String, Object>();
					param.put("userType", loginpvo.getUserType());
					param.put("uid", user.getUserId());
					List<SchoolDBO> schools = SchoolService_.doSelectSchoolByTypeAndUid(param);
					rs.setStatus(0);
					JSONObject data = new JSONObject();
					data.put("info", "登陆成功");
					data.put("uid", user.getUserId());
					data.put("userType", loginpvo.getUserType());
					data.put("school", schools);
					data.put("token", user.getToken());
					rs.setData(data);
				}
				// 登陆失败
				else {
					rs.setStatus(Integer.parseInt(user.getLoginStatus()));
					JSONObject data = new JSONObject();
					data.put("info", getLoginStatusStr(user.getLoginStatus()));
					rs.setData(data);
					rs.setInfo(getLoginStatusStr(user.getLoginStatus()));
				}
				return rs;
			} else {
				rs.setStatus(1);
				rs.setInfo("非法操作！");
				return rs;
			}
		}
	}

	/**
	 * 选择登录学校及角色接口
	 * 
	 * @param sid
	 * @param userType 登录角色（1-教师；2-家长；3-学生）
	 * @param uid
	 * @param id_type值默认为0（0-普通教师；1-系统管理员；2-学校管理员）
	 * @return
	 */
	@RequestMapping(value = "/login/in", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	@Transactional
	public RESTResultBean loginInPOST(@RequestBody String jsonparam) {
		RESTResultBean rs = new RESTResultBean();
		try {
			JSONObject param = JSONObject.parseObject(jsonparam);
			String token = (String) param.get("token");
			Long sid = param.getLong("sid");
			String userType = (String) param.get("userType");
			// 设定返回
			// 二次登陆
			UserBean user = LoginService_.loadLoginer(token);
			if (user == null) {
				rs.setStatus(1);
				rs.setInfo("非法操作！");
				return rs;
			}
			user.setUserType(userType);
			user.setSchoolId(sid);
			user.setToken(EMPTY);
			// 二次登录系统
			LoginService_.makeLogIn(user, true);
//
			rs.setStatus(0);
			
			JSONObject data = new JSONObject();
			data.put("loginStatus", user.getLoginStatus());
			data.put("loginTime", user.getLoginTime());
			data.put("schoolId",user.getSchoolId());
			data.put("token", user.getToken());
			data.put("userId", user.getUserId());
			data.put("userType", userType);
			//获得学校名称
			SchoolDBO schl = new SchoolDBO();
			schl.setPuk("1");
			schl.setSid(sid);
			Long  a = schl.getSid();
//			Long sid2 = (long) 123456789;
			schl = (SchoolDBO) SchoolService_.doRead(schl);
			String schoolName = schl.getSchoolName();
			data.put("schoolName", schoolName);
			if ("1".equals(userType)) {				
				//获得老师相关信息
				UTeacherDBO condition = new UTeacherDBO();
//				condition.setSid(sid);
				condition.setUid(param.getLong("uid"));
				condition = (UTeacherDBO) UTeacherService_.doReadByUid(condition);
				if(condition == null || condition.getTid()== null){
					rs.setStatus(1);
					rs.setInfo("您还不是教师");
					return rs;
				}
				String teacherName = condition.getTeacherName();
				String phone = condition.getPhone();
				String email = condition.getEmail();
				Long tid = condition.getTid();
				data.put("teacherName", teacherName);
				data.put("phone", phone);
				data.put("email", email);		
				data.put("tid", tid);
				data.put("idType", condition.getIdType()==null?0:condition.getIdType());
			} else if ("2".equals(userType)) {
				//得到父母信息
				UParentDBO condition = new UParentDBO();
				condition.setUid(param.getLong("uid"));
				condition = (UParentDBO) UParentService_.doReadByUParentUid(condition);
				if( condition == null){
					rs.setStatus(1);
					rs.setInfo("您还不是家长");
					return rs;
				}
				data.put("parentId",condition.getParentId() );
				data.put("parentName",condition.getParentName() );
				data.put("phone",condition.getPhone() );
				//得到角色
				UStudentParentDBO uStudentParentDBO = new UStudentParentDBO();
				uStudentParentDBO.setParentId(condition.getParentId());
				uStudentParentDBO = (UStudentParentDBO) UStudentParentService_.doReadByParentId(uStudentParentDBO);
				data.put("role",uStudentParentDBO.getRole());
				//得到邮箱
				UserDBO userDBO = new UserDBO();
				userDBO.setUid(param.getLong("uid"));
				userDBO = (UserDBO) UserService_.doRead(userDBO);
				data.put("email",userDBO.getEmail());
				//获得学生信息List<UStudentParentPVO> doFindByParentId
				UStudentParentPVO uStudentParentPVO = new UStudentParentPVO();
				 uStudentParentPVO.setParentId(condition.getParentId());
				List<UStudentParentPVO> uStudentParentPVOs = UStudentParentService_.doFindByParentId(uStudentParentPVO);
//				ObjectArray objectArray = new ObjectArray();
				   ArrayList a1 = new ArrayList();
				for (UStudentParentPVO uStudentParentPVO2 : uStudentParentPVOs) {
					Map<String, Object> data2 = new HashMap<String, Object>();
//					JSONObject data2 = new JSONObject();
					data2.put("studentName", uStudentParentPVO2.getClassName());
					data2.put("studentId", uStudentParentPVO2.getStudentId());
					data2.put("cid", uStudentParentPVO2.getCid());
					data2.put("className", uStudentParentPVO2.getClassName());
					a1.add(data2);
				}
				data.put("studentInfo", a1);
			} else {
				
			}
			
//			Map<String, Object> param = new HashMap<String, Object>();
//			param.put("userType", loginpvo.getUserType());
//			param.put("uid", user.getUserId());
//			List<SchoolDBO> schools = SchoolService_.doSelectSchoolByTypeAndUid(param);
			
			rs.setData(data);
			
//			rs.setData(user);
			//rs.setToken(user.getToken());
			
			
//
//		rs.setData(user);
			//rs.setToken(user.getToken());
		} catch (Exception e) {
			rs.setStatus(2);
			rs.setInfo("失败");
		}
		System.out.println("====>>>>>" + rs);
		return rs;
	}

	/**
	 * 生成短信验证码接口
	 * 
	 * @param phone
	 * @param request
	 * @param response
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/captcha/create", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public RESTResultBean captchaCreatePOST(@RequestBody String jsonparam) {
		RESTResultBean rs = new RESTResultBean();
		try {
			JSONObject param = JSONObject.parseObject(jsonparam);
			String phone = param.getString("phone");
			if (StringUtils.isEmpty(phone)) {
				rs.setStatus(2);
				rs.setInfo("手机号码不能为空");
				return rs;
			}
			UserDBO user = new UserDBO();
			user.setPhone(phone);
			List<UserDBO> ulist = (List<UserDBO>) UserService_.doSelectData(user);
			if(ulist == null || ulist.size() == 0){
				rs.setStatus(2);
				rs.setInfo("您手机号码没有注册，请先注册");
				return rs;
			}
			String key = phone;
			// 产生验证码
			String captcha = SecurityCodeHelper.makeRandomNumCode(1800, 6, key);
			String effectiveTime = "00:30:00";
			String createTime = DateHelper.currentTimeMillisCN1();
			String message = SMSTemplateConfig_.getMessageWithTemplate("YZM",captcha);
			UserSMSSendServiceImpl_.send(defualtSid+"", phone,message, EMPTY);
			/*JSONObject data = new JSONObject();
			data.put("captcha", captcha);
			data.put("key", key);
			data.put("phone", phone);
			data.put("effectiveTime", effectiveTime);
			data.put("createTime", createTime);
			rs.setData(data);*/
		} catch (Exception e) {
			rs.setStatus(2);
			rs.setInfo("生成验证码失败");
		}
		return rs;
	}

	/**
	 * 忘记密码（手机）接口
	 * 
	 * @param phone
	 * @param captcha
	 * @return
	 */
	@RequestMapping(value = "/forgetPassword", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public RESTResultBean forgetPasswordPOST(@RequestBody String jsonparam) {
		RESTResultBean rs = new RESTResultBean();
		try {
			JSONObject param = JSONObject.parseObject(jsonparam);
			String phone = (String) param.get("phone");
			String captcha = (String) param.get("captcha");
			if (StringUtils.isEmpty(phone) || StringUtils.isEmpty(captcha)) {
				rs.setStatus(2);
				rs.setInfo("关键参数缺失");
				return rs;
			}
			// 验证码校验
			int flag = SecurityCodeHelper.checkRandomCode(1, captcha, phone);
			if (flag == 1) {
				rs.setStatus(1);
				rs.setInfo("验证失败，验证码错误");
			} else if (flag == 2) {
				rs.setStatus(1);
				rs.setInfo("验证失败，验证码已过期");
			} else if (flag == 0) {
				// 获取手机号对应的UID
				UserDBO user = new UserDBO();
				user.setPhone(phone);
				user.setPuk("1");
				List<UserDBO> users = (List<UserDBO>) UserService_.doSelectData(user);
				if (users != null && users.size() > 0) {
					JSONObject data = new JSONObject();
					data.put("info", "验证成功");
					data.put("uid", users.get(0).getUid());
					rs.setData(data);
					rs.setStatus(0);
				} else {
					rs.setStatus(1);
					rs.setInfo("验证失败，手机号不是系统用户");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			rs.setStatus(2);
			rs.setInfo("验证失败");
		}
		return rs;
	}

	/**
	 * 重置密码接口
	 * 
	 * @param uid
	 * @param newPassword
	 * @param repeatPassword
	 * @return
	 */
	@RequestMapping(value = "/resetPassword", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public RESTResultBean resetPasswordPOST(@RequestBody String jsonparam) {
		RESTResultBean rs = new RESTResultBean();
		try {
			JSONObject param = JSONObject.parseObject(jsonparam);
			Long uid = param.getLong("uid");
			String newPassword = param.getString("newPassword");
			if (uid == null || StringUtils.isEmpty(newPassword)) {
				rs.setStatus(2);
				rs.setInfo("密码修改失败，参数缺失");
				return rs;
			}
			UserDBO user = new UserDBO();
			user.setUid(uid);
			user.setPuk("1");
			user.setPassword(newPassword);
			int flag = UserService_.doUpdate(user);
			if (flag == 1) {
				rs.setStatus(0);
				rs.setInfo("重置密码成功");
			} else {
				rs.setStatus(1);
				rs.setInfo("重置密码失败");
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rs;
	}

	@RequestMapping(value = "/component", method = RequestMethod.GET, produces = "application/json;charset=utf-8")
	@ResponseBody
	public RESTResultBean componentGET(String token) {
		RESTResultBean rs = new RESTResultBean();
		try {
			// token校验
			if (doCheckToken(token) == false) {
				return tokenFail();
			}
			UserBean loginer = UserCacheHelper.removeUser(token);
			loginer.setLoginTime(UserBean.loginTime());
			loginer.setToken(EMPTY);
			String newtoken = loginer.getToken();
			UserCacheHelper.saveUser(loginer);
			JSONObject data = new JSONObject();
			data.put("authorizer_access_token", newtoken);
			data.put("expires_in", 7200);
			rs.setData(data);
		} catch (Exception e) {
			rs.setStatus(1);
			rs.setInfo("刷新token异常");
		}
		return rs;
	}
	
	/**
	 * 账户唯一性检查
	 * 
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/accountCheck", method = RequestMethod.POST)
	@ResponseBody
	public RESTResultBean accountCheckPOST(String account, String userType, HttpServletRequest request, HttpServletResponse response, HttpSession session) {
		RESTResultBean rs = new RESTResultBean();
		boolean only = LoginService_.accountCheck(account, userType, false);
		if (only == true) {

		}
		return rs;
	}

	// /**
	// * 用户登录(WEB页面)
	// *
	// * @param productId
	// * @return
	// */
	// @RequestMapping(value = "/00000000", method = RequestMethod.POST)
	// public MyModelAndViewSupport normalLoginPOST(LoginerBean loginer,
	// HttpServletRequest request, HttpServletResponse response, HttpSession
	// session) {
	// // 设定返回
	// MyModelAndViewSupport myPv = getModelAndView();
	//
	// if (logger.isDebugEnabled())
	// logger.debug("loginer====///loginer////loginer=======>>>>>=========>>>" +
	// loginer);
	//
	// // 画面校验码
	// if (StringUtils.isNotEmpty(loginer.getVerCode())) {
	// setSessionAttribute(RANDOM_CODE, session.getAttribute(RANDOM_CODE));
	//
	// if (!checkRandomCode(loginer.getVerCode())) {
	// // 错误返回
	// loginer.setCallBackUrl(loginer.getLoginUrl());
	// myPv.addObject(PAGE_MESSAGE, "验证码错误，请重新输入！");
	// myPv.addObject("loginer", loginer);
	// myPv.setViewName("callback");
	// return myPv;
	// }
	// }
	//
	// // 防伪验证码==MD5(产品ID+默认码+安全码)
	// {
	// String securityCode = loginer.getSecurityCode();// 用户SessionID
	// if (logger.isDebugEnabled())
	// logger.debug("securityCode===========>>>>>>>>>>>>>>>>>>>>>" +
	// securityCode);
	//
	// SecurityCodeService scs = null;
	// try {
	// scs = (SecurityCodeService)
	// BeanFactoryHelper.getBean("securityCodeService");
	// if (scs != null && scs.checkSecurityCode(loginer) == false) {
	// // 错误返回
	// loginer.setCallBackUrl(loginer.getLoginUrl());
	// loginer.setVerCode("安全码校验失败，请关闭后重新登录！");
	// myPv.addObject(PAGE_MESSAGE, loginer.getVerCode());
	// return myPv;
	// }
	// } catch (Exception e) {
	// }
	// }
	//
	// // MD5加密
	// String passWord = new
	// Md5PasswordEncoder().encodePassword(loginer.getPassWord(), null);
	// loginer.setPassWord(passWord);
	//
	// // 登录系统
	// RESTResultBean rs = LoginService_.login(loginer);
	// LoginerBean loginerResult = (LoginerBean) rs.getResult();
	// loginerResult.setCallBackUrl(loginer.getCallBackUrl());
	// loginerResult.setLoginUrl(loginer.getLoginUrl());
	// myPv.addObject("loginer", loginerResult);
	// myPv.setViewName("callback");
	//
	// if (logger.isDebugEnabled())
	// logger.debug("loginer====///loginer////loginer=======>>>>>=========>>>" +
	// loginer);
	//
	// // 成功判断
	// if (StringUtils.isEmpty(rs.getToken())) {
	// // 错误返回
	// loginer.setCallBackUrl(loginer.getLoginUrl());
	// myPv.addObject(PAGE_MESSAGE, rs.getMessage());
	// } else {
	// // 成功登录
	// LoginService_.doLogIn(loginerResult);
	// // Session保存
	// // session.setAttribute(CONSTANT_LOGINER, loginerResult);
	// // 清除验证码缓存
	// session.removeAttribute(RANDOM_CODE);
	// }
	// return myPv;
	// }

	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////
	// ////////////////////////////////////////////////////////////////////////////
	/**
	 * 登录用户退出系统
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public RESTResultBean loginOutGET(UserBean user, HttpSession session) {
		RESTResultBean rs = new RESTResultBean();
		// 销毁session
		session.invalidate();
		LoginService_.doLogOut(user);
		// 退出登录
		return rs;
	}

	/**
	 * 登录用户退出系统
	 * 
	 * @param token
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	public RESTResultBean loginOutPOST(UserBean user, HttpSession session) {
		RESTResultBean rs = new RESTResultBean();
		// 销毁session
		session.invalidate();
		LoginService_.doLogOut(user);

		// 退出登录
		return rs;
	}

	public String getLoginStatusStr(String status) {
		int state = Integer.parseInt(status);
		String value = "";
		/**
		 * 登陆结果<br>
		 * 0:成功 1:密码错误 2:用户不存在 3:二次登录8:用户类型未知 9:用户异常锁定
		 */
		switch (state) {
		case 0:
			value = "成功";
			break;
		case 1:
			value = "密码错误";
			break;
		case 2:
			value = "用户不存在";
			break;
		case 3:
			value = "二次登录";
			break;
		case 8:
			value = "用户类型未知";
			break;
		case 9:
			value = "用户异常锁定";
			break;
		default:
			break;
		}
		return value;
	}
	
	/**
	 * 文件上传
	 * 
	 * @param productId
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/file/upload66", method ={RequestMethod.GET, RequestMethod.POST}, produces = "application/json;charset=utf-8")
	
	public RESTResultBean fileUploadPOST66(@RequestParam(value = "savingType")  String savingType,  HttpServletRequest request, HttpServletResponse response) throws Exception {
		RESTResultBean tb = new RESTResultBean();
//		request.getPathInfo("savingType");
		 String savingType1 = request.getParameter(savingType);
//		String savingType = "cdnFile";
//		String savingType = "File";
//		String token = "1212403_94_05_76_17_58_49_9";
//		UserBean userBean = UserCacheHelper.checkUser(token);
//		RESTResultBean restResultBean = FileImageControl.fileUploadPOST(token, file2, request, response);
		FTPUtil ft = new FTPUtil();
//		String filePath = ft.uploadFile(file2,savingType);
//		logger.debug(token);
//		if (UserCacheHelper.checkUser(token) == null) {
//			OutputStream out = response.getOutputStream();
//			tb.setMessage("用户登录失败");
//			out.write(JSON.toJSONString(tb).getBytes("UTF-8"));
//			out.flush();
//			out.close();
//			return null;
//		}
//		logger.debug(file2.getOriginalFilename());
//		 file2.transferTo(new File(file2.getOriginalFilename()));
//		String filePath = FTPUtil_.uploadFile(file);
//		if (EmptyHelper.isEmpty(filePath)) {
//			tb.setCode("1");
//		} else {
//			tb.setResult(filePath);
//		}
		logger.debug(tb.toString());
		return tb;
	}
	
	/**
	 * 
	 * @param file ftp文件上传。
	 * @param type "0"表示需 要cdn加速,"1表示不需要加速的文件"
	 * @return  /file/upload?type=[type]&token=[token]
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/file/upload88", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
	@ResponseBody
	public RESTResultBean fileUploadFtp(@RequestParam("fileName") MultipartFile file,String token, String type, HttpServletRequest request, HttpServletResponse response) throws Exception{
		RESTResultBean tb = new RESTResultBean();
		logger.debug(token);
		if (UserCacheHelper.checkUser(token) == null) {
			OutputStream out = response.getOutputStream();
			tb.setMessage("用户登录失败");
			out.write(JSON.toJSONString(tb).getBytes("UTF-8"));
			out.flush();
			out.close();
			return null;
		}
		logger.debug(file.getOriginalFilename());
		// file.transferTo(new File(file.getOriginalFilename()));
		String filePath = new FTPUtil().uploadFile(file,type);
		if (EmptyHelper.isEmpty(filePath)) {
			tb.setCode("1");
		} else {
			tb.setResult(filePath);
		}
		logger.debug(tb.toString());
		return tb;
	}

}