package com.hhly.cms.ordermgr.service.impl;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hhly.cms.ordermgr.service.OrderRcmdUserService;
import com.hhly.cmscore.cms.remote.service.IOrderMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.util.HttpUtil;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.cms.recommend.bo.RcmdUserCheckBO;
import com.hhly.skeleton.cms.recommend.vo.RcmdUserCheckVO;

@Service
public class OrderRcmdUserServiceImpl implements OrderRcmdUserService {
	@Autowired
    private IOrderMgrService iOrderMgrService;
	
	@Value("${user_core_url}")
	private String userCoreUrl;

	@Override
	public PagingBO<RcmdUserCheckBO> findRcmdUserCheckList(RcmdUserCheckVO vo) {
		return iOrderMgrService.findRcmdUserCheckList(vo);
	}

	@Override
	public int setStatus(RcmdUserCheckVO vo) {
		return iOrderMgrService.setStatus(vo);
	}

	@SuppressWarnings("unchecked")
	@Override
	public int addRcmdUser(RcmdUserCheckVO vo) {
		if(StringUtil.isBlank(vo.getCusMobile()))
			return 0;
		Map<String,Object> params = new HashMap<String,Object>();
		params.put("idCard", vo.getIdNum());
		params.put("realName", vo.getUserName());
		params.put("mobile", vo.getCusMobile());
		try
		{
			
			String rs = HttpUtil.doPost(userCoreUrl + "passport/register/regByCms", JsonUtil.object2Json(params));
			ResultBO<Integer> bo = (ResultBO<Integer>) JsonUtil.json2Object(rs, ResultBO.class);
			if(bo.getSuccess() == 1){
				vo.setUserId(bo.getData());
				return iOrderMgrService.addRcmdUser(vo);
			}else{
				return 0;
			}
		} catch (IOException e) {
			e.printStackTrace();
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return 0;
	}
}
