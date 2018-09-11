package com.hhly.cmscore.cms.remote.service;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.cooperate.bo.*;
import com.hhly.skeleton.cms.cooperate.vo.*;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * @author lgs
 * @version v1.0
 * @desc 积分兑换彩票CMS service
 * @date 2017年2月8日
 * @company 益彩网络
 */
public interface ICooperateService {

    /**
     * @param cooperateChannelVO 参数对象
     * @return 查询分页列表
     * @desc 查询渠道分页列表
     * @author lgs
     * @date 2017年2月8日
     */
    PagingBO<CooperateChannelBO> findPaging(CooperateChannelVO cooperateChannelVO);

    /**
     * @param vo 参数对象
     * @return 查询分页列表
     * @desc 查询渠道彩种分页列表
     * @author lgs
     * @date 2017年2月8日
     */
    PagingBO<CooperateLotteryBO> findCooperateLotteryPagingBO(CooperateLotteryVO vo);

    /**
     * 保存商户彩种信息
     *
     * @param vo
     * @return
     */
    int saveCooperateLottery(CooperateLotteryVO vo);

    /**
     * 删除商户彩种
     *
     * @param vo
     * @return
     */
    int delCooperateLottery(CooperateLotteryVO vo);

    /**
     * 保存渠道信息
     *
     * @return
     */
    int saveCooperateChannel(CooperateChannelVO vo);


    /**
     * 保存渠道信息
     *
     * @return
     */
    int delCooperateChannel(CooperateChannelVO vo);


    /**
     * @param vo 参数对象
     * @return 查询分页列表
     * @desc 查询流水分页列表
     * @author lgs
     * @date 2017年2月8日
     */
    PagingBO<CooperateExchangeRecordBO> findCooperateExchangeRecordPagingBO(CooperateExchangeRecordVO vo);

    /**
     * 查询流水导出信息
     *
     * @param vo
     * @return
     */
    List<CooperateExchangeRecordBO> findCooperateExchangeRecordBOList(CooperateExchangeRecordVO vo);


    /***
     * 查询可用渠道
     * @return
     */
    List<DictionaryBO> selectChannelNameDictBO();


    /**
     * 查询渠道彩种信息
     *
     * @param vo
     * @return
     */
    List<DictionaryBO> findCooperateLotteryListByChannelId(CooperateLotteryVO vo);


    /**
     * 新增流水充值记录
     *
     * @param vo
     * @return
     */
    int saveCooperateExchangeRecord(CooperateExchangeRecordVO vo);


    /**
     * 新增cdkey库存
     *
     * @param vos
     * @return
     */
    int saveCooperateCdKeyList(List<CooperateCdkeyVO> vos);


    /**
     * 查询渠道彩种数量
     *
     * @return
     */
    List<CooperateCdKeyLotteryNumBO> findCooperateCdKeyLotteryNumBOList(CooperateCdkeyVO vo);


    /**
     * @param vo 参数对象
     * @return 查询分页列表
     * @desc 查询彩种流水详情
     * @author lgs
     * @date 2017年2月8日
     */
    PagingBO<CooperateCdKeyLotteryRecodeListBO> findCooperateCdKeyLotteryRecodeListBOPagingBO(CooperateCdkeyVO vo);


    /**
     * 查询渠道信息
     * @param channelId
     * @return
     */
    CooperateChannelBO selectByChannelId(String channelId);


    /**
     * 根据渠道查询 中介商户代理渠道
     *
     * @param vo
     * @return
     */
    PagingBO<CooperateAgencyBO> findCooperateAgencyBOByChannelId(CooperateAgencyVO vo);


    /**
     * 保存中介商户代理渠道
     *
     * @param vo
     * @return
     */
    int saveChildChannel(CooperateAgencyVO vo);

    /**
     * 删除中介商户代理渠道
     *
     * @param vo
     * @return
     */
    int delChildChannel(CooperateAgencyVO vo);


    /**
     * 重置password
     *
     * @param vo
     * @return
     */
    int resetPassword(CooperateChannelVO vo);


    List<CooperateCdKeyLotteryRecodeListBO> findLotteryRecodeList(CooperateCdkeyVO vo);

    /**
     * 更新已结束合作时间的渠道状态
     *
     * @return
     */
    int updateStop();


    /**
     * 更新过期兑换码
     *
     * @return
     */
    int updateOverTimeCdKey();
}
