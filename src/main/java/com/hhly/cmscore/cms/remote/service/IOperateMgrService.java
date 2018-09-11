package com.hhly.cmscore.cms.remote.service;

import java.io.IOException;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityAddedBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityAwardBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityCdkeyBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityChpGuessBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityChpInfoBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityConfigBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityExcelBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityRechargeBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateActivityRuleBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateAdBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateAdMenuBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateAdTypeBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateArticleBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateArticleTypeBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateCouponBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateCouponExcelBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateFastBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateFastInfoBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateHelpBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateHelpCorrectBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateHelpTypeBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateImgBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateLotteryBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateLotteryInfoBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMarketChannelBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMarketChannelExcelBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgConfigBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgInfoBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgLotteryConfigBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgNewBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgTemplateBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateRebateUserBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateSoftwareVersionBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateUserAnalysisBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateWechatTemplateBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateActivityAddedVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateActivityAwardVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateActivityCdkeyVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateActivityConfigVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateActivityRechargeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateActivityRuleVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateActivitySportRuleVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateActivityVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateAdMenuVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateAdTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateAdVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateArticleTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateArticleVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateCouponVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateFastInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateFastVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpCorrectVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateImgVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateLotteryInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateLotteryVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMarketChannelVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgConfigVo;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgLotteryConfigVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgNewVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgTemplateVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateRebateUserVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateSoftwareVersionVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateUserAnalysisVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateWechatTemplateVO;
import com.hhly.skeleton.cms.operatemgr.vo.SportAgainstVO;
import com.hhly.skeleton.lotto.base.operate.bo.OperateCouponTempBO;
import com.hhly.skeleton.lotto.base.operate.bo.SportAgainstBO;
import com.hhly.skeleton.lotto.base.operate.vo.OperateCouponTempVO;
import com.hhly.skeleton.user.bo.UserInfoBO;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-12-7 上午10:12:17
 * @Desc 运营管理
 */
