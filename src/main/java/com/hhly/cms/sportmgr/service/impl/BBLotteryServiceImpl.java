package com.hhly.cms.sportmgr.service.impl;

import java.io.ByteArrayOutputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.sportmgr.service.BBLotteryService;
import com.hhly.cms.utils.WebConstant;
import com.hhly.cmscore.cms.remote.service.ISportBallMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.JCLQConstants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.sportsutil.BetContentConverUtil;
import com.hhly.skeleton.base.util.sportsutil.FuShiBetsContent;
import com.hhly.skeleton.cms.ordermgr.bo.OrderMatchContentBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportBBDataExcelBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportBBLotteryInfoBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportDataBbSSSBO;
import com.hhly.skeleton.cms.sportmgr.bo.SportDataBbWFBO;
import com.hhly.skeleton.cms.sportmgr.vo.BBDataVO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportDataBaseVO;

/**
 * Created by lgs on 2016/12/23.
 * 竞彩篮球service
 */
@Service
public class BBLotteryServiceImpl implements BBLotteryService{

    @Autowired
    private ISportBallMgrService iSportBallMgrService;
    @Autowired
    private ExcelExportService excelExportService;


    @Override
    public PagingBO<SportBBLotteryInfoBO> findBBData(SMGLotteryParamVO vo) {
        return iSportBallMgrService.findBBData(vo);
    }

    @Override
    public Map<String, Object> findBBSp(Long againstId) {
        return iSportBallMgrService.findBBSp(againstId);
    }

    @Override
    public int saveBBData(BBDataVO vo) {
    	
    	String firstScore = vo.getFirstScore();
        String secondScore = vo.getSecondScore();
        String thirdScore = vo.getThirdScore();
        String fourthScore = vo.getFourthScore();
        String outTimeScore = vo.getOutTimeScore();
        String fullScore = vo.getFullScore();
        String notSalePay = vo.getNotSalePay();
        
        if(fullScore!=null&&!fullScore.equals("")){
        	  modifyDraw(vo);
        }else{
        	vo.setFullWf(null);
        	vo.setLetWf(null);
        	vo.setSizeScore(null);
        	vo.setWinScore(null);
        }
        
        return iSportBallMgrService.saveBBData(vo);
    }



