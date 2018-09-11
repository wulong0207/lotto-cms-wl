package com.hhly.cms.customermgr.service;

import java.io.ByteArrayOutputStream;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.customermgr.bo.UserWinningStatisticsBO;
import com.hhly.skeleton.cms.customermgr.vo.UserWinningStatisticsVO;

/**
 * 
 * @desc 用户中奖统计服务
 * @author jiangwei
 * @date 2017年4月24日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface UserWinningStatisticsService {
    /**
     * 导出excel
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年4月24日 上午11:05:14
     * @param vo
     * @return
     */
	ByteArrayOutputStream listUserWinningStatisticsExcel(UserWinningStatisticsVO vo);
    /**
     * 分页查询
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年4月24日 上午11:05:30
     * @param vo
     * @return
     */
	PagingBO<UserWinningStatisticsBO> listUserWinningStatistics(UserWinningStatisticsVO vo);

}
