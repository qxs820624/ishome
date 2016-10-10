package com.mcookies.qxy.common.StudentRfid;

import org.isotope.jfp.framework.beans.page.PageVOSupport;
import org.isotope.jfp.framework.support.sync.MyServiceSupport;
import org.springframework.stereotype.Service;

import com.mcookies.qxy.biz.device.CardInfoPVO;

/** 学生一卡通关联表 */
@Service
public class StudentRfidService extends MyServiceSupport {

	public StudentRfidDao getDao() {
		return getMySqlSession().getMapper(StudentRfidDao.class);
	}
	/**
	 * 根据rfid获取学生信息
	 * @param formParamPageModel
	 * @return
	 */
	public PageVOSupport doSelectPageStudentByrfid(PageVOSupport formParamPageModel) {
		formParamPageModel.setPageListData(getDao().doSelectPageStudentByrfid(formParamPageModel));
		return formParamPageModel;
	}
	/**
	 * 设备对应学生学期班级学校信息查询
	 * @param sr
	 * @return
	 */
	public CardInfoPVO doReadCardInfoByRfid(StudentRfidDBO sr) {
		return getDao().doReadCardInfoByRfid(sr);
	}
}