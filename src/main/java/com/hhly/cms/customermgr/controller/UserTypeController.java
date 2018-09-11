package com.hhly.cms.customermgr.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.customermgr.service.UserTypeService;
import com.hhly.cms.utils.Authority;
import com.hhly.skeleton.base.bo.ResultBO;
import com.hhly.skeleton.base.valid.GroupValue;
import com.hhly.skeleton.base.valid.Param;
import com.hhly.skeleton.base.valid.ParameterValid;
import com.hhly.skeleton.base.valid.Valid;
import com.hhly.skeleton.cms.base.vo.StringVO;
import com.hhly.skeleton.cms.customermgr.vo.UserTypeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @desc    
 * @author  cheng chen
 * @date    2017年4月25日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@Controller
@RequestMapping(value = "/customermgr/usertype")
public class UserTypeController extends BaseController {

	@Autowired
	UserTypeService userTypeService;

	/**
	 * 跳转用户类别管理页面
	 * @return
	 * @date 2017年5月25日上午11:46:01
	 * @author cheng.chen
	 */
	@RequestMapping()
	@Authority(privilege = AuthEnum.SEARCH)
	public String index() {
		return "customermgr/user_type";
	}
	
	/**
	 * 查询用户类别集合
	 * @param vo
	 * @return
	 * @date 2017年5月25日上午11:47:57
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/list")
	@Authority(privilege = AuthEnum.SEARCH)
	@ResponseBody
	public Object list(UserTypeVO vo) {
		return userTypeService.findUserTypeList(vo);
	}

	/**
	 * 查询用户类别集合做静态查询数据
	 * @return
	 * @date 2017年5月25日上午11:48:11
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/basic")
	@Authority(privilege = AuthEnum.ALL)
	@ResponseBody
	public Object base() {
		return userTypeService.findBaseUserType();
	}

	/**
	 * 添加用户类别对象
	 * @param session
	 * @param response
	 * @param vo
	 * @return
	 * @date 2017年5月25日上午11:48:33
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/add")
	@Authority(privilege = AuthEnum.ADD)
	@ParameterValid
	@ResponseBody
	public Object add(HttpSession session,HttpServletResponse response, @Valid(GroupValue.ADD)UserTypeVO vo){
		int num = userTypeService.valiUserTypeCode(vo.getCode());
		if(num > 0){
			ResultBO<?> FAIL = new ResultBO<>();
			FAIL.setErrorCode(ResultBO.FAIL_CODE);
			FAIL.setSuccess(0);
			FAIL.setMessage("类别编码重复, 请修改类别编码!!!");
			return FAIL;
		}
		vo.setCreateBy(getUserName(session));
		return getSaveResult(userTypeService.addUserType(vo));
	}

	/**
	 * 绑定标签到会员
	 * @param typeCode
	 * @return
	 * @date 2017年5月25日上午11:48:48
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/addTypeToUser", method = RequestMethod.GET)
	@Authority(privilege = AuthEnum.ADD)
	@ParameterValid({
		@Param(index =0 ,notNull=true,msg="会员类型编码")})
	@ResponseBody
	public Object addTypeToUser(String typeCode){
		userTypeService.addTypeToUser(typeCode);
		return getSaveResult(1);
	}

	/**
	 * 删除用户类别对象
	 * @param id
	 * @return
	 * @date 2017年5月25日上午11:49:01
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/del/{id}" , method = RequestMethod.DELETE)
	@Authority(privilege = AuthEnum.DEL)
	@ResponseBody
	public Object del(@PathVariable(value = "id") String id){
		StringVO vo = new StringVO();
		vo.setStr(id);
		return getSaveResult(userTypeService.delUserTypeByIds(vo));
	}

	/**
	 * 修改用户类别对象
	 * @param session
	 * @param vo
	 * @return
	 * @date 2017年5月25日上午11:49:18
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/upd")
	@Authority(privilege = AuthEnum.UPD)
	@ParameterValid
	@ResponseBody
	public Object upd(HttpSession session, UserTypeVO vo){
		vo.setModifyBy(getUserName(session));
		return getSaveResult(userTypeService.updateUserTypeById(vo));
	}

	/**
	 * 导出用户类别相关会员集合
	 * @param response
	 * @param typeId
	 * @throws IOException
	 * @date 2017年5月25日上午11:49:34
	 * @author cheng.chen
	 */
	@RequestMapping(value = "/excel")
	@Authority(privilege = AuthEnum.EXPORT)
	@ParameterValid({
		@Param(index =0 ,notNull=true,msg="会员类型ID")})
	public void excel(HttpServletResponse response, int typeId) throws IOException{
		ByteArrayOutputStream outputStream = userTypeService.findExcel(typeId);
		excel("user_type", outputStream, response);
	}
	
}
