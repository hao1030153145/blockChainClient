package com.smartdatachain.api.biz.service.impl.local;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.biz.service.BaseService;
import com.smartdatachain.api.biz.service.WalletService;
import com.smartdatachain.api.integration.WalletDataService;
import com.smartdatachain.api.integration.bo.WalletBO;
import com.smartdatachain.api.integration.bo.WalletDealDetailBO;
import com.smartdatachain.api.web.po.WalletDealDetailPO;
import com.smartdatachain.api.web.po.WalletPO;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author haolen
 * @version 1.0
 */
@Service("walletService")
public class WalletServicePojo extends BaseService implements WalletService {

    @Resource
    private WalletDataService walletDataService;

    @Override
    public WalletPO getWalletDetailByNameAndPwd(Map<String, Object> param) throws BizException {
        return walletDataService.getWalletDetailByNameAndPwd(param);
    }

    @Override
    public WalletBO getWalletByPrivate(Map<String, Object> param) throws BizException {
        return walletDataService.getWalletByPrivate(param);
    }

    @Override
    public Integer createWallet(WalletBO walletBO) throws BizException {
        return walletDataService.createWallet(walletBO);
    }

    @Override
    public Integer updateWalletPasswordByAddress(Map<String, Object> param) throws BizException {
        return walletDataService.updateWalletPasswordByAddress(param);
    }

    @Override
    public WalletPO getMnemonicByPwdAndAddress(Map<String, Object> param) throws BizException {
        return walletDataService.getMnemonicByPwdAndAddress(param);
    }

    @Override
    public WalletPO getWalletKeyByPwdAndAddress(Map<String, Object> param) throws BizException {
        return walletDataService.getWalletKeyByPwdAndAddress(param);
    }

    @Override
    public Integer deleteWalletByAddressAndPwd(Map<String, Object> param) throws BizException {
        return walletDataService.deleteWalletByAddressAndPwd(param);
    }

    @Override
    public List<WalletDealDetailPO> getWalletDealDetailByAddress(Map<String, Object> param) throws BizException {
        return walletDataService.getWalletDealDetailByAddress(param);
    }

    @Override
    public Integer createWalletDealDetail(WalletDealDetailBO walletDealDetailBO) throws BizException {
        return walletDataService.createWalletDealDetail(walletDealDetailBO);
    }

    @Override
    public Integer updateWalletCreditByAddress(Map<String, Object> param) throws BizException {
        return walletDataService.updateWalletCreditByAddress(param);
    }

    @Override
    public WalletPO getWalletDetailByAddressAndPwd(Map<String, Object> param) throws BizException {
        return walletDataService.getWalletDetailByAddressAndPwd(param);
    }
}