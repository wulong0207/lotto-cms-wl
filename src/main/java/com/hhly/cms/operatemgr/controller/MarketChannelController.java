package com.hhly.cms.operatemgr.controller;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.operatemgr.service.MarketChannelService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.common.OperateEnum;
import com.hhly.skeleton.base.common.cache.pay.PayCacheEnum.OperateMarketChannel;
import com.hhly.skeleton.base.util.EncryptUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMarketChannelVO;
/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2017-1-3 上午9:32:28
 * @Desc 市场渠道管理
 */
@Controller
@RequestMapping(value = "/operatemgr/marketchannel")
public class MarketChannelController extends BaseController {
	
	@Autowired
	private MarketChannelService channelService;
	 
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(){
		return "operatemgr/market_channel";
	}
	
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	public @ResponseBody Object list(OperateMarketChannelVO vo){
		// 改成用treeGrid显示，不分页
		vo.setPageSize(Integer.MAX_VALUE);
		return channelService.listMarketchannel(vo);
	}
	
	@RequestMapping(method = RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@DeleteBatchAssignCache(GetCacheEnumService = OperateMarketChannel.class)
	@ResponseBody
	@ParameterValid
	public  Object udpate(@Valid(GroupValue.UPD)OperateMarketChannelVO vo,HttpSession session) {
		vo.setModifyBy(getUserRealName(session));
//		Assert.isTrue(vo.getCoopStartTime().getTime() < vo.getCoopEndTime().getTime(), "合作开始结束时间错误");
		if(ObjectUtil.isBlank(vo.getSecretKey()))
			vo.setSecretKey(EncryptUtil.getRandomString(24));
		int num = channelService.updOperateMarketChannel(vo);
		return getSaveResult(num);
	}
	
	@RequestMapping(method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@DeleteBatchAssignCache(GetCacheEnumService = OperateMarketChannel.class)
	@ResponseBody
	@ParameterValid
	public  Object add(@Valid(GroupValue.ADD)OperateMarketChannelVO vo,HttpSession session) {
		vo.setCreateBy(getUserRealName(session));
//		Assert.isTrue(vo.getCoopStartTime().getTime() < vo.getCoopEndTime().getTime(), "合作开始结束时间错误");
		vo.setSecretKey(EncryptUtil.getRandomString(24));
		int num = channelService.addOperateMarketChannel(vo);
		return getSaveResult(num);
	}
	/**
	 * 
	 * @param grade 级别
	 * @param channelId 自己本身渠道ID
	 * @return
	 */
	@RequestMapping(value = "/parent/id")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object findParentId(@RequestParam(value = "grade", required = true)String grade,
			@RequestParam(value = "channelId", required = false)String channelId){
		return getResultSuccess(channelService.findParentId(grade,channelId));
	}
	
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,OperateMarketChannelVO vo) throws IOException{
		ByteArrayOutputStream outputStream=channelService.getExcelMarketChannel(vo);
		excel("channel", outputStream, response);
	}
	
    /**
     * @desc   查询所有渠道类型，以MiniUI数据字典的格式{id:1,text:'字典值'}返回
     * @author Tony Wang
     * @create 2017年4月21日
     * @return 
     */
    @RequestMapping(value="/dic", method = RequestMethod.GET)
    @ResponseBody
    @Authority(privilege =AuthEnum.ALL)
    public List<DictionaryBO> findDic() {
    	// 增加"全部渠道"选项
    	List<DictionaryBO> options = channelService.findAllChannelDic();
    	options.add(0,  new DictionaryBO(OperateEnum.AD_ALL_CHANNELS, "全部渠道"));
    	return options;
    }
    
	/**
	 * 查询所有渠道(用于加载树形)
	 * @return
	 */
	@RequestMapping(value = "/tree")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object channelList(@RequestParam(value = "channelId") String channelId){
		return channelService.findChannelList(channelId);
	}    
}
