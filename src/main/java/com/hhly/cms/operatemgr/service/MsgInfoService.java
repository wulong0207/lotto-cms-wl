package com.hhly.cms.operatemgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgConfigBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgInfoBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgLotteryConfigBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgNewBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateMsgTemplateBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateWechatTemplateBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgNewVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgTemplateVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateMsgUserConfigVo;
import com.hhly.skeleton.cms.operatemgr.vo.OperateWechatTemplateVO;
import com.hhly.skeleton.user.bo.UserInfoBO;

/**
 *	@Desc	   通知信息管理
 *	@Author  HouXB
 *	@Date    2017年2月28日 下午2:47:58
 *  @Company 益彩网络科技公司
 *  @Version 1.0.0
 */
public interface MsgInfoService {
     /**
      * 分页查询
      * @param vo
      * @return
      */
	 PagingBO<OperateMsgInfoBO> findMsgInfo(OperateMsgInfoVO vo);
	 
	 /**
	  * 通知信息模板查询
	  * @Desc   
	  * @Author HouXB
	  * @Date   2017年3月3日 下午4:09:00
	  * @param vo
	  * @return
	  */
	 PagingBO<OperateMsgTemplateBO> findMsgTemplate(OperateMsgTemplateVO vo);
	 
	 /**
	  * 
	  * @Desc   模板下拉选项查询
	  * @Author HouXB
	  * @Date   2017年3月3日 下午4:17:18
	  * @param type 1001通知信息模板下拉列表，1002微信公众号模板下拉列表
	  * @return
	  */
	 List<DictionaryBO> findAllTemplateDic(String code);
	 
	 /**
		 * 
		 * @Desc   查询通知信息详情
		 * @Author HouXB
		 * @Date   2017年3月8日 上午10:45:20
		 * @param id
		 * @return
		 */
		OperateMsgInfoBO findMsgInfoById(int id);
		
		/**
		 * 
		 * @Desc   查询信息模板详情
		 * @Author HouXB
		 * @Date   2017年3月8日 上午10:45:37
		 * @param id
		 * @return
		 */
		OperateMsgTemplateBO findMsgTemplateById(int id);
		
		/**
		 * 
		 * @Desc   查询微信公众号模板设置详情
		 * @Author HouXB
		 * @Date   2017年3月8日 上午10:45:52
		 * @param id
		 * @return
		 */
		OperateWechatTemplateBO findWechatTemplateById(int id);
		
		/**
		 * 
		 * @Desc   新增消息模板
		 * @Author HouXB
		 * @Date   2017年3月8日 下午12:18:41
		 * @param po
		 * @return
		 */
		int addMsgTemplate(OperateMsgTemplateVO vo);
		
		/**
		 * 
		 * @Desc   新增微信公众号设置模板
		 * @Author HouXB
		 * @Date   2017年3月8日 下午12:18:53
		 * @param po
		 * @return
		 */
		int addWechatTemplate(OperateWechatTemplateVO vo);
		
		/**
		 * 
		 * @Desc   更新消息状态
		 * @Author HouXB
		 * @Date   2017年3月8日 下午12:21:46
		 * @param list
		 * @return
		 */
		int updateMsgStatus(List<OperateMsgInfoVO> list);
		
		/**
		 * 
		 * @Desc   更新模板
		 * @Author HouXB
		 * @Date   2017年3月8日 下午12:22:03
		 * @param po
		 * @return
		 */
		int updateMsgTemplate(OperateMsgTemplateVO vo);
		
		/**
		 * 
		 * @Desc   设置微信公众号模板
		 * @Author HouXB
		 * @Date   2017年3月8日 下午12:22:20
		 * @param po
		 * @return
		 */
		int updateWechatTemplate(OperateWechatTemplateVO vo);
		
