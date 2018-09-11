package com.hhly.activity.remote.service;

import com.hhly.skeleton.activity.vo.ActivityInfoVO;
import com.hhly.skeleton.base.bo.ResultBO;

public interface ReissueActivityService {
	/**
	 * 补发红包
	 * @param vo
	 * @return
	 */
	ResultBO<?> addReissue(ActivityInfoVO vo); 
}
