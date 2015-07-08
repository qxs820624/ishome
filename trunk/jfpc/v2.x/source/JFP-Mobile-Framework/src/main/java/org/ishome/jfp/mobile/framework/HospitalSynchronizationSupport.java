package org.ishome.jfp.mobile.framework;

import javax.annotation.Resource;

import org.ishome.jfp.framework.beands.RESTResultBean;
import org.ishome.jfp.framework.beands.ext.BaseHAO;
import org.ishome.jfp.framework.job.HospitalCloundKeyService;
import org.ishome.jfp.framework.mq.IMedMqService;
import org.ishome.jfp.framework.support.MyBusinessSupport;
import org.ishome.jfp.framework.utils.EmptyHelper;
import org.ishome.jfp.mobile.utils.HAOHelper;

import com.alibaba.fastjson.JSON;

/**
 * 接口控制层超类
 * 
 * @author Spook 
 * @version 2.3.1 2015/6/23
 * @since 2.3.1 2015/6/23
 */
public class HospitalSynchronizationSupport extends MyBusinessSupport {

	// 缓存队列
	@Resource
	protected IMedMqService mqService;
	
	/**
	 * 默认超时时间10s
	 */
	public int waitTime = 10;	
	
	public int getWaitTime() {
		return waitTime;
	}
	public void setWaitTime(int waitTime) {
		this.waitTime = waitTime;
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	/**
	 * 访问前置机进行业务处理
	 * @param hao 请求参数
	 * @return 处理结果
	 * @see #waitTime
	 */
	public RESTResultBean doHospitalDataSynchronization(BaseHAO hao){
		return doHospitalDataSynchronization(hao,waitTime);
	}
	/**
	 * 访问前置机进行业务处理
	 * @param hao 请求参数
	 * @param timeout 超时时间
	 * @return 处理结果
	 */
	public RESTResultBean doHospitalDataSynchronization(BaseHAO hao, int timeout){
		hao.setPuk(this.getOperationId());
		return doHospitalDataSynchronization(HAOHelper.getStringToRedis(hao), timeout);
	}
	/**
	 * 访问前置机进行业务处理
	 * @param jsonData 基于转换后的JSON字符串
	 * @param timeout 超时时间
	 * @return 处理结果
	 */
	public RESTResultBean doHospitalDataSynchronization(String jsonData, int timeout) {
		long t1 = System.currentTimeMillis();
		try {
			// 发送数据
			mqService.putObject(getHospitalSynchronizationKeyName(), jsonData, timeout, false);
			while (true) {
				Thread.sleep(200);
				// ////////////////////////////////////////////////
				// 处理结果返回
				String result = (String)mqService.getObject(this.getOperationId(), false);
				if (EmptyHelper.isNotEmpty(result)) {
					mqService.deleteObject(this.getOperationId());
					return JSON.parseObject(result, RESTResultBean.class);
				}				
				// ////////////////////////////////////////////////
				if (System.currentTimeMillis() - t1 > timeout * 1000) {
					RESTResultBean rs = new RESTResultBean();
					rs.setCode(SYSTEM_ERROR_CODE);
					rs.setResult(SYSTEM_ERROR_MESSAGE);
					return rs;
				}
			}
		} catch (Exception e) {
			RESTResultBean rs = new RESTResultBean();
			rs.setCode("9990");
			rs.setMessage("系统升级中，请稍后再试");
			return rs;
		} 
	}
	
	/**
	 * 获得当前业务请求队列（前置机使用）
	 * @return
	 */
	public String getHospitalSynchronizationKeyName(){
		return HospitalCloundKeyService.getHospitalSynchronizationKeyName(this.getCompanyId(), this.getBizName());
	}
}
