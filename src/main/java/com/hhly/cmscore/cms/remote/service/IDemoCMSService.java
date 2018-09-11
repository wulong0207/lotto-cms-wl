package com.hhly.cmscore.cms.remote.service;

import com.hhly.skeleton.base.bo.DemoBO;
import com.hhly.skeleton.base.vo.DemoVO;



/**
 * @author Bruce Liu
 * @create on: 2016-5-10  上午10:48:58
 * @describe : 
 */
public interface IDemoCMSService {

	public void addDemo(DemoVO demoVO)throws Exception;
	
	public void delDemo(DemoVO demoVO)throws Exception;
	
	public void updDemo(DemoVO demoVO)throws Exception;
	
	public void saveDemo(DemoVO demoVO)throws Exception;
	
	public void doDemo()throws Exception;
	
	public DemoBO findDemoByName(DemoVO demoVO)throws Exception;
	
}
