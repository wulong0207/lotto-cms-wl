package com.hhly.cms.base.rabbitmq;

public class AlarmInfo implements Comparable<AlarmInfo>{
	private int id;

	private String info;

	private long time;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	@Override
	public int compareTo(AlarmInfo info) {
		if(time < info.getTime()){
			return 1;
		}else {
			return -1;
		}
	}
}
