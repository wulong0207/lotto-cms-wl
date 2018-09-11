package com.hhly.cms.operatemgr.service;

import java.util.List;

import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateSoftwareVersionBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateSoftwareVersionVO;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年5月2日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public interface OperateSoftwareVersionService {

	/**
	 * 创建移动端包发布对象
	 * @param vo
	 * @return
	 * @author cheng.chen
	 * @date 2017年5月02日 上午 12:06:01
	 */
	int addSoftwareVersion(OperateSoftwareVersionVO vo);
	
	/**
	 * 查询移动端软件管理集合
	 * @param vo
	 * @return
	 * @date 2017年5月2日下午12:12:05
	 * @author cheng.chen
	 */
	PagingBO<OperateSoftwareVersionBO> findSoftwareVersionList(OperateSoftwareVersionVO vo);
	
	/**
	 * 查询静态版本号
	 * @return
	 * @date 2017年5月2日下午5:12:04
	 * @author cheng.chen
	 */
	List<DictionaryBO> findBaseSoftwareVersion(OperateSoftwareVersionVO vo);	
	
	/**
	 * 批量删除
	 * @param vo
	 * @return
	 * @date 2017年5月2日下午5:29:22
	 * @author cheng.chen
	 */
	int delSoftwareVersionByIds(StringVO vo);
	
	/**
	 * 修改移动端包发布对象
	 * @param vo
	 * @return
	 * @date 2017年5月2日下午4:13:40
	 * @author cheng.chen
	 */
	int updSoftwareVersion(OperateSoftwareVersionVO vo);
	
	/**
	 * 根据是否最新字段修改
	 * @param isnew
	 * @param type
	 * @return
	 * @date 2017年5月2日下午6:13:06
	 * @author cheng.chen
	 */
	int updateIsnew(int isnew, int type);	
	
	/**
	 * 验证编码唯一
	 * @param code
	 * @return
	 * @date 2017年5月2日下午4:14:26
	 * @author cheng.chen
	 */
	int valiSoftwareVersionCode(int code);
	
	/**
	 * @desc   查询符合条件的记录数
	 * @author Tony Wang
	 * @create 2017年9月13日
	 * @param vo
	 * @return 
	 */
	int count(OperateSoftwareVersionVO vo);

	int updSoftwareVersionSwitch(OperateSoftwareVersionVO vo);

	/**
	 * @desc   查找最新的版本
	 * @author Tony Wang
	 * @create 2017年10月14日
	 * @param vo
	 * @return 
	 */
	List<OperateSoftwareVersionBO> findNewSoftwareVersion(OperateSoftwareVersionVO vo);

	/**
	 * @desc   查询单条版本
	 * @author Tony Wang
	 * @create 2017年10月15日
	 * @param vo
	 * @return 
	 */
	OperateSoftwareVersionBO findOneSoftwareVersion(OperateSoftwareVersionVO vo);
//	/**
//	 * @Description: 根据渠道ID，批量关闭/开启同步功能
//	 * @param vo
//	 * @return
//	 * @author wuLong
//	 * @date 2017年10月21日 下午12:14:29
//	 */
//	int updSoftwareVersionSwitchByChannlId(OperateSoftwareVersionVO vo);

	/**
	 * @desc   更新'是否同步官方'
	 * @author Tony Wang
	 * @create 2017年10月24日
	 * @param target
	 * @return 
	 */
	int updSoftwareVersionSwitchSynOfficial(OperateSoftwareVersionVO vo);

	/**
	 * @desc   查询所有不同名称的版本
	 * @author Tony Wang
	 * @create 2017年12月22日
	 * @param operateSoftwareVersionVO
	 * @return 
	 */
	List<OperateSoftwareVersionBO> distinctSoftwareVersionName(OperateSoftwareVersionVO operateSoftwareVersionVO);
}
