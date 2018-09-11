package com.hhly.cms.sysmgr.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.dicmgr.service.DictionaryService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.model.DicDataEnum;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataDetailBO;
import com.hhly.skeleton.cms.dicmgr.vo.DicDataDetailVO;

@Controller
@RequestMapping(value = "/sysmgr/customertel")
public class CustomerTelController extends BaseController {

	public static final Logger LOGGER = Logger.getLogger(CustomerTelController.class);

	@Autowired
	private DictionaryService dictionaryService;

	@RequestMapping(method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "sysmgr/customertel";
	}

	/**
	 * 获取字典表数据
	 * 
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/customerTel", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public DicDataDetailBO getCustomerTel() {
		DicDataDetailBO result = null;
		StringVO vo = new StringVO();
		vo.setStr(DicDataEnum.CUSTOMER_TELEPHONE.getDicCode());
		List<DicDataDetailBO> dataDetailBOs = dictionaryService.findDetail(vo);
		if (!ObjectUtil.isBlank(dataDetailBOs)) {
			result = dataDetailBOs.get(0);
		}
		return result;
	}
	
	@RequestMapping(value = "/updCustomerTel",method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public  Object updCustomerTel(String telNo) {
		int num = 0;
		StringVO vo = new StringVO();
		vo.setStr(DicDataEnum.CUSTOMER_TELEPHONE.getDicCode());
		List<DicDataDetailBO> dataDetailBOs = dictionaryService.findDetail(vo);
		if (!ObjectUtil.isBlank(dataDetailBOs)) {
			DicDataDetailBO dataDetailBO = dataDetailBOs.get(0);
			DicDataDetailVO dataDetailVO = new DicDataDetailVO();
			BeanUtils.copyProperties(dataDetailBO, dataDetailVO);
			dataDetailVO.setDicDataName(telNo);
			if(ObjectUtil.isBlank(dataDetailBO.getOrderBy())){
				dataDetailVO.setOrderBy(dataDetailBO.getOrderBy().shortValue());
			} else {
				dataDetailVO.setOrderBy((short)1);
			}
			
			num =  dictionaryService.updDetail(dataDetailVO);
		}
		return getSaveResult(num);
	}
}
