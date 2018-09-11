package com.hhly.cms.operatemgr.service;

import java.io.IOException;
import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateHelpBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateHelpTypeBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpTypeVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateHelpVO;

/**
 * @desc    帮助中心管理
 * @author  Tony Wang
 * @date    2017年3月1日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OperateHelpService {

	/**
	 * @desc   查询帮助中心栏目名称
	 * @author Tony Wang
	 * @create 2017年3月1日
	 * @return  
	 */
	List<OperateHelpTypeBO> listHelpType();

	/**
	 * @desc   查询帮助中心
	 * @author Tony Wang
	 * @create 2017年3月1日
	 * @param vo
	 * @return  
	 */
	PagingBO<OperateHelpBO> listHelp(OperateHelpVO vo);

	/**
	 * @desc   增加或更新帮助中心栏目
	 * @author Tony Wang
	 * @create 2017年3月2日
	 * @param vo
	 * @return  
	 */
	int mergeHelpType(OperateHelpTypeVO vo);

	/**
	 * @desc   增加或更新帮助中心
	 * @author Tony Wang
	 * @create 2017年3月8日
	 * @param vo
	 * @return 
	 
	int mergeHelp(OperateHelpVO vo);
	 */
	
	/**
	 * @desc   增加帮助中心
	 * @author Tony Wang
	 * @create 2017年3月17日
	 * @param vo
	 * @return 
	 * @throws IOException 
	 */
	int addHelp(OperateHelpVO vo) throws IOException;

	/**
	 * @desc   查询单篇帮助中心
	 * @author Tony Wang
	 * @create 2017年3月21日
	 * @param operateHelpVO
	 * @return 
	 */
	OperateHelpBO findSingle(OperateHelpVO vo);

	/**
	 * @desc   更新帮助中心
	 * @author Tony Wang
	 * @create 2017年4月12日
	 * @param vo
	 * @return 
	 * @throws IOException 
	 */
	int updateHelp(OperateHelpVO vo) throws IOException;

	/**
	 * @desc   修改帮助中心状态
	 * @author Tony Wang
	 * @create 2017年4月14日
	 * @param vo
	 * @return 
	 */
	int updateHelpStatus(OperateHelpVO vo);

	/**
	 * @desc   更新帮助中心栏目顺序
	 * @author Tony Wang
	 * @create 2017年4月17日
	 * @param vos
	 * @return 
	 */
	int updateHelpTypeOrder(List<OperateHelpTypeVO> vos);

}
