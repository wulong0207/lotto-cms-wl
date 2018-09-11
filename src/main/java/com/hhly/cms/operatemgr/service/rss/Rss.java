package com.hhly.cms.operatemgr.service.rss;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.hhly.cms.base.xml.AbstractXml;
import com.hhly.skeleton.base.util.DateUtil;
import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.annotations.XStreamAsAttribute;
/**
 * @desc 睿朗请求xml实体类
 * @author jiangwei
 * @date 2017年5月11日
 * @company 益彩网络科技公司
 * @version 1.0
 */
@XStreamAlias("rss")
public  class Rss extends AbstractXml{
	
	
	@XStreamAsAttribute
    private String version = "2.0";
	
	private Channel channel;

	

	
	public String getVersion() {
		return version;
	}


	public void setVersion(String version) {
		this.version = version;
	}


	public Channel getChannel() {
		return channel;
	}

	
	public void setChannel(Channel channel) {
		this.channel = channel;
	}

	public static void main(String[] args) {
		Rss rss  = new Rss();
		Channel channel = new Channel();
		rss.setChannel(channel);
		/*channel.setDescription("0");
		channel.setGenerator("1");
		channel.setLink("3");
		channel.setTitle("4");*/
		//channel.setImage(new Image());
		
		List<Item> items = new ArrayList<>();
		channel.setItems(items);
		Item item = new Item();
		items.add(item);
		item.setAuthor("6");
		item.setDescription("7");
		item.setLink("8");
		item.setPubDate(DateUtil.timeZone(new Date()));
		item.setTitle("9");
		item.setSource(new Source("10", "11"));
		System.out.println(rss.toXml());
		
	}

}