		/**
		 * 
		 * @Desc   根据用户ID查询用户配置信息
		 * @Author HouXB
		 * @Date   2017年3月11日 上午9:53:58
		 * @param userId
		 * @return
		 */
		List<OperateMsgConfigBO> findConfigByUserId(int userId);
		
		/**
		 * 
		 * @Desc   更新发布消息
		 * @Author HouXB
		 * @Date   2017年3月8日 下午12:22:03
		 * @param vo
		 * @return
		 */
		int updateNewMsg(OperateMsgNewVO vo,List<String> userList);
		
		/**
		 * 
		 * @Desc   发布消息
		 * @Author HouXB
		 * @Date   2017年3月8日 下午12:18:41
		 * @param vo
		 * @return
		 */
		int addNewMsg(OperateMsgNewVO vo,List<String> userList);
		
	     /**
	      * 分页查询
	      * @param vo
	      * @return
	      */
		 PagingBO<OperateMsgNewBO> findNewMsg(OperateMsgNewVO vo);
		
		/**
		 * 查询发布信息详情
		 * @param id
		 * @return
		 */
		OperateMsgNewBO findNewMsgById(int id);
		
		/**
		 * 根据发送批次号查询发布信息详情
		 * @param id
		 * @return
		 */
		OperateMsgNewBO findNewMsgByBatch(String msgBatch);
		
		/**
		 * @Desc 查询用户关闭的通知设置
		 * @author tangxiaobo
		 * @CreatDate 2017年4月25日 下午2:30:20
		 * @param userId
		 * @return
		 */
		List<OperateMsgConfigBO> findUserCloseNoticConfig(Integer userId);
		
		/**
		 * 
		 * @Desc 更新用户通知信息开关 
		 * @author tangxiaobo
		 * @CreatDate 2017年4月26日 下午3:17:56
		 * @param vo
		 */
		void updateMsgConfig(OperateMsgUserConfigVo vo);
		
		/**
		 * 获取用户消息接收配置
		 * @param userId
		 * @param type
		 * @return
		 */
		List<OperateMsgLotteryConfigBO> findLotteryConfig(Integer userId,Integer type);
		
		/**
		 * 更新信息状态
		 * @param status
		 * @param id
		 * @return
		 */
		int updateNewMsgStatus(int status,Integer id);
		
		/**
		 * 
		 * @Desc 根据用户id集合分次查询用户列表
		 * @CreatDate 2017年4月14日 下午2:28:46
		 * @param list
		 * @return
		 */
		List<UserInfoBO> findUserInfoByIds(List<Integer> list);
		
		/**
		 * 
		 * @Desc   新增通知消息
		 * @Author HouXB
		 * @Date   2017年3月8日 下午12:18:13
		 * @param po
		 * @return
		 */
		int addMsgInfo(List<OperateMsgInfoVO> list);
		
		/**
		 * 查询用户模板接收设置信息
		 * @param userId
		 * @param typeId
		 */
		OperateMsgConfigBO findUserConfig(Integer userId, Integer typeId);
		
		/**
		 * 
		 * @Desc 查询有效用户个数
		 * @return
		 */
		int findValidUserInfoCount();
		
		/**
		 * 
		 * @Desc 分次查询有效用户列表
		 * @param list
		 * @param begin
		 * @param end
		 * @return
		 */
		List<UserInfoBO> findValidUserInfo(Integer begin,Integer end);
		
		/**
		 * 
		 * @Description 
		 * @author HouXiangBao289
		 * @param msgBatch
		 * @return
		 */
		List<String> getSendUserList(String msgBatch);

		/**
		 * 
		 * @Description 根据模板编号查询
		 * @author HouXiangBao289
		 * @param typeId
		 * @return
		 */
		OperateMsgTemplateBO findTemplateTypeId(int typeId);

		/**
		 * 
		 * @Description 取消发送新消息 
		 * @author HouXiangBao289
		 * @param list
		 */
		void cancelSendMsg(List<OperateMsgInfoVO> list);

}
