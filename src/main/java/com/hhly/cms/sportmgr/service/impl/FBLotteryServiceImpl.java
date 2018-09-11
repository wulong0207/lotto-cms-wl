package com.hhly.cms.sportmgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.sportmgr.service.FBLotteryService;
import com.hhly.cms.utils.WebConstant;
import com.hhly.cmscore.cms.remote.service.ISportBallMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.sportsutil.BetContentConverUtil;
import com.hhly.skeleton.base.util.sportsutil.FuShiBetsContent;
import com.hhly.skeleton.cms.ordermgr.bo.OrderMatchContentBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportFBDataExcelBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportFBLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportDataBaseVO;
import com.hhly.skeleton.lotto.base.sport.vo.FBDataVO;


/**
 * Created by lgs on 2016/12/16.
 */
@Service
public class FBLotteryServiceImpl implements FBLotteryService{

    @Autowired
    private ISportBallMgrService iSportBallMgrService;
    @Autowired
    private ExcelExportService excelExportService;

    @Override
    public PagingBO<SportFBLotteryInfoBO> findFBData(SMGLotteryParamVO vo) {
        if(!StringUtils.isBlank(vo.getWeek()) && !StringUtils.isBlank(vo.getOfficialNum())){
            vo.setOfficialNum(vo.getWeek()+vo.getOfficialNum());
        }
        return iSportBallMgrService.findFBData(vo);
    }

    @Override
    public Map<String, Object> findFBSp(Long againstId) {
        return iSportBallMgrService.findFBSp(againstId);
    }

    @Override
    public int saveFBData(FBDataVO vo) {
    	 String fullScore = vo.getFullScore();
        if(fullScore!=null&&!fullScore.equals("")){
        	 modifyDraw(vo);
        }else{
        	vo.setFullSpf(null);
        	vo.setLetSpf(null);
        	vo.setLetNum(null);
        	vo.setHfWdf(null);
            vo.setGoalNum(null);
            vo.setScore(null);
        }
       
        return iSportBallMgrService.saveFBData(vo);
    }

    /**
     * 设置vo胜平负让分值
     * @param vo
     */
    private void modifyDraw(FBDataVO vo){
        String fullScore = vo.getFullScore();
        String halfScore = vo.getHalfScore();
        if(fullScore.indexOf(SymbolConstants.COLON) == -1 || halfScore.indexOf(SymbolConstants.COLON) == -1){
            throw new IllegalArgumentException("比分格式错误");
        }

        String[] fullScores = fullScore.split(SymbolConstants.COLON);
        String[] halfScores = halfScore.split(SymbolConstants.COLON);
        int homeFullScore =  Integer.valueOf(fullScores[0]);
        int guestFullScore =  Integer.valueOf(fullScores[1]);

        int homeHalfScore =  Integer.valueOf(halfScores[0]);
        int guestHalfScore =  Integer.valueOf(halfScores[1]);

        String hfWdf = "";
        //半全场胜平负
        if(homeHalfScore > guestHalfScore){
            hfWdf += WebConstant.WIN;
        } else if(homeHalfScore < guestHalfScore){
            hfWdf += WebConstant.LOST;
        } else if(homeHalfScore == guestHalfScore){
            hfWdf += WebConstant.DRAW;
        }
        //全场胜平负 和半全场胜平负
        if(homeFullScore > guestFullScore){
            vo.setFullSpf(Short.valueOf(WebConstant.WIN));
            hfWdf += WebConstant.WIN;
        } else if(homeFullScore < guestFullScore){
            vo.setFullSpf(Short.valueOf(WebConstant.LOST));
            hfWdf += WebConstant.LOST;
        } else if(homeFullScore == guestFullScore){
            vo.setFullSpf(Short.valueOf(WebConstant.DRAW));
            hfWdf += WebConstant.DRAW;
        }
        //让球胜平负
        if((homeFullScore+vo.getLetNum())>guestFullScore){
            vo.setLetSpf(Short.valueOf(WebConstant.WIN));
        }else if((homeFullScore+vo.getLetNum()) < guestFullScore){
            vo.setLetSpf(Short.valueOf(WebConstant.LOST));
        } else if((homeFullScore+vo.getLetNum()) == guestFullScore){
            vo.setLetSpf(Short.valueOf(WebConstant.DRAW));
        }

        vo.setHfWdf(hfWdf);
        //计算总进球数
        Integer goalNum = homeFullScore+guestFullScore;//进球数
        if(goalNum > 7)
        	goalNum = 7;
        vo.setGoalNum(new Short(goalNum.shortValue()));
        //计算总比分
        String score = "";
        if(homeFullScore > guestFullScore){
        	if((homeFullScore >= 4 && guestFullScore >=3) || homeFullScore > 5)
        		score = "90";
        }else if (homeFullScore < guestFullScore){
        	if((homeFullScore >= 3 && guestFullScore >= 4) || guestFullScore > 5)
        		score = "09";
        }else if(homeFullScore == guestFullScore && homeFullScore > 3 && guestFullScore > 3){
        	score = "99";
        }
        if(ObjectUtil.isBlank(score))
        	score = String.valueOf(homeFullScore)+String.valueOf(guestFullScore);
        vo.setScore(score);

        String notSalePay = vo.getNotSalePay();
        if(notSalePay != null && !notSalePay.equals("")){
            if(notSalePay.indexOf(SymbolConstants.COMMA) == -1){
                setStatus(vo,notSalePay);
            }else{
                String[] notSalePays = notSalePay.split(SymbolConstants.COMMA);
                for (String str: notSalePays) {
                    setStatus(vo,str);
                }
            }
        }
    }

