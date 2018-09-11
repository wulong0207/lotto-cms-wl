package com.hhly.cms.base.common;
/**
 * 
 * @author jiangwei
 * @Version 1.0
 * @CreatDate 2016-11-30 上午10:40:20
 * @Desc 权限枚举类
 * @ModifyBy lgs  增加枚举增加code,对应数据库字典表code
 */
public enum AuthEnum {
	ALL("无需权限控制","无需权限控制"),
	ADD("1","添加"),
	DEL("2","删除"),
	UPD("3","修改"),
	SEARCH("4","查询"),
	SAVE("5","保存"),
	EXPORT("6","导出"),
	FORBIDDEN("7","禁用"),
	CHECK("8","审核"),
	CANCEL_CHECK("9","取消审核"),
	UPLOAD("10","上传"),
	MANUAL_EXECUTE("11","手动执行"),
	RESUPPLY("12","补单"),
	MANUAL_RECHARGE("13","人工充值"),
	SETTLE("14","结算"),
	DISPATCH("15","派发"),
	REJECT("16","驳回"),
	APPROVE("17","审核通过"),
	RELEASE("18","发布"),
	SUBMIT_AUDIT("19","提交审核"),
	CANCEL_ORDER("20","撤单"),
	CHECK_TAKEN("21","审核提款"),
	PROCESS_TAKEN("22","处理提款"),
	ALREADY_PROCESS_TAKEN("23","已处理提款"),
	TAKEN_CONFIG("24","提款配置"),
	PROCESS_SUCCESS_TAKEN("25","银行处理成功提款"),
	RESET_ARTICLE("26","重新生成文章"),
	;
	AuthEnum(String code,String desc){
		this.code=code;
		this.desc = desc;
	}
	private String code;
	private String desc;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDesc() {
		return desc;
	}
	public void setDesc(String desc) {
		this.desc = desc;
	}
	
}