    /**
     * 设置竞彩篮球修改数据
     * @param vo
     */
    public void modifyDraw(BBDataVO vo){
        String firstScore = vo.getFirstScore();
        String secondScore = vo.getSecondScore();
        String thirdScore = vo.getThirdScore();
        String fourthScore = vo.getFourthScore();
        String outTimeScore = vo.getOutTimeScore();
        String fullScore = vo.getFullScore();
        String notSalePay = vo.getNotSalePay();

        String[] firstScores = firstScore.split(SymbolConstants.COLON);
        String[] secondScores = secondScore.split(SymbolConstants.COLON);
        String[] thirdScores = thirdScore.split(SymbolConstants.COLON);
        String[] fourthScores = fourthScore.split(SymbolConstants.COLON);
        String[] fullScores = fullScore.split(SymbolConstants.COLON);


        int firstGuestScore= Integer.parseInt(firstScores[0]);
        int firstHomeScore  = Integer.valueOf(firstScores[1]);
        int secondGuestScore = Integer.valueOf(secondScores[0]);
        int secondHomeScore = Integer.valueOf(secondScores[1]);
        int thirdGuestScore = Integer.valueOf(thirdScores[0]);
        int thirdHomeScore = Integer.valueOf(thirdScores[1]);
        int fourthGuestScore = Integer.valueOf(fourthScores[0]);
        int fourthHomeScore = Integer.valueOf(fourthScores[1]);
        int fullGuestScore = Integer.valueOf(fullScores[0]);
        int fullHomeScore = Integer.valueOf(fullScores[1]);

        int totalHomeScore =  firstHomeScore+secondHomeScore+thirdHomeScore+fourthHomeScore;
        int totalGuestScore =  firstGuestScore+secondGuestScore+thirdGuestScore+fourthGuestScore;
        int outTimeHomeScore = 0;
        int outTimeGuestScore = 0;
        if(outTimeScore!=null&&outTimeScore.indexOf(SymbolConstants.COLON)!=-1){
            if(totalHomeScore!=totalGuestScore){
               throw new IllegalArgumentException("前四节比分主队与客队未打平，不能有加时比分。");
            }

            String[] outTimeScores = outTimeScore.split(SymbolConstants.COLON);
            outTimeGuestScore =  Integer.valueOf(outTimeScores[0]);
            outTimeHomeScore =  Integer.valueOf(outTimeScores[1]);
        }
        totalHomeScore = totalHomeScore+outTimeHomeScore;
        totalGuestScore= totalGuestScore+outTimeGuestScore;

        //设置子玩法
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

        String fullScoreValue = "";
        if(fullHomeScore!=totalHomeScore||fullGuestScore!=totalGuestScore){
            fullScoreValue = totalHomeScore + SymbolConstants.COLON + totalGuestScore;
            vo.setFullScore(fullScoreValue);
        }
        //获取全场胜负
        String fullWf = "";
        //获取主胜客胜，以及胜分差
        String winScore = "";
        if (totalHomeScore > totalGuestScore) {
            winScore = getScore(Constants.NUM_0,totalHomeScore,totalGuestScore); //主胜
            fullWf = WebConstant.WIN;
        } else if (totalHomeScore < totalGuestScore) {
            winScore = getScore(Constants.NUM_1,totalHomeScore,totalGuestScore);//客胜
            fullWf = WebConstant.LOST;
        }
        vo.setWinScore(winScore);
        vo.setFullWf(Short.valueOf(fullWf));
        String letWf = "";
        //让分胜负
        Map<String, String> valiMap = new HashMap<>();
        if(vo.getLetSportDataBbWFBOs()!=null){
            for(SportDataBbWFBO bo : vo.getLetSportDataBbWFBOs()){
                if(valiMap.containsKey(bo.getLetScore().toString())){
                 	continue;
                }else{
                	valiMap.put(bo.getLetScore().toString(), bo.getLetScore().toString());
                }
                String wf = "";
                if((totalHomeScore+bo.getLetScore())>totalGuestScore){
                    wf = bo.getLetScore()+SymbolConstants.VERTICAL_BAR+WebConstant.WIN;
                } else if((totalHomeScore+bo.getLetScore())<totalGuestScore){
                    wf = bo.getLetScore()+SymbolConstants.VERTICAL_BAR+WebConstant.LOST;
                }

                if(letWf.equals("") && letWf.indexOf(SymbolConstants.COMMA)==-1){
                    letWf = wf;
                } else {
                    letWf += SymbolConstants.COMMA + wf;
                }
            }
            vo.setLetWf(letWf);
        }

        //大小分胜负
        int totalScore = totalHomeScore + totalGuestScore;
        String sizeScore = "";
        if(vo.getSportDataBbSSSBOs()!=null){
            for(SportDataBbSSSBO bo : vo.getSportDataBbSSSBOs()){
            	
                if(sizeScore.contains(bo.getPresetScore().toString()))
                	continue;
                String score = "";
                if(totalScore > bo.getPresetScore()){
                    score = bo.getPresetScore() + SymbolConstants.VERTICAL_BAR + JCLQConstants.BIG_SMALL_SCORE_FORMAT[0];
                } else if(totalScore < bo.getPresetScore()){
                    score = bo.getPresetScore() + SymbolConstants.VERTICAL_BAR + JCLQConstants.BIG_SMALL_SCORE_FORMAT[1];
                }

                if(sizeScore.equals("") && sizeScore.indexOf(SymbolConstants.COMMA)==-1){
                    sizeScore = score;
                } else {
                    sizeScore += SymbolConstants.COMMA + score;
                }
            }
            vo.setSizeScore(sizeScore);
        }
    }


    /**
     * 设置子玩法状态
     * @param vo
     * @param notSalePay
     */
    private void setStatus(BBDataVO vo,String notSalePay){
        if(notSalePay.equals(Constants.BB_BIG_SMALL)){
            vo.setStatusBigSmall(Constants.STOP_SALE);
        }else if(notSalePay.equals(Constants.BB_LET_WF)){
            vo.setStatusLetWf(Constants.STOP_SALE);
        }else if(notSalePay.equals(Constants.BB_SCORE_WF)){
            vo.setStatusScoreWf(Constants.STOP_SALE);
        }else if(notSalePay.equals(Constants.BB_WF)){
            vo.setStatusWf(Constants.STOP_SALE);
        }
    }

    /**
     * 获取胜分差
     * @param winFlag 0代表主胜,1代表客胜
     * @param totalHomeScore 主队总分数
     * @param totalGuestScore 客队总分数
     * @return
     */
    private String getScore(int winFlag,int totalHomeScore,int totalGuestScore){
        String result = "";
        int score = 0;
        if(winFlag == Constants.NUM_0){
            score =  totalHomeScore - totalGuestScore;
        } else {
            score =  totalGuestScore - totalHomeScore;
        }

        String text = "";
        if(score >= Constants.NUM_1 && score <= Constants.NUM_5){
            text = String.valueOf(Constants.NUM_1);
        } else if(score >= Constants.NUM_6 && score <= Constants.NUM_10){
            text = String.valueOf(Constants.NUM_2);
        } else if(score >= Constants.NUM_11 && score <= Constants.NUM_15){
            text = String.valueOf(Constants.NUM_3);
        } else if(score >= Constants.NUM_16 && score <= Constants.NUM_20){
            text = String.valueOf(Constants.NUM_4);
        } else if(score >= Constants.NUM_21 && score <= Constants.NUM_25){
            text = String.valueOf(Constants.NUM_5);
        } else if(score > Constants.NUM_25 ){
            text = String.valueOf(Constants.NUM_6);
        }

        result = winFlag + text;
        return result;
    }
    
