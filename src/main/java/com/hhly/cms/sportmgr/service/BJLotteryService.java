package com.hhly.cms.sportmgr.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderMatchContentBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportBJLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.BJDataVO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;

/**
 * Created by lgs on 2016/12/16.
 * 北京单场Servcie
 */
public interface BJLotteryService {

    /**
     * 查询北京单场分页数据
     * @param vo
     * @return
     */
    PagingBO<SportBJLotteryInfoBO> findBJData(SMGLotteryParamVO vo);


    /**
     * 获取北京单场详情的SP值
     *
     * @param againstId
     * @return
     */
    Map<String, Object> findBJSp(Long againstId);


    /**
     * 保存北京单场修改数据
     * @param vo
     * @return
     */
    int saveBJData(BJDataVO vo);
    
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
    List<OrderMatchContentBO> findBjOrderMatchDetail(Integer lotteryCode,Integer lotteryChildCode,
			String systemCode, String content); 
}
