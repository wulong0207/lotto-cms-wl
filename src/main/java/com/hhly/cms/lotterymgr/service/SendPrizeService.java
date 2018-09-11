package com.hhly.cms.lotterymgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderInfoCmsBO;
import com.hhly.skeleton.cms.ordermgr.vo.OrderInfoCmsSearchVO;

import java.io.ByteArrayOutputStream;

/**
 * @desc 派奖服务接口
 * @author huangb
 * @date 2017年2月12日
 * @company 益彩网络
 * @version v1.0
 */
public interface SendPrizeService {

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
	 * @return excel导出查询列表 并输出字节流
	 */
	ByteArrayOutputStream findExcelSendPrize(OrderInfoCmsSearchVO orderInfoCmsSearch);
    /**
     * 开奖信息提示
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2018年5月30日 上午9:08:47
     * @param orderInfoCmsSearch
     * @return
     */
	String getAwardInfo(OrderInfoCmsSearchVO orderInfoCmsSearch);
}
