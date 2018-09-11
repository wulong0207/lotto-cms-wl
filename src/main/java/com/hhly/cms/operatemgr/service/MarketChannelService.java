package com.hhly.cms.operatemgr.service;

import com.hhly.cms.bo.TreeGridBO;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMarketChannelBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMarketChannelVO;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-12-7 上午10:51:50
 * @Desc 市场渠道管理
 */
public interface MarketChannelService {
	 /**
	  * 获取所有渠道信息
	  * @return
	  */
     List<DictionaryBO> findAllChannelDic();
     /**
      * 分页查询
      * @param vo
      * @return
      */
	 PagingBO<OperateMarketChannelBO> findMarketchannel(OperateMarketChannelVO vo);
	 
	 /**
	 * @desc   查询渠道，不分页
	 * @author Tony Wang
	 * @create 2017年8月28日
	 * @param vo
	 * @return 
	 */
	List<OperateMarketChannelBO> listMarketchannel(OperateMarketChannelVO vo); 
	
	 /**
	  * 修改
	  * @param vo
	  * @return
	  */
	 int updOperateMarketChannel(OperateMarketChannelVO vo);
	 /**
	  * 添加
	  * @param vo
	  * @return
	  */
	 int addOperateMarketChannel(OperateMarketChannelVO vo);
	 /**
	  * 查询父级id
	  * @param grade
	  * @return
	  */
	 List<DictionaryBO> findParentId(String grade,String channelId);
	 /**
	  * 导出excel
	  * @param vo
	  * @return
	  */
	ByteArrayOutputStream getExcelMarketChannel(OperateMarketChannelVO vo);
	
	/**
	 * 查询渠道树状图
	 * @param channelId
	 * @return
	 * @date 2017年7月11日下午12:26:48
	 * @author cheng.chen
	 */
	List<TreeGridBO> findChannelList(String channelId);
	
	/**
	 * @desc   查询符合条件的渠道数
	 * @author Tony Wang
	 * @create 2017年10月26日
	 * @param vo
	 * @return 
	 */
	int count(OperateMarketChannelVO vo);
	
	/**
	 * @desc   更新app_url字段
	 * @author Tony Wang
	 * @create 2017年10月26日
	 * @param vo 
	 */
	int updAppUrl(OperateMarketChannelVO vo);
}
