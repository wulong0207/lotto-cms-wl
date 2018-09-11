package com.hhly.cms.burypoint.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hhly.cms.burypoint.service.BuryPointService;
import com.hhly.cms.burypoint.vo.*;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.cmscore.cms.remote.service.ISysMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.dicmgr.bo.DicDataDetailBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMarketChannelBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateSoftwareVersionBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMarketChannelVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateSoftwareVersionVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @desc    数据埋点服务
 * @author  Tony Wang
 * @date    2017年12月14日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class BuryPointServiceImpl implements BuryPointService{

	@Value("${point_core_url}")
	private String POINT_CORE_URL;

	private static String POINT_URL="point";
	private static String FUNNEL_URL="funnel";
	private static String PAGE_URL="page";
	private static String MODE_URL="mode";
	private static String BUTTON_URL="button";
	private static String USER_URL="user";

	private RestTemplate rest = new RestTemplate();
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

	@Autowired
	private IOperateMgrService iOperateMgrService;

	@Autowired
	private ISysMgrService iSysMgrService;

	@Override
	public PagingBO<BpFunnelVO> pageFunnel(BpFunnelVO vo) {
		PagingBO<BpFunnelVO> page = gson.fromJson(httpExchange(rest, POINT_CORE_URL+FUNNEL_URL+"/page", vo, HttpMethod.POST),
				new TypeToken<PagingBO<BpFunnelVO>>(){}.getType());
		List<BpFunnelVO> funnels = page.getData();
		if(!CollectionUtils.isEmpty(funnels)) {
			List<OperateMarketChannelBO> channels = iOperateMgrService.listMarketchannel(new OperateMarketChannelVO());
			Map<String, String> channelsMap = new HashMap<>();
			for(OperateMarketChannelBO channel : channels) {
				channelsMap.put(channel.getChannelId(),channel.getChannelName());
			}

			StringVO strVO = new StringVO();
			strVO.setStr("0702");
			List<DicDataDetailBO> platforms = iSysMgrService.findDicDataDetailSimple(strVO);
			Map<String, String> platformsMap = new HashMap<>();
			for(DicDataDetailBO platform : platforms) {
				platformsMap.put(platform.getDicDataValue(),platform.getDicDataName());
			}

			List<OperateSoftwareVersionBO> versions = iOperateMgrService.distinctSoftwareVersionName(new OperateSoftwareVersionVO());
			Map<String, String> versionsMap = new HashMap<>();
			for(OperateSoftwareVersionBO version : versions) {
				versionsMap.put(version.getCode()+"",version.getName());
			}

			for(BpFunnelVO funnel : funnels) {
				String channelsId = funnel.getChannelsId();
				if(StringUtils.hasText(channelsId)) {
					// 一比分内嵌，本站H5，渠道A，渠道B渠道C
					StringBuilder channelsIdText = new StringBuilder();
					for(String channelId : channelsId.split(",")) {
						channelsIdText.append(channelsMap.get(channelId)).append(",");
					}
					funnel.setChannelsIdText(channelsIdText.substring(0, channelsIdText.length()-1));
				}
				String platformsId = funnel.getPlatformsId();
				if(StringUtils.hasText(platformsId)) {
					StringBuilder platformsIdText = new StringBuilder();
					for(String platformId : platformsId.split(",")) {
						platformsIdText.append(platformsMap.get(platformId)).append(",");
					}
					funnel.setPlatformsIdText(platformsIdText.substring(0, platformsIdText.length()-1));
				}
				String versionsId = funnel.getVersionsId();
				if(StringUtils.hasText(versionsId)) {
					StringBuilder versionsIdText = new StringBuilder();
					for(String version : versionsId.split(",")) {
						versionsIdText.append(versionsMap.get(version)).append(",");
					}
					funnel.setVersionsIdText(versionsIdText.substring(0, versionsIdText.length()-1));
				}
			}
		}
		return page;
	}

	@Override
	public int mergeFunnel(BpFunnelVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+FUNNEL_URL+"/merge", vo, HttpMethod.POST),Integer.class);
	}

	@Override
	public PagingBO<BpUserBaseInfoPO> pageUser(BpUserVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+USER_URL+"/page", vo, HttpMethod.POST),
				new TypeToken<PagingBO<BpUserBaseInfoPO>>(){}.getType());
	}

	@Override
	public String analyzeFunnel(BpFunnelAnalyVO vo) {
		return httpExchange(rest, POINT_CORE_URL+FUNNEL_URL+"/analysis", vo, HttpMethod.POST);
	}

	@Override
	public int deletePoint(BpPointVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+POINT_URL, vo, HttpMethod.PUT),
				Integer.class);
	}

	@Override
	public List<BpPageVO> findPage(BpPageVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+PAGE_URL, vo, HttpMethod.POST),
				new TypeToken<List<BpPageVO>>(){}.getType());
	}

	@Override
	public int addPage(BpPageVO vo) {
		return rest.postForObject(POINT_CORE_URL+PAGE_URL+"/add", vo,Integer.class);
	}

	@Override
	public int addMode(BpModeVO vo) {
		return rest.postForObject(POINT_CORE_URL+MODE_URL+"/add", vo,Integer.class);
	}

	@Override
	public List<BpModeVO> findMode(BpModeVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+MODE_URL, vo, HttpMethod.POST),
				new TypeToken<List<BpModeVO>>(){}.getType());
	}

	@Override
	public int addButton(BpButtonVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+BUTTON_URL+"/add", vo, HttpMethod.POST),
				Integer.class);
	}

	@Override
	public List<BpButtonVO> findButton(BpButtonVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+BUTTON_URL, vo, HttpMethod.POST),
				new TypeToken<List<BpButtonVO>>(){}.getType());
	}

	@Override
	public int addPoint(BpPointVO vo) {
		String httpStr = httpExchange(rest, POINT_CORE_URL+POINT_URL+"/add", vo, HttpMethod.POST);
		return gson.fromJson(httpStr, Integer.class);
	}

	@Override
	public int deleteButton(BpButtonVO vo) {
		rest.delete(POINT_CORE_URL+BUTTON_URL+"/{id}", vo.getId());
		return 1;
	}

	@Override
	public int deleteMode(BpModeVO vo) {
		rest.delete(POINT_CORE_URL+MODE_URL+"/{id}", vo.getId());
		return 1;
	}

	@Override
	public int deletePage(BpPageVO vo) {
		rest.delete(POINT_CORE_URL+PAGE_URL+"/{id}", vo.getId());
		return 1;
	}

	@Override
	public PagingBO<BpPointVO> pagePoint(BpPointVO vo) {
		String httpStr = httpExchange(rest, POINT_CORE_URL+POINT_URL+"/page", vo, HttpMethod.POST);
		return gson.fromJson(httpStr, new TypeToken<PagingBO<BpPointVO>>(){}.getType());
	}

	private <T> String httpExchange(RestTemplate rest, String url, T vo, HttpMethod method) {
		HttpEntity<T> request = new HttpEntity<>(vo);
		ResponseEntity<String> res = rest.exchange(
				url,
				method,
				request,
				new ParameterizedTypeReference<String>() {});
		String httpStr = res.getBody();
		Assert.isTrue(res.getStatusCode()== HttpStatus.OK, httpStr);
		return httpStr;
	}

	@Override
	public int deleteFunnel(BpFunnelVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+FUNNEL_URL, vo, HttpMethod.PUT),
				Integer.class);
	}

	@Override
	public List<BpPointVO> findPoint(BpPointVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+POINT_URL, vo, HttpMethod.POST),
				new TypeToken<List<BpPointVO>>(){}.getType());
	}
	
	@Override
	public List<BpFunnelVO> findFunnel(BpFunnelVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+FUNNEL_URL, vo, HttpMethod.POST),
				new TypeToken<List<BpFunnelVO>>(){}.getType());
	}

	@Override
	public BpFunnelVO findOneFunnel(BpFunnelVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+FUNNEL_URL+"/one", vo, HttpMethod.POST),
				BpFunnelVO.class);
	}

	@Override
	public List<Long> findUserIds(BpFunnelAnalyVO vo) {
		return gson.fromJson(httpExchange(rest, POINT_CORE_URL+USER_URL+"/userIds", vo, HttpMethod.POST),
				new TypeToken<List<Long>>(){}.getType());
	}

}
