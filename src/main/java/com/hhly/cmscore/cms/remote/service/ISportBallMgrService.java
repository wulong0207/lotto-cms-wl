package com.hhly.cmscore.cms.remote.service;

import java.util.List;
import java.util.Map;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.sportmgr.bo.*;
import com.hhly.skeleton.cms.sportmgr.vo.BBDataVO;
import com.hhly.skeleton.cms.sportmgr.vo.BJDataVO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportDataBaseVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportMatchInfoVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportMatchSourceInfoVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportTeamInfoVO;
import com.hhly.skeleton.cms.sportmgr.vo.SportTeamSourceInfoVO;
import com.hhly.skeleton.cms.sportmgr.vo.WFDataVO;
import com.hhly.skeleton.lotto.base.sport.vo.FBDataVO;
import com.hhly.skeleton.lotto.base.sport.vo.OldDataVO;

/**
 * Created by lgs on 2016/12/28.
 * 竞技彩球类管理
 */
public interface ISportBallMgrService {
    /*老足彩begin*/

    /**
     * 查询老足彩数据
     * @param vo
     * @return
     */
    PagingBO<SportOldLotteryInfoBO> findOldLotteryInfo(SMGLotteryParamVO vo);

    /**
     * 获取老足彩详情的SP值
     *
     * @param againstId
     * @return
     */
    Map<String, Object> findOldSp(Long againstId);

    /**
     * 保存老足彩数据
     * 主要保存球队信息|赛事信息|对阵信息|赔率信息
     * @param vo
     * @return
     */
    int saveOldData(OldDataVO vo);
	/*老足彩end*/


	/*竞技足彩begin*/

    /**
     * 查询竞技彩数据
     * @param vo
     * @return
     */
    PagingBO<SportFBLotteryInfoBO> findFBData(SMGLotteryParamVO vo);


    /**
     * 获取竞彩足球详情的SP值
     *
     * @param againstId
     * @return
     */
    Map<String, Object> findFBSp(Long againstId);

    /**
     * 保存竞彩足球修改数据
     * @param vo
     * @return
     */
    int saveFBData(FBDataVO vo);
	/*竞技足彩end*/

	/*北京单场begin*/

    /**
     * 查询北京单场数据
     * @param vo
     * @return
     */
    PagingBO<SportBJLotteryInfoBO> findBJData(SMGLotteryParamVO vo);

    /**
     * 获取北京单场详情的SP值
     *
     * @param againstId
     * @return
     */
    Map<String, Object> findBJSp(Long againstId);


    /**
     * 保存北京单场修改后的数据
     * @param vo
     * @return
     */
    int saveBJData(BJDataVO vo);
	/*北京单场end*/

	/*竞彩篮球begin*/

    /**
     * 查询竞彩篮球数据
     * @param vo
     * @return
     */
    PagingBO<SportBBLotteryInfoBO> findBBData(SMGLotteryParamVO vo);


    /**
     * 获取竞彩篮球详情的SP值
     *
     * @param againstId
     * @return
     */
    Map<String, Object> findBBSp(Long againstId);

    /**
     * 保存竞彩篮球修改后的数据
     * @param vo
     * @return
     */
    int saveBBData(BBDataVO vo);
	/*竞彩篮球end*/

    /*胜负过关begin*/

    /**
     * 查询胜负过关数据
     * @param vo
     * @return
     */
    PagingBO<SportWFLotteryInfoBO> findWFData(SMGLotteryParamVO vo);


    /**
     * 获取竞彩篮球详情的SP值
     *
     * @param againstId
     * @return
     */
    Map<String, Object>  findWFSp(Long againstId);


    /**
     * 保存胜负过关修改后的数据
     * @param vo
     * @return
     */
    int saveWFData(WFDataVO vo);
    /*胜负过关end*/
    
    /**
     * 根据彩种审核赛事比分
     * @param lotteryCode 彩种
     * @param drawTable 比分表
     * @param modifyBy 修改人
     * @return
     * @date 2017年5月11日下午6:18:01
     * @author cheng.chen
     */
    int updCheckScore(String lotteryCode, String drawTable, String modifyBy);

    /**
     * 修改对阵赛事信息
     * @param vo
     * @return
     * @date 2017年5月22日下午4:27:50
     * @author cheng.chen
     */
	int updSportAgainstInfo(SportDataBaseVO vo);
	
    /**
     * 获取老足彩excel列表
     */
    List<SportDataFbOldExcelBo> findOldFbExcelList(SMGLotteryParamVO vo);
    /**
     * 获取竞彩足球excel列表
     */
    List<SportFBDataExcelBO> findFbExcelList(SMGLotteryParamVO vo);
    /**
     * 获取竞彩蓝球excel列表
     */
    List<SportBBDataExcelBO> findBbExcelList(SMGLotteryParamVO vo);
    /**
     * 获取北京单场excel列表
     */
    List<SportBJDataExcelBO> findBjExcelList(SMGLotteryParamVO vo);
    /**
     * 获取胜负过关excel列表
     */
    List<SportWFDataExcelBO> findWfExcelList(SMGLotteryParamVO vo);
    
    /**
     * 查询老足彩订单详情赛事信息集合
     * @param vo
     * @return
     * @date 2017年6月11日下午5:00:53
     * @author cheng.chen
     */
    List<SportOldLotteryInfoBO> findOldOrderMatchDetail(SMGLotteryParamVO vo);
    
    /**
     * 查询竞彩足球订单详情赛事信息集合
     * @param vo
     * @return
     * @date 2017年6月13日上午11:10:09
     * @author cheng.chen
     */
    Map<String, SportFBLotteryInfoBO> findFbOrderMatchDetail(SMGLotteryParamVO vo);
    
