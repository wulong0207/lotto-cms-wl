package com.hhly.cms.sysmgr.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.dicmgr.service.DictionaryService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.common.cache.sys.SysCacheEnum;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.vo.DicDataDetailVO;
import com.hhly.skeleton.cms.dicmgr.vo.DicDataVO;
/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-11-14 上午11:24:52
 * @Desc 系统字典管理
 */
@Controller
@RequestMapping(value = "/sysmgr/dic")
public class DictionaryController extends BaseController {
	
	@Autowired
	private DictionaryService dictionaryService;
	 
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(){
		return "sysmgr/dictionary";
	}
	/**
	 * 查询列表
	 * @param pageNo
	 * @param pageSize
	 * @param name
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object list(DicDataVO vo){
		return  dictionaryService.find(vo);
	}
	/**
	 * 添加
	 * @param vo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	public  Object add(@Valid(GroupValue.ADD)DicDataVO vo) {
		int num =  dictionaryService.add(vo);
		return getSaveResult(num);
	}
	/**
	 * 修改
	 * @param vo
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService=SysCacheEnum.DicDataCacheEnum.class, isLikeQuery=true)
	public  Object udpate( @Valid(GroupValue.UPD)DicDataVO vo) {
		int num = dictionaryService.update(vo);
		return getSaveResult(num);
	}
	
	@RequestMapping(value = "/list/detail")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object listDetail(DicDataDetailVO vo){
		return  dictionaryService.findDetails(vo);
	}
	/**
	 * 添加
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/detail",method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService=SysCacheEnum.DicDataCacheEnum.class, isLikeQuery=true)
	public  Object addDetail(@Valid(GroupValue.ADD)DicDataDetailVO vo) {
		int num =  dictionaryService.addDetail(vo);
		return getSaveResult(num);
	}
	
	@RequestMapping(value = "/detail",method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	@DeleteBatchAssignCache(GetCacheEnumService=SysCacheEnum.DicDataCacheEnum.class, isLikeQuery=true)
	public  Object updateDetail(@Valid(GroupValue.UPD)DicDataDetailVO vo) {
		int num =  dictionaryService.updDetail(vo);
		return getSaveResult(num);
	}
	
	@RequestMapping(value = "/detail/{id}",method = RequestMethod.DELETE)
	@Authority(privilege=AuthEnum.DEL)
	@ResponseBody
	@DeleteBatchAssignCache(GetCacheEnumService=SysCacheEnum.DicDataCacheEnum.class, isLikeQuery=true)
	public  Object delDetail(@PathVariable(value="id") String id) {
		StringVO vo = new StringVO();
		vo.setStr(id);
		int num =  dictionaryService.delDetail(vo);
		return getSaveResult(num);
	}
	
	/**
	 * 查询字典
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/dictionary")
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public  Object listDic(
			@RequestParam(value = "code", required = true) String code){
		StringVO vo = new StringVO();
		vo.setStr(code);
		return  dictionaryService.findDictionary(vo);
	}
	
	/**
	 * 查询分析师级别字典
	 * @param code
	 * @return
	 */
	@RequestMapping(value = "/findRcmdUserLevel")
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public  Object findRcmdUserLevel(){
		return  dictionaryService.findRcmdUserLevel();
	}
}
