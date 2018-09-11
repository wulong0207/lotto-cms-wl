package com.hhly.cms.customermgr.service;

import java.io.ByteArrayOutputStream;
import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.customermgr.bo.LottoCustomerBO;
import com.hhly.skeleton.cms.customermgr.vo.LottoCustomerVO;


/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-12-5 下午4:10:44
 * @Desc 会员信息
 */
public interface CustomerService {
    /**
     * 分页查询
     * @param vo
     * @return
     */
	PagingBO<LottoCustomerBO> findLottoCustomer(LottoCustomerVO vo);
    /**
     * 导出excel
     * @param vo
     * @return
     */
	ByteArrayOutputStream getLottoCustomerExcel(LottoCustomerVO vo);
	/**
	 * 查询用户信息
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
	 * @date 2017年7月19日 下午4:29:34
	 */
	int findTotal(LottoCustomerVO vo);
	/**
	 * 修改用户密码
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月9日 下午12:19:42
	 * @param vo
	 */
	void updatePassword(LottoCustomerVO vo);
	/**
	 * 清理用户发送信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年1月11日 下午5:11:27
	 * @param vo
	 */
	void updateCleanMessage(LottoCustomerVO vo);
}
