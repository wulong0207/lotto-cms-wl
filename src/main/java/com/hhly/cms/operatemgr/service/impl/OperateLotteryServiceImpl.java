package com.hhly.cms.operatemgr.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.hhly.cms.operatemgr.service.OperateLotteryService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.cms.operatemgr.bo.OperateLotteryBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateLotteryInfoBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateLotteryInfoVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateLotteryVO;

/**
 * @desc    彩种运营管理
 * @author  Tony Wang
 * @date    2017年2月17日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Service
public class OperateLotteryServiceImpl implements OperateLotteryService {

	@Autowired
  private IOperateMgrService iOperateMgrService;
	
	private static AtomicInteger lotteryReleaseCodeCounter;
	
	private static int currentDay4Lottery;
	
	private final Lock lotteryReleaseCodeLock = new ReentrantLock();
	
	static {
		currentDay4Lottery = Calendar.getInstance().get(Calendar.DATE);
	}
	/**
	 * @desc   分页查询彩种运营信息
	 * @author Tony Wang
	 * @create 2017年2月17日
	 * @param vo
	 * @return  
	 */
	@Override
	public PagingBO<OperateLotteryBO> list(OperateLotteryVO vo) {
		return iOperateMgrService.findOperateLottery(vo);
	}
	
	/**
	 * @desc   增加彩种运营信息
	 * @author Tony Wang
	 * @create 2017年2月20日
	 * @param vo
	 * @return
	 */
	@Override
	public int add(OperateLotteryVO vo) {
		vo.setReleaseCode(nexLotteryReleaseCode());
		return iOperateMgrService.addOperateLottery(vo);
	}
	
	private String nexLotteryReleaseCode() {
		String today = DateUtil.convertDateToStr(new Date(), DateUtil.DATE_FORMAT_NO_LINE);
		int nextReleaseCode;
		lotteryReleaseCodeLock.lock();
		try {
			if(lotteryReleaseCodeCounter == null) {
				String maxReleaseCode = iOperateMgrService.findMaxOperateLotteryReleaseCode(today);
				if(StringUtils.isBlank(maxReleaseCode)) {
					// 如果没有当天的编号，则从0开始
					lotteryReleaseCodeCounter = new AtomicInteger();
				} else {
					// 从2016072100001截取出00001
					lotteryReleaseCodeCounter = new AtomicInteger(Integer.parseInt(maxReleaseCode.substring(8)));
				}
			}
			// 判断日期是否有变更，有则currentArticelId归0，无则加1
			int newDay = Calendar.getInstance().get(Calendar.DATE);
			if(newDay == currentDay4Lottery) {
				nextReleaseCode = lotteryReleaseCodeCounter.incrementAndGet();
				Assert.isTrue(nextReleaseCode<100000, "当天增加的彩种运营已超过100000");
			} else {
				lotteryReleaseCodeCounter.set(0);
				nextReleaseCode = lotteryReleaseCodeCounter.incrementAndGet();
				// 更新当前日期
				currentDay4Lottery = newDay;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			lotteryReleaseCodeLock.unlock();
		}
		return new StringBuilder(today)
				.append(String.format("%05d", nextReleaseCode))
				.toString();
	}
	/**
	 * @desc   更新彩种运营信息
	 * @author Tony Wang
	 * @create 2017年2月20日
	 * @param vo
	 * @return
	 */
	@Override
	public int update(OperateLotteryVO vo) {
		return iOperateMgrService.updateOperateLottery(vo);
	}

	/**
	 * @desc   查询彩种运营详情信息
	 * @author Tony Wang
	 * @create 2017年2月21日
	 * @param vo
	 * @return  
	 */
	@Override
	public List<OperateLotteryInfoBO> listLotteryInfo(OperateLotteryInfoVO vo) {
		return iOperateMgrService.findOperateLotteryInfo(vo);
	}

	/**
	 * @desc   查询符合条件的运营方案数量
	 * @author Tony Wang
	 * @create 2017年2月22日
	 * @param vo
	 * @return  
	 */
	@Override
	public int count(OperateLotteryVO vo) {
		return iOperateMgrService.countOperateLottery(vo);
	}

//	/**
//	 * @desc   vo有id则update彩种运营信息，无则insert
//	 * @author Tony Wang
//	 * @create 2017年2月17日
//	 * @param vo
//	 * @return  
//	 */
//	@Override
//	public int merge(OperateLotteryVO vo) {
//		return iOperateMgrService.mergeOperateLottery(vo);
//	}

}
