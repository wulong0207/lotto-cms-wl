package com.hhly.cms.base.service.impl;

import java.util.concurrent.atomic.AtomicInteger;

import com.hhly.cms.base.service.IFile;

public abstract class AbstractFile implements IFile {
	// 递增数
	private static final AtomicInteger ASCEND = new AtomicInteger(0);
	/**
	 * 获取递增数
	 * @author jiangwei
	 * @Version 1.0
	 * @CreatDate 2017年4月10日 下午3:54:09
	 * @return
	 */
	protected int getAscend() {
		int num = ASCEND.incrementAndGet();
		if (num == 99999) {
			ASCEND.set(1);
		}
		return num;
	}
}
