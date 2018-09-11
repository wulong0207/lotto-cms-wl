package com.hhly.cmscore.cms.remote.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryBettingMulBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryChildBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryIssueCmsBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryIssueExcelBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryLimitBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryLimitInfoBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeExcelBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryWinningBO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryBettingMulCmsVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryChildCmsVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryIssueCmsVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryLimitInfoVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryLimitVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryTypeVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryWinningVO;
import com.hhly.skeleton.lotto.base.issue.bo.IssueLottBO;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-11-17 下午4:04:05
 * @Desc 彩种管理
 */
public interface ILotteryMgrService {
	/**
	 * 分页查询彩种
	 * @param vo
	 * @return
	 */
    PagingBO<LotteryTypeBO> findLotteryType(LotteryTypeVO vo);
    
    /**
     * 查询彩种信息
     * @param vo
     * @return
     * @date 2017年5月15日下午5:19:41
     * @author cheng.chen
     */
    LotteryTypeBO findSingle(LotteryTypeVO vo);
    /**
     * 查询 投注注数，倍数截止时间信息表
     * @param vo
     * @return
     */
    List<LotteryBettingMulBO> findLotteryDettingMul(StringVO vo);
    /**
     * 查询彩种的子玩法销售管理表
     * @param vo
     * @return
     */
    List<LotteryChildBO> findLotteryChild(StringVO vo);
    /**
     * 修改彩种
     * @param vo
     * @return
     */
    int updLotteryType(LotteryTypeVO vo);
    /**
     * 添加彩种注数和倍数截止时间
     * @param vo
     * @return
     */
    int addLotteryBettingMul(LotteryBettingMulCmsVO vo);
    /**
     * 添加彩种子玩法
     * @param vo
     * @return
     */
    int addLotteryChild(LotteryChildCmsVO vo);
    /**
     * 修改彩种注数和倍数截止时间
     * @param vo
     * @return
     */
    int updLotteryBettingMul(LotteryBettingMulCmsVO vo);
    /**
     * 修改彩种子玩法
     * @param vo
     * @return
     */
    int updLotteryChild(LotteryChildCmsVO vo);
    /**
     * 删除彩种注数和倍数截止时间
     * @param vo
     * @return
     */
    int delLotteryBettingMul(StringVO vo);
    /**
     * 删除彩种子玩法
     * @param vo
     * @return
     */
    int delLotteryChild(StringVO vo);
    /**
     * 保存彩种注数和倍数截止时间
     * @param list
     * @return
     */
    int saveLotteryBettingMul(List<LotteryBettingMulCmsVO> list);
    
    /**
     * 保存彩种子玩法
     * @param list
     * @return
     */
	int saveLotteryChild(List<LotteryChildCmsVO> list);
	/**
	 * 查询数据导出到excel
	 * @param vo
	 * @return
	 */
	List<LotteryTypeExcelBO> findLotteryTypeToExcel(LotteryTypeVO vo);
	/**
	 * 添加彩种
	 * @param vo
	 * @runtimeException  IllegalArgumentException（参数不正确：彩种编号已存在）
	 * @return
	 */
	int addLotteryType(LotteryTypeVO vo);
	/**
	 * 根据彩种查询彩种期号
	 * @param vo
	 * @return
	 */
	List<String> findIssueCode(LotteryIssueCmsVO vo);
	
	PagingBO<LotteryIssueCmsBO> findLotteryIssue(LotteryIssueCmsVO vo);
	/**
	 * 修改彩期
	 * @param vo
	 * @return
	 */
	int updLotteryIssue(LotteryIssueCmsVO vo);
	/**
	 * 生成遗漏数据
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年7月5日 上午10:35:49
	 * @param lotteryCode
	 * @param issueCode
	 * @param drawCode
	 */
	void updOmotAll(int lotteryCode,String issueCode,String  drawCode);
	
	/**
	 * 添加彩期
	 * @param vo
	 * @return
	 */
	int addLotteryIssue(LotteryIssueCmsVO vo);
	/**
	 * 导出彩期
	 * @param vo
	 * @return
	 */
	List<LotteryIssueExcelBO> findLotteryIssueToExcel(LotteryIssueCmsVO vo);
	
	/**
	 * 根据id查询彩期详情
	 * @param vo
	 * @return
	 */
	LotteryIssueCmsBO findLotteryIssueDetail(StringVO vo);
	
	/**
	 * 通过彩种和当前期状态查询
	 * @param lotteryCode 彩种信息
	 * @param currentIssue 0:非当前期,1:当前期
	 * @return
	 * @date 2017年5月15日下午4:36:00
	 * @author cheng.chen
	 */
	LotteryIssueCmsBO findIssue(Integer lotteryCode, Short currentIssue);
	
