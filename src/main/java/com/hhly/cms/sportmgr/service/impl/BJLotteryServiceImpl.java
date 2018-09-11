package com.hhly.cms.sportmgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.sportmgr.service.BJLotteryService;
import com.hhly.cms.utils.WebConstant;
import com.hhly.cmscore.cms.remote.service.ISportBallMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.sportsutil.BetContentConverUtil;
import com.hhly.skeleton.base.util.sportsutil.FuShiBetsContent;
import com.hhly.skeleton.cms.ordermgr.bo.OrderMatchContentBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportBJDataExcelBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportBJLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.BJDataVO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;

/**
 * Created by lgs on 2016/12/20.
 */
@Service
public class BJLotteryServiceImpl implements BJLotteryService{

    @Autowired
    private ISportBallMgrService iSportBallMgrService;
    @Autowired
    private ExcelExportService excelExportService;

    @Override
    public PagingBO<SportBJLotteryInfoBO> findBJData(SMGLotteryParamVO vo) {
        return iSportBallMgrService.findBJData(vo);
    }

    @Override
    public Map<String, Object> findBJSp(Long againstId) {
        return iSportBallMgrService.findBJSp(againstId);
    }

    @Override
    public int saveBJData(BJDataVO vo) {
    	String fullScore = vo.getFullScore();
        String halfScore = vo.getHalfScore();
        
        if(fullScore!=null&&!fullScore.equals("")){
        	modifyDraw(vo);
        }else{
        	vo.setLetNum(null);
        	vo.setLetWdf(null);
        	vo.setGoalNum(null);
        	vo.setHfWdf(null);
        	vo.setUdSd(null);
        	vo.setScore(null);
        }
        
        return iSportBallMgrService.saveBJData(vo);
    }

    /**
     * 设置vo胜平负让分值
     * @param vo
     */
    private void modifyDraw(BJDataVO vo){
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
            hfWdf += WebConstant.WIN;
        } else if(homeFullScore < guestFullScore){
            hfWdf += WebConstant.LOST;
        } else if(homeFullScore == guestFullScore){
            hfWdf += WebConstant.DRAW;
        }
        //让球胜平负
        if((homeFullScore+vo.getLetNum())>guestFullScore){
            vo.setLetWdf(Short.valueOf(WebConstant.WIN));
        }else if((homeFullScore+vo.getLetNum()) < guestFullScore){
            vo.setLetWdf(Short.valueOf(WebConstant.LOST));
        } else if((homeFullScore+vo.getLetNum()) == guestFullScore){
            vo.setLetWdf(Short.valueOf(WebConstant.DRAW));
        }
        vo.setHfWdf(hfWdf);
        
        //计算总比分
        String score = "";
        if(homeFullScore > guestFullScore){
        	if((homeFullScore > 4 && guestFullScore >= 0) || (homeFullScore == 4 && guestFullScore == 3))
        		score = "90";
        }else if (homeFullScore < guestFullScore){
        	if((homeFullScore >= 0 && guestFullScore > 4) || (homeFullScore == 3 && guestFullScore == 4))
        		score = "09";
        }else if(homeFullScore == guestFullScore && homeFullScore > 3 && guestFullScore > 3){
        	score = "99";
        }
        if(ObjectUtil.isBlank(score))
        	score = String.valueOf(homeFullScore)+String.valueOf(guestFullScore);
        vo.setScore(score);

        Integer goalNum = homeFullScore+guestFullScore;
        
        //计算上下单双
        String udsd = "";
        if(goalNum>=3){// goalNum>=3 上盘
            if(goalNum%2!=0){
                udsd = Constants.OVER_SINGLE;//上单
            }else{
                udsd = Constants.OVER_DOUBLE;//上双
            }
        }else{
            if(goalNum%2!=0){
                udsd = Constants.UNDER_SINGLE;//下单
            }else{
                udsd = Constants.UNDER_DOUBLE;//下双
            }
        }
        vo.setUdSd(Short.valueOf(udsd));
        
        //计算进球数
        if(goalNum > 7)
        	goalNum = 7;
        vo.setGoalNum(new Short(goalNum.shortValue()));
          
        //计算北单开售状态
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
    private void setStatus(BJDataVO vo,String notSalePay){
        if(notSalePay.equals(Constants.BJ_UDSD)){
            vo.setUdSd(Constants.BJ_STOP_SALE);
        }else if(notSalePay.equals(Constants.BJ_LET_WDF)){
            vo.setStatusLetWdf(Constants.BJ_STOP_SALE);
        }else if(notSalePay.equals(Constants.BJ_SCORE)){
            vo.setStatusScore(Constants.BJ_STOP_SALE);
        }else if(notSalePay.equals(Constants.BJ_GOAL)){
            vo.setStatusGoal(Constants.BJ_STOP_SALE);
        }else if(notSalePay.equals(Constants.BJ_HF_WDF)){
            vo.setStatusHfWdf(Constants.BJ_STOP_SALE);
        }
    }
    

