package com.hhly.cms.ordermgr.service;

import java.io.ByteArrayOutputStream;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.ordermgr.bo.GroupUserBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderGroupBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderGroupContentBO;
import com.hhly.skeleton.cms.ordermgr.vo.GroupUserVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderGroupVO;

/**
 * @desc    发单管理
 * @author  Tony Wang
 * @date    2017年10月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OrderGroupService {

	/** 合买接口  start**/
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
	 * @Description 导出 
	 * @author HouXiangBao289
	 * @param vo
	 * @return
	 */
	ByteArrayOutputStream excel(OrderGroupVO vo);
	
	/** 合买接口  end**/
	
	//推荐
	ResultBO<?> recommand(OrderGroupVO vo);
	
	//置顶
	 ResultBO<?> top(OrderGroupVO vo);
		
	//网站保底
	 ResultBO<?> siteGuarantee(Integer groupId);
	 
	 /*************************************************** 合买用户 start*****************/
		PagingBO<GroupUserBO> findGroupUser(GroupUserVO vo);
		
		ByteArrayOutputStream exprotGroupUserExcel(GroupUserVO vo);
		
		void updateRecommand(GroupUserVO vo);
		
		void updateUserFlag(GroupUserVO vo);
		
		/*************************************************** 合买用户 end*****************/
	 
}
