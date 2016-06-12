package org.isotope.jfp.framework.search.task;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import org.isotope.jfp.framework.search.ISSentenceConstants;
import org.isotope.jfp.framework.search.QuerySentence;
import org.isotope.jfp.framework.search.biz.DataService;
import org.isotope.jfp.framework.support.MyTaskSupport;
import org.isotope.jfp.framework.utils.EmptyHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataIndexDeleteServiceJob extends MyTaskSupport {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	QuerySentence myQuerySentence;

	public QuerySentence getMyQuerySentence() {
		return myQuerySentence;
	}

	public void setMyQuerySentence(QuerySentence myQuerySentence) {
		this.myQuerySentence = myQuerySentence;
	}

	Map<String, String> delKeys;

	public Map<String, String> getDelKeys() {
		return delKeys;
	}

	public void setDelKeys(Map<String, String> delKeys) {
		this.delKeys = delKeys;
	}

	/**
	 * Redis缓存空间索引
	 */
	int index = 0;

	public int getIndex() {
		return index;
	}

	public void setIndex(int index) {
		this.index = index;
	}

	DataService dataService;

	public DataService getDataService() {
		return dataService;
	}

	public void setDataService(DataService dataService) {
		this.dataService = dataService;
	}

	public boolean doProcessRepeat() throws Exception {
		logger.info("全文检索索引数据删除业务  >>>>>===== 开始");
		if (EmptyHelper.isEmpty(delKeys)) {
			logger.info("全文检索索引数据删除业务  xxxxxxxxxx 中断");
			return false;
		}
		myCacheService.selectDB(index);
		Iterator<Entry<String, String>> iter = delKeys.entrySet().iterator();
		String id;
		String idx = "";
		String key = "";
		ArrayList<String> datas;
		while (iter.hasNext()) {
			datas = new ArrayList<String>();
			Map.Entry<String, String> entry = iter.next();
			idx = entry.getKey();// 索引名字
			key = entry.getValue();// 缓存Key

			logger.info("全文检索索引数据删除业务  >>>>>===== 开始 ..... " + entry);
			if (myQuerySentence.containsIndex(idx) == false) {
				//myCacheService.removeKey(ISSentenceConstants.COMPANY_DEL + key);
				logger.info("全文检索索引数据删除业务  xxxxx===== 取消 ..... " + entry);
				continue;
			}
			id = (String) myCacheService.pollFirstObjectInList(ISSentenceConstants.COMPANY_DEL + key, false);
			while (EmptyHelper.isNotEmpty(id) == true) {
				datas.add(id);
				id = (String) myCacheService.pollFirstObjectInList(ISSentenceConstants.COMPANY_DEL + key, false);
				if(datas.size()>=100){
					dataService.deleteDataInIndex(idx, datas);
					datas = new ArrayList<String>();
					Thread.sleep(1000);
				}
			}
			if(datas.size()>0)
				dataService.deleteDataInIndex(idx, datas);
			logger.info("全文检索索引数据删除业务  =====<<<<< 结束 ..... " + entry);
		}

		myCacheService.init();
		logger.info("全文检索索引数据删除业务  <<<<<===== 结束");
		return true;
	}

}