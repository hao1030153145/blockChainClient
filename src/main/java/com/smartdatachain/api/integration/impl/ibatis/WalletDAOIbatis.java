package com.smartdatachain.api.integration.impl.ibatis;

import com.jeeframework.logicframework.biz.exception.BizException;
import com.jeeframework.logicframework.integration.dao.DAOException;
import com.jeeframework.logicframework.integration.dao.ibatis.BaseDaoiBATIS;
import com.smartdatachain.api.integration.WalletDataService;
import com.smartdatachain.api.integration.bo.WalletBO;
import com.smartdatachain.api.integration.bo.WalletDealDetailBO;
import com.smartdatachain.api.web.po.WalletDealDetailPO;
import com.smartdatachain.api.web.po.WalletPO;
import org.springframework.context.annotation.Scope;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author hoalen
 * @version 1.0
 */
@Scope("prototype")
@Repository("walletDataService")
public class WalletDAOIbatis extends BaseDaoiBATIS implements WalletDataService {


    @Override
    public WalletPO getWalletDetailByNameAndPwd(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectOne("walletMapper.getWalletDetailByNameAndPwd", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public WalletBO getWalletByPrivate(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectOne("walletMapper.getWalletByPrivate", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer createWallet(WalletBO walletBO) throws BizException {
        try{
            return sqlSessionTemplate.insert("walletMapper.createWallet", walletBO);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public WalletPO getMnemonicByPwdAndAddress(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectOne("walletMapper.getMnemonicByPwdAndAddress", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public WalletPO getWalletKeyByPwdAndAddress(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectOne("walletMapper.getWalletKeyByPwdAndAddress", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer deleteWalletByAddressAndPwd(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.delete("walletMapper.deleteWalletByAddressAndPwd", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer updateWalletPasswordByAddress(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.update("walletMapper.updateWalletPasswordByAddress", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public List<WalletDealDetailPO> getWalletDealDetailByAddress(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectList("walletMapper.getWalletDealDetailByAddress", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer createWalletDealDetail(WalletDealDetailBO walletDealDetailBO) throws BizException {
        try{
            return sqlSessionTemplate.insert("walletMapper.createWalletDealDetail", walletDealDetailBO);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public Integer updateWalletCreditByAddress(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.update("walletMapper.updateWalletCreditByAddress", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }

    @Override
    public WalletPO getWalletDetailByAddressAndPwd(Map<String, Object> param) throws BizException {
        try{
            return sqlSessionTemplate.selectOne("walletMapper.getWalletDetailByAddressAndPwd", param);
        }catch (DataAccessException e){
            throw new DAOException(e);
        }
    }
}