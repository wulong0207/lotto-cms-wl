package com.hhly.cms.operatemgr.service.impl;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.bo.TreeGridBO;
import com.hhly.cms.operatemgr.service.MarketChannelService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMarketChannelBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMarketChannelExcelBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMarketChannelVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class MarketChannelServiceImpl implements MarketChannelService{
	@Autowired
    private IOperateMgrService iOperateMgrService;
	
	@Autowired
	private ExcelExportService excelExportService;
	
	@Override
	public List<DictionaryBO> findAllChannelDic() {
		List<OperateMarketChannelBO> list =  iOperateMgrService.findChannelAllSimple();
		List<DictionaryBO> result = new ArrayList<DictionaryBO>();
		for (OperateMarketChannelBO bo : list) {
			DictionaryBO dic = new DictionaryBO();
			dic.setId(bo.getChannelId());
			dic.setText(bo.getChannelName());
			result.add(dic);
		}
		return result;
	}
	@Override
	public PagingBO<OperateMarketChannelBO> findMarketchannel(
			OperateMarketChannelVO vo) {
		return iOperateMgrService.findMarketChannel(vo);
	}
	@Override
	public int updOperateMarketChannel(OperateMarketChannelVO vo) {
		return iOperateMgrService.updOperateMarketChannel(vo);
	}
	@Override
	public int addOperateMarketChannel(OperateMarketChannelVO vo) {
		return iOperateMgrService.addOperateMarketChannel(vo);
	}
	@Override
	public List<DictionaryBO> findParentId(String grade,String channelId) {
		StringVO vo = new StringVO();
		vo.setStr(grade);
		List<String> list = iOperateMgrService.findParentId(vo);
		List<DictionaryBO> result = new ArrayList<DictionaryBO>();
		for (String id : list) {
			if(StringUtils.hasText(channelId)
					&&id.equals(channelId)){
				continue;
			}
			DictionaryBO dic = new DictionaryBO();
			dic.setId(id);
			dic.setText(id);
			result.add(dic);
		}
		return result;
	}
	
	@Override
	public ByteArrayOutputStream getExcelMarketChannel(OperateMarketChannelVO vo) {
		List<OperateMarketChannelExcelBO> data=  iOperateMgrService.findExcelMarketChannel(vo);
		return excelExportService.dataToExeclByStream("channel",data);
	}
	
	@Override
	public  List<TreeGridBO>  findChannelList(String channelId) {
		String channelIds[] = StringUtils.tokenizeToStringArray(channelId, SymbolConstants.COMMA);
		List<OperateMarketChannelBO> channels = iOperateMgrService.findChannelAllSimple();
		List<TreeGridBO> grids= new ArrayList<TreeGridBO>();

		//记录所有的父级id
		Set<String> parentId =new  HashSet<String>();
		for (OperateMarketChannelBO channel : channels) {
			TreeGridBO  grid = new TreeGridBO();
			grid.setId(channel.getChannelId());
			grid.setName(channel.getChannelName());
			grid.setPid(channel.getParentChannelId()==null?"0":channel.getParentChannelId());
			parentId.add(grid.getPid());
			for (int i = 0; i < channelIds.length; i++) {
				if(channel.getChannelId().equals(channelIds[i])){
					grid.setChecked(true);
					break;
				}
			}			
			grids.add(grid);
		}
		for (TreeGridBO bo : grids) {
			if(parentId.contains(bo.getId())){
				bo.setChecked(false);
			}
		}
		return grids;
	}
	@Override
	public List<OperateMarketChannelBO> listMarketchannel(OperateMarketChannelVO vo) {
		return iOperateMgrService.listMarketchannel(vo);
	}
	@Override
	public int count(OperateMarketChannelVO vo) {
		return iOperateMgrService.countMarketchannel(vo);
	}
	
	/**
	 * @desc   更新app_url字段
	 * @author Tony Wang
	 * @create 2017年10月26日
	 * @param vo 
	 */
	@Override
	public int updAppUrl(OperateMarketChannelVO vo) {
		return iOperateMgrService.updMarketchannelAppUrl(vo);
	}	

}
