package com.verilion.database;

import java.util.HashMap;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import com.verilion.scheduler.MessageHandlerScheduler;

/**
 * Singleton class to create and hold data source for connection pooling.
 * <P>
 * June 24, 2004
 * <P>
 * 
 * @author tsawler
 * 
 */
public class SingletonObjects {

	private DataSource ds = null;
	private String use_sef_yn = "";
	private String system_online_yn = "";
	private String mailhost = "";
	private String secure_port = "";
	private String insecure_port = "";
	private String database = "";
	private String upload_path = "";
	private int homepage_page_id = 0;
	private String host_name = "";
	private String site_description = "";
	private long memory_threshold;
	private String admin_email = "";
	private String session_timeout = "";
	private HashMap hmClassMap = new HashMap();
	private int cacheTimeout = 5;
	private String system_path = "";
	private MessageHandlerScheduler myScheduler = null;
	private int store_max_subcategories = 0;
	private String homepage = "";

	private static SingletonObjects _instance;

	private SingletonObjects() {
		try {
			initDataSource();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Check to see if an instance has been created. If so, use it. If not,
	 * create one (only one can exist at any given time).
	 * 
	 * @return ConnectionPool instance
	 */
	public static SingletonObjects getInstance() {
		synchronized (SingletonObjects.class) {

			if (_instance == null) {
				_instance = new SingletonObjects();
			}
		}
		return _instance;
	}

	/**
	 * Creates instance of datasource
	 * 
	 * @throws Exception
	 */
	public synchronized void initDataSource() throws Exception {

		Context ctx = new InitialContext();
		String jdbcValue = "";

		if (ctx == null) {
			throw new Exception("No context!");
		}

		Context envCtx = (Context) ctx.lookup("java:comp/env");
		jdbcValue = (String) envCtx.lookup("jdbcValue");
		System.out.println("jdbcValue = " + jdbcValue);

		try {
			ds = (DataSource) ctx.lookup("java:comp/env/" + jdbcValue);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * @return Returns the ds.
	 */
	public DataSource getDs() {
		return ds;
	}

	/**
	 * @param ds
	 *            The ds to set.
	 */
	public void setDs(DataSource ds) {
		this.ds = ds;
	}

	/**
	 * @return Returns the database.
	 */
	public String getDatabase() {
		return database;
	}

	/**
	 * @param database
	 *            The database to set.
	 */
	public void setDatabase(String database) {
		this.database = database;
	}

	/**
	 * @return Returns the homepage_page_id.
	 */
	public int getHomepage_page_id() {
		return homepage_page_id;
	}

	/**
	 * @param homepage_page_id
	 *            The homepage_page_id to set.
	 */
	public void setHomepage_page_id(int homepage_page_id) {
		this.homepage_page_id = homepage_page_id;
	}

	/**
	 * @return Returns the host_name.
	 */
	public String getHost_name() {
		return host_name;
	}

	/**
	 * @param host_name
	 *            The host_name to set.
	 */
	public void setHost_name(String host_name) {
		this.host_name = host_name;
	}

	/**
	 * @return Returns the insecure_port.
	 */
	public String getInsecure_port() {
		return insecure_port;
	}

	/**
	 * @param insecure_port
	 *            The insecure_port to set.
	 */
	public void setInsecure_port(String insecure_port) {
		this.insecure_port = insecure_port;
	}

	/**
	 * @return Returns the mailhost.
	 */
	public String getMailhost() {
		return mailhost;
	}

	/**
	 * @param mailhost
	 *            The mailhost to set.
	 */
	public void setMailhost(String mailhost) {
		this.mailhost = mailhost;
	}

	/**
	 * @return Returns the secure_port.
	 */
	public String getSecure_port() {
		return secure_port;
	}

	/**
	 * @param secure_port
	 *            The secure_port to set.
	 */
	public void setSecure_port(String secure_port) {
		this.secure_port = secure_port;
	}

	/**
	 * @return Returns the system_online_yn.
	 */
	public String getSystem_online_yn() {
		return system_online_yn;
	}

	/**
	 * @param system_online_yn
	 *            The system_online_yn to set.
	 */
	public void setSystem_online_yn(String system_online_yn) {
		this.system_online_yn = system_online_yn;
	}

	/**
	 * @return Returns the upload_path.
	 */
	public String getUpload_path() {
		return upload_path;
	}

	/**
	 * @param upload_path
	 *            The upload_path to set.
	 */
	public void setUpload_path(String upload_path) {
		this.upload_path = upload_path;
	}

	/**
	 * @return Returns the use_sef_yn.
	 */
	public String getUse_sef_yn() {
		return use_sef_yn;
	}

	/**
	 * @param use_sef_yn
	 *            The use_sef_yn to set.
	 */
	public void setUse_sef_yn(String use_sef_yn) {
		this.use_sef_yn = use_sef_yn;
	}

	/**
	 * @return Returns the site_description.
	 */
	public String getSite_description() {
		return site_description;
	}

	/**
	 * @param site_description
	 *            The site_description to set.
	 */
	public void setSite_description(String site_description) {
		this.site_description = site_description;
	}

	/**
	 * @return Returns the memory_threshold.
	 */
	public long getMemory_threshold() {
		return memory_threshold;
	}

	/**
	 * @param memory_threshold
	 *            The memory_threshold to set.
	 */
	public void setMemory_threshold(long memory_threshold) {
		this.memory_threshold = memory_threshold;
	}

	/**
	 * @return Returns the admin_email.
	 */
	public String getAdmin_email() {
		return admin_email;
	}

	/**
	 * @param admin_email
	 *            The admin_email to set.
	 */
	public void setAdmin_email(String admin_email) {
		this.admin_email = admin_email;
	}

	/**
	 * @return Returns the session_timeout.
	 */
	public String getSession_timeout() {
		return session_timeout;
	}

	/**
	 * @param session_timeout
	 *            The session_timeout to set.
	 */
	public void setSession_timeout(String session_timeout) {
		this.session_timeout = session_timeout;
	}

	/**
	 * @return Returns the hmClassMap.
	 */
	public HashMap getHmClassMap() {
		return hmClassMap;
	}

	/**
	 * @param hmClassMap
	 *            The hmClassMap to set.
	 */
	public void setHmClassMap(HashMap hmClassMap) {
		this.hmClassMap = hmClassMap;
	}

	/**
	 * @return Returns the cacheTimeout.
	 */
	public int getCacheTimeout() {
		return cacheTimeout;
	}

	/**
	 * @param cacheTimeout
	 *            The cacheTimeout to set.
	 */
	public void setCacheTimeout(int cacheTimeout) {
		this.cacheTimeout = cacheTimeout;
	}

	/**
	 * @return Returns the system_path.
	 */
	public String getSystem_path() {
		return system_path;
	}

	/**
	 * @param system_path
	 *            The system_path to set.
	 */
	public void setSystem_path(String system_path) {
		this.system_path = system_path;
	}

	/**
	 * @return Returns the myScheduler.
	 */
	public MessageHandlerScheduler getMyScheduler() {
		return myScheduler;
	}

	/**
	 * @param myScheduler
	 *            The myScheduler to set.
	 */
	public void setMyScheduler(MessageHandlerScheduler myScheduler) {
		this.myScheduler = myScheduler;
	}

	/**
	 * @return Returns the store_max_subcategories.
	 */
	public int getStore_max_subcategories() {
		return store_max_subcategories;
	}

	/**
	 * @param store_max_subcategories
	 *            The store_max_subcategories to set.
	 */
	public void setStore_max_subcategories(int store_max_subcategories) {
		this.store_max_subcategories = store_max_subcategories;
	}

	public String getHomepage() {
		return homepage;
	}

	public void setHomepage(String homepage) {
		this.homepage = homepage;
	}
}