	@Override
	public int updCheckScore(String lotteryCode, String modifyBy) {
		return iSportBallMgrService.updCheckScore(lotteryCode, "SPORT_DRAW_BB", modifyBy);
	}

	@Override
	public int updSportAgainstInfo(SportDataBaseVO vo) {
		return iSportBallMgrService.updSportAgainstInfo(vo);
	}

	@Override
	public ByteArrayOutputStream getExcelStream(SMGLotteryParamVO vo) {
		 List<SportBBDataExcelBO> data=iSportBallMgrService.findBbExcelList(vo);
		return excelExportService.dataToExeclByStream("bb",data);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<OrderMatchContentBO> findBbOrderMatchDetail(Integer lotteryCode,Integer lotteryChildCode,
			String systemCode, String content) {
    	SMGLotteryParamVO vo = new SMGLotteryParamVO();
    	vo.setLotteryCode(lotteryCode);
    	vo.setSystemCode(systemCode);
	  	Map<String, SportBBLotteryInfoBO>  map = iSportBallMgrService.findBbOrderMatchDetail(vo);
    	List<OrderMatchContentBO> list = (List<OrderMatchContentBO>) BetContentConverUtil.OrderContentConver(lotteryCode, lotteryChildCode, content);
    	if(!ObjectUtil.isBlank(list)){
        	for (OrderMatchContentBO orderMatchContentBO : list) {
        		SportBBLotteryInfoBO bo = map.get(orderMatchContentBO.getSystemCode());
        		orderMatchContentBO.setMatchStartTime(bo.getStartTime());
        		orderMatchContentBO.setHomeName(bo.getHomeName());
        		orderMatchContentBO.setGuestName(bo.getGuestName());
        		orderMatchContentBO.setFullScore(bo.getFullScore());
        		orderMatchContentBO.setWdf(bo.getFullWf() == null ? null : bo.getFullWf().intValue());
        		if(ObjectUtil.isNotNull(orderMatchContentBO.getWdf()))
        	   		orderMatchContentBO.setWdfText(FuShiBetsContent.spfMap.get(orderMatchContentBO.getWdf().toString()));
        		orderMatchContentBO.setLetWdf(bo.getLetWf() == null ? null : bo.getLetWf());
           		if(ObjectUtil.isNotNull(orderMatchContentBO.getLetWdf())){
           			orderMatchContentBO.setLetWdfText(converLetWf(orderMatchContentBO.getLetWdf()));
           		}
        		orderMatchContentBO.setScore(bo.getSizeScore());
           		if(ObjectUtil.isNotNull(orderMatchContentBO.getScore())){
           			orderMatchContentBO.setScoreText(converSizeScore(orderMatchContentBO.getScore()));
           		}        		
        		orderMatchContentBO.setWinScore(bo.getWinScore() == null ? null : bo.getWinScore());
        		if(ObjectUtil.isNotNull(orderMatchContentBO.getWinScore()))
        	   		orderMatchContentBO.setWinScoreText(FuShiBetsContent.sfcMap.get(orderMatchContentBO.getWinScore()));
    		}
        	return list;
    	}
		return Collections.EMPTY_LIST;		
	}
	
	private String converLetWf(String letWfStr) {
		String str = "";
		String[] letWfList = letWfStr.split(SymbolConstants.COMMA);
		for (int i = 0; i < letWfList.length; i++) {
			String[] letWf = letWfList[i].split(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR);
			str += letWf[0] + FuShiBetsContent.rqspfMap.get(letWf[1]) + SymbolConstants.COMMA; 
		}
		str = str.substring(0, str.lastIndexOf(SymbolConstants.COMMA));
		return str;
	}
	
	private String converSizeScore(String sizeScoreStr){
		String str = "";
		String[] letWfList = sizeScoreStr.split(SymbolConstants.COMMA);
		for (int i = 0; i < letWfList.length; i++) {
			String[] letWf = letWfList[i].split(SymbolConstants.DOUBLE_SLASH_VERTICAL_BAR);
			str += letWf[0] + FuShiBetsContent.dxfMap.get(letWf[1]) + SymbolConstants.COMMA; 
		}
		str = str.substring(0, str.lastIndexOf(SymbolConstants.COMMA));
		return str;		
	}
}
