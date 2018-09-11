package com.hhly.cms.operatemgr.service;

import com.hhly.cms.bo.TreeGridBO;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.operatemgr.bo.*;
import com.hhly.skeleton.cms.operatemgr.vo.*;
import com.hhly.skeleton.lotto.base.operate.bo.OperateCouponTempBO;
import com.hhly.skeleton.lotto.base.operate.bo.SportAgainstBO;

import java.io.ByteArrayOutputStream;
import java.util.List;


public interface ActivityService {
	/**
     * 分页查询
     * @param vo
     * @return
     */
	 PagingBO<OperateActivityBO> findActivity(OperateActivityVO vo);

    /**
     * 兑换码，分页
     *
     * @param vo
     * @return
     */
    PagingBO<OperateActivityCdkeyBO> findCdkeyList(OperateActivityCdkeyVO vo);

    /**
	 * 查询cdk各种记录
	 * @param vo
	 * @return
	 */
	OperateActivityCdkeyBO findCdkCount(OperateActivityCdkeyVO vo);

    /**
     * 修改
	  * @param vo
	  * @return
	  */
	 int updOperateActivity(OperateActivityVO vo);
	 /**
	  * 添加
	  * @param vo
	  * @return
	  */
	 int addOperateActivity(OperateActivityVO vo);
	 /**
	  * 查询详情
	  * @param id
	  * @return
	  */
	 OperateActivityBO findActivityDetail(int id);
	 /**
	  * 导出excel
	  * @param vo
	  * @return
	  */
	 ByteArrayOutputStream getExcelActivity(OperateActivityVO vo);
	 /**
	  * 获取树形菜单渠道
	  * @param channelId
	  * @return
	  */
	 List<TreeGridBO> findChannelList(String channelId);

	 /**
	  * 活动配置相关操作
	  */
	 /**
	  * 保存活动配置
	  * @param vo
	  * @return
	  * @date 2017年7月25日下午9:15:34
	  * @author cheng.chen
	  */
	 int mergeActivityConfig(OperateActivityConfigVO vo);

	/**
	 * 新增活动配置规则
	 * @param vo
	 * @return
	 */
	int mergeSportRule(OperateActivitySportRuleVO vo);
	 
	 /**
	  * 查询活动配置详情
	  * @param vo
	  * @return
	  * @date 2017年7月25日下午9:13:34
	  * @author cheng.chen
	  */
	 OperateActivityConfigBO findActivityConfigDetail(OperateActivityConfigVO vo);	 
	 
	 /**
	  * 增加活动规则
	  * @param vo
	  * @return
	  * @date 2017年7月25日下午3:37:13
	  * @author cheng.chen
	  */
	 int addActivityRule(OperateActivityRuleVO vo);
	 
	 /**
	  * 修改活动规则
	  * @param vo
	  * @return
	  * @date 2017年7月25日下午3:54:41
	  * @author cheng.chen
	  */
	 int updActivityRule(OperateActivityRuleVO vo);
	 
	 /**
	  * 删除活动规则
	  * @param id
	  * @return
	  * @date 2017年7月25日下午3:54:41
	  * @author cheng.chen
	  */
	 int delActivityRule(Integer id);	

	 /**
	  * 
	  * @return
	  * @date 2017年7月25日下午4:26:30
	  * @author cheng.chen
	  */
	 PagingBO<OperateActivityRuleBO> findActivityRuleList(OperateActivityRuleVO vo);
	 
	 /**
	  * 
	  * @return
	  * @date 2017年7月25日下午4:26:57
	  * @author cheng.chen
	  */
	 OperateActivityRuleBO findActivityRuleDetail(Integer id);

	/**
	 * 查询赛事详情
	 * @return
	 */
	PagingBO<SportAgainstBO> findSportAgainstInfo(SportAgainstVO vo);
	/**
	 * 查询未过期的活动组成下拉框
	 * @return
	 */
	List<DictionaryBO> findActivityStatusTrue();
	
	/**
	 * 插入抽奖详细信息
	 * @param vo
	 * @return
	 */
	OperateActivityAwardBO addPrizeAwardConfig(OperateActivityAwardVO vo);
	/**
	 * 删除抽奖详情
	 * @param vo
	 * @return
	 */
	int delPrizeAwardConfig(OperateActivityAwardVO vo);
	
	/**
	 * 插入追号详细信息
	 * @param vo
	 * @return
	 */
	OperateActivityAddedBO addAddCodeConfig(OperateActivityAddedVO vo);
	/**
	 * 删除追号详情
	 * @param vo
	 * @return
	 */
	int delAddCodeConfig(OperateActivityAddedVO vo);
	/**
	 * 更新追号彩期
	 * @param vo
	 * @return
	 */
	int updateAddCodeIssue(OperateActivityAddedVO vo);
	
	/**
	 * 更新充值送详细信息
	 * @param vo
	 * @return
	 */
	OperateActivityRechargeBO addRechargeConfig(OperateActivityRechargeVO vo);	
	 
	 /**
	  * 查询充值送详细信息
	  * @param vo
	  * @return
	  */
	ResultBO<List<OperateCouponTempBO>> findRechargeDetail(OperateActivityRechargeVO vo);

	/**
	 * 查询世界杯冠军竞猜球队信息
	 * @return
     * @param vo
	 */
	List<OperateActivityChpGuessBO> findChpTeams(OperateActivityChpGuessBO vo);

	/**
	 * 查询参与世界杯冠军竞猜的用户信息
	 * @return
	 */
    PagingBO<OperateActivityChpInfoBO> pageChpUsers(OperateActivityChpInfoBO vo);

	/**
	 * 导出参与世界杯冠军竞猜的用户信息
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream excelChpUsers(OperateActivityChpInfoBO vo);

	/**
	 * 添加世界杯冠军竞猜用户
	 * @param chpInfoes
	 * @return
	 */
    int addActivityChpInfo(List<OperateActivityChpInfoBO> chpInfoes);
}
