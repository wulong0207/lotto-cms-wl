package com.hhly.cms.burypoint.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.burypoint.service.BuryPointService;
import com.hhly.cms.burypoint.vo.BpFunnelAnalyVO;
import com.hhly.cms.burypoint.vo.BpUserBaseInfoPO;
import com.hhly.cms.burypoint.vo.BpUserVO;
import com.hhly.cms.customermgr.service.CustomerService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.customermgr.bo.LottoCustomerBO;
import com.hhly.skeleton.cms.customermgr.vo.LottoCustomerVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @desc    埋点
 * @author  Tony Wang
 * @date    2017年12月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@RequestMapping("bp/user")
@Controller
public class UserController extends BaseController {

	@Autowired
	private BuryPointService buryPointService;

	@Autowired
	private CustomerService customerService;

	@RequestMapping(method=RequestMethod.GET)
	@Authority(privilege = AuthEnum.ALL)
	public String index() {
		return "burypoint/user";
	}

	@RequestMapping(value="page",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public PagingBO<BpUserBaseInfoPO> page(BpUserVO vo){
		PagingBO<BpUserBaseInfoPO> page = buryPointService.pageUser(vo);
		if(page.getTotal()>0) {
            // point-core的bp_user_base_info表不维护用户注册渠道时间和用户注册渠道id，Cms自行查询
            LottoCustomerVO userVO = new LottoCustomerVO();
            userVO.setType("u.id");
            userVO.setValues(page.getData().stream().map(u -> u.getUserId()+"").collect(Collectors.toList()));
            userVO.setPageIndex(0);
            userVO.setPageSize(userVO.getValues().size());
            PagingBO<LottoCustomerBO> cmsUserInfos = customerService.findLottoCustomer(userVO);
            Map<Long,LottoCustomerBO> cmsUserInfosMap = cmsUserInfos.<LottoCustomerBO>getData().stream().collect(Collectors.toMap(u->u.getId().longValue(),u->u));
            page.getData().stream().forEach(u->{
                LottoCustomerBO tmp = cmsUserInfosMap.get(u.getUserId());
                // 如果埋点的用户数据没有用户id，则返回
                if(tmp==null) return;
                u.setRegisterChannelId(Integer.parseInt(tmp.getChannelId()));
                u.setRegisterTime(tmp.getRegistTime());
            });
        }
		return page;
	}

	@RequestMapping(value="userIds",method=RequestMethod.GET)
	@Authority(privilege=AuthEnum.ALL)
	@ResponseBody
	public List<Long> findUserIds(BpFunnelAnalyVO vo){
		return buryPointService.findUserIds(vo);
	}
}
