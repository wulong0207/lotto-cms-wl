package com.hhly.cms.operatemgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateImgBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateImgVO;

/**
 * 
 * @desc 图片管理
 * @author jiangwei
 * @date 2017年4月10日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface ImageService {
	/**
	 * 分页查询
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月10日 上午10:12:34
	 * @param vo
	 * @return
	 */
	PagingBO<OperateImgBO> listOperateImg(OperateImgVO vo);
    /**
     * 添加图片
     * @author jiangwei
     * @Version 1.0
     * @CreatDate 2017年4月11日 上午10:07:09
     * @param vos
     * @return
     */
	int addOperateImg(List<OperateImgVO> vos);
	/**
	 * 修改图片名称
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:12:51
	 * @param vo
	 * @return
	 */
	int updOperateImg(OperateImgVO vo);
	/**
	 * 查询图片
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:32:04
	 * @param asList
	 * @return
	 */
	List<OperateImgBO> listOperateImgInfo(List<String> id);
	/**
	 * 删除图片
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:32:20
	 * @param id
	 */
	int delOperateImg(List<String> id);
	/**
	 * 移动图片分组
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月11日 上午10:32:27
	 * @param vo
	 * @return
	 */
	int updOperateImgMove(OperateImgVO vo);
	/**
	 * 查询图片信息
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月14日 下午12:14:05
	 * @param vo
	 * @return
	 */
	OperateImgBO getOperateImg(OperateImgVO vo);

}
