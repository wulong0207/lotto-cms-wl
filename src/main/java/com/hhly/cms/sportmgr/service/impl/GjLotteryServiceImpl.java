package com.hhly.cms.sportmgr.service.impl;

import com.hhly.cms.base.rabbitmq.MQProducer;
import com.hhly.cms.sportmgr.service.GjLotteryService;
import com.hhly.cmscore.cms.remote.service.ISportBallMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.mq.msg.MessageModel;
import com.hhly.skeleton.base.mq.msg.OperateNodeMsg;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.cms.sportmgr.bo.SportAgainstInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportDataBaseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Objects;

/**
 * @author Tony Wang
 * @version 1.0
 * @desc
 * @date 2018/3/21 15:08
 * @company 益彩网络科技有限公司
 */
@Service
public class GjLotteryServiceImpl implements GjLotteryService {

    @Autowired
    private ISportBallMgrService iSportBallMgrService;

    @Autowired
    private MQProducer producer;

    @Value("${msg_queue_name}")
    private String QUEUE_NAME;

    @Override
    public PagingBO<SportAgainstInfoBO> pageGj(SMGLotteryParamVO vo) {
        return iSportBallMgrService.pageGj(vo);
    }

    @Override
    public int update(SportDataBaseVO vo) {
        String nodeDate = "";
        if(Objects.equals(vo.getMatchStatus(),"18")) {
            nodeDate = Objects.requireNonNull(vo.getLotteryCode(),"彩种为空")+";"+
                            Objects.requireNonNull(vo.getSystemCode(),"场次编号为空")+";"+
                            Objects.requireNonNull(vo.getHomeTeamFullName(),"对阵球队为空");
            vo.setHomeTeamFullName(null);
            vo.setLotteryCode(null);
            vo.setSystemCode(null);
        }
        int affected = iSportBallMgrService.updateSportAgainstInfo(vo);
        // 18已淘汰
        if(affected > 0 && Objects.equals(vo.getMatchStatus(),"18")) {
            OperateNodeMsg nodeMsg = new OperateNodeMsg(21, nodeDate);
            MessageModel msg = new MessageModel("cms", "nodeMsgSend", nodeMsg);
            producer.sendDataToQueue(QUEUE_NAME,JsonUtil.object2Json(msg));
        }
        return affected;
    }
}
