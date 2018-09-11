package com.hhly.cms.sportmgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportAgainstInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportDataBaseVO;

/**
 * @author Tony Wang
 * @version 1.0
 * @desc    冠军\冠亚军
 * @date 2018/3/21 15:05
 * @company 益彩网络科技有限公司
 */
public interface GjLotteryService {
    /**
     * 查询冠军对阵
     * @param vo
     * @return
     */
    PagingBO<SportAgainstInfoBO> pageGj(SMGLotteryParamVO vo);

    /**
     * 更新冠军对阵
     * @param vo
     * @return
     */
    int update(SportDataBaseVO vo);
}
