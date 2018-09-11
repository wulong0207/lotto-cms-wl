package com.hhly.cms.transmgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.*;

import com.hhly.cms.utils.DateUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.transmgr.bo.*;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.paymentmgr.service.PaymentService;
import com.hhly.cms.transmgr.service.TransMgrService;
import com.hhly.cms.utils.DicUtils;
import com.hhly.cmscore.cms.remote.service.ITransMgrService;
import com.hhly.pay.service.RechargeService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.util.MathUtil;
import com.hhly.skeleton.cms.payment.bo.PayBankBO;
import com.hhly.skeleton.cms.payment.vo.PayBankVO;
import com.hhly.skeleton.cms.transmgr.vo.TransChannelSettleVO;
import com.hhly.skeleton.cms.transmgr.vo.TransRechargeVO;
import com.hhly.skeleton.cms.transmgr.vo.TransRedVO;
import com.hhly.skeleton.cms.transmgr.vo.TransTakenVO;
import com.hhly.skeleton.cms.transmgr.vo.TransUserVO;
import com.hhly.skeleton.pay.vo.CmsRechargeVO;

/**
 * 财务管理
 *
 * @author wytong
 * @Version 1.0
 * @CreatDate 2017年1月5日 上午9:48:38
 */
@Service
public class TransMgrServiceImpl implements TransMgrService {

    private static Logger logger = Logger.getLogger(TransMgrServiceImpl.class);
    @Autowired
    private ITransMgrService iTransMgrService;
    @Autowired
    private ExcelExportService excelExportService;
    @Autowired
    private PaymentService paymentService;
    @Autowired
    private RechargeService rechargeService;

