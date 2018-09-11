package com.hhly.cmscore.cms.remote.service;

import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.ordermgr.bo.GroupUserBO;
import com.hhly.skeleton.cms.ordermgr.bo.GroupUserExcelBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderAddBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderAddContentBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderAddIssueBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderAddUserExcelCmsBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderDetailBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderFollowedBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderFollowedExcelBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderGroupBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderGroupContentBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderGroupContentExcelBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderInfoCmsBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderInfoCmsExcelBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderInfoCmsUserExcelBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderInfoDetailCmsBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderIssueBO;
import com.hhly.skeleton.cms.ordermgr.vo.GroupUserVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddCmsVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddContentVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddIssueVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderFollowedVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderGroupVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoCmsSearchVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderIssueVO;
import com.hhly.skeleton.cms.recommend.bo.RcmdUserCheckBO;
import com.hhly.skeleton.cms.recommend.vo.RcmdUserCheckVO;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-12-6 上午9:22:30
 * @Desc 会员订单方案管理
 */
public interface IOrderMgrService {
	/**
	 * 分页查订单信息
	 * @param vo
	 * @return
	 */
	PagingBO<OrderInfoCmsBO> findOrderInfo(OrderInfoCmsSearchVO vo);
	/**
	 * 查询订单详情
	 * @param vo 订单id
	 * @return
	 */
	OrderInfoDetailCmsBO findOrderInfoDetail(StringVO vo);
    /**
     * 查询订单投注内容
     * @param vo 订单编号
     * @return 
     */
	PagingBO<OrderDetailBO> findOrderDetail(OrderInfoCmsSearchVO vo);
	
	/**
	 * 修改方案
	 * @param vo
	 * @return
	 */
	int updOrderInfo(OrderInfoVO vo);
	/**
	 * 导出订单信息
	 * @param vo
	 * @return
	 */
	List<OrderInfoCmsExcelBO> getOrderInfoExcel(OrderInfoCmsSearchVO vo);
	/**
	 * 导出订单联系人
	 * @param vo
	 * @return
	 */
	
	List<OrderInfoCmsUserExcelBO> getOrderInfoUserExcel(OrderInfoCmsSearchVO vo);
	
	/*************************************** 追号计划相关 **********************************************/
	/**
	 * @param orderAddCms
	 *            查询对象
	 * @return 分页查询追号计划信息
	 * @Desc 分页查询追号计划信息
	 */
	PagingBO<OrderAddBO> findPagingOrderAdd(OrderAddCmsVO orderAddCms);

	/**
	 * @param orderAddCms
	 *            查询对象
	 * @return 查询单个追号计划
	 * @Desc 查询单个追号计划
	 */
	OrderAddBO findSingleOrderAdd(OrderAddCmsVO orderAddCms);

	/**
	 * @desc 查询导出追号计划信息
	 * @author huangb
	 * @date 2017年1月20日
	 * @param orderAddCms
	 *            查询对象
	 * @return 查询导出追号计划信息
	 */
	List<OrderAddBO> findExcelOrderAdd(OrderAddCmsVO orderAddCms);
	
	/**
	 * @desc 查询导出追号计划相关用户信息
	 * @author huangb
	 * @date 2017年1月20日
	 * @param orderAddCms
	 * @return 查询导出追号计划相关用户信息
	 */
	List<OrderAddUserExcelCmsBO> findExcelOrderAddUser(OrderAddCmsVO orderAddCms);
	
	/**
	 * @param orderAddContent
	 *            查询对象
	 * @return 分页查询追号内容
	 * @Desc 分页查询追号内容
	 */
	PagingBO<OrderAddContentBO> findPagingOrderAddContent(OrderAddContentVO orderAddContent);

	/**
	 * @param orderAddIssue
	 *            查询对象
	 * @return 分页查询追号期数
	 * @Desc 分页查询追号期数
	 */
	PagingBO<OrderAddIssueBO> findPagingOrderAddIssue(OrderAddIssueVO orderAddIssue);
	
	/**
	 * @desc 修改指定id列表的追号彩期状态(撤单)
	 * @author huangb
	 * @date 2017年1月19日
	 * @param ids 要修改的追号彩期id列表（逗号分隔）
	 * @return 修改的追号彩期id列表记录数
	 */
	/*int updOrderAddIssueStatusByIds(String ids);*/
	
	/**
	 * @desc 查询导出的追号彩期
	 * @author huangb
	 * @date 2017年1月20日
	 * @param orderAddIssue
	 *            查询对象
	 * @return 查询导出的追号彩期
	 */
	List<OrderAddIssueBO> findExcelOrderAddIssue(OrderAddIssueVO orderAddIssue);
	
	/****************************** 派奖管理相关接口 *********************************/
	/**
	 * @desc 分页查询
	 * @author huangb
	 * @date 2017年2月11日
	 * @param orderInfoCmsSearch
	 *            参数对象
	 * @return 分页查询列表
	 */
	PagingBO<OrderInfoCmsBO> findPagingSendPrize(OrderInfoCmsSearchVO orderInfoCmsSearch);