	/**
	 * 根据彩种分类查询
	 * @param vo
	 * @return 只包括 lotteryCode，lotteryName
	 */
	List<LotteryTypeBO> findLotteryTypeDic(StringVO vo);

	/**
	 * 创建彩期
	 * @param LotteryCode 彩种
	 * @return 生成彩期数
	 * @throws ServiceRuntimeException(生成彩失败)
	 */
	int createIssue(Integer LotteryCode);
	
	/**
	 * @desc 查询多个子玩法列表
	 * @author huangb
	 * @date 2017年1月19日
	 * @param lotteryChildCms
	 *            子玩法对象
	 * @return 查询多个子玩法列表
	 */
	List<LotteryChildBO> findMultipleChild(LotteryChildCmsVO lotteryChildCms);
	
	/************************限号管理操作*****************************/
	/**
	 * @desc 查询唯一的限号记录
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @return 查询唯一的限号记录
	 */
	LotteryLimitBO findSingleLimit(LotteryLimitVO lotteryLimitVO);

	/**
	 * @desc 查询限号分页列表
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @return 查询限号分页列表
	 */
	PagingBO<LotteryLimitBO> findPagingLimit(LotteryLimitVO lotteryLimitVO);

	/**
	 * @desc 新增限号
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @return 新增限号
	 */
	int addLimit(LotteryLimitVO lotteryLimitVO);

	/**
	 * @desc 修改限号
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitVO
	 *            参数对象
	 * @return 修改限号
	 */
	int updLimit(LotteryLimitVO lotteryLimitVO);

	/**
	 * @desc 查询唯一的限号内容记录
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @return 查询唯一的限号内容记录
	 */
	LotteryLimitInfoBO findSingleLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO);

	/**
	 * @desc 查询限号内容分页列表
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @return 查询限号内容分页列表
	 */
	PagingBO<LotteryLimitInfoBO> findPagingLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO);

	/**
	 * @desc 新增限号内容
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @return 新增限号
	 */
	int addLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO);

	/**
	 * @desc 修改限号内容
	 * @author huangb
	 * @date 2017年2月14日
	 * @param lotteryLimitInfoVO
	 *            参数对象
	 * @return 修改限号
	 */
	int updLimitInfo(LotteryLimitInfoVO lotteryLimitInfoVO);
	
	/**
	 * @desc 保存限号内容（批量操作：包含新增和修改的动作）
	 * @author huangb
	 * @date 2017年2月16日
	 * @param list 新增或修改的集合列表
	 * @return 保存限号内容（批量操作：包含新增和修改的动作）
	 */
	int saveLimitInfo(List<LotteryLimitInfoVO> list);
    /************************限号管理操作*****************************/
	/************************ 彩种奖项相关 *****************************/
	/**
	 * @desc 查询：单条记录
	 * @author huangb
	 * @date 2017年2月24日
	 * @param lotteryWinningVO
	 *            参数对象
	 * @return 查询：单条记录
	 */
	LotteryWinningBO findSingleLotWinning(LotteryWinningVO lotteryWinningVO);

	/**
	 * @desc 查询：多条记录
	 * @author huangb
	 * @date 2017年2月24日
	 * @param lotteryWinningVO
	 *            参数对象
	 * @return 查询：多条记录
	 */
	List<LotteryWinningBO> findMultipleLotWinning(LotteryWinningVO lotteryWinningVO);
	/************************ 彩种奖项相关 *****************************/
	/**
	 * 审核开奖号码
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月13日 上午11:45:03
	 * @param vo
	 * @return
	 */
	int updateAuditDrawCode(LotteryIssueCmsVO vo);
	
	/**
	 * 查询彩种信息
	 * @return
	 */
	List<LotteryTypeBO> findAllLotteryType();
	
	/**
	 * 查询数字彩和胜负彩信息
	 * @return
	 */
	List<LotteryTypeBO> findLFLottery();
	
	/**
	 * @desc   根据条件查询单个彩期
	 * @author Tony Wang
	 * @create 2017年7月10日
	 * @param lotteryCode
	 * @param issueCode
	 * @param issueLastest 是否为最新开奖
	 * @return 
	 */
	IssueLottBO findIssueByParam(Integer lotteryCode, String issueCode, int issueLastest);
	
	/**
	 * 查询当前期+历史期
	 * @param lotteryCode 彩种
	 * @param qryCount 记录数
	 * @author wul687 2018-08-15
	 * @return
	 */
	List<String> cutOffHistoryIssue(Integer lotteryCode,Integer qryCount);
}
