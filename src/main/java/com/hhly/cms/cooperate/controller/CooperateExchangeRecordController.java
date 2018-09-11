package com.hhly.cms.cooperate.controller;

import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.cooperate.bo.CooperateExcelExchangeRecordBO;
import com.hhly.cms.utils.Authority;
import com.hhly.cms.utils.DateUtil;
import com.hhly.cmscore.cms.remote.service.ICooperateService;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.base.util.CopyUtil;
import com.hhly.skeleton.cms.cooperate.bo.CooperateExchangeRecordBO;
import com.hhly.skeleton.cms.cooperate.vo.CooperateExchangeRecordVO;
import com.hhly.skeleton.cms.sportmgr.vo.SMGLotteryParamVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * @author lgs on
 * @version 1.0
 * @desc
 * @date 2018/3/11.
 * @company 益彩网络科技有限公司
 */
@Controller
@RequestMapping("/cooperate/exchange-record")
public class CooperateExchangeRecordController extends BaseController {

    @Autowired
    private ICooperateService iCooperateService;

    @Autowired
    private ExcelExportService excelExportService;

    @Authority(privilege = AuthEnum.SEARCH)
    @RequestMapping("/")
    public String index() {
        return "cooperate/exchange_record";
    }


    @Authority(privilege = AuthEnum.SEARCH)
    @RequestMapping("/list")
    @ResponseBody
    public Object list(CooperateExchangeRecordVO vo) {
        vo.setSerialType(1); //只查询商户充值流水
        return iCooperateService.findCooperateExchangeRecordPagingBO(vo);
    }

    @Authority(privilege = AuthEnum.SAVE)
    @RequestMapping("/save")
    @ResponseBody
    public Object save(CooperateExchangeRecordVO vo, HttpSession session) {
        vo.setCreateBy(getUserName(session));
        vo.setModifyBy(getUserName(session));
        return getSaveResult(iCooperateService.saveCooperateExchangeRecord(vo));
    }


    /**
     * 导出excel
     *
     * @throws IOException
     */
    @RequestMapping("/export")
    @Authority(privilege = {AuthEnum.SEARCH})
    public void export(HttpServletResponse response, CooperateExchangeRecordVO vo) throws IOException {
        if (vo.getExchangeRecordBeginTime() == null) {
            vo.setExchangeRecordBeginTime(DateUtil.addDays(new Date(), -30));
        }
        vo.setSerialType(1);
        ByteArrayOutputStream outputStream = getExcelStream(vo);
        excel("Transaction", outputStream, response);
    }

    /**
     * 获取导出excel数据流
     *
     * @param vo
     * @return
     */
    private ByteArrayOutputStream getExcelStream(CooperateExchangeRecordVO vo) {
        List<CooperateExchangeRecordBO> list = iCooperateService.findCooperateExchangeRecordBOList(vo);
        List<CooperateExcelExchangeRecordBO> results = CopyUtil.copyPropertiesList(CooperateExcelExchangeRecordBO.class, list);
        return excelExportService.dataToExeclByStream("Transaction",results);
    }

}
