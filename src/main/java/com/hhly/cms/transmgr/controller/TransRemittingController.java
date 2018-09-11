package com.hhly.cms.transmgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.transmgr.service.TransMgrService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.cms.transmgr.bo.TransRemittingBO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Date;

/**
 * 汇款管理
 * @author wytong
 * @Version 1.0
 * @CreatDate 2017年1月3日 下午6:35:16
 */
@Controller
@RequestMapping(value="/transmgr/remitting")
public class TransRemittingController extends BaseController {

	@Autowired	
	private TransMgrService transMgrService;
	
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String index(){
		return "transmgr/trans_remitting";
	}
	
	@RequestMapping(value = "/page")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public PagingBO<TransRemittingBO> list(TransRemittingBO vo){
		return transMgrService.pageTransRemitting(vo);
	}
	
	@RequestMapping("/excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(TransRemittingBO vo, HttpServletResponse response) throws IOException{
		excel("remitting", transMgrService.excelTransRemitting(vo), response);
	}

	@RequestMapping(value = "", method = RequestMethod.PUT)
	@Authority(privilege =AuthEnum.UPD)
	@ResponseBody
	public ResultBO update(TransRemittingBO vo, HttpSession session) throws IOException{
		vo.setModifyBy(getUserRealName(session));
		vo.setModifyTime(new Date());
		int upd = transMgrService.updateTransRemitting(vo);
		return getSaveResult(upd);
	}
}
