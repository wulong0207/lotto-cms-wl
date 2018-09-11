
package com.hhly.cms.sportmgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.sportmgr.service.TeamInfoService;
import com.hhly.cmscore.cms.remote.service.ISportBallMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.sportmgr.bo.SportTeamInfoBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportTeamSourceInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SportTeamInfoVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportTeamSourceInfoVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2018年1月20日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class TeamInfoServiceImpl implements TeamInfoService {

    @Autowired
    private ISportBallMgrService iSportBallMgrService;
    
	@Override
	public List<SportTeamInfoBO> findLikeList(SportTeamInfoVO vo) {
		return iSportBallMgrService.findTeamLikeList(vo);
	}

	@Override
	public PagingBO<SportTeamInfoBO> findList(SportTeamInfoVO vo) {
		return iSportBallMgrService.findTeamList(vo);
	}

	@Override
	public int insertTeamInfo(SportTeamInfoVO vo) {
		return iSportBallMgrService.insertTeamInfo(vo);
	}

	@Override
	public int updateTeamInfo(SportTeamInfoVO vo) {
		return iSportBallMgrService.updateTeamInfo(vo);
	}

	@Override
	public int deleteBatchTeamInfo(StringVO vo) {
		return iSportBallMgrService.deleteBatchTeamInfo(vo);
	}

	@Override
	public PagingBO<SportTeamSourceInfoBO> findList(SportTeamSourceInfoVO vo) {
		return iSportBallMgrService.findTeamSourceList(vo);
	}

	@Override
	public int updateTeamSourceInfo(SportTeamSourceInfoVO vo) {
		return iSportBallMgrService.updateTeamSourceInfo(vo);
	}

	@Override
	public int removeBind(Integer id) {
		return iSportBallMgrService.removeTeamBind(id);
	}

	@Override
	public void deleteBatchTeamSource(StringVO vo) {
		iSportBallMgrService.deleteBatchTeamSource(vo);
	}
}
