package com.hhly.cms.lotterymgr.service;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryBettingMulBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryChildBO;
import com.hhly.skeleton.cms.lotterymgr.bo.LotteryTypeBO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryBettingMulCmsVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryChildCmsVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryTypeVO;
import com.hhly.skeleton.cms.lotterymgr.vo.LotteryWinningVO;

import java.io.ByteArrayOutputStream;
import java.util.List;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-11-17 下午5:45:02
 * @Desc 彩种管理
 */
public interface LotteryTypeService {

	PagingBO<LotteryTypeBO> find(LotteryTypeVO vo);
	
    /**
     * 查询彩种信息
     * @param vo
     * @return
     * @date 2017年5月15日下午5:19:41
     * @author cheng.chen
     */
    LotteryTypeBO findSingle(LotteryTypeVO vo);

	List<LotteryBettingMulBO> findBettingMul(StringVO vo);

	List<LotteryChildBO> findChild(StringVO vo);

	int updLotteryType(LotteryTypeVO vo);

	int addLotteryBettingMul(LotteryBettingMulCmsVO vo);

	int addLotteryChild(LotteryChildCmsVO vo);

	int updLotteryBettingMul(LotteryBettingMulCmsVO vo);

	int updLotteryChild(LotteryChildCmsVO vo);

	int delBettingMul(StringVO vo);

	int delChild(StringVO vo);

	int saveLotteryBettingMul(List<LotteryBettingMulCmsVO> list);

	int saveLotteryChild(List<LotteryChildCmsVO> list);

	ByteArrayOutputStream getLotteryTypeExcel(LotteryTypeVO vo);

	int addLotteryType(LotteryTypeVO vo);

	List<DictionaryBO> findTypeDictionary(StringVO vo);

	/**
	 * @desc 彩种子玩法字典
	 * @author huangb
	 * @date 2017年1月19日
	 * @param lotteryChildCms 子玩法对象
	 * @return 子玩法字典列表
	 */
	List<DictionaryBO> findChildDictionary(LotteryChildCmsVO lotteryChildCms);
	
	/**
	 * @desc 查询：彩种奖级字典
	 * @author huangb
	 * @date 2017年2月24日
	 * @param lotteryWinningVO 参数对象
	 * @return 查询：彩种奖级字典
	 */
	List<DictionaryBO> findWinningDictionary(LotteryWinningVO lotteryWinningVO);
}
