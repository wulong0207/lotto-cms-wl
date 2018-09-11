package com.hhly.cms.operatemgr.service.impl;

import com.hhly.cms.operatemgr.service.OperateFastService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateFastBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateFastInfoBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateFastInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateFastVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @desc    快投运营管理
 * @author  lidecheng
 * @date    2017年2月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateFastServiceImpl implements OperateFastService {

	@Autowired
  private IOperateMgrService iOperateMgrService;
	/**
	 * @desc   分页查询快投运营信息
	 * @author lidecheng
	 * @create 2017年2月17日
	 * @param vo
	 * @return  
	 */
	@Override
	public PagingBO<OperateFastBO> list(OperateFastVO vo) {
		return iOperateMgrService.findOperateFast(vo);
	}
	
	/**
	 * @desc   增加快投运营信息
	 * @author lidecheng
	 * @create 2017年2月20日
	 * @param vo
	 * @return
	 */
	@Override
	public int add(OperateFastVO vo) {
		return iOperateMgrService.addOperateFast(vo);
	}
	
	/**
	 * @desc   更新快投运营信息
	 * @author lidecheng
	 * @create 2017年2月20日
	 * @param vo
	 * @return
	 */
	@Override
	public int update(OperateFastVO vo) {
		return iOperateMgrService.updateOperateFast(vo);
	}

	/**
	 * @desc   分页查询快投运营详情信息
	 * @author lidecheng
	 * @create 2017年2月21日
	 * @param vo
	 * @return  
	 */
	@Override
	public PagingBO<OperateFastInfoBO> listFastInfo(OperateFastInfoVO vo) {
		return iOperateMgrService.findOperateFastInfo(vo);
	}

	/**
	 * @desc   查询符合条件的运营方案数量
	 * @author lidecheng
	 * @create 2017年2月22日
	 * @param vo
	 * @return  
	 */
	@Override
	public int count(OperateFastVO vo) {
		return iOperateMgrService.countOperateFast(vo);
	}

//	/**
//	 * @desc   vo有id则update快投运营信息，无则insert
//	 * @author lidecheng
//	 * @create 2017年2月17日
//	 * @param vo
//	 * @return  
//	 */
//	@Override
//	public int merge(OperateFastVO vo) {
//		return iOperateMgrService.mergeOperateFast(vo);
//	}

}
