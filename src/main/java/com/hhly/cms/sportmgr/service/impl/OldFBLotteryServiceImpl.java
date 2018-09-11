package com.hhly.cms.sportmgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.sportmgr.service.OldFBLotteryService;
import com.hhly.cms.utils.WebConstant;
import com.hhly.cmscore.cms.remote.service.ISportBallMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.sportsutil.BetContentConverUtil;
import com.hhly.skeleton.base.util.sportsutil.FuShiBetsContent;
import com.hhly.skeleton.cms.ordermgr.bo.OrderOldMatchContentBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportDataFbOldExcelBo;
import com.hhly.skeleton.cms.sportmgr.bo.SportOldLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.lotto.base.sport.vo.OldDataVO;

/**
 * Created by lgs on 2016/12/6.
 */
@Service
public class OldFBLotteryServiceImpl implements OldFBLotteryService{

    @Autowired
    private ISportBallMgrService iSportBallMgrService;
    @Autowired
    private ExcelExportService excelExportService;


    @Override
    public PagingBO<SportOldLotteryInfoBO> findOldLotteryInfo(SMGLotteryParamVO vo) {
        return iSportBallMgrService.findOldLotteryInfo(vo);
    }

    @Override
    public Map<String, Object> findOldSp(Long againstId) {
        return iSportBallMgrService.findOldSp(againstId);
    }

    @Override
    public int saveOldData(OldDataVO vo) {
    	String fullScore = vo.getFullScore();
        if(fullScore!=null&&!fullScore.equals("")){
        	modifyDraw(vo);
        }
        
        return iSportBallMgrService.saveOldData(vo);
    }

    private void modifyDraw(OldDataVO vo){
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
        String sixHfWdf="";
        if(homeHalfScore > guestHalfScore){
            sixHfWdf = WebConstant.WIN;
        } else if(homeHalfScore < guestHalfScore){
            sixHfWdf = WebConstant.LOST;
        } else if(homeHalfScore == guestHalfScore){
            sixHfWdf = WebConstant.DRAW;
        }

        if(homeFullScore > guestFullScore){
            vo.setFourteenWdf(WebConstant.WIN);
            sixHfWdf += WebConstant.WIN;
        } else if(homeFullScore < guestFullScore){
            vo.setFourteenWdf(WebConstant.LOST);
            sixHfWdf += WebConstant.LOST;
        } else if(homeFullScore == guestFullScore){
            vo.setFourteenWdf(WebConstant.DRAW);
            sixHfWdf += WebConstant.DRAW;
        }


        vo.setSixHfWdf(sixHfWdf);
        String totalScore = homeFullScore+""+guestFullScore;
        vo.setFourGoal(String.valueOf(totalScore));
    }

	@Override
	public ByteArrayOutputStream getExcelStream(SMGLotteryParamVO vo) {
		 List<SportDataFbOldExcelBo> data=iSportBallMgrService.findOldFbExcelList(vo);
		return excelExportService.dataToExeclByStream("oldfb",data);
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<OrderOldMatchContentBO> findOldOrderMatchDetail(Integer lotteryCode, String issueCode, String content) {
	   	SMGLotteryParamVO vo = new SMGLotteryParamVO();
    	vo.setLotteryCode(lotteryCode);
    	vo.setLotteryIssue(issueCode);
    	List<SportOldLotteryInfoBO> oldLotlist = iSportBallMgrService.findOldOrderMatchDetail(vo);
    	List<OrderOldMatchContentBO> list = (List<OrderOldMatchContentBO>) BetContentConverUtil.OrderContentConver(lotteryCode, null, content);
    	List<OrderOldMatchContentBO> datalist = null;
    	if(!ObjectUtil.isBlank(list)){
    		datalist = new LinkedList<>();
    		for (int i = 0; i < list.size(); i++) {
    			OrderOldMatchContentBO orderOldMatchContentBO = list.get(i);
        		if(!orderOldMatchContentBO.isBuy()){
        			continue;
        		}
        		SportOldLotteryInfoBO bo = oldLotlist.get(i);
        		orderOldMatchContentBO.setSystemCode(bo.getSystemCode());
        		orderOldMatchContentBO.setHomeName(bo.getHomeName());
        		orderOldMatchContentBO.setGuestName(bo.getGuestName());
        		orderOldMatchContentBO.setHalfScore(bo.getHalfScore());
        		orderOldMatchContentBO.setFullScore(bo.getFullScore());
        		orderOldMatchContentBO.setWdf(bo.getFourteenWdf() == null ? null : bo.getFourteenWdf().intValue());
        		if(ObjectUtil.isNotNull(orderOldMatchContentBO.getWdf()))
        	   		orderOldMatchContentBO.setWdfText(FuShiBetsContent.spfMap.get(orderOldMatchContentBO.getWdf().toString())); 
        		datalist.add(orderOldMatchContentBO);
			}
        	return datalist;
    	}
    	return datalist;
	}
}
