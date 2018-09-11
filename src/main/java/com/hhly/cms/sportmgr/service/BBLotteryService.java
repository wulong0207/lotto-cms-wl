package com.hhly.cms.sportmgr.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderMatchContentBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportBBLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.BBDataVO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportDataBaseVO;

/**
 * Created by lgs on 2016/12/23.
 * 竞彩篮球service
 */
public interface BBLotteryService {
    /**
     * 查询竞彩篮球数据
     * @param vo
     * @return
     */
    PagingBO<SportBBLotteryInfoBO> findBBData(SMGLotteryParamVO vo);

    /**
     * 获取竞彩篮球详情的SP值
     *
     * @param againstId
     * @return
     */
    Map<String, Object> findBBSp(Long againstId);

    /**
     * 保存竞彩篮球修改后的数据
     * @param vo
     * @return
     */
    int saveBBData(BBDataVO vo);
    
    /**
     * 根据彩种审核赛事比分
     * @param lotteryCode 彩种
     * @return
     * @date 2017年5月11日下午6:18:01
     * @author cheng.chen
     */
    int updCheckScore(String lotteryCode, String modifyBy);

    /**
     * 修改对阵赛事信息
     * @param vo
     * @return
     * @date 2017年5月22日下午4:27:50
     * @author cheng.chen
     */
	int updSportAgainstInfo(SportDataBaseVO vo);
	
    /**
     * 获取excel表格流
     */
    ByteArrayOutputStream getExcelStream(SMGLotteryParamVO vo);
    
    /**
     * 查询竞彩篮球订单详情赛事信息集合
     * @param vo
     * @return
     * @date 2017年6月13日下午7:17:27
     * @author cheng.chen
     */
    List<OrderMatchContentBO> findBbOrderMatchDetail(Integer lotteryCode,Integer lotteryChildCode,
			String systemCode, String content);
}