	/**
	 * @desc excel导出查询
	 * @author huangb
	 * @date 2017年2月11日
	 * @param orderInfoCmsSearch
	 *            参数对象
	 * @return excel导出查询列表
	 */
	List<OrderInfoCmsBO> findExcelSendPrize(OrderInfoCmsSearchVO orderInfoCmsSearch);
	/****************************** 派奖管理相关接口 *********************************/
	/**
	 * 修改订单状态
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月13日 下午5:27:58
	 * @param vo
	 * @return
	 */
	int updateOrderStatus(OrderInfoVO vo);
	/**
	 * 执行订单操作
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年3月15日 下午4:44:32
	 * @param vo
	 * @return
	 */
	String updateOrderOperate(OrderInfoVO vo);
	
	/*******************彩期时间或赛事时间变动修改***********/
	/**
	 * 修改订单的截止出票和检票时间
	 * @param lotteryCode 彩种编码
	 * @param issueCode 彩期编码
	 * @param endTicketTime 出票截止时间
	 * @param endCheckTime 检票截止时间
	 * @param endSysTime 本站销售截止时间
	 * @param systemCode 赛事系统编码
	 * @return
	 * @date 2017年5月15日下午4:01:54
	 * @author cheng.chen
	 */
	int updTicketAndCheckTime(Map<String, Object> param);
	
	/**
	 * 修改撤销理由到订单备注
	 * @param orderCode
	 * @param remark
	 * @return
	 * @date 2017年6月11日上午11:07:25
	 * @author cheng.chen
	 */
	int updCancelOrderRemark(String orderCode, String remark);
	
	/****************************** 发单、抄单管理相关接口 *********************************/
	/**
	 * @desc   分页查询发单信息
	 * @author Tony Wang
	 * @create 2017年10月11日
	 * @param vo
	 * @return 
	 */
	PagingBO<OrderIssueBO> pageOrderIssue(OrderIssueVO vo);
	
	/**
	 * @desc   查询发单信息
	 * @author Tony Wang
	 * @create 2017年10月11日
	 * @param vo
	 * @return 
	 */
	List<OrderIssueBO> listOrderIssue(OrderIssueVO vo);
	
	/**
	 * @desc   更新发单信息
	 * @author Tony Wang
	 * @create 2017年10月12日
	 * @param vo
	 * @return 
	 */
	int updateOrderIssue(OrderIssueVO vo);
	
	/**
	 * @desc   查询跟单信息
	 * @author Tony Wang
	 * @create 2017年10月11日
	 * @param vo
	 * @return 
	 */
	List<OrderFollowedBO> listOrderFollowed(OrderFollowedVO vo);
	
	/**
	 * @desc   查询跟单信息，用于导出excel
	 * @author Tony Wang
	 * @create 2017年10月12日
	 * @param vo
	 * @return 
	 */
	List<OrderFollowedExcelBO> listOrderFollowedExcel(OrderFollowedVO vo);
	/**
	 * @desc   分页查询跟单信息
	 * @author Tony Wang
	 * @create 2017年10月11日
	 * @param vo
	 * @return 
	 */
	PagingBO<OrderFollowedBO> pageOrderFollowed(OrderFollowedVO vo);

	/****************************** 发单、抄单管理相关接口 *********************************/
	/**
	 * 派奖信息提示
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年5月30日 上午9:09:37
	 * @param orderInfoCmsSearch
	 * @return
	 */
	String getAwardInfo(OrderInfoCmsSearchVO orderInfoCmsSearch);

	
	/******************************* 合买接口  start ************************************/
	/**
	 * 
	 * @Description 查询合买订单列表 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	PagingBO<OrderGroupBO> findGroup(OrderGroupVO vo);
	
	/**
	 * 
	 * @Description 查询合买认购列表 
	 * @author HouXiangBao289
	 * @param userName
	 * @return
	 */
	PagingBO<OrderGroupContentBO> findGroupContent(OrderGroupVO vo);


	/**
	 * 
	 * @Description 查询合买订单
	 * @author HouXiangBao289
	 * @param id
	 * @return
	 */
	OrderGroupBO findGroupById(Integer id);

	/**
	 * 
	 * @Description 合买截止时间和说明更新
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	int update(OrderGroupVO vo);
	
	/**
	 * 
	 * @Description 查询参与合买用户导出数据
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	List<OrderGroupContentExcelBO> findGroupUserExcel(OrderGroupVO vo);
	
	
	
	ResultBO<?> recommand(OrderGroupVO vo);
	
	ResultBO<?> top(OrderGroupVO vo);
	
	ResultBO<?> siteGuarantee(Integer groupId);
	
	ResultBO<?> cancelGroupOrder(String orderCode);
	
	
	/*************************************************** 合买接口  end *****************/
	
	
	
	/*************************************************** 合买用户 start*****************/
	PagingBO<GroupUserBO> findGroupUser(GroupUserVO vo);
	
	List<GroupUserExcelBO> exprotGroupUserExcel(GroupUserVO vo);
	
	void updateRecommand(GroupUserVO vo);
	
	void updateUserFlag(GroupUserVO vo);
	
	/*************************************************** 合买用户 end*****************/
	
	/*************************************************** 推荐人审核 start*****************/
	
	PagingBO<RcmdUserCheckBO> findRcmdUserCheckList(RcmdUserCheckVO vo);
	
	int setStatus(RcmdUserCheckVO vo);
	
	int addRcmdUser(RcmdUserCheckVO vo);

	/*************************************************** 推荐人审核 end*****************/

}
