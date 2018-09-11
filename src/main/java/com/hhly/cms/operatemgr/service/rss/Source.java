package com.hhly.cms.operatemgr.service.rss;

import com.thoughtworks.xstream.annotations.XStreamAlias;
import com.thoughtworks.xstream.converters.Converter;
import com.thoughtworks.xstream.converters.MarshallingContext;
import com.thoughtworks.xstream.converters.UnmarshallingContext;
import com.thoughtworks.xstream.io.HierarchicalStreamReader;
import com.thoughtworks.xstream.io.HierarchicalStreamWriter;

@XStreamAlias("source")
public class Source implements Converter {

	private String url;

	private String value;

	public Source(String url, String value) {
		this.url = url == null ? "" : url;
		this.value = value == null ? "" : value;
	}

	public Source() {

	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public boolean canConvert(Class type) {
		return type.equals(this.getClass());
	}

	@Override
	public void marshal(Object source, HierarchicalStreamWriter writer, MarshallingContext context) {
		Source s = (Source) source;
		writer.addAttribute("url", s.getUrl());
		writer.setValue(s.getValue());
	}

	@Override
	public Object unmarshal(HierarchicalStreamReader reader, UnmarshallingContext context) {
		Source s = new Source(reader.getAttribute("url"), reader.getValue());
		return s;
	}

}
