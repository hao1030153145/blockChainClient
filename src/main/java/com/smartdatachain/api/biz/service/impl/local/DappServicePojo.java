package com.smartdatachain.api.biz.service.impl.local;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.biz.service.BaseService;
import com.smartdatachain.api.biz.service.DappService;
import com.smartdatachain.api.integration.DappDataService;
import com.smartdatachain.api.integration.bo.DappBO;
import com.smartdatachain.api.integration.bo.DataBaseDetailBO;
import com.smartdatachain.api.integration.bo.DataBaseDetailsBO;
import com.smartdatachain.api.web.po.DappDataPO;
import com.smartdatachain.api.web.po.DataBasePO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author haolen
 * @version 1.0
 */
@Service("dappService")
public class DappServicePojo extends BaseService implements DappService {

    @Resource
    private DappDataService dappDataService;

    @Override
    public Integer createDapp(Map<String, Object> param) throws BizException {
        return dappDataService.createDapp(param);
    }

    @Override
    public List<DappBO> getDappByMacAddress(Map<String, Object> param) throws BizException {
        return dappDataService.getDappByMacAddress(param);
    }

    @Override
    public Integer getDataBaseIdByName(String name) throws BizException {
        return dappDataService.getDataBaseIdByName(name);
    }

    @Override
    public Integer getDataToDappCountByParam(Map<String, Object> param) throws BizException {
        return dappDataService.getDataToDappCountByParam(param);
    }

    @Override
    public List<DappDataPO> getDataToDappByParam(Map<String, Object> param) throws BizException {
        return dappDataService.getDataToDappByParam(param);
    }

    @Override
    public List<DataBaseDetailBO> getDataBaseDetailByDappId(Integer dappId) throws BizException {
        return dappDataService.getDataBaseDetailByDappId(dappId);
    }

    @Override
    public List<DataBaseDetailsBO> getDataBaseDetailByDappIdAndDBId(Map<String, Object> param) throws BizException {
        return dappDataService.getDataBaseDetailByDappIdAndDBId(param);
    }

    @Override
    public Integer getDataBaseIdByParam(Map<String, Object> param) throws BizException {
        return dappDataService.getDataBaseIdByParam(param);
    }

    @Override
    public Integer dappRelevantDB(Map<String, Object> param) throws BizException {
        return dappDataService.dappRelevantDB(param);
    }

    @Override
    public Integer updateRelevantDBStatus(Map<String, Object> param) throws BizException {
        return dappDataService.updateRelevantDBStatus(param);
    }

    @Override
    public List<DataBasePO> getDataBaseListByWalletAddress(Map<String, Object> param) throws BizException {
        return dappDataService.getDataBaseListByWalletAddress(param);
    }

    @Override
    public Integer deleteDappById(Integer dappId) throws BizException {
        return dappDataService.deleteDappById(dappId);
    }

    @Override
    public Integer deleteDappRelevantByParam(Map<String, Object> param) throws BizException {
        return dappDataService.deleteDappRelevantByParam(param);
    }
}