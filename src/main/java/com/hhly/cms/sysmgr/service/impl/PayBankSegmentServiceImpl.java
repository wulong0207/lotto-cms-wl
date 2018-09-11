package com.hhly.cms.sysmgr.service.impl;

import com.hhly.cms.sysmgr.service.PayBankSegmentService;
import com.hhly.cmscore.cms.remote.service.ISysMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.sysmgr.vo.PayBankSegmentVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Tony Wang
 * @version 1.0
 * @desc
 * @date 2018/1/12 14:19
 * @company 益彩网络科技有限公司
 */
@Service
public class PayBankSegmentServiceImpl implements PayBankSegmentService {

    @Autowired
    private ISysMgrService iSysMgrService;

    @Override
    public PagingBO<PayBankSegmentVO> page(PayBankSegmentVO vo) {
        return iSysMgrService.pagePayBankSegment(vo);
    }

    @Override
    public int merge(PayBankSegmentVO vo) {
        return iSysMgrService.mergePayBankSegment(vo);
    }
}
