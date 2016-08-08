package com.mcookies.qxy.common.News;
import org.isotope.jfp.framework.support.MyServiceSupport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/** 新闻表 */
@Service
public class NewsService extends MyServiceSupport {
	protected static final Logger logger = LoggerFactory.getLogger(NewsService.class);

	public NewsDao getDao() {
		return getMySqlSession().getMapper(NewsDao.class);
	}

}