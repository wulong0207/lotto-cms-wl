package com.hhly.cms.sportmgr.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderOldMatchContentBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportOldLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.lotto.base.sport.vo.OldDataVO;

/**
 * Created by lgs on 2016/12/6.
 */
public interface OldFBLotteryService {

    /**
     * 查询老足彩数据
     * @param vo
     * @return
     */
    PagingBO<SportOldLotteryInfoBO> findOldLotteryInfo(SMGLotteryParamVO vo);


    /**
     * 获取老足彩详情的SP值
     *
     * @param againstId
     * @return
     */
    Map<String, Object> findOldSp(Long againstId);

    /**
     * 保存老足彩数据
     * 主要保存球队信息|赛事信息|对阵信息|赔率信息
     * @param vo
     * @return
     */
    int saveOldData(OldDataVO vo);
    
    /**
     * 获取excel表格流
     */
    ByteArrayOutputStream getExcelStream(SMGLotteryParamVO vo);
    
    /**
     * 查询老足彩订单详情赛事信息集合
     * @param vo
     * @return
     * @date 2017年6月11日下午5:00:53
     * @author cheng.chen
     */
    List<OrderOldMatchContentBO> findOldOrderMatchDetail(Integer lotteryCode, String issueCode, String content);
}
