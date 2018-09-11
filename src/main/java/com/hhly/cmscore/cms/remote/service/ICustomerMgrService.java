package com.hhly.cmscore.cms.remote.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.customermgr.bo.LottoCustomerBO;
import com.hhly.skeleton.cms.customermgr.bo.LottoCustomerExcelBO;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueBO;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueExcelBO;
import com.hhly.skeleton.cms.customermgr.bo.UserIssueLevelBO;
import com.hhly.skeleton.cms.customermgr.bo.UserTypeBO;
import com.hhly.skeleton.cms.customermgr.bo.UserTypeExcelBO;
import com.hhly.skeleton.cms.customermgr.bo.UserWinningStatisticsBO;
import com.hhly.skeleton.cms.customermgr.bo.UserWinningStatisticsExcelBO;
import com.hhly.skeleton.cms.customermgr.vo.LottoCustomerVO;
import com.hhly.skeleton.cms.customermgr.vo.UserIssueLevelVO;
import com.hhly.skeleton.cms.customermgr.vo.UserIssueVO;
import com.hhly.skeleton.cms.customermgr.vo.UserTypeVO;
import com.hhly.skeleton.cms.customermgr.vo.UserWinningStatisticsVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgUserConfigVo;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-12-5 下午3:59:11
 * @Desc 会员管理
 */
public interface ICustomerMgrService {
	/**
	 * 分页查询
	 * @param vo
	 * @return
	 */
	PagingBO<LottoCustomerBO> findLottoCustomer(LottoCustomerVO vo);
	/**
	 * 导出
	 * @param vo
	 * @return
	 */
	List<LottoCustomerExcelBO> findLottoCustomerExcel(LottoCustomerVO vo);
	/**
	 * 根据用户id查询用户信息
	 * @param vo
	 * @return
	 */
	LottoCustomerBO findLottoCustomerDetail(StringVO vo);
	/**
	 * 修改会员信息
	 * @param vo
	 * @return
	 */
	int updLottoCustomer(LottoCustomerVO vo);
	/**
	 * 会员中奖分页查询
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月24日 上午11:07:52
	 * @param vo
	 * @return
	 */
	PagingBO<UserWinningStatisticsBO> listUserWinningStatistics(UserWinningStatisticsVO vo);
	/**
	 * 会员中奖excel导出
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月24日 上午11:08:10
	 * @param vo
	 * @return
	 */
	List<UserWinningStatisticsExcelBO> listUserWinningStatisticsExcel(UserWinningStatisticsVO vo);
	
	/**
	 * 添加会员类型
	 * @param vo
	 * @return
	 */
	int addUserType(UserTypeVO vo);
	
	/**
	 * 会员添加会员类型标签
	 * @param typeCode
	 * @return
	 */
	void addTypeToUser(String typeCode);	
	
	/**
	 * 根据会员类型id删除
	 * @param id
	 * @return
	 */
	int delUserTypeByIds(StringVO vo);
	
	/**
	 * 会员类型分页查询
	 * @param vo
	 * @return
	 */
	PagingBO<UserTypeBO> findUserTypeList(UserTypeVO vo);
	
	/**
	 * 根据会员类型id修改信息
	 * @param vo
	 * @return
	 */
	int updateUserTypeById(UserTypeVO vo);
	
	/**
	 * 查询会员类型基础数据
	 * @return
	 */
	List<UserTypeBO> findBaseUserType();
	
	/**
	 * 根据会员类型导出会员ids
	 * @return
	 */
	List<UserTypeExcelBO> findExcelByTypeId(Integer typeId);
	
	/**
	 * 验证用户类型编码唯一
	 * @param code 类别编码
	 * @return
	 */
	int valiUserTypeCode(String code);
	
	/**
	 * @Desc 更新用户接收信息总配置
	 * @author tangxiaobo
	 * @CreatDate 2017年4月26日 下午3:08:32
	 * @param vo
	 * @return
	 */
	int updateMsgConfig(OperateMsgUserConfigVo vo);
	
	/**
	 * @desc   查找用户id
	 * @author Tony Wang
	 * @create 2017年7月6日
	 * @param vo
	 * @return 
	 */
	List<Integer> findIds(LottoCustomerVO vo);
	/**
	 * 
	 * @Description: 查询用户数量
	 * @param vo
	 * @return
	 * @author wuLong
	 * @date 2017年7月19日 下午4:30:30
	 */
	int findTotal(LottoCustomerVO vo);
	
	/*************************** 发单相关 start *****************************************/
	/**
	 * @desc   分页查询发单用户
	 * @author Tony Wang
	 * @create 2017年10月13日
	 * @param vo
	 * @return 
	 */
	PagingBO<UserIssueBO> pageUserIssue(UserIssueVO vo);
	
	/**
	 * @desc   查询发单用户
	 * @author Tony Wang
	 * @create 2017年10月13日
	 * @param vo
	 * @return 
	 */
	List<UserIssueBO> findUserIssue(UserIssueVO vo);
	
	/**
	 * @desc   查询发单用户，用于excel导出
	 * @author Tony Wang
	 * @create 2017年10月18日
	 * @param vo
	 * @return 
	 */
	List<UserIssueExcelBO> excelUserIssue(UserIssueVO vo);
	
	/**
	 * @desc   更新发单用户
	 * @author Tony Wang
	 * @create 2017年10月13日
	 * @param vo
	 * @return 
	 */
	int updateUserIssue(UserIssueVO vo);
	
	/**
	 * @desc   更新发单用户级别
	 * @author Tony Wang
	 * @create 2017年10月13日
	 * @param vo
	 * @return 
	 */
	int mergeUserIssueLevel(UserIssueLevelVO vo);
	
	/**
	 * @desc   查询1条用户级别记录
	 * @author Tony Wang
	 * @create 2017年10月13日
	 * @param vo
	 * @return 
	 */
	UserIssueLevelBO findOneUserIssueLevel(UserIssueLevelVO vo);
	/*************************** 发单相关  end *****************************************/
	/**
	 * 修改用户密码
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月9日 下午12:25:30
	 * @param id
	 * @param password
	 */
	void updLottoCustomerPassword(LottoCustomerVO vo);
	/**
	 * 清空用户发送信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月11日 下午5:12:16
	 * @param vo
	 */
	void updateCleanMessage(LottoCustomerVO vo);
}