	@Override
	public int updCheckScore(String lotteryCode, String modifyBy) {
		return iSportBallMgrService.updCheckScore(lotteryCode, "SPORT_DRAW_BJ", modifyBy);
	}

	@Override
	public ByteArrayOutputStream getExcelStream(SMGLotteryParamVO vo) {
		 List<SportBJDataExcelBO> data=iSportBallMgrService.findBjExcelList(vo);
		return excelExportService.dataToExeclByStream("bj",data);
	}

	@Override
	public List<OrderMatchContentBO> findBjOrderMatchDetail(Integer lotteryCode,Integer lotteryChildCode,
			String systemCode, String content) {
    	SMGLotteryParamVO vo = new SMGLotteryParamVO();
    	vo.setLotteryCode(lotteryCode);
    	vo.setSystemCode(systemCode);
	  	Map<String, SportBJLotteryInfoBO>  map = iSportBallMgrService.findBjOrderMatchDetail(vo);
    	List<OrderMatchContentBO> list = (List<OrderMatchContentBO>) BetContentConverUtil.OrderContentConver(lotteryCode, lotteryChildCode, content);
    	if(!ObjectUtil.isBlank(list)){
        	for (OrderMatchContentBO orderMatchContentBO : list) {
        		SportBJLotteryInfoBO bo = map.get(orderMatchContentBO.getSystemCode());
        		orderMatchContentBO.setMatchStartTime(bo.getStartTime());
        		orderMatchContentBO.setHomeName(bo.getHomeName());
        		orderMatchContentBO.setGuestName(bo.getGuestName());
        		orderMatchContentBO.setHalfScore(bo.getHalfScore());
        		orderMatchContentBO.setFullScore(bo.getFullScore());
//        		orderMatchContentBO.setWdf(bo.getFullSpf() == null ? null : bo.getFullSpf().intValue());
//        		if(!ObjectUtil.isBlank(orderMatchContentBO.getWdf()))
//        	   		orderMatchContentBO.setWdfText(FuShiBetsContent.spfMap.get(orderMatchContentBO.getWdf().toString()));
        		orderMatchContentBO.setLetWdf(bo.getLetWdf() == null ? null : bo.getLetWdf().toString());
        		orderMatchContentBO.setLetWdfText(FuShiBetsContent.rqspfMap.get(orderMatchContentBO.getLetWdf()));
        		orderMatchContentBO.setLetWdfSp(bo.getSpLetWdf() == null ? null : bo.getSpLetWdf().floatValue());
           		orderMatchContentBO.setUdsd(bo.getUdSd() == null ? null : bo.getUdSd().intValue());
        		if(ObjectUtil.isNotNull(orderMatchContentBO.getUdsd()))
        			orderMatchContentBO.setUdsdText(FuShiBetsContent.sxdsMap.get(orderMatchContentBO.getUdsd().toString()));
        		orderMatchContentBO.setUdsdSp(bo.getSpUdSd() == null ? null : bo.getSpUdSd().floatValue());
        		orderMatchContentBO.setScore(bo.getScore());
        		orderMatchContentBO.setScoreText(FuShiBetsContent.bfMap.get(orderMatchContentBO.getScore()));
        		orderMatchContentBO.setScoreSp(bo.getSpScore() == null ? null : bo.getSpScore().floatValue());
        		orderMatchContentBO.setGoalNum(bo.getGoalNum() == null ? null : bo.getGoalNum().intValue());
        		if(ObjectUtil.isNotNull(orderMatchContentBO.getGoalNum())){
        			if(orderMatchContentBO.getGoalNum() > 7){
        				orderMatchContentBO.setGoalNumText("7+");
        			}else{
            			orderMatchContentBO.setGoalNumText(orderMatchContentBO.getGoalNum().toString());
            		}
        		}
        		orderMatchContentBO.setGoalNumSp(bo.getSpGoalNum() == null ? null : bo.getSpGoalNum().floatValue());
        		orderMatchContentBO.setHfWdf(bo.getHfWdf());
        		orderMatchContentBO.setHfWdfText(FuShiBetsContent.bqcMap.get(orderMatchContentBO.getHfWdf()));
        		orderMatchContentBO.setHfWdfSp(bo.getSpHfWdf() == null ? null : bo.getSpHfWdf().floatValue());
    		}
        	return list;
    	}
		return Collections.EMPTY_LIST;
	}
	
}
