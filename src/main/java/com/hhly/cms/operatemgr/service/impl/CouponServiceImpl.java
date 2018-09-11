package com.hhly.cms.operatemgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.operatemgr.service.CouponService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OperateCouponEnum.RedTypeEnum;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateCouponBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateCouponExcelBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateCouponVO;
import com.hhly.skeleton.pay.vo.UserRedAddParamVo;
import com.hhly.usercore.cms.remote.service.UserWalletService;

@Service
public class CouponServiceImpl implements CouponService {
	
	private static final Logger log = Logger.getLogger(CouponServiceImpl.class);
	
	@Autowired
    private IOperateMgrService iOperateMgrService;
	
	@Autowired
	private UserWalletService userWalletMgrService;
	
	@Autowired
	private ExcelExportService excelExportService;
	@Override
	public PagingBO<OperateCouponBO> findCoupon(OperateCouponVO vo) {
		return iOperateMgrService.findCoupon(vo);
	}
	@Override
	public ByteArrayOutputStream getExcelCoupon(OperateCouponVO vo) {
		List<OperateCouponExcelBO> data=  iOperateMgrService.finCouponExcel(vo);
		return excelExportService.dataToExeclByStream("coupon", data);
	}
	@Override
	public OperateCouponBO findCouponDetail(StringVO vo) {
		return iOperateMgrService.findCouponDetail(vo);
	}
	
	@Override
	public int updCoupon(OperateCouponVO vo) {
		int num = 0;
		try {
			if(vo.getRedType().intValue() == RedTypeEnum.HANDSEL_RED_PACKAGE.getType()){
				List<UserRedAddParamVo> voList = new ArrayList<UserRedAddParamVo>();
				UserRedAddParamVo uraVo = new UserRedAddParamVo();
				uraVo.setRedCode(vo.getRedCode());
				uraVo.setUserId(vo.getUserId());
				uraVo.setRedAmount(vo.getRedBalance());
				voList.add(uraVo);
				ResultBO<?> bo = userWalletMgrService.subRedColorAmount(voList);
				if(bo.isOK()){
					num = iOperateMgrService.updOperateCoupon(vo);
				}
			}else{
				num = iOperateMgrService.updOperateCoupon(vo);
			}		
		} catch (Exception e) {
			log.error("撤销红包, 修改用户账户金额异常 : " + e.getMessage());
		}
		return num;
	}
	
	@SuppressWarnings("unchecked")
	private List<UserRedAddParamVo> couvertCouponVo(List<OperateCouponVO> list){
		if(!ObjectUtil.isBlank(list)){
			List<UserRedAddParamVo> voList = new ArrayList<UserRedAddParamVo>();
			for (OperateCouponVO couponVO : list) {
				UserRedAddParamVo vo = new UserRedAddParamVo();
				vo.setUserId(couponVO.getUserId());
				vo.setRedAmount(couponVO.getRedValue().doubleValue());
				vo.setRedCode(couponVO.getRedCode());
				voList.add(vo);
			}
			return voList;
		}
		return Collections.EMPTY_LIST;
	}
}