    /**
     * 查询用户流水
     *
     * @param vo
     * @return
     */
    @Override
    public PagingBO<TransUserBO> findUserTrans(TransUserVO vo) {
        if (ObjectUtil.isBlank(vo.getChannelName())
                && ObjectUtil.isBlank(vo.getEndIssue())
                && ObjectUtil.isBlank(vo.getLotteryCategory())
                && ObjectUtil.isBlank(vo.getLotteryCode())
                && ObjectUtil.isBlank(vo.getOrderCode())
                && ObjectUtil.isBlank(vo.getOrderInfo())
                && ObjectUtil.isBlank(vo.getStartIssue())
                && ObjectUtil.isBlank(vo.getThirdTransId())
                && ObjectUtil.isBlank(vo.getTransCode())
                && ObjectUtil.isBlank(vo.getTransType())
                && ObjectUtil.isBlank(vo.getUserSearchValue())
                && ObjectUtil.isBlank(vo.getStartTime())
                && ObjectUtil.isBlank(vo.getEndTime())
                ) {


            try {
                vo.setStartTime(DateUtil.convertStrToDate(DateUtil.getNow("yyyy-MM-dd 00:00:00")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            vo.setEndTime(new Date());
        }
        return iTransMgrService.findUserTrans(vo);
    }

    @Override
    public PagingBO<TransRedBO> findRedTrans(TransRedVO vo) {

        if (ObjectUtil.isBlank(vo.getRedCode())
                && ObjectUtil.isBlank(vo.getEndIssue())
                && ObjectUtil.isBlank(vo.getLotteryCategory())
                && ObjectUtil.isBlank(vo.getLotteryCode())
                && ObjectUtil.isBlank(vo.getOrderCode())
                && ObjectUtil.isBlank(vo.getOrderInfo())
                && ObjectUtil.isBlank(vo.getStartIssue())
                && ObjectUtil.isBlank(vo.getRedTransCode())
                && ObjectUtil.isBlank(vo.getRedType())
                && ObjectUtil.isBlank(vo.getTransType())
                && ObjectUtil.isBlank(vo.getUserSearchValue())
                && ObjectUtil.isBlank(vo.getTransStatus())
                && ObjectUtil.isBlank(vo.getStartTime())
                && ObjectUtil.isBlank(vo.getEndTime())
                ) {
            try {
                vo.setStartTime(DateUtil.convertStrToDate(DateUtil.getNow("yyyy-MM-dd 00:00:00")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            vo.setEndTime(new Date());
            if (vo.getSearchTimeType() == null) {
                vo.setSearchTimeType(2);
            }
        }


        return iTransMgrService.findRedTrans(vo);
    }

    @Override
    public PagingBO<TransRechargeBO> findRechargeTrans(TransRechargeVO vo) {

        if (ObjectUtil.isBlank(vo.getBankCardType())
                && ObjectUtil.isBlank(vo.getChannelName())
                && ObjectUtil.isBlank(vo.getPayType())
                && ObjectUtil.isBlank(vo.getRechargeChannel())
                && ObjectUtil.isBlank(vo.getRechargeBank())
                && ObjectUtil.isBlank(vo.getThirdTransNum())
                && ObjectUtil.isBlank(vo.getTransRechargeCode())
                && ObjectUtil.isBlank(vo.getTransStatus())
                && ObjectUtil.isBlank(vo.getSearchUserValue())
                && ObjectUtil.isBlank(vo.getStartTime())
                && ObjectUtil.isBlank(vo.getEndTime())
                ) {
            try {
                vo.setStartTime(DateUtil.convertStrToDate(DateUtil.getNow("yyyy-MM-dd 00:00:00")));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            vo.setEndTime(new Date());
        }

        return iTransMgrService.findRechargeTrans(vo);
    }

    @Override
    public PagingBO<TransTakenBO> findTakenTrans(TransTakenVO vo) {
        return iTransMgrService.findTakenTrans(vo);
    }

    @Override
    public PagingBO<TransChannelSettleBO> findChannelSettleTrans(TransChannelSettleVO vo) {
        return iTransMgrService.findChannelSettleTrans(vo);
    }

    @Override
    public ByteArrayOutputStream getTransUserExcel(TransUserVO vo) {
        return excelExportService.dataToExeclByStream("用户流水", iTransMgrService.getTransUserExcel(vo));
    }

    @Override
    public ByteArrayOutputStream getTransRedExcel(TransRedVO vo) {
        return excelExportService.dataToExeclByStream("红包流水", iTransMgrService.getTransRedExcel(vo));
    }

    @Override
    public ByteArrayOutputStream getTransRechargeExcel(TransRechargeVO vo) {
        Map<String, List<DictionaryBO>> otherDic = new HashMap<String, List<DictionaryBO>>();
        List<PayBankBO> banks = paymentService.findBank(new PayBankVO());
        List<DictionaryBO> bankDic = DicUtils.toDic(banks, "id", Integer.class, "cname", String.class);
        otherDic.put("rechargeBank", bankDic);
        return excelExportService.dataToExeclByStreamDictionary("充值流水", iTransMgrService.getTransRechargeExcel(vo), otherDic);
    }

    @Override
    public ByteArrayOutputStream getTransTakenExcel(TransTakenVO vo) {
        Map<String, List<DictionaryBO>> otherDic = new HashMap<String, List<DictionaryBO>>();
        List<PayBankBO> banks = paymentService.findBank(new PayBankVO());
        List<DictionaryBO> bankDic = DicUtils.toDic(banks, "id", Integer.class, "cname", String.class);
        otherDic.put("takenBank", bankDic);
        return excelExportService.dataToExeclByStreamDictionary("提款流水", iTransMgrService.getTransTakenExcel(vo), otherDic);
    }

    @Override
    public ByteArrayOutputStream getTransTakenBankExcel(TransTakenVO vo) {
        Map<String, List<DictionaryBO>> otherDic = new HashMap<String, List<DictionaryBO>>();
        List<PayBankBO> banks = paymentService.findBank(new PayBankVO());
        List<DictionaryBO> bankDic = DicUtils.toDic(banks, "id", Integer.class, "cname", String.class);
        otherDic.put("takenBank", bankDic);
        return excelExportService.dataToExeclByStreamDictionary(iTransMgrService.getTransTakenBankExcel(vo), otherDic);
    }

    @Override
    public ByteArrayOutputStream getTransChannelSettleExcel(TransChannelSettleVO vo) {
        return excelExportService.dataToExeclByStream("渠道结算流水", iTransMgrService.getTransChannelSettleExcel(vo));
    }

    @Override
    public int resupply(TransRechargeVO vo) {
        return iTransMgrService.resupply(vo);
    }

    @Override
    public ResultBO<?> manual(CmsRechargeVO vo) {
        logger.info("Cms人工充值开始:" + vo);
        Assert.notNull(vo.getUserId(), "充值用户信息不能为空");
        Assert.isTrue(MathUtil.compareTo(vo.getRechargeAmount(), 0) == 1, "充值金额必须大于0");
        // 调用lotto-pay
        ResultBO<?> ret = rechargeService.updateRecharge(vo);
        logger.info("Cms人工充值结束:" + vo);
        return ret;
    }

//	/**
//	 * @desc   更新提款记录状态(可能会同时更新操作人员信息)
//	 * @author Tony Wang
//	 * @create 2017年5月22日
//	 * @param vos
//	 * @return 
//	 */
//	@Override
//	public int updateTakenStatus(List<TransTakenVO> vos) {
//		return iTransMgrService.updateTakenStatus(vos);
//	}

    @Override
    public List<String> findTransTakenBatchNums(TransTakenVO vo) {
        return iTransMgrService.findTransTakenBatchNums(vo);
    }

    /**
     * @param vo
     * @return
     * @desc 查询提款申请记录
     * @author Tony Wang
     * @create 2017年8月4日
     */
    @Override
    public List<TransTakenBO> findTakenTransNoPaging(TransTakenVO vo) {
        return iTransMgrService.findTakenTransNoPaging(vo);
    }

    @Override
    public int countTransTaken(TransTakenVO vo) {
        return iTransMgrService.countTransTaken(vo);
    }

    @Override
    public PagingBO<TransRemittingBO> pageTransRemitting(TransRemittingBO vo) {
        return iTransMgrService.pageTransRemitting(vo);
    }

    @Override
    public ByteArrayOutputStream excelTransRemitting(TransRemittingBO vo) {
        Map<String, List<DictionaryBO>> otherDic = new HashMap<String, List<DictionaryBO>>();
        List<DictionaryBO> statusDic = new ArrayList<>();
        // 1待充值，2已充值
        DictionaryBO dic1 = new DictionaryBO();
        dic1.setId("1");
        dic1.setText("待充值");
        DictionaryBO dic2 = new DictionaryBO();
        dic2.setId("2");
        dic2.setText("已充值");
        DictionaryBO dic3 = new DictionaryBO();
        dic3.setId("3");
        dic3.setText("误充值");
        statusDic.add(dic1);
        statusDic.add(dic2);
        statusDic.add(dic3);
        otherDic.put("status", statusDic);
        return excelExportService.dataToExeclByStreamDictionary("提款流水", iTransMgrService.excelTransRemitting(vo), otherDic);
    }

    @Override
    public int updateTransRemitting(TransRemittingBO vo) {
        return iTransMgrService.updateTransRemitting(vo);
    }
}
