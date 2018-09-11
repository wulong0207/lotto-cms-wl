package com.hhly.cms.utils;

public interface WebConstant {
	//用户
	String USERID = "userId";
	String USERNAME = "userName";
	String LOGINNAME = "loginName";
	String LASTLOGINTIME = "lastLoginTime";
	String ROLEID = "roleId";
	String AUTERITY = "autherity";
	// 用户昵称
	String USER_CNAME = "userCname";
	//是否管理员
	String ISADMIN = "isAdmin";

	/**
	 * 胜
	 */
	String WIN = "3";

	/**
	 * 负
	 */
	String LOST = "0";

	/**
	 * 平
	 */
	String DRAW = "1";


	//是否商家
	String ISMERCHANT = "isMerchant";
	
	String REFURL = "refUrl";
	
	String CURJSPPATH = "curJspPath";
	
	String ACCESSERROR = "accessError";
	
	public enum DataSource{
		LOTTERY("lotteryDataSource"),
		BASKETBALL("bbDataSource");
		private String name;  
		private DataSource(String name) {  
	        this.name = name;  
	    }
	    public String getName() {  
	        return name;  
	    }  
	    public void setName(String name) {  
	        this.name = name;
	    }  
	}
	public enum Language{
		TW("TW"),
		EN("EN"),
		KP("KP"),
		IND("IND"),
		TH("TH"),
		VN("VN");
		private String name;  
		private Language(String name) {  
	        this.name = name;  
	    }
	    public String getName() {  
	        return name;  
	    }  
	    public void setName(String name) {  
	        this.name = name;
	    }  
	}
	 
	
}