package com.hhly.cms.utils;

import com.caucho.hessian.client.HessianProxyFactory;
import com.hhly.cmscore.cms.remote.service.IDemoCMSService;
import com.hhly.cmscore.cms.remote.service.ILotteryMgrService;
import com.hhly.cmscore.cms.remote.service.ISysMgrService;

import java.net.MalformedURLException;

/**
 * @author Bruce Liu
 * @create on: 2016-5-11 下午05:24:53
 * @describe :
 */
public class HessianUtils {
	/**
	 * 连接超时
	 */
	private static final long CONNECT_TIME_OUT = 10000;
	/**
	 * 读取超时
	 */
	private static final long READ_TIME_OUT = 20000;
	/**
	 * 远程接口所在目录
	 */
	public static final String LOTTO_CORE_URL = PropertiesHandler.getConfigValue("lotto_core_url");
	/**
	 * demo
	 */
	public static final String DEMO_REMOTE_URL = PropertiesHandler.getConfigValue("remote_demo_service");
	


	public static final String CMS_SYSMGR_URL = PropertiesHandler.getConfigValue("remote_cmsSysMgr_service");
	
	public static final String CMS_LOTTERYMGR_URL = PropertiesHandler.getConfigValue("remote_cmsLotteryMgr_service");
	/**
	 * 单实例
	 */
	private static HessianUtils hessian = null;

	public static HessianUtils getInstance() {
		if (hessian == null) {
			hessian = new HessianUtils();
		}
		return hessian;
	}

	/**
	 * @return
	 * @throws Exception
	 * @Desc 获取远程接口
	 */
	public IDemoCMSService getIDemoCMSService() throws Exception {
		return (IDemoCMSService) this.getRemoteService(IDemoCMSService.class, DEMO_REMOTE_URL);
	}

	/**
	 * @return
	 * @throws Exception
	 * @Desc 获取core系统，系统管理远程接口
	 */
	public ISysMgrService getISysMgrService() throws Exception {
		return (ISysMgrService) this.getRemoteService(ISysMgrService.class, CMS_SYSMGR_URL);
	}
	
	/**
	 * @return
	 * @throws Exception
	 * @Desc 获取core系统，系统管理远程接口
	 */
	public ILotteryMgrService getILotteryMgrService() throws Exception {
		return (ILotteryMgrService) this.getRemoteService(ILotteryMgrService.class, CMS_LOTTERYMGR_URL);
	}
	
	/**
	 * @param c
	 * @param url
	 * @return 接口对象
	 * @throws MalformedURLException
	 * 
	 */
	private Object getRemoteService(Class<?> c, String remoteUrl) throws MalformedURLException {
		HessianProxyFactory factory = new HessianProxyFactory();
		factory.setConnectTimeout(CONNECT_TIME_OUT);
		factory.setReadTimeout(READ_TIME_OUT);
		return factory.create(c, LOTTO_CORE_URL + remoteUrl);
	}

}
