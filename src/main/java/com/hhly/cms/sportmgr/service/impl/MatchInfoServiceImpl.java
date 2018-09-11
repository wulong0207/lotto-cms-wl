
package com.hhly.cms.sportmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.sportmgr.service.MatchInfoService;
import com.hhly.cmscore.cms.remote.service.ISportBallMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.sportmgr.bo.SportMatchInfoBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportMatchSourceInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SportMatchInfoVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportMatchSourceInfoVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2018年1月20日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class MatchInfoServiceImpl implements MatchInfoService {

    @Autowired
    private ISportBallMgrService iSportBallMgrService;
    
	@Override
	public List<SportMatchInfoBO> findLikeList(SportMatchInfoVO vo) {
		return iSportBallMgrService.findMatchLikeList(vo);
	}

	@Override
	public PagingBO<SportMatchInfoBO> findList(SportMatchInfoVO vo) {
		return iSportBallMgrService.findMatchList(vo);
	}

	@Override
	public int insertMatchInfo(SportMatchInfoVO vo) {
		return iSportBallMgrService.insertMatchInfo(vo);
	}

	@Override
	public int updateMatchInfo(SportMatchInfoVO vo) {
		return iSportBallMgrService.updateMatchInfo(vo);
	}

	@Override
	public int deleteBatchMatchInfo(StringVO vo) {
		return iSportBallMgrService.deleteBatchMatchInfo(vo);
	}

	@Override
	public PagingBO<SportMatchSourceInfoBO> findList(SportMatchSourceInfoVO vo) {
		return iSportBallMgrService.findMatchSourceList(vo);
	}

	@Override
	public int updateMatchSourceInfo(SportMatchSourceInfoVO vo) {
		return iSportBallMgrService.updateMatchSourceInfo(vo);
	}

	@Override
	public int removeBind(Integer id) {
		return iSportBallMgrService.removeMatchBind(id);
	}

	@Override
	public void deleteBatchMatchSource(StringVO vo) {
		iSportBallMgrService.deleteBatchMatchSource(vo);
	}
}
