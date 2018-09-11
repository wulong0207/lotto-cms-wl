package com.hhly.cms.operatemgr.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hhly.cms.operatemgr.service.ImageService;
import com.hhly.cmscore.cms.remote.service.IOperateMgrService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.operatemgr.bo.OperateImgBO;
import com.hhly.skeleton.cms.operatemgr.vo.OperateImgVO;

@Service
public class ImageServiceImpl implements ImageService {
	
	@Autowired
    private IOperateMgrService iOperateMgrService;

	@Override
	public PagingBO<OperateImgBO> listOperateImg(OperateImgVO vo) {
		return iOperateMgrService.listOperateImg(vo);
	}

	@Override
	public int addOperateImg(List<OperateImgVO> vos) {
		return iOperateMgrService.addOperateImg(vos);
	}

	@Override
	public int updOperateImg(OperateImgVO vo) {
		return iOperateMgrService.updOperateImg(vo);
	}

	@Override
	public List<OperateImgBO> listOperateImgInfo(List<String> id) {
		return iOperateMgrService.listOperateImgInfo(id);
	}

	@Override
	public int delOperateImg(List<String> id) {
		return iOperateMgrService.delOperateImg(id);
	}

	@Override
	public int updOperateImgMove(OperateImgVO vo) {
		return iOperateMgrService.updOperateImgMove(vo);
	}

	@Override
	public OperateImgBO getOperateImg(OperateImgVO vo) {
		return iOperateMgrService.getOperateImg(vo);
	}
}
