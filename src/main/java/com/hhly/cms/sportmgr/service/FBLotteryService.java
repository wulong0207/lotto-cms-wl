package com.hhly.cms.sportmgr.service;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.ordermgr.bo.OrderMatchContentBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportFBLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportDataBaseVO;
import com.hhly.skeleton.lotto.base.sport.vo.FBDataVO;

/**
 * Created by lgs on 2016/12/16.
 * 竞彩足球Servcie
 */
public interface FBLotteryService {

    /**
     * 查询竞彩足球数据
     * @param vo
     * @return
     */
    PagingBO<SportFBLotteryInfoBO> findFBData(SMGLotteryParamVO vo);

    /**
     * 获取竞彩足球详情的SP值
     *
     * @param againstId
     * @return
     */
    Map<String, Object> findFBSp(Long againstId);

    /**
     * 保存用户修改竞彩足球数据
     * @param vo
     * @return
     */
    int saveFBData(FBDataVO vo);
    
    /**
     * 获取excel表格流
     */
    ByteArrayOutputStream getExcelStream(SMGLotteryParamVO vo);
    
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
     * 查询竞彩足球订单详情赛事信息集合
     * @param vo
     * @return
     * @date 2017年6月13日上午11:10:09
     * @author cheng.chen
     */
	List<OrderMatchContentBO> findFbOrderMatchDetail(Integer lotteryCode,Integer lotteryChildCode,
			String systemCode, String content);
}
