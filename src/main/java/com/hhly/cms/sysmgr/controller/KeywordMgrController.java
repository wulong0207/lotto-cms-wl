package com.hhly.cms.sysmgr.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.sysmgr.service.CMSKeywordService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.JsonUtil;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sysmgr.vo.CmsKeywordVO;

@RequestMapping("/sysmgr/keyword")
@Controller
public class KeywordMgrController extends BaseController {

	@Autowired
	private CMSKeywordService cMSKeywordService;

	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "sysmgr/keyword";
	}

	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object list(CmsKeywordVO vo) {
		return cMSKeywordService.findKeyword(vo);
	}
	
	@RequestMapping("addList")
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public Object addList(CmsKeywordVO vo,HttpSession session){
		vo.setCreateBy(getUserRealName(session));
		return getSaveResult(cMSKeywordService.addKeywordList(vo));
	}
	
	@RequestMapping("updateList")
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	public Object updateList(String json,HttpSession session){
		@SuppressWarnings("unchecked")
		List<CmsKeywordVO> list=JsonUtil.json2ObjectList(json,CmsKeywordVO.class);
		
		for(CmsKeywordVO vo:list){
			vo.setModifyBy(getUserRealName(session));
		}
		
		return getSaveResult(cMSKeywordService.updateKeywordList(list));
	}
	
	@RequestMapping("export")
    @Authority(privilege=AuthEnum.SEARCH)
    public void export(HttpServletResponse response,CmsKeywordVO vo) throws IOException{
		vo.setKeyword(new String(vo.getKeyword().getBytes("ISO-8859-1"), "utf-8"));
    	
    	ByteArrayOutputStream outputStream=cMSKeywordService.getExcelStream(vo);
    	excel("keyword", outputStream, response);
    }

	
}