/**
 * @desc
 * @author jiangwei
 * @date 2017年4月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
/**
 * @desc
 * @author jiangwei
 * @date 2017年4月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface IOperateMgrService {
	List<OperateMarketChannelBO> findChannelAllSimple();

	/**
	 * 分页查询
	 * 
	 * @param vo
	 * @return
	 */
	PagingBO<OperateMarketChannelBO> findMarketChannel(OperateMarketChannelVO vo);

	/**
	 * 根据级别查询父级ID
	 * 
	 * @param vo
	 * @return
	 */
	List<String> findParentId(StringVO vo);

	/**
	 * 修改渠道
	 * 
	 * @param vo
	 * @return
	 */
	int updOperateMarketChannel(OperateMarketChannelVO vo);

	/**
	 * 添加渠道
	 * 
	 * @param vo
	 * @return
	 */
	int addOperateMarketChannel(OperateMarketChannelVO vo);

	/**
	 * 导出excel
	 * @param vo
	 * @return
	 */
	List<OperateMarketChannelExcelBO> findExcelMarketChannel(
			OperateMarketChannelVO vo);
	
	/**
	 * @desc   查询符合条件的渠道数
	 * @author Tony Wang
	 * @create 2017年10月26日
	 * @param vo
	 * @return 
	 */
	int countMarketchannel(OperateMarketChannelVO vo);

	/**
	 * @desc   更新app_url字段
	 * @author Tony Wang
	 * @create 2017年10月26日
	 * @param vo 
	 */
	int updMarketchannelAppUrl(OperateMarketChannelVO vo);
	
	/**
 	 * 运营活动分页
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
     * 修改活动
	  * @param vo
	  * @return
	  */
	 int updOperateActivity(OperateActivityVO vo);
	 /**
	  * 添加活动
	  * @param vo
	  * @return
	  */
	 int addOperateActivity(OperateActivityVO vo);
	 		 
	 /**
	  * 导出excel
	  * @param vo
	  * @return
	  */
	 List<OperateActivityExcelBO> findExcelActivity(OperateActivityVO vo);
	 
	 /**
	  * 保存活动配置详情
	  * @param vo
	  * @return
	  * @date 2017年7月25日下午9:15:13
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
	  * 根据ID查询详情
	  * @param id
	  * @return
	  */
	 OperateActivityBO findOperateActivityDetail(int id);
	 
	 /**
	  * 分页查询优惠券
	  * @param vo
	  * @return
	  */
	 PagingBO<OperateCouponBO> findCoupon(OperateCouponVO vo);
	 /**
	  * 查询优惠券详情
	  * @param vo
	  * @return
	  */
	 OperateCouponBO findCouponDetail(StringVO vo);
	 /**
	  * 导出excel
	  * @param vo
	  * @return
	  */
	 List<OperateCouponExcelBO> finCouponExcel(OperateCouponVO vo);
	 /**
	  * 修改红包信息
	  * @param vo
	  * @return
	  */
	 int updOperateCoupon(OperateCouponVO vo);
	 	 
	/************************** 广告图管理相关  start*****************************/
	/**
	 * @desc   查询广告列表
	 * @author Tony Wang
	 * @create 2017年2月8日
	 * @param vo
	 * @return  
	 */
	 PagingBO<OperateAdBO> findOperateAd(OperateAdVO vo);
	 
	/**
	 * @desc   vo有id则update广告信息，无则insert
	 * @author Tony Wang
	 * @create 2017年2月16日
	 * @param vo
	 * @return  
	 
	int mergeOperateAd(OperateAdVO vo);*/
	
	/**
	 * @desc   更新广告
	 * @author Tony Wang
	 * @create 2017年4月6日
	 * @param vo
	 * @return 
	 */
	int udpateOperateAd(OperateAdVO vo);

	/**
	 * @desc   添加广告
	 * @author Tony Wang
	 * @create 2017年4月6日
	 * @param vo
	 * @return 
	 */
	int addOperateAd(OperateAdVO vo);
	
	/**
	 * @desc   查询广告类型
	 * @author Tony Wang
	 * @create 2017年4月25日
	 * @param vo
	 * @return 
	 */
	List<OperateAdTypeBO> listAdType(OperateAdTypeVO vo);
	

	/**
	 * @desc   广告图页面列表
	 * @author Tony Wang
	 * @create 2017年5月10日
	 * @param vo
	 * @return 
	 */
	PagingBO<OperateAdMenuBO> listAdMenu(OperateAdMenuVO vo);

	/**
	 * @desc   增加或更新广告图页面
	 * @author Tony Wang
	 * @create 2017年5月10日
	 * @param vo
	 * @return 
	 */
	int mergeAdMenu(OperateAdMenuVO vo);

	/**
	 * @desc   删除广告页面
	 * @author Tony Wang
	 * @create 2017年5月10日
	 * @param vo
	 * @return 
	 */
	int deleteAdMenu(OperateAdMenuVO vo);
	
	/**
	 * @desc   查询广告页面
	 * @author Tony Wang
	 * @create 2017年5月11日
	 * @param vo
	 * @return 
	 */
	List<OperateAdMenuBO> findAdMenus(OperateAdMenuVO vo);

	/**
	 * @desc   返回广告最大编号
	 * @author Tony Wang
	 * @create 2017年7月25日
	 * @param today
	 * @return 
	 */
	String findMaxAdvCode(String today);
	/************************** 广告图管理相关  end*****************************/
	
	/************************** 彩种运营管理相关  start*****************************/
	/**
	 * @desc   分页查询彩种运营信息
	 * @author Tony Wang
	 * @create 2017年2月17日
	 * @param vo
	 * @return  
	 */
	PagingBO<OperateLotteryBO> findOperateLottery(OperateLotteryVO vo);

	/**
	 * @desc   查询彩种运营详情信息
	 * @author Tony Wang
	 * @create 2017年2月21日
	 * @param vo
	 * @return  
	 */
	List<OperateLotteryInfoBO> findOperateLotteryInfo(OperateLotteryInfoVO vo);
	
	/**
	 * @desc   vo有id则update彩种运营信息，无则insert
	 * @author Tony Wang
	 * @create 2017年2月17日
	 * @param vo
	 * @return  
	int mergeOperateLottery(OperateLotteryVO vo);
	*/
	
	/**
	 * @desc   增加彩种运营信息
	 * @author Tony Wang
	 * @create 2017年2月20日
	 * @param vo
	 * @return
	 */
	int addOperateLottery(OperateLotteryVO vo);

	/**
	 * @desc   更新彩种运营信息
	 * @author Tony Wang
	 * @create 2017年2月20日
	 * @param vo
	 * @return
	 */
	int updateOperateLottery(OperateLotteryVO vo);
	
	/**
	 * @desc   查询符合条件的运营方案数量
	 * @author Tony Wang
	 * @create 2017年2月22日
	 * @param vo
	 * @return  
	 */
	int countOperateLottery(OperateLotteryVO vo);
	
	/**
	 * @desc   查询最大编号
	 * @author Tony Wang
	 * @create 2017年7月25日
	 * @param today
	 * @return 
	 */
	String findMaxOperateLotteryReleaseCode(String today);
	/************************** 彩种运营管理相关 	 end*****************************/
	
	/************************** 会员访问信息分析相关 *****************************/
	/**
	 * @desc 查询分页列表
	 * @author huangb
	 * @date 2017年2月10日
	 * @param operateUserAnalysis
	 *            参数对象
	 * @return 查询分页列表
	 */
	PagingBO<OperateUserAnalysisBO> findPagingUserAnalysis(OperateUserAnalysisVO operateUserAnalysis);

	/**
	 * @desc 查询excel导出的数据
	 * @author huangb
	 * @date 2017年2月10日
	 * @param operateUserAnalysis
	 *            查询参数
	 * @return excel导出的数据
	 */
	List<OperateUserAnalysisBO> findExcelUserAnalysis(OperateUserAnalysisVO operateUserAnalysis);
	/************************** 会员访问信息分析相关 *****************************/


	/************************** 快投模块管理相关  start*****************************/
	/**
	 * @desc   分页查询快投模块信息
	 * @author lidecheng
	 * @create 2017年2月24日
	 * @param vo
	 * @return  
	 */
	PagingBO<OperateFastBO> findOperateFast(OperateFastVO vo);

	/**
	 * @desc   分页查询快投模块详情信息
	 * @author lidecheng
	 * @create 2017年2月24日
	 * @param vo
	 * @return  
	 */
	PagingBO<OperateFastInfoBO> findOperateFastInfo(OperateFastInfoVO vo);
	
	
	/**
	 * @desc   增加快投模块信息
	 * @author lidecheng
	 * @create 2017年2月24日
	 * @param vo
	 * @return
	 */
	int addOperateFast(OperateFastVO vo);

	/**
	 * @desc   更新快投模块信息
	 * @author lidecheng
	 * @create 2017年2月24日
	 * @param vo
	 * @return
	 */
	int updateOperateFast(OperateFastVO vo);
	
	/**
	 * @desc   查询符合条件的快投模块方案数量
	 * @author lidecheng
	 * @create 2017年2月24日
	 * @param vo
	 * @return  
	 */
	int countOperateFast(OperateFastVO vo);
	/************************** 快投模块管理相关 	 end*****************************/
	
	/**
	 * 分页查询
	 * 
	 * @param vo
	 * @return
	 */
	PagingBO<OperateMsgInfoBO> findMsgInfo(OperateMsgInfoVO vo);
	
	 /**
	  * 
	  * @Desc   通知信息模板查询
	  * @Author HouXB
	  * @Date   2017年3月3日 下午4:02:57
	  * @param vo
	  * @return
	  */
	 PagingBO<OperateMsgTemplateBO> findMsgTemplatePage(OperateMsgTemplateVO vo);
	 
	 /**
	  * 
	  * @Desc   通知信息模板查询
	  * @Author HouXB
	  * @Date   2017年3月3日 下午4:02:57
	  * @param vo
	  * @return
	  */
	 List<OperateMsgTemplateBO> findMsgTemplate(OperateMsgTemplateVO vo);
	 
	 /**
	  * 微信公众号模板查询
	  * @Desc   
	  * @Author HouXB
	  * @Date   2017年3月3日 下午4:04:15
	  * @param vo
	  * @return
	  */
	 List<OperateWechatTemplateBO> findWechatTemplate(OperateWechatTemplateVO vo);

	/************************** 文章管理相关 	 start***************************/
	/**
	 * @desc   新增或更新文章栏目
	 * @author Tony Wang
	 * @create 2017年3月2日
	 * @param vo
	 * @return  
	 */
	int mergeArticleType(OperateArticleTypeVO vo);
	
	/**
	 * @desc   查询文章栏目
	 * @author Tony Wang
	 * @create 2017年3月2日
	 * @return  
	 */
	List<OperateArticleTypeBO> listArticleType();
	
	/**
	 * @desc   查询文章栏目
	 * @author Tony Wang
	 * @create 2017年6月5日
	 * @param criteria
	 * @return 
	 */
	OperateArticleTypeBO findArticleType(OperateArticleTypeVO criteria);

	/**
	 * @desc   查询文章
	 * @author Tony Wang
	 * @create 2017年3月3日
	 * @param vo
	 * @return 
	 */
	PagingBO<OperateArticleBO> listArticle(OperateArticleVO vo);

	/**
	 * @desc   增加或更新文章
	 * @author Tony Wang
	 * @create 2017年3月8日
	 * @param vo
	 * @return 
	 
	int mergeArticle(OperateArticleVO vo);*/
	
	/**
	 * @desc   增加文章
	 * @author Tony Wang
	 * @create 2017年3月17日
	 * @param vo
	 * @return 
	 */
	int addArticle(OperateArticleVO vo) throws IOException;

	/**
	 * @desc   更新文章
	 * @author Tony Wang
	 * @create 2017年4月12日
	 * @param vo
	 * @return 
	 */
	int updateArticle(OperateArticleVO vo) throws IOException;

	/**
	 * @desc   修改文章状态
	 * @author Tony Wang
	 * @create 2017年4月14日
	 * @param vo
	 * @return 
	 */
	int updateArticleStatus(OperateArticleVO vo);

	/**
	 * @desc   更新文章栏目顺序
	 * @author Tony Wang
	 * @create 2017年4月17日
	 * @param vos
	 * @return 
	 */
	int updateArticleTypeOrder(List<OperateArticleTypeVO> vos);
	
	/**
	 * @desc   查询文章的所有父栏目名称
	 * @author Tony Wang
	 * @create 2017年5月23日
	 * @param vo
	 * @return 
	 */
	List<String> findArticleParentPaths(OperateArticleVO vo);
	
	/**
	 * @desc   查询最大的
	 * @author Tony Wang
	 * @create 2017年5月24日
	 * @param articleIdLike，作like 'xxx%'条件查询
	 * @return 
	 */
	String findMaxArticleId(String articleIdLike);
	
	 /**
	 * @desc   查询文章
	 * @author Tony Wang
	 * @create 2017年8月28日
	 * @param vo
	 * @return 
	 */
	List<OperateArticleBO> findArticle(OperateArticleVO vo);
	/************************** 文章管理相关 	 end*****************************/
	
	/**
	 * 
	 * @Desc   查询通知信息详情
	 * @Author HouXB
	 * @Date   2017年3月8日 上午10:45:20
	 * @param id
	 * @return
	 */
	OperateMsgInfoBO findMsgInfoById(int id);
	
	/**
	 * 
	 * @Desc   查询信息模板详情
	 * @Author HouXB
	 * @Date   2017年3月8日 上午10:45:37
	 * @param id
	 * @return
	 */
	OperateMsgTemplateBO findMsgTemplateById(int id);
	
	/**
	 * 
	 * @Desc   查询微信公众号模板设置详情
	 * @Author HouXB
	 * @Date   2017年3月8日 上午10:45:52
	 * @param id
	 * @return
	 */
	OperateWechatTemplateBO findWechatTemplateById(int id);
	
	/**
	 * 
	 * @Desc   新增通知消息
	 * @Author HouXB
	 * @Date   2017年3月8日 下午12:18:13
	 * @param po
	 * @return
	 */
	int addMsgInfo(OperateMsgInfoVO vo,List<String> uploadUser);
	
	/**
	 * 
	 * @Desc   新增消息模板
	 * @Author HouXB
	 * @Date   2017年3月8日 下午12:18:41
	 * @param po
	 * @return
	 */
	int addMsgTemplate(OperateMsgTemplateVO vo);
	
	/**
	 * 
	 * @Desc   新增微信公众号设置模板
	 * @Author HouXB
	 * @Date   2017年3月8日 下午12:18:53
	 * @param po
	 * @return
	 */
	int addWechatTemplate(OperateWechatTemplateVO vo);
	
	/**
	 * 
	 * @Desc   更新消息状态
	 * @Author HouXB
	 * @Date   2017年3月8日 下午12:21:46
	 * @param list
	 * @return
	 */
	int updateMsgStatus(List<OperateMsgInfoVO> list);
	
	/**
	 * 
	 * @Desc   更新模板
	 * @Author HouXB
	 * @Date   2017年3月8日 下午12:22:03
	 * @param po
	 * @return
	 */
	int updateMsgTemplate(OperateMsgTemplateVO vo);
	
	/**
	 * 
	 * @Desc   设置微信公众号模板
	 * @Author HouXB
	 * @Date   2017年3月8日 下午12:22:20
	 * @param po
	 * @return
	 */
	int updateWechatTemplate(OperateWechatTemplateVO vo);
	
	/**
	 * 
	 * @Desc   根据用户ID查询用户配置信息
	 * @Author HouXB
	 * @Date   2017年3月11日 上午9:53:58
	 * @param userId
	 * @return
	 */
	List<OperateMsgConfigBO> findConfigByUserId(int userId);
	
	/**
	 * 
	 * @Desc   更新发布消息
	 * @Author HouXB
	 * @Date   2017年3月8日 下午12:22:03
	 * @param vo
	 * @return
	 */
	int updateNewMsg(OperateMsgNewVO vo,List<String> userList);
	
	/**
	 * 
	 * @Desc   发布消息
	 * @Author HouXB
	 * @Date   2017年3月8日 下午12:18:41
	 * @param vo
	 * @return
	 */
	int addNewMsg(OperateMsgNewVO vo,List<String> userList);
	/**
     * 分页查询
     * @param vo
     * @return
     */
	 PagingBO<OperateMsgNewBO> findNewMsg(OperateMsgNewVO vo);

	/**
	 * CMS分页查询总条数
	 * 
	 * @param vo
	 * @return
	 */
	int findNewMsgTotal(OperateMsgNewVO vo);

	/**
	 * @desc   查询单篇文章
	 * @author Tony Wang
	 * @create 2017年3月21日
	 * @param operateArticleVO
	 * @return 
	 */
	OperateArticleBO findSingleArticle(OperateArticleVO vo);
	
	/**
	 * 查询发布信息详情
	 * @param id
	 * @return
	 */
	OperateMsgNewBO findNewMsgById(@Param("id") int id);
    /**
     * 分页查询图片信息
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年4月10日 上午10:13:30
     * @param vo
     * @return
     */
	PagingBO<OperateImgBO> listOperateImg(OperateImgVO vo);
	/**
	 * 添加图片
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:33:58
	 * @param vos
	 * @return
	 */
	int addOperateImg(List<OperateImgVO> vos);
	/**
	 * 修改图片
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:34:13
	 * @param vo
	 * @return
	 */
	int updOperateImg(OperateImgVO vo);
	/**
	 * 查询图片
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:34:22
	 * @param id
	 * @return
	 */
	List<OperateImgBO> listOperateImgInfo(List<String> id);
	/**
	 * 删除图片
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:34:45
	 * @param id
	 * @return
	 */
	int delOperateImg(List<String> id);
	/**
	 * 移动图片
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:35:02
	 * @param vo
	 * @return
	 */
	int updOperateImgMove(OperateImgVO vo);
    
	/**
	 * 查询图片信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月14日 下午12:15:33
	 * @param vo
	 * @return
	 */
	OperateImgBO getOperateImg(OperateImgVO vo);
	
	/**
	 * 查询意见箱集合
	 * @author chen cheng
	 * @create 2017年4月27日
	 * @param vo
	 * @return
	 */
	PagingBO<OperateHelpCorrectBO> findOperateCorrectList(OperateHelpCorrectVO vo);
	
	/**
	 * 修改意见箱信息
	 * @author chen cheng
	 * @create 2017年4月27日 
	 * @param vo
	 * @return
	 */
	int updateOperateCorrect(OperateHelpCorrectVO vo);

	/************************** 帮助中心管理相关 	 start*****************************/
	/**
	 * @desc   查询帮助中心栏目
	 * @author Tony Wang
	 * @create 2017年4月28日
	 * @return 
	 */
	List<OperateHelpTypeBO> listHelpType();

	/**
	 * @desc   查询帮助文章
	 * @author Tony Wang
	 * @create 2017年4月28日
	 * @param vo
	 * @return 
	 */
	PagingBO<OperateHelpBO> listHelp(OperateHelpVO vo);

	/**
	 * @desc   合并帮助中心栏目
	 * @author Tony Wang
	 * @create 2017年4月28日
	 * @param vo
	 * @return 
	 */
	int mergeHelpType(OperateHelpTypeVO vo);

	/**
	 * @desc   添加帮助文章
	 * @author Tony Wang
	 * @create 2017年4月28日
	 * @param vo
	 * @return 
	 */
	int addHelp(OperateHelpVO vo);

	/**
	 * @desc   查询单篇帮助文章
	 * @author Tony Wang
	 * @create 2017年4月28日
	 * @param vo
	 * @return 
	 */
	OperateHelpBO findSingleHelp(OperateHelpVO vo);

	/**
	 * @desc   更新帮助文章
	 * @author Tony Wang
	 * @create 2017年4月28日
	 * @param vo
	 * @return 
	 */
	int updateHelp(OperateHelpVO vo);

	/**
	 * @desc   更新帮助文章状态或审核人员
	 * @author Tony Wang
	 * @create 2017年4月28日
	 * @param vo
	 * @return 
	 */
	int updateHelpStatus(OperateHelpVO vo);
	
	/**
	 * @desc   更新帮助中心栏目顺序
	 * @author Tony Wang
	 * @create 2017年4月28日
	 * @param vos
	 * @return 
	 */
	int updateHelpTypeOrder(List<OperateHelpTypeVO> vos);
	
	/**
	 * @desc   查询父栏目
	 * @author Tony Wang
	 * @create 2017年5月24日
	 * @param vo
	 * @return 
	 */
	List<String> findHelpParentPaths(OperateHelpVO vo);

	/**
	 * @desc   查询最大帮助文章编号
	 * @author Tony Wang
	 * @create 2017年5月24日
	 * @param helpCodeLike
	 * @return 
	 */
	String findMaxHelpCode(String helpCodeLike);
	
	/**
	 * @desc   查询栏目
	 * @author Tony Wang
	 * @create 2017年11月17日
	 * @param criteria
	 * @return 
	 */
	OperateHelpTypeBO findHelpType(OperateHelpTypeVO criteria);
	/************************** 帮助中心管理相关 	 end*****************************/


	/**
	 * @Desc 查询用户关闭的通知设置
	 * @author tangxiaobo
	 * @CreatDate 2017年4月25日 下午2:30:20
	 * @param userId
	 * @return
	 */
	List<OperateMsgConfigBO> findUserCloseNoticConfig(Integer userId);
	
	/**
	 * 删除用户的已关闭配置
	 */
	int deleteMsgConfByUserId(Integer userId );
	/**
	 * @Desc 
	 * @author tangxiaobo
	 * @CreatDate 2017年4月26日 下午3:33:47
	 */
	void addMsgConfig(List<OperateMsgConfigVo> vo,Integer userId);
	
	/**
	 * 获取用户消息接收配置
	 * @param userId
	 * @param type
	 * @return
	 */
	List<OperateMsgLotteryConfigBO> findLotteryConfig(Integer userId,Integer type);
	
	/**
	 * 查询彩种消息接收开关
	 * @param id
	 * @return
	 */
	OperateMsgLotteryConfigBO findLotteryConfigById(Integer id);
	
	/**
	 * 添加彩种消息接收配置
	 * @param vo
	 */
	void updateLotteryConfig(OperateMsgLotteryConfigVO vo);
	
	/**
	 * 更新彩种消息接收配置
	 * @param vo
	 */
	void addLotteryConfig(OperateMsgLotteryConfigVO vo);
	
	/**
	 * 创建移动端包发布对象
	 * @param vo
	 * @return
	 * @author cheng.chen
	 * @date 2017年5月02日 上午 12:06:01
	 */
	int addSoftwareVersion(OperateSoftwareVersionVO vo);
	
	/**
	 * 查询移动端软件管理集合
	 * @param vo
	 * @return
	 * @date 2017年5月2日下午12:12:05
	 * @author cheng.chen
	 */
	PagingBO<OperateSoftwareVersionBO> findSoftwareVersionList(OperateSoftwareVersionVO vo);
	
	/**
	 * @desc   查询符合条件的记录数
	 * @author Tony Wang
	 * @create 2017年9月13日
	 * @param vo
	 * @return 
	 */
	int countSoftwareVersion(OperateSoftwareVersionVO vo);
	
	/**
	 * 查询静态版本号
	 * @return
	 * @date 2017年5月2日下午5:12:04
	 * @author cheng.chen
	 */
	List<OperateSoftwareVersionBO> findBaseSoftwareVersion(OperateSoftwareVersionVO vo);

	/**
	 * 批量删除
	 * @param vo
	 * @return
	 * @date 2017年5月2日下午5:29:22
	 * @author cheng.chen
	 */
	int delSoftwareVersionByIds(StringVO vo);
	
	/**
	 * 修改移动端包发布对象
	 * @param vo
	 * @return
	 * @date 2017年5月2日下午4:13:40
	 * @author cheng.chen
	 */
	int updSoftwareVersion(OperateSoftwareVersionVO vo);

	/**
	 * 根据是否最新字段修改
	 * @param isnew
	 * @param type
	 * @return
	 * @date 2017年5月2日下午6:13:06
	 * @author cheng.chen
	 */
	int updateIsnew(int isnew, int type);
	
	/**
	 * 验证编码唯一
	 * @param code
	 * @return
	 * @date 2017年5月2日下午4:14:26
	 * @author cheng.chen
	 */
	int valiSoftwareVersionCode(int code);

	/**
	 * @desc   更新审核开关
	 * @author Tony Wang
	 * @create 2017年10月14日
	 * @param vo
	 * @return 
	 */
	int updSoftwareVersionSwitch(OperateSoftwareVersionVO vo);

	/**
	 * @desc   查找最新版本
	 * @author Tony Wang
	 * @create 2017年10月14日
	 * @param vo
	 * @return 
	 */
	List<OperateSoftwareVersionBO> findNewSoftwareVersion(OperateSoftwareVersionVO vo);
	
	/**
	 * 根据批次号查询信息
	 * @param msgBatch
	 * @return
	 */
	OperateMsgNewBO findNewMsgByBatch(String msgBatch);
	
	/**
	 * 更新信息状态
	 * @param status
	 * @param id
	 * @return
	 */
	int updateNewMsgStatus(int status,Integer id);
	
	/**
	 * 
	 * @Desc 根据用户id集合分次查询用户列表
	 * @CreatDate 2017年4月14日 下午2:28:46
	 * @param list
	 * @return
	 */
	List<UserInfoBO> findUserInfoByIds(List<Integer> list);
	
	/**
	 * 
	 * @Desc   新增通知消息
	 * @Author HouXB
	 * @Date   2017年3月8日 下午12:18:13
	 * @param po
	 * @return
	 */
	int addMsgInfo(List<OperateMsgInfoVO> list);
	
	/**
	 * 查询用户模板接收设置信息
	 * @param userId
	 * @param typeId
	 */
	OperateMsgConfigBO findUserConfig(Integer userId, Integer typeId);
	
	/**
	 * 
	 * @Desc 查询有效用户个数
	 * @return
	 */
	int findValidUserInfoCount();
	
	/**
	 * 
	 * @Desc 分次查询有效用户列表
	 * @param list
	 * @param begin
	 * @param end
	 * @return
	 */
	List<UserInfoBO> findValidUserInfo(Integer begin,Integer end);

	/**
	 * 
	 * @Description 获取发送用户名单
	 * @author HouXiangBao289
	 * @param msgBatch
	 * @return
	 */
	List<String> getSendUserList(String msgBatch);

	/**
	 * 
	 * @Description 取消发送新信息 
	 * @author HouXiangBao289
	 * @param list
	 */
	void cancelSendMsg(List<OperateMsgInfoVO> list);
	/**
	 * 查询大客户用户信息
	 * @param vo
	 * @return
	 */
	PagingBO<OperateRebateUserBO> findRebateUser(OperateRebateUserVO vo);
	/**
	 * 新增大客户信息
	 * @param vo
	 * @return
	 */
	int addRebateUser(OperateRebateUserVO vo);
	
	/**
	 * 查询大客户最新信息
	 * @param userId
	 * @return
	 */
	OperateRebateUserBO findByUser(Integer userId);

	/**
	 * 查询当前彩种赛事列表分页
	 * @param vo
	 * @return
	 */
	PagingBO<SportAgainstBO> findSportAgainstInfo(SportAgainstVO vo);

	 /**
	 * @desc   查询渠道，不分页
	 * @author Tony Wang
	 * @create 2017年8月28日
	 * @param vo
	 * @return 
	 */
	List<OperateMarketChannelBO> listMarketchannel(OperateMarketChannelVO vo);
	
	/**
	 * 查询大客户历史数据
	 * @param userId
	 * @return
	 */
	List<OperateRebateUserBO> findUserHisList(Integer userId);
	
	/**
	 * 查询未过期的活动组成下拉框
	 * @return
	 */
	List<DictionaryBO> findActivityStatusTrue();
	/**
	 * 更新抽奖明细信息
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
	 * 更新追号明细信息
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
	 * @desc   查询单条版本
	 * @author Tony Wang
	 * @create 2017年10月15日
	 * @param vo
	 * @return 
	 */
	OperateSoftwareVersionBO findOneSoftwareVersion(OperateSoftwareVersionVO vo);
