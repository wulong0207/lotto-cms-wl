package com.hhly.cms.operatemgr.controller;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hhly.activity.remote.service.ReissueActivityService;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.rabbitmq.MQProducer;
import com.hhly.cms.customermgr.service.CustomerService;
import com.hhly.cms.operatemgr.service.CouponService;
import com.hhly.cms.transmgr.service.TransMgrService;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.DateUtil;
import com.hhly.skeleton.activity.vo.ActivityInfoVO;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.common.OrderEnum.NumberCode;
import com.hhly.skeleton.base.constants.Constants;
import com.hhly.skeleton.base.constants.SymbolConstants;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.mq.msg.MessageModel;
import com.hhly.skeleton.base.mq.msg.OperateNodeMsg;
import com.hhly.skeleton.base.util.JsonUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.base.util.OrderNoUtil;
import com.hhly.skeleton.base.util.StringUtil;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.customermgr.bo.LottoCustomerBO;
import com.hhly.skeleton.cms.customermgr.vo.LottoCustomerVO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateCouponVO;
import com.hhly.skeleton.cms.transmgr.vo.TransRedVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2017-1-4 下午3:29:47
 * @Desc 活动管理
 */
@Controller
@RequestMapping(value = "/operatemgr/coupon")
public class CouponController extends BaseController {
	
	@Autowired
	private CouponService couponService;
	
	@Autowired
	private MQProducer mQProducer;
	
	@Autowired	
	private TransMgrService transMgrService;
	
	@Autowired
	private CustomerService customerService;
	@Autowired
	private ReissueActivityService reissueActivityService;
	@Autowired
	private RestTemplate restTemplate;
	@Value("${lotto_pay_url}")
	private String LOTTO_PAY_URL;
	private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
	/**
	 * 跳转优惠券管理页面
	 * @return
	 * @date 2017年5月25日上午11:51:44
	 * @author cheng.chen
	 */
	@RequestMapping()
	@Authority(privilege=AuthEnum.SEARCH)
	public String  index(){
		return "operatemgr/coupon";
	}

