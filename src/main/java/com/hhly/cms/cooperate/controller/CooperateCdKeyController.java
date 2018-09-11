package com.hhly.cms.cooperate.controller;

import com.hhly.cms.base.annotation.DeleteBatchAssignCache;
import com.hhly.cms.base.common.AuthEnum;
import com.hhly.cms.base.controller.BaseController;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.cooperate.bo.CooperateExcelCdKeyLotteryRecodeListBO;
import com.hhly.cms.cooperate.bo.CooperateExcelExchangeRecordBO;
import com.hhly.cms.ticket.service.ITicketChannelService;
import com.hhly.cms.utils.Authority;

import com.hhly.cms.utils.JsonUtil;
import com.hhly.cms.utils.StringUtil;
import com.hhly.cmscore.cms.remote.service.ICooperateService;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.common.LotteryEnum;
import com.hhly.skeleton.base.common.cache.cooperate.CoOperateCacheEnum;
import com.hhly.skeleton.base.exception.Assert;
import com.hhly.skeleton.base.util.CopyUtil;
import com.hhly.skeleton.base.util.DateUtil;
import com.hhly.skeleton.base.util.ExcelUtil;
import com.hhly.skeleton.base.util.ObjectUtil;
import com.hhly.skeleton.cms.cooperate.bo.CooperateCdKeyLotteryRecodeListBO;
import com.hhly.skeleton.cms.cooperate.bo.CooperateExchangeRecordBO;
import com.hhly.skeleton.cms.cooperate.vo.CooperateCdkeyVO;
import com.hhly.skeleton.cms.cooperate.vo.CooperateExchangeRecordVO;
import com.qiniu.util.Json;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.method.P;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
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
@RequestMapping("/cooperate/cdkey")
public class CooperateCdKeyController extends BaseController {

    @Autowired
    private ICooperateService iCooperateService;


    @Autowired
    private ExcelExportService excelExportService;

    @Autowired
    private ITicketChannelService ticketChannelService;

    /**
     * 首页
     *
     * @return
     */
    @Authority(privilege = AuthEnum.SEARCH)
    @RequestMapping("/")
    public String index() {
        return "cooperate/cdkey";
    }

    /**
     * 上传excel
     *
     * @param file
     * @return
     */
    @Authority(privilege = {AuthEnum.SAVE, AuthEnum.UPLOAD})
    @RequestMapping("/upload")
    @ResponseBody
    public Object upload(@RequestParam MultipartFile file, CooperateCdkeyVO vo, HttpSession session) throws IOException {
        Assert.notNull(null != file, "请选择一个Excel文件");
        String originalFilename = file.getOriginalFilename();
        Assert.isTrue(originalFilename.endsWith(".xls") || originalFilename.endsWith(".xlsx"), "请上传excel文件");

        List<List<String>> excels = ExcelUtil.readExcel(file);
        String pattern = "[0-9]{3}[1-9]|[0-9]{2}[1-9][0-9]{1}|[0-9]{1}[1-9][0-9]{2}|[1-9][0-9]{3}";
        List<CooperateCdkeyVO> vos = new ArrayList<>();
        if (excels != null && excels.size() > 0) {
            excels.forEach(item -> {

                String v = item.get(item.size() - 1);

                if (ObjectUtil.isBlank(v) || v.equals("结束日期(*)")) {
                    return;
                } else {
                    //金额不为2的不导入
                    if (item.get(2) != null && !item.get(2).equals("2")) {
                        return;
                    }
                    CooperateCdkeyVO temp = new CooperateCdkeyVO();
                    temp.setTicketChannel(vo.getTicketChannel());
                    temp.setLotteryCode(vo.getLotteryCode());
                    temp.setLottoCdkey(item.get(0) + item.get(1));
                    temp.setStauts(2);
                    temp.setOverTime(DateUtil.convertStrToDate(item.get(4), DateUtil.DATE_FORMAT));
                    temp.setModifyBy(getUserName(session));
                    temp.setCreateBy(getUserName(session));
                    vos.add(temp);
                }
            });
        }
        if( vos.size() <1){
            return JsonUtil.object2Json(getSaveResult(0));
        }
        return JsonUtil.object2Json(getSaveResult(iCooperateService.saveCooperateCdKeyList(vos)));
    }

