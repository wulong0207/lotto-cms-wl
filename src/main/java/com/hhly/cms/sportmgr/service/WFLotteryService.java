package com.hhly.cms.sportmgr.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderMatchContentBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportWFLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.WFDataVO;

/**
 * Created by lgs on 2017/1/3.
 * 胜负过关service
 */
public interface WFLotteryService {


    PagingBO<SportWFLotteryInfoBO> findWFData(SMGLotteryParamVO vo);


    /**
     * 胜负过关SP
     *
     * @param againstId
     * @return
     */
    Map<String, Object> findWFSp(Long againstId);


    int saveWFData(WFDataVO vo);

    /**
     * 根据彩种审核赛事比分
     * @param lotteryCode
     * @return
     * @date 2017年5月11日下午7:43:17
     * @author cheng.chen
     */
    int updCheckScore(String lotteryCode, String modifyBy);
    
    /**
     * 获取excel表格流
     */
    ByteArrayOutputStream getExcelStream(SMGLotteryParamVO vo);
    
    /**
     * 查询竞彩北单订单详情赛事集合信息
     * @param vo
     * @return
     * @date 2017年10月25日下午6:30:30
     * @author cheng.chen
     */
    List<OrderMatchContentBO> findWfOrderMatchDetail(Integer lotteryCode,Integer lotteryChildCode,
			String systemCode, String content);     
}