	/**
	 * 查询优惠券集合信息
	 * @param vo
	 * @return
	 * @date 2017年5月25日上午11:51:53
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object list(OperateCouponVO vo){
		return couponService.findCoupon(vo);
	}

	/**
	 * 查询优惠券详情
	 * @param redCode
	 * @return
	 * @date 2017年5月25日上午11:52:19
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/detail/{redCode}")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	public  Object list(@PathVariable(value="redCode") String redCode){
		StringVO vo = new StringVO();
		vo.setStr(redCode);
		return getResultSuccess(couponService.findCouponDetail(vo));
	}
	
	
	/**
	 * 导出excel
	 * @param response
	 * @param vo
	 * @throws IOException
	 */
	@RequestMapping("excel")
	@Authority(privilege =AuthEnum.EXPORT)
	public void exportOpExcel(HttpServletResponse response,OperateCouponVO vo) throws IOException{
		ByteArrayOutputStream outputStream=couponService.getExcelCoupon(vo);
		excel("coupon", outputStream, response);
	}
	/**
	 * 红包流水
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/trans/list")
	@Authority(privilege=AuthEnum.SEARCH)
	@ResponseBody
	@ParameterValid
	public  Object list(@Valid("coupon")TransRedVO vo){
		return transMgrService.findRedTrans(vo);
	}

	/**
	 * 批量创建优惠券信息
	 * @param session
	 * @param file
	 * @param vo
	 * @return
	 * @date 2017年5月25日上午11:52:29
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/add", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	@ParameterValid
	public Object add(HttpSession session,@RequestParam MultipartFile file, @Valid(GroupValue.ADD) OperateCouponVO vo){
			ResultBO<?> resultBo = null;
			List<String> accountNamelist = null;
		try {
			// 1.解析文件
			if(file == null && StringUtil.isBlank(vo.getUserAccountNameStr())){
				return JsonUtil.object2Json(ResultBO.err());
			}
			if(file != null && !StringUtil.isBlank(file.getOriginalFilename())){
				accountNamelist = analysisFile(file);
			}else if(!StringUtil.isBlank(vo.getUserAccountNameStr())){
				accountNamelist = new ArrayList<>();
				String[] names = vo.getUserAccountNameStr().split(SymbolConstants.COMMA);
				for (String name : names) {
					accountNamelist.add(name);
				}
			}
			//会员账号名称集合不存在
			Assert.notEmpty(accountNamelist, "40166");
			
			LottoCustomerVO userVO = new LottoCustomerVO();
			userVO.setType("ACCOUNT_NAME");
			userVO.setValues(accountNamelist);
			userVO.setPageIndex(Constants.NUM_0);
			userVO.setPageSize(accountNamelist.size());
			List<LottoCustomerBO> userList = customerService.findLottoCustomer(userVO).getData();
			//会员查询集合不存在
			Assert.notEmpty(userList, "40167");
			
			List<OperateCouponVO> list = new ArrayList<OperateCouponVO>();
			vo.setRedBalance(vo.getRedValue());
			if(ObjectUtil.isBlank(vo.getOperateLotteryId())){
				vo.setOperateLotteryId(com.hhly.skeleton.base.constants.Constants.getActivityLotteryId());
	    	}
			//计算优惠券时间
			Date date = new Date();
			Short ectivityDay = vo.getEctivityDay();
			if(!ectivityDay.equals(Constants.NO)){
				Date redOverdueTime = DateUtil.addDays(DateUtil.getNeedTime(23,59,59,0,0), ectivityDay.intValue());
				vo.setRedOverdueTime(redOverdueTime);
			}
			vo.setObtainTime(date);
			vo.setUpdateTime(date);
			vo.setCreateBy(getUserCName(session));
			vo.setCreateTime(date);
			
			for (int i = 0; i < userList.size(); i++) {
				LottoCustomerBO userBO = userList.get(i);
				if(accountNamelist.contains(userBO.getAccountName()))
					accountNamelist.remove(userBO.getAccountName());
				int userId = userBO.getId();
				int num = vo.getNum();
				for (int y = 0; y < num; y++) {
					OperateCouponVO voi = (OperateCouponVO)vo.clone();
					voi.setUserId(userId);
					voi.setRedCode(OrderNoUtil.getOrderNo(NumberCode.COUPON));
					list.add(voi);
				}
			}
			String retStr = restTemplate.postForObject(LOTTO_PAY_URL+"recharge/redColor", list,String.class);
			ResultBO<?> ret = gson.fromJson(retStr, new TypeToken<ResultBO<?>>(){}.getType());
			if(ret.isOK()){
				for(OperateCouponVO voi :list){
					senNodeMsg(voi.getUserId(),voi.getRedCode());
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			resultBo = ResultBO.err(accountNamelist);
			resultBo.setMessage(e.getMessage());
			return JsonUtil.object2Json(resultBo);
		}
		return JsonUtil.object2Json(ResultBO.ok(accountNamelist));
	}
	
	
	/**
	 * 修改红包状态为已作废
	 * @param vo
	 * @return
	 */
	@RequestMapping(value = "/cancel",method=RequestMethod.PUT)
	@Authority(privilege=AuthEnum.UPD)
	@ResponseBody
	@ParameterValid
	public  Object cancel(@Valid("updcancel")OperateCouponVO vo){
		return getSaveResult(couponService.updCoupon(vo));
	}
	/**
	 * 红包流水导出
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017-2-6 下午12:19:28
	 * @param response
	 * @param vo
	 * @throws IOException
	 */
	@RequestMapping("excel/red")
    @Authority(privilege =AuthEnum.EXPORT)
    public void exportOpExcel(HttpServletResponse response,TransRedVO vo) throws IOException{
        excel("coupon_record", transMgrService.getTransRedExcel(vo), response);
    }
	
	/**
	 * 批量创建优惠券信息
	 * @param session
	 * @param file
	 * @param vo
	 * @return
	 * @date 2017年5月25日上午11:52:29
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/reissueadd", method = RequestMethod.POST)
	@Authority(privilege=AuthEnum.ADD)
	@ResponseBody
	public Object reissueAdd(@RequestBody ActivityInfoVO vo){		
		return reissueActivityService.addReissue(vo);
	}
	
	
	/**
	 * 红包生成发送消息通知用户
	 * @param UserId
	 */
	public void senNodeMsg(Integer UserId,String redCode){
        MessageModel model = new  MessageModel();
        OperateNodeMsg bodyMsg = new OperateNodeMsg();
        model.setKey(Constants.MSG_NODE_RESEND);
        model.setMessageSource("lotto_cms");
        bodyMsg.setNodeId(2);
        bodyMsg.setNodeData(UserId+";"+redCode);
        model.setMessage(bodyMsg);
        mQProducer.sendMessage(Constants.QUEUE_NAME_MSG_QUEUE, model);
	} 
}
