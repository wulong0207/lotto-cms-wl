package com.hhly.cms.base.rabbitmq;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.hhly.cms.ticket.service.IAlarmService;
import com.hhly.skeleton.base.valid.util.ParamUtil;
import com.hhly.skeleton.cms.ticketmgr.bo.TicketAlarmInfoBO;
import com.hhly.skeleton.cms.ticketmgr.vo.TicketAlarmInfoVO;

/**
 * @desc 数字彩订单列队监听
 * @author jiangwei
 * @date 2017年5月9日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Component
public class AlarmInfoListenter implements MessageListener {

	private static final Logger LOGGER = Logger.getLogger(AlarmInfoListenter.class);

	private static final Map<String, AlarmInfo> ALARM_INFO_MAP = new ConcurrentHashMap<>();

	@Autowired
	private IAlarmService alarmService;

	private static final List<Integer> ALARM_CHILD_LIST = new ArrayList<>();

	static {
		ALARM_CHILD_LIST.add(15);
		ALARM_CHILD_LIST.add(16);
		ALARM_CHILD_LIST.add(17);
		ALARM_CHILD_LIST.add(18);
		ALARM_CHILD_LIST.add(31);
		ALARM_CHILD_LIST.add(34);
		ALARM_CHILD_LIST.add(35);
	}

	@Override
	public void onMessage(Message message) {
		String result = "";
		try {
			result = new String(message.getBody(), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			LOGGER.error(message.toString(), e);
		}
		if (StringUtils.isEmpty(result)) {
			return;
		}
		LOGGER.info("mq接受到报警信息" + result);
		try {
			TicketAlarmInfoVO vo = JSONObject.parseObject(result, TicketAlarmInfoVO.class);
			vo.setStatus((short) 0);
			vo.setTypeId(0);
			ParamUtil.validation(vo);
			int id = alarmService.addTicketAlarmInfo(vo);
			if (vo.getAlarmType() == 1) {
				switch (vo.getAlarmChild()) {
				case 15:
				case 16:
				case 17:
				case 18:
				case 31:
				case 34:
				case 35:
					AlarmInfo info = new AlarmInfo();
					info.setId(id);
					info.setInfo(vo.getAlarmInfo());
					info.setTime(System.currentTimeMillis());
					ALARM_INFO_MAP.put(String.valueOf(id), info);
					break;
				default:
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.info("添加报警信息错误", e);
		}

		if (ALARM_INFO_MAP.size() > 50) {
			List<AlarmInfo> list = new ArrayList<>(ALARM_INFO_MAP.values());
			ALARM_INFO_MAP.clear();
			Collections.sort(list);
			for (int i = 0; i < list.size(); i++) {
				if (i > 20) {
					break;
				}
				AlarmInfo info = list.get(i);
				ALARM_INFO_MAP.put(String.valueOf(info.getId()), info);
			}
		}
	}

	public static void remove(String id) {
		ALARM_INFO_MAP.remove(id);
	}

	public static List<Integer> getAlarmChildList() {
		return ALARM_CHILD_LIST;
	}

	public static Map<String, String> getInfo() {
		Map<String, String> result = new HashMap<>();
		for (Map.Entry<String, AlarmInfo> entry : ALARM_INFO_MAP.entrySet()) {
			result.put(entry.getKey(), entry.getValue().getInfo());
		}
		return result;
	}

	/**
	 * 初始化
	 * 
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2018年7月31日 下午6:10:21
	 * @param alarmInfos
	 */
	public static void init(List<TicketAlarmInfoBO> alarmInfos) {
		for (TicketAlarmInfoBO bo : alarmInfos) {
			AlarmInfo info = new AlarmInfo();
			info.setId(bo.getId());
			info.setInfo(bo.getAlarmInfo());
			info.setTime(bo.getAlarmTime().getTime());
			ALARM_INFO_MAP.put(String.valueOf(bo.getId()), info);
		}
	}
}
