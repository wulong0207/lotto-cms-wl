package com.hhly.cms.sysmgr.service;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.sysmgr.vo.PayBankSegmentVO;

/**
 * @author Tony Wang
 * @version 1.0
 * @desc
 * @date 2018/1/12 14:18
 * @company 益彩网络科技有限公司
 */
public interface PayBankSegmentService {
    PagingBO<PayBankSegmentVO> page(PayBankSegmentVO vo);

    int merge(PayBankSegmentVO vo);
}
