package com.hhly.cms.agent.service.impl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.hhly.cms.agent.service.AgentService;
import com.hhly.cms.base.service.ExcelExportService;
import com.hhly.cms.paymentmgr.service.PaymentService;
import com.hhly.cms.utils.DicUtils;
import com.hhly.skeleton.base.bo.DictionaryBO;
import com.hhly.skeleton.base.bo.PagingBO;
import com.hhly.skeleton.cms.agent.bo.AgentSubMemberBO;
import com.hhly.skeleton.cms.agent.bo.AgentTakenCMBCBankExcelBO;
import com.hhly.skeleton.cms.agent.bo.AgentTransLogBO;
import com.hhly.skeleton.cms.agent.bo.AgentTransTakenExcelBO;
import com.hhly.skeleton.cms.agent.vo.AgentQueryVO;
import com.hhly.skeleton.cms.agent.vo.AgentTransTakenVO;
import com.hhly.skeleton.cms.payment.bo.PayBankBO;
import com.hhly.skeleton.cms.payment.vo.PayBankVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.hhly.cms.utils.RestUtil.httpExchange;

/**
 * @author Tony Wang
 * @version 1.0
 * @desc
 * @date 2018/3/8 17:56
 * @company 益彩网络科技有限公司
 */
@Service
public class AgentServiceImpl implements AgentService{

    @Autowired
    private PaymentService paymentService;
    @Autowired
    private ExcelExportService excelExportService;

    private Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();

    @Value("${lotto_core_url}")
    private String CMS_CORE_URL;

    @Override
    public PagingBO<AgentSubMemberBO> pageDirectMember(AgentQueryVO vo) {
        return gson.fromJson(httpExchange(getAgentUrl()+"direct/page", vo, HttpMethod.POST),
                new TypeToken<PagingBO<AgentSubMemberBO>>(){}.getType());
    }

    @Override
    public PagingBO<AgentSubMemberBO> pageSubMember(AgentQueryVO vo) {
        return gson.fromJson(httpExchange(getAgentUrl()+"sub/page", vo, HttpMethod.POST),
                new TypeToken<PagingBO<AgentSubMemberBO>>(){}.getType());
    }

    @Override
    public PagingBO<AgentTransLogBO> pageTrans(AgentQueryVO vo) {
        return gson.fromJson(httpExchange(getAgentUrl()+"trans/page", vo, HttpMethod.POST),
                new TypeToken<PagingBO<AgentTransLogBO>>(){}.getType());
    }

    @Override
    public PagingBO<AgentTransTakenVO> pageTaken(AgentTransTakenVO vo) {
        return gson.fromJson(httpExchange(getAgentUrl()+"taken/page", vo, HttpMethod.POST),
                new TypeToken<PagingBO<AgentTransTakenVO>>(){}.getType());
    }

    @Override
    public int countTaken(AgentTransTakenVO vo) {
        return gson.fromJson(httpExchange(getAgentUrl()+"taken/count", vo, HttpMethod.POST),
                Integer.class);
    }

    @Override
    public List<AgentTransTakenVO> findTaken(AgentTransTakenVO vo) {
        return gson.fromJson(httpExchange(getAgentUrl()+"taken/find", vo, HttpMethod.POST),
                new TypeToken<List<AgentTransTakenVO>>(){}.getType());
    }

    @Override
    public ByteArrayOutputStream getTakenExcel(AgentTransTakenVO vo) {
        Map<String, List<DictionaryBO>> otherDic = new HashMap<String, List<DictionaryBO>>();
        List<PayBankBO> banks = paymentService.findBank(new PayBankVO());
        List<DictionaryBO> bankDic = DicUtils.toDic(banks, "id", Integer.class, "cname", String.class);
        otherDic.put("takenBank", bankDic);
        List<AgentTransTakenExcelBO> trans = gson.fromJson(httpExchange(getAgentUrl()+"taken/excel", vo, HttpMethod.POST),
                new TypeToken<List<AgentTransTakenExcelBO>>(){}.getType());
        return excelExportService.dataToExeclByStreamDictionary("代理提款流水",trans, otherDic);

    }

    @Override
    public ByteArrayOutputStream getTakenBankExcel(AgentTransTakenVO vo) {
        Map<String, List<DictionaryBO>> otherDic = new HashMap<String, List<DictionaryBO>>();
        List<PayBankBO> banks = paymentService.findBank(new PayBankVO());
        List<DictionaryBO> bankDic = DicUtils.toDic(banks, "id", Integer.class, "cname", String.class);
        otherDic.put("takenBank", bankDic);
        Map<String, List<AgentTakenCMBCBankExcelBO>> trans = gson.fromJson(httpExchange(getAgentUrl()+"taken/bank/excel", vo, HttpMethod.POST),
                new TypeToken<Map<String, List<AgentTakenCMBCBankExcelBO>>>(){}.getType());
        return excelExportService.dataToExeclByStreamDictionary(trans, otherDic);
    }

    @Override
    public ByteArrayOutputStream getTransExcel(AgentQueryVO vo) {
        List<AgentTransLogBO> trans = gson.fromJson(httpExchange(getAgentUrl()+"trans/excel", vo, HttpMethod.POST),
                new TypeToken<List<AgentTransLogBO>>(){}.getType());
        return excelExportService.dataToExeclByStream(trans);
    }

    private String getAgentUrl() {
        return CMS_CORE_URL +"agent/";
    }
}