    /**
     * 查询竞彩篮球订单详情赛事信息集合
     * @param vo
     * @return
     * @date 2017年6月13日下午7:17:27
     * @author cheng.chen
     */
    Map<String, SportBBLotteryInfoBO> findBbOrderMatchDetail(SMGLotteryParamVO vo);
     
    /**
     * 查询竞彩北单订单详情赛事集合信息
     * @param vo
     * @return
     * @date 2017年10月25日下午6:30:30
     * @author cheng.chen
     */
    Map<String, SportBJLotteryInfoBO> findBjOrderMatchDetail(SMGLotteryParamVO vo);
    
    /**
     * 查询竞彩胜负订单详情赛事集合信息
     * @param vo
     * @return
     * @date 2017年10月25日下午6:30:30
     * @author cheng.chen
     */
    Map<String, SportWFLotteryInfoBO> findWfOrderMatchDetail(SMGLotteryParamVO vo);     
    
    /**
     * 模糊查找赛事信息
     * @param sportMatchVO
     * @return
     */
	List<SportMatchInfoBO> findMatchLikeList(SportMatchInfoVO vo);
	
	/**
	 * 分页查询赛事信息
	 * @param sportMatchInfoVO
	 * @return
	 * @date 2018年1月20日下午2:20:00
	 * @author cheng.chen
	 */
	PagingBO<SportMatchInfoBO> findMatchList(SportMatchInfoVO vo);
	
	/**
	 * 新增赛事信息
	 * @param sportMatchInfoVO
	 * @return
	 * @date 2018年1月20日下午2:20:57
	 * @author cheng.chen
	 */
	int insertMatchInfo(SportMatchInfoVO vo);
	
	/**
	 * 修改赛事信息
	 * @param sportMatchInfoVO
	 * @return
	 * @date 2018年1月20日下午2:20:57
	 * @author cheng.chen
	 */
	int updateMatchInfo(SportMatchInfoVO vo);	
	
	/**
	 * 批量删除赛事信息
	 * @param vo
	 * @return
	 * @date 2018年1月20日下午4:29:38
	 * @author cheng.chen
	 */
	int deleteBatchMatchInfo(StringVO vo);
	
	/**
	 * 分页查询赛事渠道集合
	 * @return
	 * @date 2018年1月24日下午5:59:43
	 * @author cheng.chen
	 */
	PagingBO<SportMatchSourceInfoBO> findMatchSourceList(SportMatchSourceInfoVO vo);
	
	/**
	 * 修改渠道赛事信息
	 * @param vo
	 * @return
	 * @date 2018年1月24日下午6:44:32
	 * @author cheng.chen
	 */
	int updateMatchSourceInfo(SportMatchSourceInfoVO vo);
	
	/**
	 * 通过id删除赛事id
	 * @param id
	 * @return
	 * @date 2018年1月26日下午3:37:42
	 * @author cheng.chen
	 */
	int removeMatchBind(Integer id);
	
	/**
	 * 批量删除渠道球队
	 * @param vo
	 * @date 2018年3月22日上午11:55:35
	 * @author cheng.chen
	 */
	void deleteBatchMatchSource(StringVO vo);	
	
	/**
	 * 查找球队信息
	 * @param sportTeamInfoVO
	 * @return
	 */
	List<SportTeamInfoBO> findTeamLikeList(SportTeamInfoVO vo);
	
	/**
	 * 分页查询球队信息
	 * @param sportMatchInfoVO
	 * @return
	 * @date 2018年1月20日下午2:20:00
	 * @author cheng.chen
	 */
	PagingBO<SportTeamInfoBO> findTeamList(SportTeamInfoVO vo);	
	
	/**
	 * 新增球队信息
	 * @param vo
	 * @return
	 * @date 2018年1月20日下午4:32:53
	 * @author cheng.chen
	 */
	int insertTeamInfo(SportTeamInfoVO vo);
	
	/**
	 * 修改球队信息
	 * @param vo
	 * @return
	 * @date 2018年1月20日下午4:34:18
	 * @author cheng.chen
	 */
	int updateTeamInfo(SportTeamInfoVO vo);
	
	/**
	 * 批量删除球队信息
	 * @param vo
	 * @return
	 * @date 2018年1月20日下午4:29:38
	 * @author cheng.chen
	 */
	int deleteBatchTeamInfo(StringVO vo);
	
	/**
	 * 分页查询球队渠道集合
	 * @return
	 * @date 2018年1月24日下午5:59:43
	 * @author cheng.chen
	 */
	PagingBO<SportTeamSourceInfoBO> findTeamSourceList(SportTeamSourceInfoVO vo);
	
	/**
	 * 修改渠道球队信息
	 * @param vo
	 * @return
	 * @date 2018年1月24日下午6:44:32
	 * @author cheng.chen
	 */
	int updateTeamSourceInfo(SportTeamSourceInfoVO vo);
	
	/**
	 * 通过id删除球队id
	 * @param id
	 * @return
	 * @date 2018年1月26日下午3:37:00
	 * @author cheng.chen
	 */
	int removeTeamBind(Integer id);

	/**
	 * 查询冠军对阵
	 * @param vo
	 * @return
	 */
    PagingBO<SportAgainstInfoBO> pageGj(SMGLotteryParamVO vo);

	/**
	 * 更新冠军对阵
	 * @param vo
	 * @return
	 */
	int updateSportAgainstInfo(SportDataBaseVO vo);
	/**
	 * 批量删除渠道球队
	 * @param vo
	 * @date 2018年3月22日上午11:55:35
	 * @author cheng.chen
	 */
	void deleteBatchTeamSource(StringVO vo);
	
}