    @Authority(privilege = AuthEnum.SEARCH)
    @RequestMapping("/list")
    @ResponseBody
    public Object list(CooperateCdkeyVO vo) {
        if (vo.getChannelId() == null || vo.getChannelId().equals("9999")) {
            vo.setChannelId("");
        }
        return iCooperateService.findCooperateCdKeyLotteryNumBOList(vo);
    }

    @Authority(privilege = AuthEnum.SEARCH)
    @RequestMapping("/recode-list")
    @ResponseBody
    public Object recodeList(CooperateCdkeyVO vo) {
        if (vo.getChannelId().equals("9999") || vo.getChannelId().equals("")) {
            vo.setChannelId(null);
        }
        return iCooperateService.findCooperateCdKeyLotteryRecodeListBOPagingBO(vo);
    }


    /**
     * 导出excel
     *
     * @throws IOException
     */
    @RequestMapping("/export")
    @Authority(privilege = {AuthEnum.SEARCH})
    public void export(HttpServletResponse response, CooperateCdkeyVO vo) throws IOException {
        ByteArrayOutputStream outputStream = getExcelStream(vo);
        excel("Exchange", outputStream, response);
    }


    /**
     * 获取导出excel数据流
     *
     * @param vo
     * @return
     */
    private ByteArrayOutputStream getExcelStream(CooperateCdkeyVO vo) {
        List<CooperateCdKeyLotteryRecodeListBO> list = iCooperateService.findLotteryRecodeList(vo);
        List<CooperateExcelCdKeyLotteryRecodeListBO> results = new ArrayList<>();
        for (CooperateCdKeyLotteryRecodeListBO bo : list) {
            CooperateExcelCdKeyLotteryRecodeListBO temp = new CooperateExcelCdKeyLotteryRecodeListBO();
            temp.setId(bo.getId());
            temp.setAccount(bo.getAccount());
            temp.setChannelId(bo.getChannelId());
            temp.setCreateTime(bo.getCreateTime());
            temp.setExchangeChannel(bo.getExchangeChannel());
            temp.setExchangeOverTime(bo.getExchangeOverTime());
            temp.setExchangeRecordTime(bo.getExchangeRecordTime());
            if (!com.hhly.skeleton.base.util.StringUtil.isBlank(bo.getLotteryCode())) {
                temp.setLotteryCode(LotteryEnum.Lottery.getLottery(Integer.valueOf(bo.getLotteryCode())).getDesc());
            }
            if (!com.hhly.skeleton.base.util.StringUtil.isBlank(bo.getChannelId())) {
                List<DictionaryBO> dictionaryBOS = iCooperateService.selectChannelNameDictBO();
                for (DictionaryBO dictionaryBO : dictionaryBOS) {
                    if (dictionaryBO.getId().equals(bo.getChannelId())) {
                        temp.setChannelId(dictionaryBO.getText());
                        break;
                    }
                }
            }
            if (!com.hhly.skeleton.base.util.StringUtil.isBlank(bo.getExchangeChannel())) {
                List<DictionaryBO> ticketDicts = ticketChannelService.listDrawerIdNameDicticonary(null);
                for (DictionaryBO dictionaryBO : ticketDicts) {
                    if (dictionaryBO.getId().equals(bo.getExchangeChannel())) {
                        temp.setExchangeChannel(dictionaryBO.getText());
                        break;
                    }
                }
            }

            temp.setLottoCdkey(bo.getLottoCdkey());
            temp.setModifyTime(bo.getModifyTime());
            temp.setMyCdKey(bo.getMyCdKey());
            temp.setStauts(bo.getStauts());
            temp.setUserId(bo.getUserId());
            results.add(temp);
        }


        return excelExportService.dataToExeclByStream("Exchange", results);
    }

    /**
     * 更新过期兑换码
     *
     * @return
     */
    @Authority(privilege = AuthEnum.ALL)
    @RequestMapping(value = "/update-over-cdkey")
    @ResponseBody
    public Object updateOverTimeCdKey() {
        return getResultSuccess(iCooperateService.updateOverTimeCdKey());
    }
}
