package com.hhly.cms.ordermgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderAddBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderAddContentBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderAddIssueBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddCmsVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddContentVO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderAddIssueVO;

import java.io.ByteArrayOutputStream;

/**
 * @author huangb
 *
 * @Date 2017年1月17日
 *
 * @Desc 追号服务接口
 */
public interface OrderAddService {
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
	 * @desc 查询导出追号计划的数据并输出字节流
	 * @author huangb
	 * @date 2017年1月20日
	 * @param orderAddCms
	 *            参数对象
	 * @param type
	 *            导出类型
	 * @return 查询导出追号计划的数据集合并输出字节流
	 */
	ByteArrayOutputStream findExcelOrderAdd(OrderAddCmsVO orderAddCms, String type);
	
	/**
	 * @desc 查询导出的追号彩期并输出字节流
	 * @author huangb
	 * @date 2017年1月20日
	 * @param orderAddIssue
	 *            查询对象
	 * @return 查询导出的追号彩期并输出字节流
	 */
	ByteArrayOutputStream findExcelOrderAddIssue(OrderAddIssueVO orderAddIssue);
}
