package com.hhly.cms.operatemgr.service.rss;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("image")
public class Image {
    private String 	url;
    
    private String title;
    
    private String link;
    
	public Image(String url, String title, String link) {
		super();
		this.url = url;
		this.title = title;
		this.link = link;
	}
	
	

	public Image() {
		super();
	}



	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}
    
    
}
