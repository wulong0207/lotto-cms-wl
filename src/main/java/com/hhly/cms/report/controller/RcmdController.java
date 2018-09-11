package com.hhly.cms.report.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.utils.Authority;
import com.hhly.report.remote.service.IRcmdService;
import com.hhly.skeleton.base.util.CopyUtil;
import com.hhly.skeleton.cms.rcmdmgr.bo.RcmdExeclBO;
import com.hhly.skeleton.cms.rcmdmgr.bo.RcmdPayExeclBO;
import com.hhly.skeleton.cms.rcmdmgr.vo.RcmdRecordVO;
import com.hhly.skeleton.cms.rcmdmgr.vo.RcmdVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2018/8/11.
 * @company 益彩网络科技有限公司
 */
@Controller
@RequestMapping(value = "/rcmd")
public class RcmdController extends BaseController {

    @Autowired
    private IRcmdService iRcmdService;

    @Autowired
    private ExcelExportService excelExportService;

    @RequestMapping("")
    @Authority(privilege = AuthEnum.SEARCH)
    public String index() {
        return "rcmd/rcmd";
    }

    @RequestMapping("/pay")
    @Authority(privilege = AuthEnum.SEARCH)
    public String payList() {
        return "rcmd/rcmdPay";
    }

    /**
     * 推单数据统计
     *
     * @param vo
     * @return
     */
    @RequestMapping("/list")
    @Authority(privilege = AuthEnum.SEARCH)
    public Object rcmdList(RcmdVO vo) {
        return iRcmdService.findRcmdList(vo);
    }


    /**
     * 推单数据统计
     *
     * @param vo
     * @return
     */
    @RequestMapping("/excel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void rcmdExcelList(RcmdVO vo, HttpServletResponse response) throws IOException {
        List<RcmdExeclBO> list = CopyUtil.copyPropertiesList(RcmdExeclBO.class, iRcmdService.findRcmdExcelList(vo));
        excel("推单统计", excelExportService.dataToExeclByStream(list), response);
    }

    /**
     * 推单数据统计
     *
     * @param vo
     * @return
     */
    @RequestMapping("/pay-excel")
    @Authority(privilege = AuthEnum.EXPORT)
    public void rcmdPayExcelList(RcmdVO vo, HttpServletResponse response) throws IOException {
        List<RcmdPayExeclBO> list = CopyUtil.copyPropertiesList(RcmdPayExeclBO.class, iRcmdService.findRcmdExcelList(vo));
        excel("推单付费统计", excelExportService.dataToExeclByStream(list), response);
    }
}
