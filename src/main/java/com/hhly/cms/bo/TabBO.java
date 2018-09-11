package com.hhly.cms.bo;

/**
 * @desc tab 菜单
 * @author jiangwei
 * @date 2017年3月29日
 * @company 益彩网络科技公司
 * @version 1.0
 */
public class TabBO {
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 路径
	 */
	private String url;
	/**
	 * 关闭按钮
	 */
	private boolean showCloseButton;
	/**
	 * 点击刷新
	 */
	private boolean refreshOnClick;
	
	public TabBO(){
		
	}

	public TabBO(String title, String url, boolean showCloseButton, boolean refreshOnClick) {
		super();
		this.title = title;
		this.url = url;
		this.showCloseButton = showCloseButton;
		this.refreshOnClick = refreshOnClick;
	}

	public boolean isRefreshOnClick() {
		return refreshOnClick;
	}

	public void setRefreshOnClick(boolean refreshOnClick) {
		this.refreshOnClick = refreshOnClick;
	}

	public boolean isShowCloseButton() {
		return showCloseButton;
	}

	public void setShowCloseButton(boolean showCloseButton) {
		this.showCloseButton = showCloseButton;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}
