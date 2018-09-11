package com.hhly.cms.base.xml;

import com.hhly.cms.operatemgr.service.rss.Source;
import com.hhly.cms.utils.XmlUtil;
import com.hhly.skeleton.base.exception.ServiceRuntimeException;
import com.thoughtworks.xstream.XStream;

public class AbstractXml implements IXml {

	private static final XStream  XS_TO_XML;
	
	static{
		XS_TO_XML= XmlUtil.createXStream();
		XS_TO_XML.registerConverter(new Source());
	}
	
	@Override
	public String toXml() {
		return XS_TO_XML.toXML(this);
	}

	@Override
	public <T> T fromXML(String xml) {
		throw new ServiceRuntimeException("请实现");
	}
}
