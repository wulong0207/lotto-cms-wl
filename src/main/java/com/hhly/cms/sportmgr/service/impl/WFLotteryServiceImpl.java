package com.hhly.cms.sportmgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.sportmgr.service.WFLotteryService;
import com.hhly.cms.utils.WebConstant;
import com.hhly.cmscore.cms.remote.service.ISportBallMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.sportsutil.BetContentConverUtil;
import com.hhly.skeleton.base.util.sportsutil.FuShiBetsContent;
import com.hhly.skeleton.cms.ordermgr.bo.OrderMatchContentBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportWFDataExcelBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportWFLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.WFDataVO;

/**
 * Created by lgs on 2017/1/3.
 */
@Service
public class WFLotteryServiceImpl implements WFLotteryService{

    @Autowired
    private ISportBallMgrService iSportBallMgrService;
    @Autowired
    private ExcelExportService excelExportService;


    @Override
    public PagingBO<SportWFLotteryInfoBO> findWFData(SMGLotteryParamVO vo) {
        return iSportBallMgrService.findWFData(vo);
    }

    @Override
    public Map<String, Object> findWFSp(Long againstId) {
        return iSportBallMgrService.findWFSp(againstId);
    }

    @Override
    public int saveWFData(WFDataVO vo) {
    	 String fullScore = vo.getFullScore();
    	 
    	 if(fullScore!=null&&!fullScore.equals("")){
    		 modifyDraw(vo);
    	 }else{
    		 vo.setLetSf(null);
    		 vo.setLetNum(null);
    	 }
        
        return iSportBallMgrService.saveWFData(vo);
    }

    /**
     * 设置vo胜平负让分值
     * @param vo
     */
    private void modifyDraw(WFDataVO vo) {
        String fullScore = vo.getFullScore();

        if (fullScore.indexOf(SymbolConstants.COLON) == -1) {
            throw new IllegalArgumentException("比分格式错误");
        }

        String[] fullScores = fullScore.split(SymbolConstants.COLON);
        int homeFullScore = Integer.valueOf(fullScores[0]);
        int guestFullScore = Integer.valueOf(fullScores[1]);

        //让球胜平负
        if ((homeFullScore + vo.getLetNum()) > guestFullScore) {
            vo.setLetSf(Short.valueOf(WebConstant.WIN));
        } else if ((homeFullScore + vo.getLetNum()) < guestFullScore) {
            vo.setLetSf(Short.valueOf(WebConstant.LOST));
        } else if ((homeFullScore + vo.getLetNum()) == guestFullScore) {
            vo.setLetSf(Short.valueOf(WebConstant.DRAW));
        }
    }
    
	@Override
	public int updCheckScore(String lotteryCode, String modifyBy) {
		return iSportBallMgrService.updCheckScore(lotteryCode, "SPORT_DRAW_WF", modifyBy);
	}


	@Override
	public ByteArrayOutputStream getExcelStream(SMGLotteryParamVO vo) {
		 List<SportWFDataExcelBO> data=iSportBallMgrService.findWfExcelList(vo);
		return excelExportService.dataToExeclByStream("wf",data);
	}

	@Override
	public List<OrderMatchContentBO> findWfOrderMatchDetail(Integer lotteryCode, Integer lotteryChildCode,
			String systemCode, String content) {
    	SMGLotteryParamVO vo = new SMGLotteryParamVO();
    	vo.setLotteryCode(lotteryCode);
    	vo.setSystemCode(systemCode);
	  	Map<String, SportWFLotteryInfoBO>  map = iSportBallMgrService.findWfOrderMatchDetail(vo);
    	List<OrderMatchContentBO> list = (List<OrderMatchContentBO>) BetContentConverUtil.OrderContentConver(lotteryCode, lotteryChildCode, content);
    	if(!ObjectUtil.isBlank(list)){
        	for (OrderMatchContentBO orderMatchContentBO : list) {
        		SportWFLotteryInfoBO bo = map.get(orderMatchContentBO.getSystemCode());
        		orderMatchContentBO.setMatchStartTime(bo.getStartTime());
        		orderMatchContentBO.setHomeName(bo.getHomeName());
        		orderMatchContentBO.setGuestName(bo.getGuestName());
        		orderMatchContentBO.setFullScore(bo.getFullScore());
//        		orderMatchContentBO.setWdf(bo.getFullSpf() == null ? null : bo.getFullSpf().intValue());
//        		if(!ObjectUtil.isBlank(orderMatchContentBO.getWdf()))
//        	   		orderMatchContentBO.setWdfText(FuShiBetsContent.spfMap.get(orderMatchContentBO.getWdf().toString()));
        		orderMatchContentBO.setLetWdf(bo.getLetSf() == null ? null : bo.getLetSf().toString());
        		orderMatchContentBO.setLetWdfText(FuShiBetsContent.sfMap.get(orderMatchContentBO.getLetWdf()));
        		orderMatchContentBO.setLetWdfSp(bo.getSpLetWf() == null ? null : bo.getSpLetWf().floatValue());
    		}
        	return list;
    	}
		return Collections.EMPTY_LIST;
	}
}