    /**
     * 设置子玩法状态
     * @param vo
     * @param notSalePay
     */
    private void setStatus(FBDataVO vo,String notSalePay){
        if(notSalePay.equals(Constants.WDF)){
            vo.setStatusWdf(Constants.STOP_SALE);
        }else if(notSalePay.equals(Constants.LET_WDF)){
            vo.setStatusLetWdf(Constants.STOP_SALE);
        }else if(notSalePay.equals(Constants.SCORE)){
            vo.setStatusScore(Constants.STOP_SALE);
        }else if(notSalePay.equals(Constants.GOAL)){
            vo.setStatusGoal(Constants.STOP_SALE);
        }else if(notSalePay.equals(Constants.HF_WDF)){
            vo.setStatusHfWdf(Constants.STOP_SALE);
        }
    }

	@Override
	public ByteArrayOutputStream getExcelStream(SMGLotteryParamVO vo) {
		 List<SportFBDataExcelBO> data=iSportBallMgrService.findFbExcelList(vo);
		return excelExportService.dataToExeclByStream("fb",data);
	}

	@Override
	public int updCheckScore(String lotteryCode, String modifyBy){
		return iSportBallMgrService.updCheckScore(lotteryCode, "SPORT_DRAW_FB", modifyBy);
	}

	@Override
	public int updSportAgainstInfo(SportDataBaseVO vo) {
		return iSportBallMgrService.updSportAgainstInfo(vo);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrderMatchContentBO> findFbOrderMatchDetail(Integer lotteryCode,Integer lotteryChildCode,
			String systemCode, String content) {
    	SMGLotteryParamVO vo = new SMGLotteryParamVO();
    	vo.setLotteryCode(lotteryCode);
    	vo.setSystemCode(systemCode);
	  	Map<String, SportFBLotteryInfoBO>  map = iSportBallMgrService.findFbOrderMatchDetail(vo);
    	List<OrderMatchContentBO> list = (List<OrderMatchContentBO>) BetContentConverUtil.OrderContentConver(lotteryCode, lotteryChildCode, content);
    	if(!ObjectUtil.isBlank(list)){
        	for (OrderMatchContentBO orderMatchContentBO : list) {
        		SportFBLotteryInfoBO bo = map.get(orderMatchContentBO.getSystemCode());
        		orderMatchContentBO.setMatchStartTime(bo.getStartTime());
        		orderMatchContentBO.setHomeName(bo.getHomeName());
        		orderMatchContentBO.setGuestName(bo.getGuestName());
        		orderMatchContentBO.setHalfScore(bo.getHalfScore());
        		orderMatchContentBO.setFullScore(bo.getFullScore());
        		orderMatchContentBO.setWdf(bo.getFullSpf() == null ? null : bo.getFullSpf().intValue());
        		if(ObjectUtil.isNotNull(orderMatchContentBO.getWdf()))
        	   		orderMatchContentBO.setWdfText(FuShiBetsContent.spfMap.get(orderMatchContentBO.getWdf().toString()));
        		orderMatchContentBO.setLetWdf(bo.getLetSpf() == null ? null : bo.getLetSpf().toString());
           		orderMatchContentBO.setLetWdfText(FuShiBetsContent.rqspfMap.get(orderMatchContentBO.getLetWdf()));
        		orderMatchContentBO.setScore(bo.getScore());
        		orderMatchContentBO.setScoreText(FuShiBetsContent.bfMap.get(orderMatchContentBO.getScore()));
        		orderMatchContentBO.setGoalNum(bo.getGoalNum() == null ? null : bo.getGoalNum().intValue());
        		if(ObjectUtil.isNotNull(orderMatchContentBO.getGoalNum())){
        			if(orderMatchContentBO.getGoalNum() > 7){
        				orderMatchContentBO.setGoalNumText("7+");
        			}else{
            			orderMatchContentBO.setGoalNumText(orderMatchContentBO.getGoalNum().toString());
            		}
        		}
        		orderMatchContentBO.setHfWdf(bo.getHfWdf());
        		orderMatchContentBO.setHfWdfText(FuShiBetsContent.bqcMap.get(orderMatchContentBO.getHfWdf()));
    		}
        	return list;
    	}
		return Collections.EMPTY_LIST;
	}
}
