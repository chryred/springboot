package com.shinsegae.smon.util;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

/**
 * Refreshable SqlSessionFactoryBean
 * @reference http://bryan7.tistory.com/117
 * @author 067828
 */
@SuppressWarnings("all")
public class RefreshableSqlSessionFactoryBean extends SqlSessionFactoryBean {
	
	/*
	public RefreshableSqlSessionFactoryBean() {
		super();
		LOG.debug("************* Initialized RefreshableSqlSessionFactoryBean : {}", this);
	}

   
	/** Common Logger */
	//private static final Logger LOG = LoggerFactory.getLogger(RefreshableSqlSessionFactoryBean.class);

	/** SQL Session Factory */
	private SqlSessionFactory proxy;

	/** Default Interval 0.5 secs */
	private int interval = 500;

	/** Timer */
	private Timer timer;

	/** Timer Task */
	private TimerTask task;

	/** Configure Resource */
	private Resource configLocation;

	/** Configure Mapper Location */
	private Resource[] mapperLocations;

	/** Configure Properties */
	private Properties configurationProperties;

	/**
	 * Set optional properties to be passed into the SqlSession configuration, as alternative to a
	 * {@code &lt;properties&gt;} tag in the configuration xml file. This will be used to resolve placeholders in the
	 * config file.
	 */
	public void setConfigurationProperties(Properties sqlSessionFactoryProperties) {
		super.setConfigurationProperties(sqlSessionFactoryProperties);
		this.configurationProperties = sqlSessionFactoryProperties;
	}

	/** running status */
	private boolean running = false;

	/** Re entrant Read and Write Lock */
	private final ReentrantReadWriteLock rwl = new ReentrantReadWriteLock();

	/** Read Lock */
	private final Lock r = rwl.readLock();

	/** Write Lock */
	private final Lock w = rwl.writeLock();

	/** Set Configuration Location */
	@Override
	public void setConfigLocation(Resource configLocation) {
		super.setConfigLocation(configLocation);
		this.configLocation = configLocation;
	}

	/** Set Mapper Locations */
	public void setMapperLocations(Resource[] mapperLocations) {
		super.setMapperLocations(mapperLocations);
		this.mapperLocations = mapperLocations.clone();
	}

	/** Set Check Interval */
	public void setInterval(int interval) {
		this.interval = interval;
	}

	/** Refresh */
	public void refresh() throws Exception {
		//LOG.info("refreshing SqlSessionFactory.");

		w.lock();

		try {
			super.afterPropertiesSet();
		} finally {
			w.unlock();
		}
	}

	/** */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
		setRefreshable();
	}

	/** set refreshable proxy */
	private void setRefreshable() {
		proxy = (SqlSessionFactory) Proxy.newProxyInstance(SqlSessionFactory.class.getClassLoader(),
			new Class[] { SqlSessionFactory.class }, new InvocationHandler() {
				public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
					return method.invoke(getParentObject(), args);
				}
			});

		task = new TimerTask() {
			private Map<Resource, Long> map = new HashMap<Resource, Long>();

			public void run() {
				if (isModified()) {
					try {
						refresh();
					} catch (Exception e) {
						//LOG.error("caught exception", e);
					}
				}
			}

			private boolean isModified() {
				boolean retVal = false;

				if (mapperLocations != null) {
					for (int i = 0; i < mapperLocations.length; i++) {
						Resource mappingLocation = mapperLocations[i];
						retVal |= findModifiedResource(mappingLocation);
						if (retVal) {
							break;
						}
					}
				} else if (configLocation != null) {
					Configuration configuration = null;

					XMLConfigBuilder xmlConfigBuilder = null;
					try {
						xmlConfigBuilder = new XMLConfigBuilder(configLocation.getInputStream(), null,
							configurationProperties);
						configuration = xmlConfigBuilder.getConfiguration();
					} catch (IOException e) {
						//LOG.error("Failed to parse config resource : {} {}", configLocation, e.getMessage());
					}

					if (xmlConfigBuilder != null) {
						try {
							xmlConfigBuilder.parse();

							Field loadedResourcesField = Configuration.class.getDeclaredField("loadedResources");
							loadedResourcesField.setAccessible(true);

							@SuppressWarnings("unchecked")
							Set<String> loadedResources = (Set<String>) loadedResourcesField.get(configuration);

							Iterator<String> iterator = loadedResources.iterator();
							while (iterator.hasNext()) {
								String resourceStr = (String) iterator.next();
								if (resourceStr.endsWith(".xml")) {
									Resource mappingLocation = new ClassPathResource(resourceStr);
									retVal |= findModifiedResource(mappingLocation);
									if (retVal) {
										break;
									}
								}
							}

						} catch (NoSuchFieldException nsfe) {
							//LOG.error("Failed to parse config resource : {} {}", configLocation, nsfe.getMessage());
						} catch (IllegalAccessException iae) {
							//LOG.error("Failed to parse config resource : {} {}", configLocation, iae.getMessage());
						} finally {
							ErrorContext.instance().reset();
						}
					}
				}

				return retVal;
			}

			private boolean findModifiedResource(Resource resource) {
				boolean retVal = false;
				List<String> modifiedResources = new ArrayList<String>();

				try {
					long modified = resource.lastModified();

					if (map.containsKey(resource)) {
						long lastModified = ((Long) map.get(resource)).longValue();

						if (lastModified != modified) {
							map.put(resource, Long.valueOf(modified));

							modifiedResources.add(resource.getDescription());

							retVal = true;
						}
					} else {
						map.put(resource, Long.valueOf(modified));
					}
				} catch (IOException e) {
					//LOG.error("caught exception", e);
				}

				if (retVal) {
					//LOG.info("modified files : " + modifiedResources);
				}

				return retVal;
			}
		};

		timer = new Timer(true);
		resetInterval();
	}

	private Object getParentObject() throws Exception {
		r.lock();

		try {
			return super.getObject();

		} finally {
			r.unlock();
		}
	}

	/**
	 * 
	 */
	public SqlSessionFactory getObject() {
		return this.proxy;
	}

	/**
	 * 
	 */
	public Class<? extends SqlSessionFactory> getObjectType() {
		return this.proxy != null ? this.proxy.getClass() : SqlSessionFactory.class;
	}

	/**
	 * 
	 * @param ms
	 */
	public void setCheckInterval(int ms) {
		interval = ms;

		if (timer != null) {
			resetInterval();
		}
	}

	private void resetInterval() {
		if (running) {
			timer.cancel();
			running = false;
		}

		if (interval > 0) {
			timer.schedule(task, 0, interval);
			running = true;
		}
	}

	/**
	 * 
	 * @throws Exception
	 */
	public void destroy() throws Exception {
		timer.cancel();
	}
}
