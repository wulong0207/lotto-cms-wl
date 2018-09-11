package com.hhly.report.remote.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.rcmdmgr.bo.RcmdBO;
import com.hhly.skeleton.cms.rcmdmgr.bo.RcmdRecordBO;
import com.hhly.skeleton.cms.rcmdmgr.vo.RcmdRecordVO;
import com.hhly.skeleton.cms.rcmdmgr.vo.RcmdVO;

import java.util.List;

/**
 * @author lgs on
 * @version 1.0
 * @desc 推荐统计表
 * @date 2018/8/9.
 * @company 益彩网络科技有限公司
 */
public interface IRcmdService {

    /**
     * 查询推单数据统计
     *
     * @param vo
     * @return
     */
    PagingBO<RcmdBO> findRcmdList(RcmdVO vo);


    /**
     * 查询推单数据统计
     *
     * @param vo
     * @return
     */
    List<RcmdBO> findRcmdExcelList(RcmdVO vo);
}