//	/**
//	 * @Description: 根据渠道ID，批量关闭/开启同步功能
//	 * @param vo
//	 * @return
//	 * @author wuLong
//	 * @date 2017年10月21日 下午12:14:29
//	 */
//	int updSoftwareVersionSwitchByChannlId(OperateSoftwareVersionVO vo);

	/**
	 * @desc   更新'是否同步官方'
	 * @author Tony Wang
	 * @create 2017年10月24日
	 * @param target
	 * @return 
	 */
	int updSoftwareVersionSwitchSynOfficial(OperateSoftwareVersionVO vo);

	/**
	 * @desc   查询所有不同名称的版本
	 * @author Tony Wang
	 * @create 2017年12月22日
	 * @param operateSoftwareVersionVO
	 * @return 
	 */
	List<OperateSoftwareVersionBO> distinctSoftwareVersionName(OperateSoftwareVersionVO vo);
    /**
     * 获取rss文章
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2018年2月27日 上午10:21:40
     * @return
     */
	List<OperateArticleBO> listArticleRss();
	
	/**
	 * 更新优惠券模板信息
	 * @param vo
	 * @return
	 */
	ResultBO<?> updateCouponTemp(OperateCouponTempVO vo);
	
	/**
	 * 删除优惠券模板信息
	 * @param vo
	 * @return
	 */
	ResultBO<?> deleteCouponTemp(OperateCouponTempVO vo);
	
	/**
	 * 新增优惠券模板信息
	 * @param vo
	 * @return
	 */
	ResultBO<?> insertCouponTemp(OperateCouponTempVO vo);
	
	/**
	 * 查询优惠券模板信息
	 * @param vo
	 * @return
	 */
	ResultBO<List<OperateCouponTempBO>> findCouponTemp(OperateCouponTempVO vo);
	
	/**
	 * 更新充值送明细信息
	 * @param vo
	 * @return
	 */
	OperateActivityRechargeBO addRechargeConfig(OperateActivityRechargeVO vo);

	/**
	 * 查询世界杯冠军竞猜球队信息
	 * @return
     * @param vo
	 */
    List<OperateActivityChpGuessBO> findChpTeams(OperateActivityChpGuessBO vo);

	/**
	 * 分页查询参与世界杯冠军竞猜的用户信息
	 * @return
	 */
	PagingBO<OperateActivityChpInfoBO> pageChpUsers(OperateActivityChpInfoBO vo);

	/**
	 * 查询参与世界杯冠军竞猜的用户信息
	 * @param vo
	 * @return
	 */
    List<OperateActivityChpInfoBO> findChpUsers(OperateActivityChpInfoBO vo);

	/**
	 * 添加世界杯冠军竞猜用户
	 * @param chpInfoes
	 * @return
	 */
    int addActivityChpInfo(List<OperateActivityChpInfoBO> chpInfoes);
}


