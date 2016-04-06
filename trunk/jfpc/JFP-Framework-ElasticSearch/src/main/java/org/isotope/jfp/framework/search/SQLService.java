package org.isotope.jfp.framework.search;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Resource;

import org.apache.ibatis.session.SqlSession;
import org.isotope.jfp.framework.constants.ISFrameworkConstants;
import org.isotope.jfp.framework.utils.BeanFactoryHelper;
import org.isotope.jfp.framework.utils.DateHelper;
import org.isotope.jfp.framework.utils.EmptyHelper;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.SqlSessionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

import io.searchbox.client.JestClient;
import io.searchbox.client.JestClientFactory;
import io.searchbox.client.JestResult;
import io.searchbox.client.config.HttpClientConfig;
import io.searchbox.core.Bulk;
import io.searchbox.core.Bulk.Builder;
import io.searchbox.core.BulkResult;
import io.searchbox.core.Index;
import io.searchbox.indices.CreateIndex;
import io.searchbox.indices.DeleteIndex;
import io.searchbox.indices.IndicesExists;

/**
 * 基于数据库表建立索引
 * 
 * @author 001745
 *
 */
public class SQLService implements ISFrameworkConstants {
	private Logger logger = LoggerFactory.getLogger(SQLService.class);

	/**
	 * 数据库连接
	 */
	private SqlSession mySqlSession;

	public SqlSession getMySqlSession() {
		if (mySqlSession == null)
			mySqlSession = BeanFactoryHelper.getBean("mySqlSession");
		return mySqlSession;
	}

	protected Set<String> serverList = new LinkedHashSet<String>();

	public Set<String> getServerList() {
		return serverList;
	}

	public void setServerList(Set<String> serverList) {
		this.serverList = serverList;
	}

	public SQLService() {
		this.serverList.add("http://localhost:9200");
	}

	/**
	 * 获得连接
	 * 
	 * @return
	 * @throws IOException
	 */
	private JestClient getClient() throws IOException {
		JestClientFactory factory = new JestClientFactory();
		factory.setHttpClientConfig(new HttpClientConfig.Builder(serverList).multiThreaded(true).build());
		return factory.getObject();
	}

	/**
	 * 获得用于执行静态 SQL 语句并返回它所生成结果的对象
	 * <p>
	 * 基于事物控制
	 * 
	 * @return Statement
	 * @throws SQLException
	 */
	protected Connection getConnection() throws SQLException {
		SqlSessionTemplate st = (SqlSessionTemplate) getMySqlSession();
		return SqlSessionUtils.getSqlSession(st.getSqlSessionFactory(), st.getExecutorType(), st.getPersistenceExceptionTranslator()).getConnection();
	}

	/**
	 * 分页查询并创建索引
	 * 
	 * @param tableName
	 * @param page
	 * @return
	 * @throws SQLException
	 */
	public void creatIndexBySQL(String sqlID) throws Exception {
		creatIndexBySQL(sqlID, EMPTY, EMPTY);
	}

	public void creatIndexBySQL(String sqlID, String from2, String size2) throws Exception {

		if (EmptyHelper.isNotEmpty(from2))
			from = Integer.parseInt(from2);
		if (EmptyHelper.isNotEmpty(size2))
			size = Integer.parseInt(size2);

		JestClient jestClient = getClient();

		String index = sqlID.toLowerCase();

		// 删除索引
		boolean indexExists = jestClient.execute(new IndicesExists.Builder(index).build()).isSucceeded();
		if (indexExists) {
			JestResult deleteIndexResult = jestClient.execute(new DeleteIndex.Builder(index).build());
			logger.debug("deleteIndex===>>>ErrorMessage=" + deleteIndexResult.getErrorMessage() + ",JsonString=" + deleteIndexResult.getJsonString());
		}
		JestResult createIndexResult = jestClient.execute(new CreateIndex.Builder(index).build());
		logger.debug("createIndex===>>>ErrorMessage=" + createIndexResult.getErrorMessage() + ",JsonString=" + createIndexResult.getJsonString());
		Builder bulkIndexBuilder;

		BulkResult result;
		int num = 0;
		List<Index> actions = null;
		boolean commit = false;
		for (int c = 0; c <= Integer.MAX_VALUE; c++) {
			while (commit == false) {
				try {
					actions = loadDataFromDb(index, sqlID, c, from);
					commit = true;
				} catch (Exception e) {
					Thread.sleep(sleep * 2);
				}
			}
			logger.debug("getData===>>>" + c * size);
			// 分批提交数据
			if (actions.size() > 0) {
				commit = false;
				while (commit == false) {
					try {
						jestClient = getClient();
						bulkIndexBuilder = new Bulk.Builder();
						bulkIndexBuilder.addAction(actions);
						result = jestClient.execute(bulkIndexBuilder.build());
						Thread.sleep(sleep);
						commit = true;
						num = num + actions.size();
						logger.debug("addDataIntoIndex===>>>num=" + num + ",ErrorMessage=" + result.getErrorMessage());
					} catch (Exception e) {
						logger.error("addDataIntoIndex===>>>" + e.getMessage());
						Thread.sleep(sleep * 2);
					}
				}
				commit = false;
			} else {
				break;
			}
		}
	}

	@Resource
	QuerySentence config;

	private List<Index> loadDataFromDb(String index, String sqlID, int page, int from) throws SQLException {
		List<Index> actions = new ArrayList<Index>();
		Connection conn = null;
		Statement stmt = null;
		ResultSet resultSet = null;
		ResultSetMetaData metaData = null;

		// 获得数据库对象
		try {
			conn = getConnection();
			conn.setAutoCommit(false);
			stmt = conn.createStatement();
			int start = from + page * size;

			String sql = config.getIndex(index);
			if (EmptyHelper.isEmpty(sql))
				throw new RuntimeException("不存在该索引语句");

			if (EmptyHelper.isEmpty(starttime))
				starttime = DateHelper.currentTimeMillisCN3();
			if (EmptyHelper.isEmpty(endtime))
				endtime = DateHelper.currentTimeMillisCN3();
			
			sql = sql.replace("{starttime}", starttime);// 开始时间
			sql = sql.replace("{endtime}", endtime);// 终了时间
			sql = sql.replace("{limit}", start + "," + size);// 分页限制

			resultSet = stmt.executeQuery(sql);
			metaData = resultSet.getMetaData();
			JSONObject data;
			// rs.beforeFirst();
			while (resultSet.next()) {
				data = new JSONObject();
				for (int i = 1; i <= metaData.getColumnCount(); i++) {
					String columnName = metaData.getColumnName(i);
					String value = resultSet.getString(i);
					data.put(columnName.toLowerCase(), value);
				}
				actions.add(new Index.Builder(data.toJSONString()).index(index).type(ElasticsearchPool.TYPE).build());
			}

		} catch (SQLException e) {
			throw e;
		} finally {
			// 关闭资源
			if (stmt != null) {
				stmt.close();
			}
			if (conn != null) {
				conn.close();
			}
		}
		return actions;
	}

	//////////////////////////////////////////////////////////

	String starttime = "";
	String endtime = "";

	public String getStarttime() {
		return starttime;
	}

	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}

	public String getEndtime() {
		return endtime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	private int from = 0;

	public int getFrom() {
		return from;
	}

	public void setFrom(int from) {
		this.from = from;
	}

	private int size = 1000;

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	private int sleep = 1000;

	public int getSleep() {
		return sleep;
	}

	public void setSleep(int sleep) {
		this.sleep = sleep;
	}
}
